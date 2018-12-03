package com.fxbank.cap.paf.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.paf.entity.PafSingleCrdt;
import com.fxbank.cap.paf.mapper.PafSingleCrdtMapper;
import com.fxbank.cap.paf.model.QrySingleCapTradeModel;
import com.fxbank.cap.paf.model.SinglCrdtCapUpdModel;
import com.fxbank.cap.paf.model.SinglCrdtInfoModel;
import com.fxbank.cap.paf.service.ISingleCrdtService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

@Service(validation = "true", version = "1.0.0")
public class SingleCrdtService implements ISingleCrdtService{
	
	@Resource
	private PafSingleCrdtMapper pafSingleCrdtMapper;

	@Override
	public void CrdtInfoInit(@Valid SinglCrdtInfoModel record) throws SysTradeExecuteException {
		PafSingleCrdt pafSingleCrdt = new PafSingleCrdt();
		pafSingleCrdt.setTxBrno(record.getTx_brno());
		pafSingleCrdt.setPlatDate(record.getSysDate());
		pafSingleCrdt.setPlatTime(record.getSysTime());
		pafSingleCrdt.setPlatTrace(record.getSysTraceno());
		pafSingleCrdt.setUpBrno(record.getUp_brno());
		pafSingleCrdt.setLogId(record.getMylog().getLogId());
		pafSingleCrdt.setLogDev(record.getMylog().getLogDev());
		pafSingleCrdt.setSndDate(record.getSnd_date()); //发送方日期
		pafSingleCrdt.setSndTime(record.getSnd_time());//'发送方时间' ,
		pafSingleCrdt.setSndSeqno(record.getSnd_seqno());// '发送方流水' ,
		pafSingleCrdt.setSndUnitno(record.getSnd_unitno());// '公积金机构号' ,
		pafSingleCrdt.setSndNode(record.getSnd_node());//'发送方节点号' ,
		pafSingleCrdt.setRcvNode(record.getRcv_node());//'接收方节点号' ,
		pafSingleCrdt.setBdcDate(record.getBdc_date());// '结算系统日期' ,
		pafSingleCrdt.setBdcTime(record.getBdc_time());// '结算系统时间' ,
		pafSingleCrdt.setBdcSeqno(record.getBdc_seqno());// '结算系统流水' ,
		pafSingleCrdt.setCusNo(record.getCus_no());// '银行客户编号' ,
		pafSingleCrdt.setOprNo(record.getOpr_no());//'操作员编号' ,
		pafSingleCrdt.setTxStatus("0");// '交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败' ,
		pafSingleCrdt.setCurrNo(record.getCurr_no());//'币种，156' ,
		pafSingleCrdt.setCurrIden(record.getCurr_iden());//'钞汇鉴别：1、钞；2、汇' ,
		pafSingleCrdt.setSettleType(record.getSettle_type());//'结算模式：1、同行；2、跨行实时；3、跨行非实时' ,
		pafSingleCrdt.setBusType(record.getBus_type());//'业务类型' ,
		pafSingleCrdt.setDeAcctno(record.getDe_acctno());//'付方账号' ,
		pafSingleCrdt.setDeAcctname(record.getDe_acctname());//'付方户名' ,
		pafSingleCrdt.setDeAcctclass(record.getDe_acctclass());//'付方账户类别：1、对私；2、对公' ,
		pafSingleCrdt.setCapAmt(new BigDecimal("".equals(record.getCap_amt())?"0":record.getCap_amt()));//'本金发生额' ,
		pafSingleCrdt.setDeIntacctno(record.getDe_intacctno());//'付息户账号 ',
		pafSingleCrdt.setDeIntacctname(record.getDe_intacctname());//'付息户户名 ',
		pafSingleCrdt.setDeIntacctclass(record.getDe_intacctclass());//'付息户类别:1、对私；2、对公',
		pafSingleCrdt.setDeIntcracct(record.getDe_intcracct());//'利息收方账号',
		pafSingleCrdt.setIntAmt(new BigDecimal("".equals(record.getInt_amt())?"0":record.getInt_amt()));//'利息发生额',
		pafSingleCrdt.setCrAcctno(record.getCr_acctno());//'收方账号',
		pafSingleCrdt.setCrAcctname(record.getCr_acctname());//'收方户名 ',
		pafSingleCrdt.setCrAcctclass(record.getCr_acctclass());//'收方账户类别:1、对私；2、对公',
		pafSingleCrdt.setCrBankclass(record.getCr_bankclass());//'收方账户行别:0、本行；1、跨行',
		pafSingleCrdt.setCrBankname(record.getCr_bankname());//'收方行名 ',
		pafSingleCrdt.setCrChgno(record.getCr_chgno());//'收方联行号 ',
		pafSingleCrdt.setAmt(new BigDecimal("".equals(record.getAmt())?"0":record.getAmt()));//'交易金额' ,
		pafSingleCrdt.setRefAcctno(record.getRef_acctno());//'业务明细账号', 
		pafSingleCrdt.setRefSeqno1(record.getRef_seqno1());//'业务明细流水号 1',
		pafSingleCrdt.setRefSeqno2(record.getRef_seqno2());//'业务明细流水号 2',
		pafSingleCrdt.setSummary(record.getSummary());//'摘要', 
		pafSingleCrdt.setRemark(record.getRemark());//'备注'
		
		//pafSingleCrdt.setPlatDate(0);
		//pafSingleCrdt.setPlatTrace(0);
		//pafSingleCrdt.setTxStatus("");
		pafSingleCrdt.setCapSeqno("");
		pafSingleCrdt.setCapHostcode("");
		pafSingleCrdt.setCapHostmsg("");
		pafSingleCrdt.setIntSeqno("");
		pafSingleCrdt.setIntHostcode("");
		pafSingleCrdt.setIntHostmsg("");
		
		pafSingleCrdtMapper.insert(pafSingleCrdt);
	}

	@Override
	public void CrdtInfoHostCapUpd(@Valid SinglCrdtCapUpdModel record) throws SysTradeExecuteException {
		PafSingleCrdt pafSingleCrdt = new PafSingleCrdt();
		pafSingleCrdt.setPlatDate(record.getSysDate());
		pafSingleCrdt.setPlatTrace(record.getSysTraceno());
		pafSingleCrdt.setTxStatus(record.getTxStatus());
		pafSingleCrdt.setCapSeqno(record.getCapSeqno());
		pafSingleCrdt.setCapHostcode(record.getCapHostcode());
		pafSingleCrdt.setCapHostmsg(record.getCapHostmsg());
		pafSingleCrdtMapper.updateByPrimaryKeySelective(pafSingleCrdt);
		
	}

	@Override
	public void CrdtInfoHostIntUpd() throws SysTradeExecuteException {
		
	}
	/** 
	* @Title: queryCrdtInfoByPk 
	* @Description: 根据平台日期和平台流水号查询单笔付款信息
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public QrySingleCapTradeModel queryCrdtInfoByPk(@Valid QrySingleCapTradeModel model) throws SysTradeExecuteException{
		PafSingleCrdt pafSingleCrdt = new PafSingleCrdt();
		pafSingleCrdt.setPlatDate(model.getPlatDate());
		pafSingleCrdt.setPlatTrace(model.getPlatTrace());
		pafSingleCrdt = pafSingleCrdtMapper.selectByPrimaryKey(pafSingleCrdt);
		model.setCapSeqNo(pafSingleCrdt.getCapSeqno());
		return model;
	}
}
