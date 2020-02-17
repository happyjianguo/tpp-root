package com.fxbank.tpp.beps.service.impl;

import javax.annotation.Resource;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.tpp.beps.entity.CcmsFreeInfo;
import com.fxbank.tpp.beps.mapper.CcmsFreeInfoMapper;
import com.fxbank.tpp.beps.model.bepsmodel.CcmsFreeinfoModel;
import com.fxbank.tpp.beps.service.ICcmsFreeinfoService;

public class CcmsFreeinfoService implements ICcmsFreeinfoService {

	@Resource
	CcmsFreeInfoMapper ccmsFreeInfoMapper;

	@Override
	public int ccmsFreeinfoIns(CcmsFreeinfoModel ccmsFreeinfoModel) throws SysTradeExecuteException {
		// TODO Auto-generated method stub

		CcmsFreeInfo ccmsFreeInfo = new CcmsFreeInfo();
		ccmsFreeInfo.setAgency(ccmsFreeinfoModel.getAgency());
		ccmsFreeInfo.setAttachlen(ccmsFreeinfoModel.getAttachLen());
		ccmsFreeInfo.setAttachname(ccmsFreeinfoModel.getAttachName());
		ccmsFreeInfo.setBranchno(ccmsFreeinfoModel.getBranchNo());
		ccmsFreeInfo.setBusikind(ccmsFreeinfoModel.getBusikind());
		ccmsFreeInfo.setBusitype(ccmsFreeinfoModel.getBusitype());
		ccmsFreeInfo.setCmtno(ccmsFreeinfoModel.getCmtNo());
		ccmsFreeInfo.setDtlsndBkno(ccmsFreeinfoModel.getDtlSndBkno());
		ccmsFreeInfo.setFrchnl(ccmsFreeinfoModel.getFrChnl());
		ccmsFreeInfo.setIftown(ccmsFreeinfoModel.getIfTown());
		ccmsFreeInfo.setInformation(ccmsFreeinfoModel.getInformation());
		ccmsFreeInfo.setInfosource(ccmsFreeinfoModel.getInfoSource());
		ccmsFreeInfo.setInfotitle(ccmsFreeinfoModel.getInfoTitle());
		ccmsFreeInfo.setInfotype(ccmsFreeinfoModel.getInfoType());
		ccmsFreeInfo.setMsgid(ccmsFreeinfoModel.getMsgid());
		ccmsFreeInfo.setNpccode(ccmsFreeinfoModel.getNpcCode());
		ccmsFreeInfo.setNpcdate(ccmsFreeinfoModel.getNpcDate());
		ccmsFreeInfo.setNpcmsg(ccmsFreeinfoModel.getNpcMsg());
		ccmsFreeInfo.setNpcsts(ccmsFreeinfoModel.getNpcSts());
		ccmsFreeInfo.setPlatdate(ccmsFreeinfoModel.getPlatDate());
		ccmsFreeInfo.setPlattrace(ccmsFreeinfoModel.getPlatTraceno());
		ccmsFreeInfo.setPrintno(ccmsFreeinfoModel.getPrintNo());
		ccmsFreeInfo.setProcstate(ccmsFreeinfoModel.getProcState());
		ccmsFreeInfo.setPutuser(ccmsFreeinfoModel.getPutUser());
		ccmsFreeInfo.setRcvbankno(ccmsFreeinfoModel.getRcvBankNo());
		ccmsFreeInfo.setRcvccpc(ccmsFreeinfoModel.getRcvCcpc());
		ccmsFreeInfo.setRcvsabkno(ccmsFreeinfoModel.getRcvSaBkno());
		ccmsFreeInfo.setReplyinfo(ccmsFreeinfoModel.getReplyInfo());
		ccmsFreeInfo.setSndbankno(ccmsFreeinfoModel.getSndBankNo());
		ccmsFreeInfo.setSndccpc(ccmsFreeinfoModel.getSndCcpc());
		ccmsFreeInfo.setSnddate(ccmsFreeinfoModel.getSndDate());
		ccmsFreeInfo.setSndsabkno(ccmsFreeinfoModel.getSndSaBkno());
		ccmsFreeInfo.setSyscode(ccmsFreeinfoModel.getSysCode());
		ccmsFreeInfo.setTssno(ccmsFreeinfoModel.getTssno());

		ccmsFreeInfoMapper.insertSelective(ccmsFreeInfo);

		return 0;
	}

}
