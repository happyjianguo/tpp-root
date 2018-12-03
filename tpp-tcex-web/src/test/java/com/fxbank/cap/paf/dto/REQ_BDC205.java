package com.fxbank.cap.paf.dto;

import java.util.List;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cap.paf.model.FIELDS_LIST_INNER;
import com.fxbank.cap.paf.model.FIELDS_LIST_OUTER;
import com.tienon.util.FileFieldConv;


public class REQ_BDC205 implements Serializable{
	private static final long serialVersionUID = -5571008917849274982L;
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdf2=new SimpleDateFormat("HHmmss");
	public SER_REP_BDC generateReq(String str) throws IOException {
		String[] arr = str.split(",");
		String fileType = arr[1];
		int num = Integer.parseInt(arr[2]);
     SER_REP_BDC reqDto = new SER_REP_BDC();
     List<FIELD> head = reqDto.getHead().getField();
     List<FIELD> body = reqDto.getBody().getField();
     head.add(new FIELD("SendDate",sdf1.format(new Date())));
     head.add(new FIELD("SendTime",sdf2.format(new Date())));
     head.add(new FIELD("SendSeqNo",UUID.randomUUID().toString().replace("-", "")));
     head.add(new FIELD("TxUnitNo",arr[0]));
     head.add(new FIELD("SendNode","D00000"));
     head.add(new FIELD("TxCode","BDC205"));
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
	 body.add(new FIELD("FileType",fileType));
	 body.add(new FIELD("BatchPrjNo",""));
	 body.add(new FIELD("CrAcctNo","12009000094950"));
	 body.add(new FIELD("CrAcctName","盘锦市公积金中心"));
	 body.add(new FIELD("CrAcctClass","2"));
	 body.add(new FIELD("BankAcctNo",""));
	
	 body.add(new FIELD("Remark","批量贷款扣款交易"));
	 FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("FILE_LIST");
	 List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
	 FIELDS_LIST_INNER inner = new FIELDS_LIST_INNER("0");
	 StringBuffer acDataBuf = new StringBuffer();
	 BigDecimal totalCapAmt = new BigDecimal(0);
	 if("1".equals(fileType)) {
		 for(int i=0;i<num;i++) {
				 acDataBuf.append(String.valueOf(i+1)).append("@|$");
				 BigDecimal capAmt = new BigDecimal(Math.random()*1+1).setScale(2,BigDecimal.ROUND_HALF_UP);
				 totalCapAmt = totalCapAmt.add(capAmt);	
				 acDataBuf.append(capAmt).append("@|$");
					acDataBuf.append("622126010004155270").append("@|$");
					acDataBuf.append("测试").append("@|$");
					acDataBuf.append("0").append("@|$");
					acDataBuf.append("批量贷款扣款第"+(i+1)+"笔").append("@|$");
					acDataBuf.append("\r\n"); 
			 
		 }
	 }else if("2".equals(fileType)){
		 for(int i=0;i<num;i++) {
			 acDataBuf.append(String.valueOf(i+1)).append("@|$");
			 BigDecimal capAmt = new BigDecimal(Math.random()*1+1).setScale(2,BigDecimal.ROUND_HALF_UP);
			 totalCapAmt = totalCapAmt.add(capAmt);	
			 acDataBuf.append(capAmt).append("@|$");
				acDataBuf.append("1200100003301636").append("@|$");
				acDataBuf.append("公积金测试2").append("@|$");
				acDataBuf.append("104100000004").append("@|$");
				acDataBuf.append("104100000004").append("@|$");
				acDataBuf.append("沈阳市铁西区").append("@|$");
				acDataBuf.append("20151102003").append("@|$");
				acDataBuf.append("0").append("@|$");
				acDataBuf.append("批量贷款扣款第"+(i+1)+"笔").append("@|$");
				acDataBuf.append("\r\n"); 
		 }
	 }
	 
	 String fileName = "BDC_BAT_" + "0001" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + ".DAT";
	 String bcdString = FileFieldConv.fieldASCtoBCD(acDataBuf.toString(), PAF.ENCODING);
	// FileUtils.createFile(fileName, acDataBuf.toString());
	 inner.getField().add(new FIELD("DATA",bcdString));
	 inner.getField().add(new FIELD("NAME",fileName));
	 innerList.add(inner);
	 outer.setField_list(innerList);
	 reqDto.getBody().setField_list(outer);
	 body.add(new FIELD("BatchTotalAmt",totalCapAmt.toString()));
	 body.add(new FIELD("BatchCapAmt",totalCapAmt.toString()));
	 body.add(new FIELD("BatchTotalNum",String.valueOf(num)));
	 return reqDto;
	}
}


