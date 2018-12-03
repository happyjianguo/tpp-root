package com.fxbank.cap.paf.dto;

import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import com.fxbank.cap.paf.model.FIELD;


public class REQ_BDC212 implements Serializable{
	private static final long serialVersionUID = -4376837848489078790L;
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdf2=new SimpleDateFormat("HHmmss");
	public SER_REP_BDC generateReq(String str) {
     SER_REP_BDC reqDto = new SER_REP_BDC();
     String[] arr = str.split(",");
     List<FIELD> head = reqDto.getHead().getField();
     List<FIELD> body = reqDto.getBody().getField();
     head.add(new FIELD("SendDate",sdf1.format(new Date())));
     head.add(new FIELD("SendTime",sdf2.format(new Date())));
     head.add(new FIELD("SendSeqNo",UUID.randomUUID().toString().replace("-", "")));
     head.add(new FIELD("TxUnitNo",arr[0]));
     head.add(new FIELD("SendNode","D00000"));
     head.add(new FIELD("TxCode","BDC212"));
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
    body.add(new FIELD("FixedAcctNo",arr[1]));
    body.add(new FIELD("FixedAcctName",arr[2]));
     body.add(new FIELD("FixedAcctOpenBank","2"));
     body.add(new FIELD("BookNo",arr[3]));
     body.add(new FIELD("BookListNo",""));
     body.add(new FIELD("DepositBeginDate","20161101"));
     body.add(new FIELD("DepositEndDate","20171101"));
     body.add(new FIELD("ActivedAcctNo",arr[4]));
     body.add(new FIELD("ActivedAcctName",arr[5]));
     body.add(new FIELD("ActivedAcctOpenBank",""));
     body.add(new FIELD("DepostiAmt",""));
     body.add(new FIELD("DrawAmt",arr[6]));
     body.add(new FIELD("DepositPeriod","4"));
     body.add(new FIELD("InterestRate",""));
	 return reqDto;
	}
}


