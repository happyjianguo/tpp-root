package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description: 手机号联网核查结果查询应答报文
 * @Author: 王鹏
 * @Date: 2019/6/24 11:20
 */
public class REP_50023000202 extends REP_BASE {

    @JSONField(name = "APP_HEAD")
    private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

    @JSONField(name = "SYS_HEAD")
    private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

    @JSONField(name = "BODY")
    private REP_BODY repBody = new REP_BODY();

    public REP_APP_HEAD getRepAppHead() {
        return repAppHead;
    }

    public void setRepAppHead(REP_APP_HEAD repAppHead) {
        this.repAppHead = repAppHead;
    }

    public REP_SYS_HEAD getRepSysHead() {
        return repSysHead;
    }

    public void setRepSysHead(REP_SYS_HEAD repSysHead) {
        this.repSysHead = repSysHead;
    }

    public REP_BODY getRepBody() {
        return repBody;
    }

    public void setRepBody(REP_BODY repBody) {
        this.repBody = repBody;
    }

    public class REP_BODY {
        //循环开始
        @JSONField(name = "ResultList")
        private List<resultList> resultList;//查询结果信息

        public List<REP_50023000202.resultList> getResultList() {
            return resultList;
        }

        public void setResultList(List<REP_50023000202.resultList> resultList) {
            this.resultList = resultList;
        }

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "resultList=" + resultList +
                    '}';
        }
    }

    public static class resultList{

        @JSONField(name = "OrigTranDate")
        private String origTranDate;

        @JSONField(name = "OrigSeqNo")
        private String origSeqNo;

        @JSONField(name = "OrigTranTime")
        private String origTranTime;

        @JSONField(name = "OrgnlDlvrgMsgId")
        private String orgnlDlvrgMsgId;

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name = "MobNb")
        private String mobNb;

        @JSONField(name = "Nm")
        private String nm;

        @JSONField(name = "IdTp")
        private String idTp;

        @JSONField(name = "Id")
        private String id;

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd;

        @JSONField(name = "BizRegNb")
        private String bizRegNb;

        @JSONField(name = "Rslt")
        private String rslt;

        @JSONField(name = "MobCrr")
        private String mobCrr;

        @JSONField(name = "LocMobNb")
        private String locMobNb;

        @JSONField(name = "LocNmMobNb")
        private String locNmMobNb;

        @JSONField(name = "CdTp")
        private String cdTp;

        @JSONField(name = "Sts")
        private String sts;

        @JSONField(name = "ProcSts")
        private String procSts;

        @JSONField(name = "ProcCd")
        private String procCd;

        @JSONField(name = "Rjctinf")
        private String rjctinf;

        @JSONField(name = "Remarks1")
        private String remarks1;

        @JSONField(name = "Remarks2")
        private String remarks2;

        @JSONField(name = "Remarks3")
        private String remarks3;

        public String getOrigTranDate() {
            return origTranDate;
        }

        public void setOrigTranDate(String origTranDate) {
            this.origTranDate = origTranDate;
        }

        public String getOrigSeqNo() {
            return origSeqNo;
        }

        public void setOrigSeqNo(String origSeqNo) {
            this.origSeqNo = origSeqNo;
        }

        public String getOrigTranTime() {
            return origTranTime;
        }

        public void setOrigTranTime(String origTranTime) {
            this.origTranTime = origTranTime;
        }

        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }

        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public String getMobNb() {
            return mobNb;
        }

        public void setMobNb(String mobNb) {
            this.mobNb = mobNb;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getIdTp() {
            return idTp;
        }

        public void setIdTp(String idTp) {
            this.idTp = idTp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getBizRegNb() {
            return bizRegNb;
        }

        public void setBizRegNb(String bizRegNb) {
            this.bizRegNb = bizRegNb;
        }

        public String getRslt() {
            return rslt;
        }

        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        public String getMobCrr() {
            return mobCrr;
        }

        public void setMobCrr(String mobCrr) {
            this.mobCrr = mobCrr;
        }

        public String getLocMobNb() {
            return locMobNb;
        }

        public void setLocMobNb(String locMobNb) {
            this.locMobNb = locMobNb;
        }

        public String getLocNmMobNb() {
            return locNmMobNb;
        }

        public void setLocNmMobNb(String locNmMobNb) {
            this.locNmMobNb = locNmMobNb;
        }

        public String getCdTp() {
            return cdTp;
        }

        public void setCdTp(String cdTp) {
            this.cdTp = cdTp;
        }

        public String getSts() {
            return sts;
        }

        public void setSts(String sts) {
            this.sts = sts;
        }

        public String getProcSts() {
            return procSts;
        }

        public void setProcSts(String procSts) {
            this.procSts = procSts;
        }

        public String getProcCd() {
            return procCd;
        }

        public void setProcCd(String procCd) {
            this.procCd = procCd;
        }

        public String getRjctinf() {
            return rjctinf;
        }

        public void setRjctinf(String rjctinf) {
            this.rjctinf = rjctinf;
        }

        public String getRemarks1() {
            return remarks1;
        }

        public void setRemarks1(String remarks1) {
            this.remarks1 = remarks1;
        }

        public String getRemarks2() {
            return remarks2;
        }

        public void setRemarks2(String remarks2) {
            this.remarks2 = remarks2;
        }

        public String getRemarks3() {
            return remarks3;
        }

        public void setRemarks3(String remarks3) {
            this.remarks3 = remarks3;
        }

        @Override
        public String toString() {
            return "resultList{" +
                    "origTranDate='" + origTranDate + '\'' +
                    ", origSeqNo='" + origSeqNo + '\'' +
                    ", origTranTime='" + origTranTime + '\'' +
                    ", orgnlDlvrgMsgId='" + orgnlDlvrgMsgId + '\'' +
                    ", orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", mobNb='" + mobNb + '\'' +
                    ", nm='" + nm + '\'' +
                    ", idTp='" + idTp + '\'' +
                    ", id='" + id + '\'' +
                    ", uniSocCdtCd='" + uniSocCdtCd + '\'' +
                    ", bizRegNb='" + bizRegNb + '\'' +
                    ", rslt='" + rslt + '\'' +
                    ", mobCrr='" + mobCrr + '\'' +
                    ", locMobNb='" + locMobNb + '\'' +
                    ", locNmMobNb='" + locNmMobNb + '\'' +
                    ", cdTp='" + cdTp + '\'' +
                    ", sts='" + sts + '\'' +
                    ", procSts='" + procSts + '\'' +
                    ", procCd='" + procCd + '\'' +
                    ", rjctinf='" + rjctinf + '\'' +
                    ", remarks1='" + remarks1 + '\'' +
                    ", remarks2='" + remarks2 + '\'' +
                    ", remarks3='" + remarks3 + '\'' +
                    '}';
        }
    }
}