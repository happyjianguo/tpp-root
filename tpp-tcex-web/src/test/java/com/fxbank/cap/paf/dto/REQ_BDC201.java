package com.fxbank.cap.paf.dto;

import java.util.List;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fxbank.cap.paf.model.FIELD;


public class REQ_BDC201 implements Serializable{
	private static final long serialVersionUID = -4376837848489078790L;
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdf2=new SimpleDateFormat("HHmmss");
	public SER_REP_BDC generateReq(String str) throws UnsupportedEncodingException {
		String[] arr = str.split(",");
		String settleType = arr[6],busType = arr[7];
     SER_REP_BDC reqDto = new SER_REP_BDC();
     List<FIELD> head = reqDto.getHead().getField();
     List<FIELD> body = reqDto.getBody().getField();
     head.add(new FIELD("SendDate",sdf1.format(new Date())));
     head.add(new FIELD("SendTime",sdf2.format(new Date())));
     head.add(new FIELD("SendSeqNo",UUID.randomUUID().toString().replace("-", "")));
     head.add(new FIELD("TxUnitNo",arr[0]));
     head.add(new FIELD("SendNode","D00000"));
     head.add(new FIELD("TxCode","BDC201"));
     head.add(new FIELD("ReceiveNode","104000"));
     head.add(new FIELD("BDCDate",sdf1.format(new Date())));
     head.add(new FIELD("BDCTime",sdf2.format(new Date())));
     head.add(new FIELD("BDCSeqNo","20151102095650542667"));
     head.add(new FIELD("CustNo","CMP13140800100"));
     head.add(new FIELD("OperNo","333333"));
     head.add(new FIELD("BeginRec",""));
     head.add(new FIELD("MaxRec",""));
     body.add(new FIELD("CurrNo","156"));
	 body.add(new FIELD("CurrIden","1"));		
	 body.add(new FIELD("SettleType",settleType));
	 body.add(new FIELD("BusType",busType));
	
			body.add(new FIELD("DeIntAcctNo",""));
			body.add(new FIELD("DeIntAcctName",""));
			body.add(new FIELD("DeIntAcctClass",""));
			body.add(new FIELD("DeIntCrAcct",""));
			body.add(new FIELD("IntAmt",""));
			body.add(new FIELD("RefSeqNo1","20151102003"));
			body.add(new FIELD("RefSeqNo2",""));
		
		if("1".equals(settleType)) {
			body.add(new FIELD("CrBankClass","0"));
			body.add(new FIELD("CrBankName",""));
		}else {
			body.add(new FIELD("CrBankClass","1"));
			body.add(new FIELD("CrBankName","中国银行"));
		}
		body.add(new FIELD("DeAcctNo",arr[1]));
	    body.add(new FIELD("DeAcctName",arr[2]));
		//body.add(new FIELD("DeAcctNo","623166001016718196"));
	    //body.add(new FIELD("DeAcctName","不动一"));
		body.add(new FIELD("DeAcctClass","2"));
		body.add(new FIELD("CapAmt",arr[5]));
		body.add(new FIELD("CrAcctNo",arr[3]));
		body.add(new FIELD("CrAcctName",arr[4]));
		//body.add(new FIELD("CrAcctNo","1200100003301640"));
		//body.add(new FIELD("CrAcctName","阜新市公积金"));
		body.add(new FIELD("CrAcctClass","1"));
		body.add(new FIELD("CrChgNo","104100000004"));
		body.add(new FIELD("Amt",arr[5]));
		body.add(new FIELD("RefAcctNo",""));
		body.add(new FIELD("Summary","单笔付款"));
		body.add(new FIELD("Remark",""));
	 return reqDto;
	}
}


