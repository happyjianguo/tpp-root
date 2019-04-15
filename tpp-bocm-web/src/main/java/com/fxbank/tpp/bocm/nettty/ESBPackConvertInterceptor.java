package com.fxbank.tpp.bocm.nettty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.config.ESBConfig;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_RET;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;
import com.google.gson.JsonParser;

import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;

/**
 * @ClassName: ESBPackConvertInterceptor
 * @Description: ESB通讯与报文处理
 * @author ZhouYongwei
 * @date 2018年4月2日 下午2:14:22
 * 
 */
@Component
@ConditionalOnProperty(name = "esb.enable", havingValue = "true")
@ConfigurationProperties(prefix="esb")  
public class ESBPackConvertInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(ESBPackConvertInterceptor.class);
	
	@Resource
	private LogPool logPool;
	
	@Resource
	private ESBConfig esbConfig;
	
	@Resource
	private HisuTSSCAPI hisuTSSCAPI;

	/** 
	* @Title: afterCompletion 
	* @Description: 组包发送 
	*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MyLog logUtil = logPool.get();
		DataTransObject dto = (DataTransObject) request.getAttribute("REPDTO");
		REP_BASE repDto=null;
		logUtil.info(logger, "交易结果=["+dto.getStatus()+"]");
		if(dto.getStatus().equals(DataTransObject.SUCCESS)){	//交易成功
			logUtil.info(logger, "生成成功应答报文");
			repDto = (REP_BASE) request.getAttribute("REPDTO");
			repDto.getRepSysHead().setRetStatus("S");	
		}else{																			//交易失败
			logUtil.info(logger, "生成错误应答报文");
			repDto = new REP_BASE();
			repDto.setRepSysHead(new REP_SYS_HEAD());
			repDto.getRepSysHead().setRetStatus("F");	
		}
		
		REQ_BASE reqBase = (REQ_BASE) request.getAttribute("REQBASE");
		REQ_SYS_HEAD reqSysHead = reqBase.getReqSysHead();
		REP_SYS_HEAD repSysHead = repDto.getRepSysHead();
		
		logUtil.info(logger, "生成应答报文头");
		String rspCode = dto.getRspCode();
		String rspMsg = dto.getRspMsg();
		repSysHead.getRet().add(new REP_RET(rspCode, rspMsg));
		repSysHead.setServiceId(reqSysHead.getServiceId());
		repSysHead.setSceneId(reqSysHead.getSceneId());
		repSysHead.setSourceType(reqSysHead.getSourceType());
		repSysHead.setPrvdSysId(CIP.SYSTEM_ID);
		repSysHead.setRunDate(String.valueOf(dto.getSysDate()==null?0:dto.getSysDate()));
		repSysHead.setTranDate(repSysHead.getRunDate());
		repSysHead.setTranTimestamp(String.valueOf(dto.getSysTime()==null?0:dto.getSysTime()));
		repSysHead.setSeqNo(reqSysHead.getSeqNo());
		repSysHead.setReference(String.valueOf(dto.getSysTraceno()==null?0:dto.getSysTraceno()));
		repSysHead.setFilePath(reqSysHead.getFilePath());

		String repBody = null;
		try {
			repBody = JsonUtil.toJson(repDto);
		} catch (RuntimeException e) {
			logUtil.error(logger, "生成应答报文失败", e);
			return;
		}

		request.setAttribute("RspBody", repBody);
		logUtil.info(logger, "发送HTTP报文体=[" + repBody + "]");
		response.getWriter().print(repBody);
	}


	/** 
	* @Title: preHandle 
	* @Description: 接收拆包
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MyLog logUtil = logPool.get();
		
		StringBuffer reqBody = new StringBuffer();
		String s = "";
		ServletInputStream sis = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(sis, "UTF-8"));
		while ((s = br.readLine()) != null) {
			reqBody.append(s);
		}
		logUtil.info(logger, "收到HTTP报文体=[" + reqBody.toString() + "]");
		String txCode = null;
		REQ_BASE reqBase = null;
		try {
			reqBase = JsonUtil.toBean(reqBody.toString(), REQ_BASE.class);
			txCode = "REQ_" + reqBase.getReqSysHead().getServiceId() + reqBase.getReqSysHead().getSceneId();
		} catch (Exception e) {
			logUtil.error(logger, "解析交易代码失败", e);
			return false;
		}
		request.setAttribute("REQBASE", reqBase);
		logUtil.info(logger, "交易代码=[" + txCode + "]");
		// 服务码第5位为1需要检查MAC
		if ("1".equals(reqBase.getReqSysHead().getServiceId().substring(4, 5))) {
			String macValue = reqBase.getReqSysHead().getMacValue();
			if (macValue == null || "".equals(macValue.trim())) {
				logUtil.error(logger, "MAC【" + macValue + "】值为空");
				sendErrorResponse(request,response,"CIP_E_000003","MAC值不能为空");
				return false;
			}
			String[] macArray = macValue.split("\\|");
			if (macArray.length < 4) {
				logUtil.error(logger, "MAC【" + macValue + "】值字段个数不正确");
				sendErrorResponse(request,response,"CIP_E_000003","MAC值字段个数不正确");
				return false;
			}
			String body = new JsonParser().parse(reqBody.toString()).getAsJsonObject().get("BODY").toString();
			try {
				boolean macFlag = verifyMAC(macArray[0], macArray[1], macArray[2], body.getBytes(), macArray[3]);
				if (!macFlag) {
					logUtil.error(logger, "MAC【" + macValue + "】检查失败");
					sendErrorResponse(request,response,"CIP_E_000003","MAC检查失败");
					return false;
				}
			} catch (SysTradeExecuteException e) {
				logUtil.error(logger, "MAC校验失败", e);
				sendErrorResponse(request,response,"CIP_E_000003","MAC检查失败");
				return false;
			}
		}

		DataTransObject dto = null;
		Class<?> esbClass = null;
		String className = esbConfig.getDtoPath()+"." + txCode;
		try {
			esbClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			logUtil.error(logger, "类文件[" + className + "]未定义",e);
			return false;
		}

		try {
			dto = (DataTransObject) JsonUtil.toBean(reqBody.toString(), esbClass);
			logUtil.info(logger, "交易描述=[" + dto.getTxDesc() + "]");
			dto.setTxCode(txCode);
			dto.setSourceType(reqBase.getReqSysHead().getSourceType());
			try {
				dto.setOthDate(Integer.parseInt(reqBase.getReqSysHead().getTranDate()));
			} catch (Exception e) {
				dto.setOthDate(0);
			}
			dto.setOthTraceno(reqBase.getReqSysHead().getSeqNo());
		} catch (RuntimeException e) {
			logUtil.error(logger, "解析报文失败[" + reqBody + "]",e);
			return false;
		}

		request.setAttribute("REQDTO", dto);
		return true;
	}
	

	/**
	 * 检查MAC
	 * @param macDeginId
	 * @param macNodeId
	 * @param macKeyModelId
	 * @param dataToMAC
	 * @param MAC
	 * @return
	 * @throws SysTradeExecuteException
	 */
	private boolean verifyMAC(String macDeginId,String macNodeId,String macKeyModelId,byte[] dataToMAC,String MAC) throws SysTradeExecuteException {
		MyLog logUtil = logPool.get();
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.verifyHashDataMACBySpecKeyBytes(macDeginId, macNodeId,
					macKeyModelId, 1, 2, dataToMAC, dataToMAC.length,MAC);
			if (result.getErrCode() < 0) {
				logUtil.error(logger, "调用加密平台检查MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return true;
		} catch (Exception e) {
			logUtil.error(logger, "调用加密平台计检查MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}
	
	/**
	 * 返回错误信息
	 * @param request
	 * @param response
	 * @param errorCode
	 * @param errorMsg
	 * @throws IOException
	 */
	private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,String errorCode,String errorMsg) throws IOException{
		MyLog logUtil = logPool.get();
		
		REQ_BASE reqBase = (REQ_BASE)request.getAttribute("REQBASE");
		REP_BASE repDto = new REP_BASE();
		repDto.setRepSysHead(new REP_SYS_HEAD());
		repDto.getRepSysHead().setRetStatus("F");
		
		REQ_SYS_HEAD reqSysHead = reqBase.getReqSysHead();
		REP_SYS_HEAD repSysHead = repDto.getRepSysHead();
		
		repSysHead.getRet().add(new REP_RET(errorCode, errorMsg));
		repSysHead.setServiceId(reqSysHead.getServiceId());
		repSysHead.setSceneId(reqSysHead.getSceneId());
		repSysHead.setSourceType(reqSysHead.getSourceType());
		repSysHead.setPrvdSysId(CIP.SYSTEM_ID);
		repSysHead.setRunDate(String.valueOf(reqBase.getSysDate()==null?0:reqBase.getSysDate()));
		repSysHead.setTranDate(repSysHead.getRunDate());
		repSysHead.setTranTimestamp(String.valueOf(reqBase.getSysTime()==null?0:reqBase.getSysTime()));
		repSysHead.setSeqNo(reqSysHead.getSeqNo());
		repSysHead.setReference(String.valueOf(reqBase.getSysTraceno()==null?0:reqBase.getSysTraceno()));
		repSysHead.setFilePath(reqSysHead.getFilePath());

		String repBody = null;
		try {
			repBody = JsonUtil.toJson(repDto);
		} catch (RuntimeException e) {
			logUtil.error(logger, "生成应答报文失败", e);
			return;
		}

		request.setAttribute("RspBody", repBody);
		logUtil.info(logger, "发送HTTP报文体=[" + repBody + "]");
		response.getWriter().print(repBody);

	}

}
