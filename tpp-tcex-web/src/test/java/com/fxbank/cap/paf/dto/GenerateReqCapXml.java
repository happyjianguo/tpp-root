package com.fxbank.cap.paf.dto;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fxbank.cap.paf.utils.JAXBUtils;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenerateReqCapXml {
	public static String generateXml(String className,String str) throws JAXBException, UnsupportedEncodingException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Class cls=Class.forName("com.fxbank.cap.paf.dto."+className);
//		SER_REP_BDC reqDto
//		Method[] m = cls.getDeclaredMethods();
//		System.out.println(m.length);
		Method method2 = cls.getMethod("generateReq",String.class);
		Object obj = cls.newInstance();
		//此处注意下该invoke方法，参数为：执行该方法的对象，为该方法传入的参数列表
//		SER_REP_BDC reqDto =(SER_REP_BDC)
		SER_REP_BDC reqDto = (SER_REP_BDC)method2.invoke(obj,str);
		byte[] bs = JAXBUtils.marshal(reqDto);
		return new String(bs,"GBK");
	}
	

	public static void main(String[] args) throws JAXBException, UnsupportedEncodingException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//		System.out.println(xmlBDC202("1","05"));
		System.out.println(generateXml("REQ_BDC212",""));
	}
}

