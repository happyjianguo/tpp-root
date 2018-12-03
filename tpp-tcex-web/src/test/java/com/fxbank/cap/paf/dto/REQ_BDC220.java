package com.fxbank.cap.paf.dto;

import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import com.fxbank.cap.paf.model.FIELD;


public class REQ_BDC220 implements Serializable{
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
     head.add(new FIELD("TxCode","BDC220"));
     head.add(new FIELD("ReceiveNode","104000"));
     head.add(new FIELD("BDCDate",sdf1.format(new Date())));
     head.add(new FIELD("BDCTime",sdf2.format(new Date())));
     head.add(new FIELD("BDCSeqNo","20151102095650542667"));
     head.add(new FIELD("CustNo","CMP13140800100"));
     head.add(new FIELD("OperNo","333333"));
     head.add(new FIELD("BeginRec",""));
     head.add(new FIELD("MaxRec",""));
     //body.add(new FIELD("AcctNo","1205700000000010"));
     body.add(new FIELD("AcctNo",arr[1]));
     body.add(new FIELD("FixedType","0"));		
	 return reqDto;
	}
}


