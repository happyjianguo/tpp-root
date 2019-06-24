package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 登记信息联网核查申请ESB请求报文
 * @Author: 王鹏
 * @Date: 2019/5/20 16:40
 */
public class REQ_50023000203 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000203.REQ_BODY reqBody;

    public REQ_50023000203(){
        super.txDesc = "登记信息联网核查";
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


    public REQ_50023000203.REQ_BODY getReqBody() {
        return reqBody;
    }


    public void setReqBody(REQ_50023000203.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {
        @JSONField(name = "MAKET_TYPE")
        private String marketType;    //主体类型
        @JSONField(name = "TRA_NM")
        private String tranm;		//字号名称
        @JSONField(name = "NAME")
        private String nm;		//经营者姓名
        @JSONField(name = "ID")
        private String id;    //经营者证件号
        @JSONField(name = "ENT_NAME")
        private String entNm;    //企业名称
        @JSONField(name = "SOCIAL_CODE")
        private String uniSocCdtCd;		//统一社会信用代码
        @JSONField(name = "NAME_OF_LGL_PRSN")
        private String nmOfLglPrsn;		//法定代表人或单位负责人姓名
        @JSONField(name = "ID_OF_LGL_PRSN")
        private String idOfLglPrsn;    //法定代表人或单位负责人身份证件号
        @JSONField(name = "AgtNm")
        private String agtNm;    //代理人姓名
        @JSONField(name = "AgtId")
        private String agtId;    //代理人身份证件号码
        @JSONField(name = "OPRTR_NAME")
        private String opNm;		//操作员姓名
        @JSONField(name = "RESERVE_FIELD1")
        private String remarks1;		//备用字段1
        @JSONField(name = "RESERVE_FIELD2")
        private String remarks2;		//备用字段2
        @JSONField(name = "RESERVE_FIELD3")
        private String remarks3;		//备用字段3

        public String getMarketType() {
            return marketType;
        }

        public void setMarketType(String marketType) {
            this.marketType = marketType;
        }

        public String getTranm() {
            return tranm;
        }

        public void setTranm(String tranm) {
            this.tranm = tranm;
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
            return idOfLglPrsn;
        }

        public void setIdOfLglPrsn(String idOfLglPrsn) {
            this.idOfLglPrsn = idOfLglPrsn;
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

        public String getOpNm() {
            return opNm;
        }

        public void setOpNm(String opNm) {
            this.opNm = opNm;
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