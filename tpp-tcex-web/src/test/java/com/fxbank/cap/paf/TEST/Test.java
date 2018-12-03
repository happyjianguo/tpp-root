package com.fxbank.cap.paf.TEST;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.fxbank.cap.paf.dto.SER_REQ_BDC;
import com.fxbank.cap.paf.utils.JAXBUtils;

public class Test {

	private static String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <message> <head>"+
        "<field name=\"SendDate\">20181009</field>" +
        "<field name=\"MaxRec\"></field>" +
    "</head>" +
    "<body>" +
    "<field-list name=\"FILE_LIST\">" +
    "<field-list name=\"0\">" +
    "<field name=\"DATA\">abc</field>" +
    "<field name=\"NAME\">BDC_BAT_3302000000000000574801509113300000404.DAT</field>" +
    "</field-list>" +
    "</field-list>" +
       "<field name=\"CurrNo\">156</field>" +
        "<field name=\"CurrIden\">1</field>" +
        "</body>" +
        "</message>";

	public static void main(String[] args) throws UnsupportedEncodingException, JAXBException {
		SER_REQ_BDC reqData = (SER_REQ_BDC) JAXBUtils.unmarshal(data.getBytes("UTF-8"), SER_REQ_BDC.class);
		System.out.println(reqData);
		byte[] kk = JAXBUtils.marshal(reqData);
		System.out.println(new String(kk,"UTF-8"));

	}

}
