package com.fxbank.cap.paf.dto;

import com.fxbank.cap.paf.model.FIELD;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class REQ_BDC210 implements Serializable {

    private static final long serialVersionUID = -4376837848489078790L;
    static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat sdf2=new SimpleDateFormat("HHmmss");
    public SER_REP_BDC generateReq(String str) {
        SER_REP_BDC reqDto = new SER_REP_BDC();
        String[] arr = str.split(",");
        List<FIELD> head = reqDto.getHead().getField();
        List<FIELD> body = reqDto.getBody().getField();
        //head
        head.add(new FIELD("SendDate",sdf1.format(new Date())));
        head.add(new FIELD("SendTime",sdf2.format(new Date())));
        head.add(new FIELD("SendSeqNo", UUID.randomUUID().toString().replace("-", "")));
        head.add(new FIELD("TxUnitNo",arr[0]));
        head.add(new FIELD("SendNode","D00000"));
        head.add(new FIELD("TxCode","BDC210"));
        head.add(new FIELD("ReceiveNode","104000"));
        head.add(new FIELD("BDCDate",sdf1.format(new Date())));
        head.add(new FIELD("BDCTime",sdf2.format(new Date())));
        head.add(new FIELD("BDCSeqNo","20151102095650542667"));
        head.add(new FIELD("CustNo","CMP13140800100"));
        head.add(new FIELD("OperNo","333333"));
        head.add(new FIELD("BeginRec",arr[2]));
        head.add(new FIELD("MaxRec",arr[3]));
        //body
        body.add(new FIELD("CurrNo","156"));
        body.add(new FIELD("CurrIden","1"));
        body.add(new FIELD("AcctNo",arr[1]));
        body.add(new FIELD("AcctType","1"));

//        body.add(new FIELD("Bk1",""));
//        body.add(new FIELD("Bk2",""));
//        body.add(new FIELD("Bk3",""));
//        body.add(new FIELD("Bk4",""));
//        body.add(new FIELD("Bk5",""));
//        body.add(new FIELD("Bk6",""));
        return reqDto;
    }
}
