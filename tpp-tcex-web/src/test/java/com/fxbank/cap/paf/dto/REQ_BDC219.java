package com.fxbank.cap.paf.dto;

import com.fxbank.cap.paf.model.FIELD;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class REQ_BDC219 implements Serializable {

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
        head.add(new FIELD("TxCode","BDC219"));
        head.add(new FIELD("ReceiveNode","104000"));
        head.add(new FIELD("BDCDate",sdf1.format(new Date())));
        head.add(new FIELD("BDCTime",sdf2.format(new Date())));
        head.add(new FIELD("BDCSeqNo","20151102095650542667"));
        head.add(new FIELD("CustNo","CMP13140800100"));
        head.add(new FIELD("OperNo","333333"));
        head.add(new FIELD("BeginRec",""));
        head.add(new FIELD("MaxRec",""));
        //无返回记录
        //body.add(new FIELD("OldTxDate","20181012"));
        //body.add(new FIELD("OldSeqNo","93c172e59b2743ecbb658035d19cd5a3"));
        //同行转账 流水号ENS2018101118272849
         body.add(new FIELD("OldTxDate",arr[1]));
         body.add(new FIELD("OldSeqNo",arr[2]));
        //跨行实时转账 流水号ENS2018101118261998
        // body.add(new FIELD("OldTxDate","20181024"));
        // body.add(new FIELD("OldSeqNo","a363c2e4f228480b9efc2d078c840ed0"));
        //跨行非实时转账 流水号ENS2018101118261850
        //body.add(new FIELD("OldTxDate","20181024"));
        //body.add(new FIELD("OldSeqNo","558f324a2081458e8136f6eff0d21afd"));
        return reqDto;
    }
}
