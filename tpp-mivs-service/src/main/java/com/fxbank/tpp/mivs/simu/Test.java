package com.fxbank.tpp.mivs.simu;



import java.util.ArrayList;
import java.util.List;

import com.fxbank.tpp.mivs.simu.JaxbUtil.CollectionWrapper;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//创建java对象
		
		Document doc=new Document();
		
        doc.setName("GetIdVrfctn");
		
		//将java对象转换为XML字符串
		JaxbUtil requestBinder = new JaxbUtil(Document.class,
				CollectionWrapper.class);
		String retXml = requestBinder.toXml(doc, "utf-8");
		System.out.println("xml:"+retXml);
		
		//将xml字符串转换为java对象
		JaxbUtil resultBinder = new JaxbUtil(Document.class,
				CollectionWrapper.class);
		Document hotelObj = resultBinder.fromXml(retXml);
		
		
		
		
	}

}