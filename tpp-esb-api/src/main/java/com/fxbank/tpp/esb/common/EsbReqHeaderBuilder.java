package com.fxbank.tpp.esb.common;


import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsbReqHeaderBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(EsbReqHeaderBuilder.class);

	private String tranMode; // 交易模式

	private String sourceType; // 渠道类型

	private String branchId; // 机构号

	private String userId; // 柜员号

	private String tranDate; // 交易日期

	private String tranTimestamp; // 交易时间

	private String userLang; // 操作员语言

	private String seqNo; // 渠道流水号

	private String systemId; // 发起方系统编码

	private String company; // 法人代码

	private String sourceBranchNo; // 源节点编号

	private String destBranchNo; // 目标节点编号

	private String filePath; // 文件绝对路径

	private String macValue; // 传输密押

	private String programId; // 交易屏幕标识

	private String authUserId; // 授权柜员的柜员号

	private String authFlag; // 授权标志

	private String apprUserId; // 交易录入柜员标识

	private String apprFlag; // 复核标志

	private String version; // 服务版本

	private String threadNo; // 线程编号

	private String[] extendArray = new String[] { "" }; // 预留数组

	private String key; // 关键字

	private String value; // 关键键值

	private String extendField; // 预留字段

	private String macOrg_id; // MAC机构号

	private String cnsmSysSvrid; // 调用方系统服务器Id

	private String srcSysSvrId; // 源发起系统服务器Id

	private String gloabalSeqNo; // 全局流水号
	
	private ESB_REQ_SYS_HEAD reqSysHead;
	
	/**************************
	public EsbReqHeaderBuilder(ESB_REQ_SYS_HEAD reqSysHead, DataTransObject dto){
		this.reqSysHead=reqSysHead;
		this.sourceType=dto.getSourceType();
		this.tranDate=String.valueOf(dto.getSysDate());
		this.tranTimestamp=String.format("%06d", dto.getSysTime());
		this.seqNo=String.format("%08d", dto.getSysTraceno());
	}
	**************************/

	public EsbReqHeaderBuilder(ESB_REQ_SYS_HEAD reqSysHead, String sourceType,Integer sysDate,Integer sysTime,Integer sysTraceno){
		this.reqSysHead=reqSysHead;
		this.sourceType=sourceType;
		this.tranDate=String.valueOf(sysDate);
		this.tranTimestamp=String.format("%06d", sysTime);
		this.seqNo=String.format("%08d", sysTraceno);
	}

	public EsbReqHeaderBuilder setTranMode(String tranMode) {
		this.tranMode = tranMode;
		return this;
	}

	public EsbReqHeaderBuilder setSourceType(String sourceType) {
		this.sourceType = sourceType;
		return this;
	}

	public EsbReqHeaderBuilder setBranchId(String branchId) {
		this.branchId = branchId;
		return this;
	}

	public EsbReqHeaderBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public EsbReqHeaderBuilder setTranDate(String tranDate) {
		this.tranDate = tranDate;
		return this;
	}

	public EsbReqHeaderBuilder setTranTimestamp(String tranTimestamp) {
		this.tranTimestamp = tranTimestamp;
		return this;
	}

	public EsbReqHeaderBuilder setUserLang(String userLang) {
		this.userLang = userLang;
		return this;
	}

	public EsbReqHeaderBuilder setSeqNo(String seqNo) {
		this.seqNo = String.format("%08d", Integer.parseInt(seqNo));
		return this;
	}

	public EsbReqHeaderBuilder setSystemId(String systemId) {
		this.systemId = systemId;
		return this;
	}

	public EsbReqHeaderBuilder setCompany(String company) {
		this.company = company;
		return this;
	}

	public EsbReqHeaderBuilder setSourceBranchNo(String sourceBranchNo) {
		this.sourceBranchNo = sourceBranchNo;
		return this;
	}

	public EsbReqHeaderBuilder setDestBranchNo(String destBranchNo) {
		this.destBranchNo = destBranchNo;
		return this;
	}

	public EsbReqHeaderBuilder setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	public EsbReqHeaderBuilder setMacValue(String macValue) {
		this.macValue = macValue;
		return this;
	}

	public EsbReqHeaderBuilder setProgramId(String programId) {
		this.programId = programId;
		return this;
	}

	public EsbReqHeaderBuilder setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
		return this;
	}

	public EsbReqHeaderBuilder setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
		return this;
	}

	public EsbReqHeaderBuilder setApprUserId(String apprUserId) {
		this.apprUserId = apprUserId;
		return this;
	}

	public EsbReqHeaderBuilder setApprFlag(String apprFlag) {
		this.apprFlag = apprFlag;
		return this;
	}

	public EsbReqHeaderBuilder setVersion(String version) {
		this.version = version;
		return this;
	}

	public EsbReqHeaderBuilder setThreadNo(String threadNo) {
		this.threadNo = threadNo;
		return this;
	}

	public EsbReqHeaderBuilder setExtendArray(String[] extendArray) {
		this.extendArray = extendArray;
		return this;
	}

	public EsbReqHeaderBuilder setKey(String key) {
		this.key = key;
		return this;
	}

	public EsbReqHeaderBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	public EsbReqHeaderBuilder setExtendField(String extendField) {
		this.extendField = extendField;
		return this;
	}

	public EsbReqHeaderBuilder setMacOrg_id(String macOrg_id) {
		this.macOrg_id = macOrg_id;
		return this;
	}

	public EsbReqHeaderBuilder setCnsmSysSvrid(String cnsmSysSvrid) {
		this.cnsmSysSvrid = cnsmSysSvrid;
		return this;
	}

	public EsbReqHeaderBuilder setSrcSysSvrId(String srcSysSvrId) {
		this.srcSysSvrId = srcSysSvrId;
		return this;
	}

	public EsbReqHeaderBuilder setGloabalSeqNo(String gloabalSeqNo) {
		this.gloabalSeqNo = gloabalSeqNo;
		return this;
	}
	
	public ESB_REQ_SYS_HEAD build() throws SysTradeExecuteException{
		
		this.reqSysHead.setTranMode(this.tranMode==null?"ONLINE":this.tranMode);
		
		if(this.sourceType==null){
			SysTradeExecuteException e = new SysTradeExecuteException(
					SysTradeExecuteException.CIP_E_000002);
			logger.error("sourceType不能为空");
			throw e;
		}else{
			this.reqSysHead.setSourceType(this.sourceType);
		}
		this.reqSysHead.setBranchId(this.branchId==null?CIP.BRANCH_ID:this.branchId);
		this.reqSysHead.setUserId(this.userId==null?CIP.USER_ID:this.userId);
		
		if(this.tranDate==null){
			SysTradeExecuteException e = new SysTradeExecuteException(
					SysTradeExecuteException.CIP_E_000002);
			logger.error("tranDate不能为空");
			throw e;
		}else{
			this.reqSysHead.setTranDate(this.tranDate);
		}
		if(this.tranTimestamp==null){
			SysTradeExecuteException e = new SysTradeExecuteException(
					SysTradeExecuteException.CIP_E_000002);
			logger.error("tranTimestamp不能为空");
			throw e;
		}else{
			this.reqSysHead.setTranTimestamp(this.tranTimestamp);
		}
		
		this.reqSysHead.setUserLang("CHINESE");
		
		if(this.seqNo==null){
			SysTradeExecuteException e = new SysTradeExecuteException(
					SysTradeExecuteException.CIP_E_000002);
			logger.error("seqNo不能为空");
			throw e;
		}else{
			this.reqSysHead.setSeqNo(ESB.SYSTEM_ID+this.tranDate+this.seqNo);
		}
		
		this.reqSysHead.setSystemId(ESB.SYSTEM_ID);
		this.reqSysHead.setCompany(this.company==null?"":this.company);
		this.reqSysHead.setSourceBranchNo(this.sourceBranchNo==null?"":this.sourceBranchNo);
		this.reqSysHead.setDestBranchNo(this.destBranchNo==null?"":this.destBranchNo);
		this.reqSysHead.setFilePath(this.filePath==null?"":this.filePath);
		//this.reqSysHead.setMacValue(this.macValue==null?"":this.macValue);
		this.reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
		
		return reqSysHead;
	}
	
}
