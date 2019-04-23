package com.fxbank.tpp.mivs.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @Description: 二代支付XML报文Bean转换
 * @Author: 周勇沩
 * @Date: 2019-04-20 08:35:54
 */
public class PmtsXmlUtil {

	public static String objectToXml(Object object) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
			marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
			marshal.setProperty("jaxb.encoding", "utf-8");
			marshal.marshal(object, writer);
			return new String(writer.getBuffer());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T xmlToObject(Class<T> clazz, String xml){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);

			SAXParserFactory sax = SAXParserFactory.newInstance();
			sax.setNamespaceAware(false);
			XMLReader xmlReader = sax.newSAXParser().getXMLReader();

			Source source = new SAXSource(xmlReader, new InputSource(reader));
			return (T) unmarshaller.unmarshal(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
