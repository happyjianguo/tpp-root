package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 二代支付XML报文签名域
 * @Date : 2019/4/20 9:9
 */
public class PMTS_SIGN implements Serializable {

    private static final long serialVersionUID = -5650003059180478320L;

    private String beginFlag = "{S:";
    private String digitalSignature = null;
    private String endFlag = "}\r\n";

    public String creaFixPack() {
        if (this.digitalSignature == null) { // 无签名
            return "";
        } else { // 有签名
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("%s", this.beginFlag == null ? "" : this.beginFlag));
            sb.append(String.format("%s", this.digitalSignature == null ? "" : this.digitalSignature));
            sb.append(String.format("%s", this.endFlag == null ? "" : this.endFlag));
            return sb.toString();
        }
    }

    public void chanFixPack(String sign) {
        StringBuffer sb = new StringBuffer(sign);
        int i = 0;
        this.beginFlag = sb.substring(0, i = i + 3);
        this.digitalSignature = sb.substring(i, sb.length());
        this.endFlag = sb.substring(sb.length() - 1, sb.length());
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}