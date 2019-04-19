/**   
* @Title: sd.java 
* @Package com.fxbank.tpp.mivs.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Duzhenduo
* @date 2019年4月19日 上午10:54:27 
* @version V1.0   
*/
package com.fxbank.tpp.mivs.simu;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Document")
public class Document {
	@XmlElement(name = "GetIdVrfctn")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	

}



