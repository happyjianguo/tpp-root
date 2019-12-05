package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/2 16:38
 */
public class REP_50023000209 extends REP_BASE {

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
        @JSONField(name = "ITEM_INFO_ARRAY")
        private List<resultList> resultList;//查询结果信息

        public List<resultList> getResultList() {
            return resultList;
        }

        public void setResultList(List<resultList> resultList) {
            this.resultList = resultList;
        }

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "resultList=" + resultList +
                    '}';
        }

    }

    public static class resultList {

        @JSONField(name = "ORIG_CHK_DATE")
        private String origTranDate;

        @JSONField(name = "ORIG_CHK_SEQ_NO")
        private String origSeqNo;

        @JSONField(name = "ORIG_CHK_TIME")
        private String origTranTime;

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId;

        @JSONField(name = "ORIG_INSTG_PTY")
        private String origInstgPty;

        @JSONField(name = "ORIG_CHK_ORG_ID")
        private String origBranchId;

        @JSONField(name = "ORIG_CHK_TELLER_ID")
        private String origUserId;

        @JSONField(name = "MARKET_BODY_TYPE")
        private String marketType;

        @JSONField(name = "CORP_NAME")
        private String entNm;

        @JSONField(name = "COMPANY_NM")
        private String nmOfLglPrsn;

        @JSONField(name = "REP_DOCUMENT_ID")
        private String idOfLglPrsn;

        @JSONField(name = "FONT_NAME")
        private String traNm;

        @JSONField(name = "OPRTNG_NAME")
        private String nm;

        @JSONField(name = "OPRTNG_DOCUMENT_ID")
        private String id;

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocDdtCd;

        @JSONField(name = "PROXY_NAME")
        private String agtNm;

        @JSONField(name = "PROXY_ID_NO")
        private String agtId;

        @JSONField(name = "ITEM_INFO_RESULT")
        private String rslt;

        @JSONField(name = "CHK_RESULT_DATE")
        private String dataResrcDt;

        @JSONField(name = "BASENT_INFO_CNT")
        private String basInfoCnt;

        @JSONField(name = "CSF_INFO_CNT")
        private String coShrhdrfndInfoCnt;

        @JSONField(name = "DSM_INFO_CNT")
        private String dirSupsrsgrInfoCnt;

        @JSONField(name = "CHNG_INFO_CNT")
        private String chngInfoCnt;

        @JSONField(name = "ABNORMAL_INFO_CNT")
        private String abnmlBizInfoCnt;

        @JSONField(name = "DSCRT_INFO_CNT")
        private String illDscrtInfoCnt;

        @JSONField(name = "LIC_NULL_INFO_CNT")
        private String licNullCnt;

        @JSONField(name = "MSG_RFS_STATUS")
        private String procSts;

        @JSONField(name = "MSG_RFS_CODE")
        private String procCd;

        @JSONField(name = "MSG_RFS_INFO")
        private String rjctinf;

        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1;

        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2;

        @JSONField(name = "RESERVE_FIELD3")
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

        public String getOrigInstgPty() {
            return origInstgPty;
        }

        public void setOrigInstgPty(String origInstgPty) {
            this.origInstgPty = origInstgPty;
        }

        public String getOrigBranchId() {
            return origBranchId;
        }

        public void setOrigBranchId(String origBranchId) {
            this.origBranchId = origBranchId;
        }

        public String getOrigUserId() {
            return origUserId;
        }

        public void setOrigUserId(String origUserId) {
            this.origUserId = origUserId;
        }

        public String getMarketType() {
            return marketType;
        }

        public void setMarketType(String marketType) {
            this.marketType = marketType;
        }

        public String getEntNm() {
            return entNm;
        }

        public void setEntNm(String entNm) {
            this.entNm = entNm;
        }

        public String getNmOfLglPrsn() {
            return nmOfLglPrsn;
        }

        public void setNmOfLglPrsn(String nmOfLglPrsn) {
            this.nmOfLglPrsn = nmOfLglPrsn;
        }

        public String getIdOfLglPrsn() {
            return idOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            this.idOfLglPrsn = idOfLglPrsn;
        }

        public String getTraNm() {
            return traNm;
        }

        public void setTraNm(String traNm) {
            this.traNm = traNm;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniSocDdtCd() {
            return uniSocDdtCd;
        }

        public void setUniSocDdtCd(String uniSocDdtCd) {
            this.uniSocDdtCd = uniSocDdtCd;
        }

        public String getAgtNm() {
            return agtNm;
        }

        public void setAgtNm(String agtNm) {
            this.agtNm = agtNm;
        }

        public String getAgtId() {
            return agtId;
        }

        public void setAgtId(String agtId) {
            this.agtId = agtId;
        }

        public String getRslt() {
            return rslt;
        }

        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        public String getDataResrcDt() {
            return dataResrcDt;
        }

        public void setDataResrcDt(String dataResrcDt) {
            this.dataResrcDt = dataResrcDt;
        }

        public String getBasInfoCnt() {
            return basInfoCnt;
        }

        public void setBasInfoCnt(String basInfoCnt) {
            this.basInfoCnt = basInfoCnt;
        }

        public String getCoShrhdrfndInfoCnt() {
            return coShrhdrfndInfoCnt;
        }

        public void setCoShrhdrfndInfoCnt(String coShrhdrfndInfoCnt) {
            this.coShrhdrfndInfoCnt = coShrhdrfndInfoCnt;
        }

        public String getDirSupsrsgrInfoCnt() {
            return dirSupsrsgrInfoCnt;
        }

        public void setDirSupsrsgrInfoCnt(String dirSupsrsgrInfoCnt) {
            this.dirSupsrsgrInfoCnt = dirSupsrsgrInfoCnt;
        }

        public String getChngInfoCnt() {
            return chngInfoCnt;
        }

        public void setChngInfoCnt(String chngInfoCnt) {
            this.chngInfoCnt = chngInfoCnt;
        }

        public String getAbnmlBizInfoCnt() {
            return abnmlBizInfoCnt;
        }

        public void setAbnmlBizInfoCnt(String abnmlBizInfoCnt) {
            this.abnmlBizInfoCnt = abnmlBizInfoCnt;
        }

        public String getIllDscrtInfoCnt() {
            return illDscrtInfoCnt;
        }

        public void setIllDscrtInfoCnt(String illDscrtInfoCnt) {
            this.illDscrtInfoCnt = illDscrtInfoCnt;
        }

        public String getLicNullCnt() {
            return licNullCnt;
        }

        public void setLicNullCnt(String licNullCnt) {
            this.licNullCnt = licNullCnt;
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
    }
}
