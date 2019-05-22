package com.fxbank.tpp.bocm.nettty;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.dto.bocm.REP_BASE;
import com.fxbank.tpp.bocm.dto.bocm.REP_ERROR;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Description: 交行来账应答组包
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:16:56
 */
@Component("bocmPackConvOutHandler")
@Sharable
public class BocmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(BocmPackConvOutHandler.class);

	@Resource
	private LogPool logPool;

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		MyLog myLog = this.logPool.get();
		@SuppressWarnings("unchecked")
		Map<String, DataTransObject> dtoMap = (Map<String, DataTransObject>) msg;
		DataTransObject dto = dtoMap.get("repDto");
		DataTransObject reqDto = dtoMap.get("reqDto");
		REP_BASE repDto = null;
		String rspTyp = null;
		String rspCode = null;
		String rspMsg = null;
		if (dto.getStatus().equals(DataTransObject.SUCCESS)) { // 交易成功
			myLog.error(logger, "生成成功应答报文");
			repDto = (REP_BASE) dtoMap.get("repDto");
			rspTyp = "N";	//成功
			rspCode = "FX0000";
			rspMsg = "交易成功";
		} else { // 交易失败
			myLog.error(logger, "生成错误应答报文");
			if (dto instanceof REP_BASE) {
				repDto = (REP_BASE) dto;
			} else {
				repDto = new REP_ERROR();
			}
			rspTyp = "E";	//失败
			rspCode = (dto.getRspCode().equals("000000")||dto.getRspCode().equals(SysTradeExecuteException.CIP_E_999999)) ? "FX9999" : dto.getRspCode();
			rspMsg = dto.getRspMsg();
		}

		repDto.setTmsgTyp(rspTyp);
		repDto.setTrspCd(rspCode);
		repDto.setTrspMsg(rspMsg);
		repDto.setRlogNo(String.format("%06d%08d", reqDto.getSysDate()%1000000,reqDto.getSysTraceno()));

		//生成MAC TODO
		String mac = "FFFFFFFFFFFFFFFF";

		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(repDto));
		fixPack.append(mac);

		ctx.writeAndFlush(fixPack.toString(),promise);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "生成应答报文异常",cause);
		ctx.close();
	}

}
