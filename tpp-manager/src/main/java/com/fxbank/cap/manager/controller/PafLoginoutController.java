package com.fxbank.cap.manager.controller;

import javax.xml.bind.JAXBException;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.service.ILoginoutService;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PafLoginoutController {
	private static Logger logger = LoggerFactory.getLogger(PafLoginoutController.class);

	@Reference(version = "1.0.0")
	private ILoginoutService loginoutService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	//  http://127.0.0.1:5000/cap/paf/login.do
//	@RequestMapping({ "/paf/login.do" })
//	public void login() throws PafTradeExecuteException, JAXBException {
//		MyLog myLog = new MyLog();
//
//		Integer sysDate = publicService.getSysDate("CIP");
//		Integer sysTime = publicService.getSysTime();
//		Integer sysTraceno = publicService.getSysTraceno();
//
//		CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog,sysDate,sysTime,sysTraceno);
//
//		myLog.info(logger,"发起登录请求");
//
//		CLI_REP_DATA pack = loginoutService.login(reqData);
//		if (!pack.getHead().get("TxStatus").equals("0") || !pack.getHead().get("RtnCode").equals("00000")) {
//			myLog.error(logger, "登录失败["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
//		}
//		myLog.info(logger, "登录成功["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
//	}

	@RequestMapping("/paf/login")
	@ResponseBody
	public String login(){
		MyLog myLog = new MyLog();
		JSONObject json=new JSONObject();
		try {
			Integer sysDate = publicService.getSysDate("CIP");
			Integer sysTime = publicService.getSysTime();
			Integer sysTraceno = publicService.getSysTraceno();

			CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog,sysDate,sysTime,sysTraceno);

			myLog.info(logger,"发起登录请求");

			CLI_REP_DATA pack = null;
			pack = loginoutService.login(reqData);
			if (!pack.getHead().get("TxStatus").equals("0") || !pack.getHead().get("RtnCode").equals("00000")) {
				json.put("success",false);
				json.put("message","登录失败");
				json.put("retMessage",pack.getHead().get("RtnMessage"));
				myLog.error(logger, "登录失败["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
			}else{
				json.put("success",true);
				json.put("message","登录成功");
                json.put("retMessage",pack.getHead().get("RtnMessage"));
				myLog.info(logger, "登录成功["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
			}
		} catch (PafTradeExecuteException e) {
//			e.printStackTrace();
			json.put("success",false);
			json.put("message","登录时系统报错，错误信息："+e);
		} catch (JAXBException e) {
//			e.printStackTrace();
			json.put("success",false);
			json.put("message","登录时系统报错，错误信息："+e);
		} catch (Exception e){
			json.put("success",false);
			json.put("message","登录时系统报错，错误信息："+e);
		}
		return json.toString();
	}

	//  http://127.0.0.1:5000/cap/paf/logout.do
//	@RequestMapping({ "/paf/logout.do" })
//	public void logout() throws PafTradeExecuteException, JAXBException {
//		MyLog myLog = new MyLog();
//
//		Integer sysDate = publicService.getSysDate("CIP");
//		Integer sysTime = publicService.getSysTime();
//		Integer sysTraceno = publicService.getSysTraceno();
//
//		myLog.info(logger,"发起退出请求");
//
//		CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog,sysDate,sysTime,sysTraceno);
//
//		CLI_REP_DATA pack = loginoutService.logout(reqData);
//		if (!pack.getHead().get("TxStatus").equals("0") || !pack.getHead().get("RtnCode").equals("00000")) {
//			myLog.error(logger, "退出失败["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
//		}
//		myLog.info(logger, "退出成功["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
//	}

	@RequestMapping("/paf/logout")
	@ResponseBody
	public String logout(){
		MyLog myLog = new MyLog();
		JSONObject json=new JSONObject();
		try {
			Integer sysDate = publicService.getSysDate("CIP");
			Integer sysTime = publicService.getSysTime();
			Integer sysTraceno = publicService.getSysTraceno();

			myLog.info(logger,"发起退出请求");

			CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog,sysDate,sysTime,sysTraceno);

			CLI_REP_DATA pack = null;
			pack = loginoutService.logout(reqData);
			if (!pack.getHead().get("TxStatus").equals("0") || !pack.getHead().get("RtnCode").equals("00000")) {
				json.put("success",false);
				json.put("message","退出失败,"+pack.getHead().get("RtnMessage"));
				myLog.error(logger, "退出失败["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
			}else{
				json.put("success",true);
				json.put("message","退出成功,"+pack.getHead().get("RtnMessage"));
				myLog.info(logger, "退出成功["+pack.getHead().get("RtnCode")+"[["+pack.getHead().get("RtnMessage")+"]");
			}
		} catch (PafTradeExecuteException e) {
//			e.printStackTrace();
			json.put("success",false);
			json.put("message","退出失败，系统报错："+e);
		} catch (JAXBException e) {
//			e.printStackTrace();
			json.put("success",false);
			json.put("message","退出失败,系统报错"+e);
		} catch (Exception e){
			json.put("success",false);
			json.put("message","退出失败,系统报错"+e);
		}
		return json.toString();
	}


	@RequestMapping("/paf/loginOut")
	public String loginOut(){

		return "paf/login_out";
	}

}
