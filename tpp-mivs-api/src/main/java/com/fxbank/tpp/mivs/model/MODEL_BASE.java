package com.fxbank.tpp.mivs.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.tpp.mivs.util.PmtsXmlUtil;

/**
 * @Description: 请求报文基类
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:36:15
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MODEL_BASE extends ModelBase implements Serializable, SIGN_DATA {

	private static final long serialVersionUID = -6652288226005628489L;

	@XmlTransient
	protected String mesgType; // 报文类型代码

	@XmlAttribute(name = "xmlns")
	protected String XMLNS;

	@XmlAttribute(name = "xmlns:xsi")
	protected String XMLNS_XSI;

	public MODEL_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlTransient
	private PMTS_HEAD head = new PMTS_HEAD();

	@XmlTransient
	private PMTS_SIGN sign = new PMTS_SIGN();

	/**
	 * @Description: 生成请求报文
	 * @Author: 周勇沩
	 * @Date: 2019-04-20 14:29:50
	 */
	public String creaPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getHeader().creaFixPack());
		sb.append(this.getSign().creaFixPack());
		sb.append(PmtsXmlUtil.objectToXml(this));
		return sb.toString();
	}

	/**
	 * @Description: 生成报文的MSGID
	 * @Author: 周勇沩
	 * @Date: 2019-04-20 14:26:37
	 */
	public String msgId() {
		return String.format("%08d%08d", super.getSysDate(), super.getSysTraceno());
	}

	/**
	 * @Description: 生成报文中的CreDtTm
	 * @Author: 周勇沩
	 * @Date: 2019-04-20 14:26:55
	 */
	public String creDtTm() {
		try {
			String dateTime = String.format("%08d%06d", super.getSysDate(), super.getSysTime());
			Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dateTime);
			String sDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
			return sDateTime;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the header
	 */
	public PMTS_HEAD getHeader() {
		return head;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(PMTS_HEAD header) {
		this.head = header;
	}

	/**
	 * @return the sign
	 */
	public PMTS_SIGN getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(PMTS_SIGN sign) {
		this.sign = sign;
	}

	/**
	 * @return the mesgType
	 */
	public String getMesgType() {
		return mesgType;
	}

	/**
	 * @param xMLNS the xMLNS to set
	 */
	public void setXMLNS(String xMLNS) {
		XMLNS = xMLNS;
	}

	/**
	 * @return the xMLNS
	 */
	public String getXMLNS() {
		return XMLNS;
	}

	/**
	 * @param xMLNS_XSI the xMLNS_XSI to set
	 */
	public void setXMLNS_XSI(String xMLNS_XSI) {
		XMLNS_XSI = xMLNS_XSI;
	}

	/**
	 * @return the xMLNS_XSI
	 */
	public String getXMLNS_XSI() {
		return XMLNS_XSI;
	}
}