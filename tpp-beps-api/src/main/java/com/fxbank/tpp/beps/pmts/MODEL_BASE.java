package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.beps.constant.CONST;
import com.fxbank.tpp.beps.util.PmtsXmlUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : 周勇沩
 * @description: 请求报文基类
 * @Date : 2020/4/20 9:36
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MODEL_BASE extends ModelBase implements Serializable, SIGN_DATA {

    private static final long serialVersionUID = -8499304188268368371L;

    @XmlTransient
    protected String mesgType; // 报文类型代码

    @XmlAttribute(name = "xmlns")
    protected String XMLNS;

    @XmlAttribute(name = "xmlns:xsi")
    protected String XMLNS_XSI;

    @XmlTransient
    private PMTS_HEAD head = new PMTS_HEAD();

    @XmlTransient
    private PMTS_SIGN sign = new PMTS_SIGN();

    public MODEL_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    /**
     * @Description: 生成请求报文
     * @Author: 周勇沩
     * @Date: 2019-04-20 14:29:50
     */
    public String creaPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(FixedUtil.toFixed(this.head, CONST.CODE));
        sb.append(this.getSign().creaFixPack());
        sb.append(PmtsXmlUtil.objectToXml(this));
        return sb.toString();
    }

    /**
     * @description: 生成报文的MSGID
     * @Date : 2019/4/20 14:26
     */
    protected String msgId() {
        return String.format("%08d%08d", super.getSysDate(), super.getSysTraceno());
    }

    /**
     * @description: 生成报文中的CreDtTm
     * @Date : 2019/4/20 14:26
     */
    protected String creDtTm() {
        try {
            String dateTime = String.format("%08d%06d", super.getSysDate(), super.getSysTime());
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dateTime);
            String sDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
            return sDateTime;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMesgType() {
        return mesgType;
    }

    public void setMesgType(String mesgType) {
        this.mesgType = mesgType;
    }

    public String getXMLNS() {
        return XMLNS;
    }

    public void setXMLNS(String XMLNS) {
        this.XMLNS = XMLNS;
    }

    public String getXMLNS_XSI() {
        return XMLNS_XSI;
    }

    public void setXMLNS_XSI(String XMLNS_XSI) {
        this.XMLNS_XSI = XMLNS_XSI;
    }

    public PMTS_HEAD getHead() {
        return head;
    }

    public void setHead(PMTS_HEAD head) {
        this.head = head;
    }

    public PMTS_SIGN getSign() {
        return sign;
    }

    public void setSign(PMTS_SIGN sign) {
        this.sign = sign;
    }
}