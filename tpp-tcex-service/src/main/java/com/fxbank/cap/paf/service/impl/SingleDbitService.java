package com.fxbank.cap.paf.service.impl;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.paf.entity.PafSingleDbit;
import com.fxbank.cap.paf.mapper.PafSingleDbitMapper;
import com.fxbank.cap.paf.model.QrySingleCapTradeModel;
import com.fxbank.cap.paf.model.SinglDbitCapUpdModel;
import com.fxbank.cap.paf.model.SinglDbitInfoModel;
import com.fxbank.cap.paf.service.ISingleDbitService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

@Service(validation = "true", version = "1.0.0")
public class SingleDbitService implements ISingleDbitService{
	
	@Resource
	private PafSingleDbitMapper pafSingleDbitMapper;

	@Override
	public void DbitInfoInit(@Valid SinglDbitInfoModel record) throws SysTradeExecuteException {
		PafSingleDbit pafSingleDbit = new PafSingleDbit();
		pafSingleDbit.setTxBrno(record.getTx_brno());
		pafSingleDbit.setPlatDate(record.getSysDate());
		pafSingleDbit.setPlatTime(record.getSysTime());
		pafSingleDbit.setPlatTrace(record.getSysTraceno());
		pafSingleDbit.setUpBrno(record.getUp_brno());
		pafSingleDbit.setLogId(record.getMylog().getLogId());
		pafSingleDbit.setLogDev(record.getMylog().getLogDev());
		pafSingleDbit.setSndDate(record.getSnd_date()); //发送方日期
		pafSingleDbit.setSndTime(record.getSnd_time());//'发送方时间' ,
		pafSingleDbit.setSndSeqno(record.getSnd_seqno());// '发送方流水' ,
		pafSingleDbit.setSndUnitno(record.getSnd_unitno());// '公积金机构号' ,
		pafSingleDbit.setSndNode(record.getSnd_node());//'发送方节点号' ,
		pafSingleDbit.setRcvNode(record.getRcv_node());//'接收方节点号' ,
		pafSingleDbit.setBdcDate(record.getBdc_date());// '结算系统日期' ,
		pafSingleDbit.setBdcTime(record.getBdc_time());// '结算系统时间' ,
		pafSingleDbit.setBdcSeqno(record.getBdc_seqno());// '结算系统流水' ,
		pafSingleDbit.setCusNo(record.getCus_no());// '银行客户编号' ,
		pafSingleDbit.setOprNo(record.getOpr_no());//'操作员编号' ,
		pafSingleDbit.setTxStatus("0");// '交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败' ,
		pafSingleDbit.setCurrNo(record.getCurr_no());//'币种，156' ,
		pafSingleDbit.setCurrIden(record.getCurr_iden());//'钞汇鉴别：1、钞；2、汇' ,
		pafSingleDbit.setSettleType(record.getSettle_type());//'结算模式：1、同行；2、跨行实时；3、跨行非实时' ,
		pafSingleDbit.setBusType(record.getBus_type());//'业务类型' ,
		pafSingleDbit.setDeAcctno(record.getDe_acctno());//'付方账号' ,
		pafSingleDbit.setDeAcctname(record.getDe_acctname());//'付方户名' ,
		pafSingleDbit.setDeAcctclass(record.getDe_acctclass());//'付方账户类别：1、对私；2、对公' ,
		pafSingleDbit.setCrAcctno(record.getCr_acctno());//'收方账号',
		pafSingleDbit.setConAgrno(record.getCon_agrno());//多方协议号
		pafSingleDbit.setCrAcctname(record.getCr_acctname());//'收方户名 ',
		pafSingleDbit.setCrAcctclass(record.getCr_acctclass());//'收方账户类别:1、对私；2、对公',
		pafSingleDbit.setDeBankclass(record.getDe_bankclass());//'收方账户行别:0、本行；1、跨行',
		pafSingleDbit.setDeBankname(record.getDe_bankname());//'收方行名 ',
		pafSingleDbit.setDeChgno(record.getDe_chgno());//'收方联行号 ',
		pafSingleDbit.setAmt(new BigDecimal("".equals(record.getAmt())?"0":record.getAmt()));//'交易金额' ,
		pafSingleDbit.setRefAcctno(record.getRef_acctno());//'业务明细账号', 
		pafSingleDbit.setRefSeqno(record.getRef_seqno());//'业务明细流水号',
		pafSingleDbit.setSummary(record.getSummary());//'摘要', 
		pafSingleDbit.setRemark(record.getRemark());//'备注'
		pafSingleDbit.setCapSeqno("");
		pafSingleDbit.setCapHostcode("");
		pafSingleDbit.setCapHostmsg("");
		pafSingleDbit.setIntSeqno("");
		pafSingleDbit.setIntHostcode("");
		pafSingleDbit.setIntHostmsg("");
		pafSingleDbitMapper.insert(pafSingleDbit);
	}

	@Override
	public void DbitInfoHostCapUpd(@Valid SinglDbitCapUpdModel record) throws SysTradeExecuteException {
		PafSingleDbit pafSingleDbit = new PafSingleDbit();
		pafSingleDbit.setPlatDate(record.getSysDate());
		pafSingleDbit.setPlatTrace(record.getSysTraceno());
		pafSingleDbit.setTxStatus(record.getTxStatus());
		pafSingleDbit.setCapSeqno(record.getCapSeqno());
		pafSingleDbit.setCapHostcode(record.getCapHostcode());
		pafSingleDbit.setCapHostmsg(record.getCapHostmsg());
		pafSingleDbitMapper.updateByPrimaryKeySelective(pafSingleDbit);
		
	}

	@Override
	public void DbitInfoHostIntUpd() throws SysTradeExecuteException {
		
	}
	/** 
	* @Title: queryDbitInfoByPk 
	* @Description: 根据平台日期和平台流水号查询单笔收款信息
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public QrySingleCapTradeModel queryDbitInfoByPk(@Valid QrySingleCapTradeModel model) throws SysTradeExecuteException{
		PafSingleDbit pafSingleDbit = new PafSingleDbit();
		pafSingleDbit.setPlatDate(model.getPlatDate());
		pafSingleDbit.setPlatTrace(model.getPlatTrace());
		pafSingleDbit = pafSingleDbitMapper.selectByPrimaryKey(pafSingleDbit);
		model.setCapSeqNo(pafSingleDbit.getCapSeqno());
		return model;
	}

}
