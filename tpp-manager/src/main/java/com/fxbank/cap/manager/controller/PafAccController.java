package com.fxbank.cap.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.manager.entity.SysDepart;
import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.entity.SysUserDepart;
import com.fxbank.cap.manager.service.SysUserDepartService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30013000201;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.manager.model.PafAcNoInfo;
import com.fxbank.cap.manager.model.PafAcNoList;
import com.fxbank.cap.manager.model.PafCenterInfo;
import com.fxbank.cap.manager.model.PafCenterList;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.CLI_REP_DATA;
import com.fxbank.cap.paf.model.CLI_REQ_BDC;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.fxbank.cap.paf.service.IAccountService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import com.tienon.util.FileFieldConv;

import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
public class PafAccController {
	private static Logger logger = LoggerFactory.getLogger(PafAccController.class);

	@Reference(version = "1.0.0")
	private IAccountService accountService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
    private SysUserDepartService sysUserDepartService;

	@Resource
	private MyJedis myJedis;

	private final static String BRTEL_PREFIX = "paf_branch.";

	@RequestMapping("/paf/list")
	public String list(Model model){
		logger.info("PafAccController  list");
		JSONObject json=new JSONObject();
		try{
			SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
			SysDepart userDepart = sysUserDepartService.getUserDepart(sysUser.getId());

			String jsonStrCenterList = null;
			try (Jedis jedis = myJedis.connect()) {
				jsonStrCenterList = jedis.get(BRTEL_PREFIX + "PAFCENTER_LIST");
			}
//			jsonStrCenterList="{\"DATA\":[{\"DEPART_CODE\":\"02002\",\"PAFC_NAME\":\"盘锦公积金中心\",\"PAFC_NO\":\"211100000000000\"},{\"DEPART_CODE\":\"03002\",\"PAFC_NAME\":\"阜新公积金中心\",\"PAFC_NO\":\"211122222222222\"}]}";

			if(jsonStrCenterList==null||jsonStrCenterList.length()==0){
				logger.error("公积金中心信息未配置["+BRTEL_PREFIX + "PAFCENTER_LIST"+"]");
				throw new RuntimeException("公积金中心信息未配置["+BRTEL_PREFIX + "PAFCENTER_LIST"+"]");
			}
			PafCenterList pafCenterList = JsonUtil.toBean(jsonStrCenterList, PafCenterList.class);
			List<PafCenterInfo> pafCenterInfoList = new ArrayList<>();

			for(PafCenterInfo pafCenterInfo:pafCenterList.getData()){
				if(userDepart.getParentId()==0){
					pafCenterInfoList = pafCenterList.getData();
					break;
				}else
					if(pafCenterInfo.getDepartCode().equals(userDepart.getCode())){
						pafCenterInfoList.add(pafCenterInfo);
					}
			}

			json.put("success",true);
			model.addAttribute("pafCenterInfoList",pafCenterInfoList);
		}catch (Exception e){
			json.put("success",false);
			json.put("message","操作失败,"+e.getMessage());
			logger.error("操作失败,"+e.toString());
		}

		model.addAttribute("data",json);
		return "paf/acc_list";
	}

	@RequestMapping("/paf/getAcc")
	@ResponseBody
	public String getAccList(String pafcNo){
		logger.info("PafAccController  getAccList");
		JSONObject json=new JSONObject();
		try{
			String jsonStrAcList = null;
			try (Jedis jedis = myJedis.connect()) {
				jsonStrAcList = jedis.get(BRTEL_PREFIX + pafcNo + "_ACNOLIST");
			}

			if(jsonStrAcList==null||jsonStrAcList.length()==0){
				logger.error("尚未该公积金中心配置签约账号信息[" + pafcNo+ "]");
				throw new RuntimeException("尚未该公积金中心配置签约账号信息[" + pafcNo+ "]");
			}
			PafAcNoList pafAcnoList = JsonUtil.toBean(jsonStrAcList, PafAcNoList.class);

			JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(pafAcnoList.getData()));
			json.put("msg","");
			json.put("code",0);
			json.put("data",jsonArray.toArray());
		}catch (Exception e){
			json.put("msg",e.getMessage());
			json.put("code",0);
			json.put("data","");
			logger.error("操作失败,"+e.toString());
		}


		return json.toString();
	}



	// 127.0.0.1:5000/cap/paf/accsign.do?pafcNo=211100000000000
	@RequestMapping({ "/paf/accsign" })
	@ResponseBody
	public String accSign(String pafcNo,String accList) throws Exception {
		logger.info("PafAccController  accSign");
		JSONObject json = new JSONObject();
		MyLog myLog = new MyLog();
		try {


			Integer sysDate = publicService.getSysDate("CIP");
			Integer sysTime = publicService.getSysTime();
			Integer sysTraceno = publicService.getSysTraceno();

			JSONObject accListJson = new JSONObject();
			JSONArray arr = JSON.parseArray(accList);
			accListJson.put("DATA",arr);
			System.out.println(accListJson.toString());
			PafAcNoList pafAcnoList = JsonUtil.toBean(accListJson.toString(), PafAcNoList.class);
			StringBuffer acDataBuf = new StringBuffer();
			for (PafAcNoInfo pafAcNoInfo : pafAcnoList.getData()) {
				ESB_REP_30013000201 esb_rep_30013000201 = getAcctMsg(myLog, sysDate, sysTime, sysTraceno, pafcNo,
						pafAcNoInfo.getAcNo());
				if (!esb_rep_30013000201.getRepBody().getClientNo().equals(pafAcNoInfo.getCliNo())) {
					myLog.error(logger,"配置客户号与核心登记客户号不相符[" + esb_rep_30013000201.getRepBody().getClientNo() + "]["
							+ pafAcNoInfo.getCliNo() + "]");
				/*throw new RuntimeException("配置客户号与核心登记客户号不相符[" + esb_rep_30013000201.getRepBody().getClientNo() + "]["
						+ pafAcNoInfo.getCliNo() + "]");*/
				}
				if (!esb_rep_30013000201.getRepBody().getAcctName().equals(pafAcNoInfo.getAcName())) {
					myLog.error(logger,"配置户名与核心登记户名不相符[" + esb_rep_30013000201.getRepBody().getAcctName() + "]["
							+ pafAcNoInfo.getAcName() + "]");
/*				throw new RuntimeException("配置户名与核心登记户名不相符[" + esb_rep_30013000201.getRepBody().getAcctName() + "]["
						+ pafAcNoInfo.getAcName() + "]");*/
				}
				if (!esb_rep_30013000201.getRepBody().getBranch().equals(pafAcNoInfo.getBrNo())) {
					myLog.error(logger,"配置开户机构与核心登记开户机构不相符[" + esb_rep_30013000201.getRepBody().getBranch() + "]["
							+ pafAcNoInfo.getBrNo() + "]");
/*				throw new RuntimeException("配置开户机构与核心登记开户机构不相符[" + esb_rep_30013000201.getRepBody().getBranch() + "]["
						+ pafAcNoInfo.getBrNo() + "]");*/
				}
				acDataBuf.append(pafAcNoInfo.getCliNo()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcNo()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcName()).append("|");
				acDataBuf.append(pafAcNoInfo.getBrNo()).append("|");
				acDataBuf.append(pafAcNoInfo.getBrName()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcType()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcKind()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcBitype()).append("|");
				acDataBuf.append(pafAcNoInfo.getAcSts()).append("|");
				acDataBuf.append("|"); // 签约转账方式
				acDataBuf.append("|"); // 银行扣款资金内部过渡户
				acDataBuf.append("|"); // 银行委托贷款本金账号
				acDataBuf.append("|"); // 银行贷款本金内部过渡户
				acDataBuf.append("|"); // 银行贷款利息内部过渡户
				acDataBuf.append("\n");
			}

			String fileName = "BANK_" + PAF.BANK_CODE + "_" + pafcNo + "_" + sysDate + ".DAT";
			CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog, sysDate, sysTime, sysTraceno);
			FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("FILE_LIST");
			List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
			FIELDS_LIST_INNER inner0 = new FIELDS_LIST_INNER("0");
			innerList.add(inner0);
			inner0.getField().add(new FIELD("NAME", fileName));
			myLog.info(logger,"发送文件名称["+fileName+"]");
			myLog.info(logger,"发送文件内容["+acDataBuf.toString()+"]");
			String bcdString = FileFieldConv.fieldASCtoBCD(acDataBuf.toString(), PAF.ENCODING);
			inner0.getField().add(new FIELD("DATA", bcdString));
			outer.setField_list(innerList);
			reqData.getBody().setField_list(outer);

			CLI_REP_DATA pack = accountService.notify(reqData, "SBDC200");
			if (!pack.getHead().get("TxStatus").equals("0") || !pack.getHead().get("RtnCode").equals("00000")) {
				myLog.error(logger, "公积金中心：" + pafcNo + ",签约账户通知失败，RtnMessage：" + pack.getHead().get("RtnMessage"));
				PafTradeExecuteException e = new PafTradeExecuteException(PafTradeExecuteException.PAF_E_10011);
				throw e;
			}
			myLog.info(logger, "公积金中心：" + pafcNo + ",签约账户通知成功，RtnMessage：" + pack.getHead().get("RtnMessage"));
			json.put("success",true);
			json.put("message","签约账户通知成功");
		}catch (Exception e){
			json.put("success",false);
			json.put("message","操作失败,"+e.getMessage());
			logger.error("操作失败,"+e.toString());

		}

		return json.toString();
	}

	private ESB_REP_30013000201 getAcctMsg(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String txUnitNo, String acct) throws SysTradeExecuteException {
		String txBrno = null;
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXBRNO");
			txTel = jedis.get(BRTEL_PREFIX + txUnitNo + "_TXTEL");
		}

		ESB_REQ_30013000201 esbReq_30013000201 = new ESB_REQ_30013000201(myLog, sysDate, sysTime, sysTraceno);
		DataTransObject reqDto = new DataTransObject();
		reqDto.setSourceType("GJ");
		reqDto.setSysDate(sysDate);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30013000201.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30013000201.setReqSysHead(reqSysHead);

		ESB_REQ_30013000201.REQ_BODY reqBody_30013000201 = esbReq_30013000201.getReqBody();
		reqBody_30013000201.setBaseAcctNo(acct);
		ESB_REP_30013000201 esb_rep_30013000201 = forwardToESBService.sendToESB(esbReq_30013000201, reqBody_30013000201,
				ESB_REP_30013000201.class);

		return esb_rep_30013000201;
	}

}
