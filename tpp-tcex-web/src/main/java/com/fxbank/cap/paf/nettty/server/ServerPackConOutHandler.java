package com.fxbank.cap.paf.nettty.server;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS;
import com.fxbank.cap.paf.utils.JAXBUtils;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

@Component("serverPackConOutHandler")
@Sharable
public class ServerPackConOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ServerPackConOutHandler.class);
	
	@Resource
	private LogPool logPool;
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		MyLog logUtil = logPool.get();
		Map<String, DataTransObject> dtoMap = (Map<String, DataTransObject>) msg;
		DataTransObject dto = dtoMap.get("repDto");
		SER_REP_BDC repDto = null;
		String rspCode = null;
		String rspMsg = null;
		String packHeader = null;
		String txStatus = null;
		if (dto.getStatus().equals(DataTransObject.SUCCESS)) { // 交易成功
			logUtil.error(logger, "生成成功应答报文");
			repDto = (SER_REP_BDC) dtoMap.get("repDto");
			rspCode = "00000";
			rspMsg = "成功";
			txStatus = "0";		//技术层面成功
		} else { // 交易失败
			logUtil.error(logger, "生成错误应答报文");
			if (dto instanceof SER_REP_BDC) {
				repDto = (SER_REP_BDC) dto;
			} else {
				repDto = new SER_REP_BDC();
			}
			if(dto.getRspCode().startsWith("CIP_E_")){
				rspCode = PAF.BANK_CODE+"99999";
				txStatus = "1";		//技术层面失败
			}else{
				rspCode = dto.getRspCode();
				txStatus = "0";		//技术层面成功
			}
			rspMsg = dto.getRspMsg();
		}
		
		FIELDS repHead = repDto.getHead();
		repHead.getField().add(new FIELD("TxStatus",txStatus)); 
		repHead.getField().add(new FIELD("RtnCode",rspCode));
		repHead.getField().add(new FIELD("RtnMessage",rspMsg));

		repHead.getField().add(new FIELD("BkTotNum",String.valueOf(repDto.getBkTotNum()==null?0:repDto.getBkTotNum())));
		repHead.getField().add(new FIELD("BkRecNum",String.valueOf(repDto.getBkRecNum()==null?0:repDto.getBkRecNum())));

		if(dtoMap.containsKey("reqDto")){
			SER_REQ_DATA reqDto = (SER_REQ_DATA) dtoMap.get("reqDto");
			packHeader = reqDto.getPackHeader();
			Map<String,Object> reqHead = reqDto.getHead();
			repHead.getField().add(new FIELD("ReceiveDate",String.valueOf(reqDto.getSysDate())));
			repHead.getField().add(new FIELD("ReceiveTime",String.valueOf(reqDto.getSysTime())));
			repHead.getField().add(new FIELD("ReceiveSeqNo",String.valueOf(reqDto.getSysTraceno())));
			repHead.getField().add(new FIELD("ReceiveNode",PAF.BANK_CODE));
			repHead.getField().add(new FIELD("TxCode",reqDto.getTxCode()));
			repHead.getField().add(new FIELD("BDCDate",String.valueOf(reqHead.get("BDCDate"))));
			repHead.getField().add(new FIELD("BDCTime",String.valueOf(reqHead.get("BDCTime"))));
			repHead.getField().add(new FIELD("BDCSeqNo",String.valueOf(reqHead.get("BDCSeqNo"))));
			repHead.getField().add(new FIELD("SendNode",String.valueOf(reqHead.get("SendNode"))));
		}

		String repBody = null;
		try {
			byte[] xmlBytes = JAXBUtils.marshal(repDto);
			repBody = new String(xmlBytes, PAF.ENCODING);
		} catch (Exception e) {
			logUtil.error(logger, "生成应答报文失败", e);
			throw e;
		}
		
		StringBuffer pack = new StringBuffer();
		pack.append(PAF.BANK_CODE+packHeader.substring(6));				//获取通讯报文头
		pack.append(repBody);
		
		logUtil.info(logger, "返回报文体=[" + pack.toString() + "]");
		ctx.writeAndFlush(pack.toString(), promise);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		MyLog logUtil = logPool.get();
		logUtil.error(logger, "PackConvertOutHandler process error!", cause);
		FIELDS repDto = new FIELDS();
		repDto.getField().add(new FIELD("TxStatus","1"));	//失败
		String xml=null;
		try {
			byte[] xmlBytes = JAXBUtils.marshal(repDto);
			xml = new String(xmlBytes, "UTF-8");
		} catch (JAXBException | UnsupportedEncodingException e) {
			logUtil.error(logger, "生成错误应答报文失败", e);
		}
		logUtil.error(logger, "错误应答报文=["+ xml+"]");
		ctx.writeAndFlush(PAF.BANK_CODE+"0000000000"+xml);
		ctx.close();
	}
}
