package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 登记信息核查结果汇总查询请求
 * @Author: 王鹏
 * @Date: 2019/7/2 16:38
 */
public class REQ_50023000209 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_BODY reqBody;

    public REQ_50023000209(){
        super.txDesc = "登记信息核查结果查询";
    }

    public REQ_APP_HEAD getReqAppHead() {
        return reqAppHead;
    }

    public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
        this.reqAppHead = reqAppHead;
    }

    public REQ_SYS_HEAD getReqSysHead() {
        return reqSysHead;
    }

    public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
        this.reqSysHead = reqSysHead;
    }

    public REQ_BODY getReqBody() {
        return reqBody;
    }

    public void setReqBody(REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "CHK_START_DATE")
        private Integer startDt;    // 核查开始时间

        @JSONField(name = "CHK_END_DATE")
        private Integer endDt; //核查结束时间

        @JSONField(name = "ORIG_CHK_ORG_ID")
        private String origBranchId; //原核查机构号

        @JSONField(name = "ORIG_CHK_TELLER_ID")
        private String origUserId; // 原核查柜员号

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号

        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;        //统一社会信用代码

        @JSONField(name = "CORP_NAME")
        private String entNm;    //单位名称

        @JSONField(name = "COMPANY_NM")
        private String nmOfLglPrsn;        //法定代表人或单位负责人姓名

        @JSONField(name = "REP_DOCUMENT_ID")
        private String IdOfLglPrsn; //法定代表人或单位负责人身份证件号

        @JSONField(name = "FONT_NAME")
        private String traNm;    //字号名称

        @JSONField(name = "OPRTNG_NAME")
        private String nm;        //经营者姓名

        @JSONField(name = "OPRTNG_DOCUMENT_ID")
        private String id;        //经营者证件号

        public Integer getStartDt() {
            return startDt;
        }

        public void setStartDt(Integer startDt) {
            this.startDt = startDt;
        }

        public Integer getEndDt() {
            return endDt;
        }

        public void setEndDt(Integer endDt) {
            this.endDt = endDt;
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

        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }

        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        public String getEntNm() {
            return entNm;
        }

        public void setEntNm(String entNm) {
            this.entNm = entNm;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getNmOfLglPrsn() {
            return nmOfLglPrsn;
        }

        public void setNmOfLglPrsn(String nmOfLglPrsn) {
            this.nmOfLglPrsn = nmOfLglPrsn;
        }

        public String getIdOfLglPrsn() {
            return IdOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            IdOfLglPrsn = idOfLglPrsn;
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
    }
}