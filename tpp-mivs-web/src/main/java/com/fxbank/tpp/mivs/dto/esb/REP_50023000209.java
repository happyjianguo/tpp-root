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
        @JSONField(name = "ResultList")
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

        @JSONField(name = "OrigTranDate")
        private String origTranDate;

        @JSONField(name = "OrigSeqNo")
        private String origSeqNo;

        @JSONField(name = "OrigTranTime")
        private String origTranTime;

        @JSONField(name = "OrgnlDlvrgMsgId")
        private String orgnlDlvrgMsgId;

        @JSONField(name = "OrigBranchId")
        private String origBranchId;

        @JSONField(name = "OrigUserId")
        private String origUserId;

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

        //登记核查结果
        @JSONField(name = "TXPYR_INFO_VER_RESULT")
        private String rslt;

        //“数据源日期”
        @JSONField(name = "DATA_SOURCE_DATE")
        private String dataResrcD;

        //循环开始
        @JSONField(name = "BasInfoEnt")
        private List<BasInfoEnt> basInfoEntList;//企业照面信息部分

        //循环开始
        @JSONField(name = "BasInfOfSlfEplydPpl")
        private List<BasInfOfSlfEplydPpl> basInfOfSlfEplydPplList;//个体户照面信息部分

        //循环开始
        @JSONField(name = "CoShrhdrFndInfo")
        private List<CoShrhdrFndInfo> coShrhdrFndInfoList;//企业股东及出资信息部分

        //循环开始
        @JSONField(name = "DirSupSrMgrInfo")
        private List<DirSupSrMgrInfo> dirSupSrMgrInfoList;//董事监事及高管信息

        //循环开始
        @JSONField(name = "ChngInfo")
        private List<ChngInfo> chngInfoList;//变更信息

        //循环开始
        @JSONField(name = "AbnmlBizInfo")
        private List<AbnmlBizInfo> abnmlBizInfoList;//异常经营信息

        //循环开始
        @JSONField(name = "IllDscrtInfo")
        private List<IllDscrtInfo> illDscrtInfoList;//严重违法失信信息

        //循环开始
        @JSONField(name = "LicNull")
        private List<LicNull> licNullList;//营业执照作废声明

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

        public String getRslt() {
            return rslt;
        }

        public void setRslt(String rslt) {
            this.rslt = rslt;
        }

        public String getDataResrcD() {
            return dataResrcD;
        }

        public void setDataResrcD(String dataResrcD) {
            this.dataResrcD = dataResrcD;
        }

        public List<BasInfoEnt> getBasInfoEntList() {
            return basInfoEntList;
        }

        public void setBasInfoEntList(List<BasInfoEnt> basInfoEntList) {
            this.basInfoEntList = basInfoEntList;
        }

        public List<BasInfOfSlfEplydPpl> getBasInfOfSlfEplydPplList() {
            return basInfOfSlfEplydPplList;
        }

        public void setBasInfOfSlfEplydPplList(List<BasInfOfSlfEplydPpl> basInfOfSlfEplydPplList) {
            this.basInfOfSlfEplydPplList = basInfOfSlfEplydPplList;
        }

        public List<CoShrhdrFndInfo> getCoShrhdrFndInfoList() {
            return coShrhdrFndInfoList;
        }

        public void setCoShrhdrFndInfoList(List<CoShrhdrFndInfo> coShrhdrFndInfoList) {
            this.coShrhdrFndInfoList = coShrhdrFndInfoList;
        }

        public List<DirSupSrMgrInfo> getDirSupSrMgrInfoList() {
            return dirSupSrMgrInfoList;
        }

        public void setDirSupSrMgrInfoList(List<DirSupSrMgrInfo> dirSupSrMgrInfoList) {
            this.dirSupSrMgrInfoList = dirSupSrMgrInfoList;
        }

        public List<ChngInfo> getChngInfoList() {
            return chngInfoList;
        }

        public void setChngInfoList(List<ChngInfo> chngInfoList) {
            this.chngInfoList = chngInfoList;
        }

        public List<AbnmlBizInfo> getAbnmlBizInfoList() {
            return abnmlBizInfoList;
        }

        public void setAbnmlBizInfoList(List<AbnmlBizInfo> abnmlBizInfoList) {
            this.abnmlBizInfoList = abnmlBizInfoList;
        }

        public List<IllDscrtInfo> getIllDscrtInfoList() {
            return illDscrtInfoList;
        }

        public void setIllDscrtInfoList(List<IllDscrtInfo> illDscrtInfoList) {
            this.illDscrtInfoList = illDscrtInfoList;
        }

        public List<LicNull> getLicNullList() {
            return licNullList;
        }

        public void setLicNullList(List<LicNull> licNullList) {
            this.licNullList = licNullList;
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

        @Override
        public String toString() {
            return "REP_BODY{" +
                    "rslt='" + rslt + '\'' +
                    ", dataResrcD='" + dataResrcD + '\'' +
                    ", basInfoEntList=" + basInfoEntList +
                    ", basInfOfSlfEplydPplList=" + basInfOfSlfEplydPplList +
                    ", coShrhdrFndInfoList=" + coShrhdrFndInfoList +
                    ", dirSupSrMgrInfoList=" + dirSupSrMgrInfoList +
                    ", chngInfoList=" + chngInfoList +
                    ", abnmlBizInfoList=" + abnmlBizInfoList +
                    ", illDscrtInfoList=" + illDscrtInfoList +
                    ", licNullList=" + licNullList +
                    ", procSts='" + procSts + '\'' +
                    ", procCd='" + procCd + '\'' +
                    ", rjctinf='" + rjctinf + '\'' +
                    '}';
        }
    }

    //以下信息 当“纳税信息核查结果”为“MCHD” 时填
    public static class BasInfoEnt{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="MARKETTYPE")
        private Integer marketType;

        @JSONField(name = "EntNm")
        private String entNm;

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd;

        @JSONField(name = "CoTp")
        private String coTp;

        @JSONField(name = "Dom")
        private String dom;

        @JSONField(name = "RegCptl")
        private String regCptl;

        @JSONField(name = "DtEst")
        private String dtEst;

        @JSONField(name = "OpPrdFrom")
        private String opPrdFrom;

        @JSONField(name = "OpPrdTo")
        private String opPrdTo;

        @JSONField(name = "RegSts")
        private String regSts;

        @JSONField(name = "NmOfLglPrsn")
        private String nmOfLglPrsn;

        @JSONField(name = "RegAuth")
        private String regAuth;

        @JSONField(name = "BizScp")
        private String bizScp;

        @JSONField(name = "dtAppr")
        private String dtAppr;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getMarketType() {
            return marketType;
        }

        public void setMarketType(Integer marketType) {
            this.marketType = marketType;
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

        public String getCoTp() {
            return coTp;
        }

        public void setCoTp(String coTp) {
            this.coTp = coTp;
        }

        public String getDom() {
            return dom;
        }

        public void setDom(String dom) {
            this.dom = dom;
        }

        public String getRegCptl() {
            return regCptl;
        }

        public void setRegCptl(String regCptl) {
            this.regCptl = regCptl;
        }

        public String getDtEst() {
            return dtEst;
        }

        public void setDtEst(String dtEst) {
            this.dtEst = dtEst;
        }

        public String getOpPrdFrom() {
            return opPrdFrom;
        }

        public void setOpPrdFrom(String opPrdFrom) {
            this.opPrdFrom = opPrdFrom;
        }

        public String getOpPrdTo() {
            return opPrdTo;
        }

        public void setOpPrdTo(String opPrdTo) {
            this.opPrdTo = opPrdTo;
        }

        public String getRegSts() {
            return regSts;
        }

        public void setRegSts(String regSts) {
            this.regSts = regSts;
        }

        public String getNmOfLglPrsn() {
            return nmOfLglPrsn;
        }

        public void setNmOfLglPrsn(String nmOfLglPrsn) {
            this.nmOfLglPrsn = nmOfLglPrsn;
        }

        public String getRegAuth() {
            return regAuth;
        }

        public void setRegAuth(String regAuth) {
            this.regAuth = regAuth;
        }

        public String getBizScp() {
            return bizScp;
        }

        public void setBizScp(String bizScp) {
            this.bizScp = bizScp;
        }

        public String getDtAppr() {
            return dtAppr;
        }

        public void setDtAppr(String dtAppr) {
            this.dtAppr = dtAppr;
        }

        @Override
        public String toString() {
            return "BasInfoEnt{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", marketType=" + marketType +
                    ", entNm='" + entNm + '\'' +
                    ", uniSocCdtCd='" + uniSocCdtCd + '\'' +
                    ", coTp='" + coTp + '\'' +
                    ", dom='" + dom + '\'' +
                    ", regCptl='" + regCptl + '\'' +
                    ", dtEst='" + dtEst + '\'' +
                    ", opPrdFrom='" + opPrdFrom + '\'' +
                    ", opPrdTo='" + opPrdTo + '\'' +
                    ", regSts='" + regSts + '\'' +
                    ", nmOfLglPrsn='" + nmOfLglPrsn + '\'' +
                    ", regAuth='" + regAuth + '\'' +
                    ", bizScp='" + bizScp + '\'' +
                    ", dtAppr='" + dtAppr + '\'' +
                    '}';
        }
    }

    public static class BasInfOfSlfEplydPpl {

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name = "PGNB")
        private Integer pgNb;

        @JSONField(name = "MARKETTYPE")
        private Integer marketType;

        @JSONField(name = "TraNm")
        private String traNm;

        @JSONField(name = "UniSocCdtCd")
        private String uniSocCdtCd;

        @JSONField(name = "CoTp")
        private String coTp;

        @JSONField(name = "OpLoc")
        private String opLoc;

        @JSONField(name = "FdAmt")
        private String fdAmt;

        @JSONField(name = "DtReg")
        private String dtReg;

        @JSONField(name = "RegSts")
        private String regSts;

        @JSONField(name = "Nm")
        private String nm;

        @JSONField(name = "RegAuth")
        private String regAuth;

        @JSONField(name = "BizScp")
        private String bizScp;

        @JSONField(name = "dtAppr")
        private String dtAppr;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getMarketType() {
            return marketType;
        }

        public void setMarketType(Integer marketType) {
            this.marketType = marketType;
        }

        public String getTraNm() {
            return traNm;
        }

        public void setTraNm(String traNm) {
            this.traNm = traNm;
        }

        public String getUniSocCdtCd() {
            return uniSocCdtCd;
        }

        public void setUniSocCdtCd(String uniSocCdtCd) {
            this.uniSocCdtCd = uniSocCdtCd;
        }

        public String getCoTp() {
            return coTp;
        }

        public void setCoTp(String coTp) {
            this.coTp = coTp;
        }

        public String getOpLoc() {
            return opLoc;
        }

        public void setOpLoc(String opLoc) {
            this.opLoc = opLoc;
        }

        public String getFdAmt() {
            return fdAmt;
        }

        public void setFdAmt(String fdAmt) {
            this.fdAmt = fdAmt;
        }

        public String getDtReg() {
            return dtReg;
        }

        public void setDtReg(String dtReg) {
            this.dtReg = dtReg;
        }

        public String getRegSts() {
            return regSts;
        }

        public void setRegSts(String regSts) {
            this.regSts = regSts;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getRegAuth() {
            return regAuth;
        }

        public void setRegAuth(String regAuth) {
            this.regAuth = regAuth;
        }

        public String getBizScp() {
            return bizScp;
        }

        public void setBizScp(String bizScp) {
            this.bizScp = bizScp;
        }

        public String getDtAppr() {
            return dtAppr;
        }

        public void setDtAppr(String dtAppr) {
            this.dtAppr = dtAppr;
        }

        @Override
        public String toString() {
            return "BasInfOfSlfEplydPpl{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", marketType=" + marketType +
                    ", traNm='" + traNm + '\'' +
                    ", uniSocCdtCd='" + uniSocCdtCd + '\'' +
                    ", coTp='" + coTp + '\'' +
                    ", opLoc='" + opLoc + '\'' +
                    ", fdAmt='" + fdAmt + '\'' +
                    ", dtReg='" + dtReg + '\'' +
                    ", regSts='" + regSts + '\'' +
                    ", nm='" + nm + '\'' +
                    ", regAuth='" + regAuth + '\'' +
                    ", bizScp='" + bizScp + '\'' +
                    ", dtAppr='" + dtAppr + '\'' +
                    '}';
        }
    }

    public static class CoShrhdrFndInfo {

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="CoShrhdrfndInfoNb")
        private Integer coShrhdrfndInfoNb;

        @JSONField(name ="NatlPrsnFlag")
        private Integer natlPrsnFlag;

        @JSONField(name = "InvtrNm")
        private String invtrNm;

        @JSONField(name = "InvtrId")
        private String invtrId;

        @JSONField(name = "SubscrCptlConAmt")
        private String subscrCptlConAmt;

        @JSONField(name = "ActlCptlConAmt")
        private String actlCptlConAmt;

        @JSONField(name = "SubscrCptlConFm")
        private String subscrCptlConFm;

        @JSONField(name = "SubscrCptlConDt")
        private String subscrCptlConDt;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getCoShrhdrfndInfoNb() {
            return coShrhdrfndInfoNb;
        }

        public void setCoShrhdrfndInfoNb(Integer coShrhdrfndInfoNb) {
            this.coShrhdrfndInfoNb = coShrhdrfndInfoNb;
        }

        public Integer getNatlPrsnFlag() {
            return natlPrsnFlag;
        }

        public void setNatlPrsnFlag(Integer natlPrsnFlag) {
            this.natlPrsnFlag = natlPrsnFlag;
        }

        public String getInvtrNm() {
            return invtrNm;
        }

        public void setInvtrNm(String invtrNm) {
            this.invtrNm = invtrNm;
        }

        public String getInvtrId() {
            return invtrId;
        }

        public void setInvtrId(String invtrId) {
            this.invtrId = invtrId;
        }

        public String getSubscrCptlConAmt() {
            return subscrCptlConAmt;
        }

        public void setSubscrCptlConAmt(String subscrCptlConAmt) {
            this.subscrCptlConAmt = subscrCptlConAmt;
        }

        public String getActlCptlConAmt() {
            return actlCptlConAmt;
        }

        public void setActlCptlConAmt(String actlCptlConAmt) {
            this.actlCptlConAmt = actlCptlConAmt;
        }

        public String getSubscrCptlConFm() {
            return subscrCptlConFm;
        }

        public void setSubscrCptlConFm(String subscrCptlConFm) {
            this.subscrCptlConFm = subscrCptlConFm;
        }

        public String getSubscrCptlConDt() {
            return subscrCptlConDt;
        }

        public void setSubscrCptlConDt(String subscrCptlConDt) {
            this.subscrCptlConDt = subscrCptlConDt;
        }

        @Override
        public String toString() {
            return "CoShrhdrFndInfo{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", coShrhdrfndInfoNb=" + coShrhdrfndInfoNb +
                    ", natlPrsnFlag=" + natlPrsnFlag +
                    ", invtrNm='" + invtrNm + '\'' +
                    ", invtrId='" + invtrId + '\'' +
                    ", subscrCptlConAmt='" + subscrCptlConAmt + '\'' +
                    ", actlCptlConAmt='" + actlCptlConAmt + '\'' +
                    ", subscrCptlConFm='" + subscrCptlConFm + '\'' +
                    ", subscrCptlConDt='" + subscrCptlConDt + '\'' +
                    '}';
        }
    }

    public static class DirSupSrMgrInfo{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="DirSupSrMgrInfoNb")
        private Integer dirSupSrMgrInfoNb;

        @JSONField(name = "Nm")
        private String nm;

        @JSONField(name = "Posn")
        private String posn;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getDirSupSrMgrInfoNb() {
            return dirSupSrMgrInfoNb;
        }

        public void setDirSupSrMgrInfoNb(Integer dirSupSrMgrInfoNb) {
            this.dirSupSrMgrInfoNb = dirSupSrMgrInfoNb;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        public String getPosn() {
            return posn;
        }

        public void setPosn(String posn) {
            this.posn = posn;
        }

        @Override
        public String toString() {
            return "DirSupSrMgrInfo{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", dirSupSrMgrInfoNb=" + dirSupSrMgrInfoNb +
                    ", nm='" + nm + '\'' +
                    ", posn='" + posn + '\'' +
                    '}';
        }
    }

    public static class ChngInfo{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="ChngInfoNb")
        private Integer chngInfoNb;

        @JSONField(name = "CHNG_ITM")
        private String chngItm;

        @JSONField(name = "BF_CHNG")
        private String bfChng;

        @JSONField(name = "AFT_CHNG")
        private String aftChng;

        @JSONField(name = "DT_OF_CHNG")
        private String dtOfChng;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getChngInfoNb() {
            return chngInfoNb;
        }

        public void setChngInfoNb(Integer chngInfoNb) {
            this.chngInfoNb = chngInfoNb;
        }

        public String getChngItm() {
            return chngItm;
        }

        public void setChngItm(String chngItm) {
            this.chngItm = chngItm;
        }

        public String getBfChng() {
            return bfChng;
        }

        public void setBfChng(String bfChng) {
            this.bfChng = bfChng;
        }

        public String getAftChng() {
            return aftChng;
        }

        public void setAftChng(String aftChng) {
            this.aftChng = aftChng;
        }

        public String getDtOfChng() {
            return dtOfChng;
        }

        public void setDtOfChng(String dtOfChng) {
            this.dtOfChng = dtOfChng;
        }

        @Override
        public String toString() {
            return "ChngInfo{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", chngInfoNb=" + chngInfoNb +
                    ", chngItm='" + chngItm + '\'' +
                    ", bfChng='" + bfChng + '\'' +
                    ", aftChng='" + aftChng + '\'' +
                    ", dtOfChng='" + dtOfChng + '\'' +
                    '}';
        }
    }

    public static class AbnmlBizInfo{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="AbnInfoNb")
        private Integer abnInfoNb;

        @JSONField(name = "ABNML_CAUSE")
        private String abnmlCause;

        @JSONField(name = "ABNML_DATE")
        private String abnmlDate;

        @JSONField(name = "ABNML_CAUSE_DCSN_AUTH")
        private String abnmlCauseDcsnAuth;

        @JSONField(name = "RMV_CAUSE")
        private String rmvCause;

        @JSONField(name = "RMV_DATE")
        private String rmvDate;

        @JSONField(name = "RMV_CAUSE_DCSN_AUTH")
        private String rmvCauseDcsnAuth;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getAbnInfoNb() {
            return abnInfoNb;
        }

        public void setAbnInfoNb(Integer abnInfoNb) {
            this.abnInfoNb = abnInfoNb;
        }

        public String getAbnmlCause() {
            return abnmlCause;
        }

        public void setAbnmlCause(String abnmlCause) {
            this.abnmlCause = abnmlCause;
        }

        public String getAbnmlDate() {
            return abnmlDate;
        }

        public void setAbnmlDate(String abnmlDate) {
            this.abnmlDate = abnmlDate;
        }

        public String getAbnmlCauseDcsnAuth() {
            return abnmlCauseDcsnAuth;
        }

        public void setAbnmlCauseDcsnAuth(String abnmlCauseDcsnAuth) {
            this.abnmlCauseDcsnAuth = abnmlCauseDcsnAuth;
        }

        public String getRmvCause() {
            return rmvCause;
        }

        public void setRmvCause(String rmvCause) {
            this.rmvCause = rmvCause;
        }

        public String getRmvDate() {
            return rmvDate;
        }

        public void setRmvDate(String rmvDate) {
            this.rmvDate = rmvDate;
        }

        public String getRmvCauseDcsnAuth() {
            return rmvCauseDcsnAuth;
        }

        public void setRmvCauseDcsnAuth(String rmvCauseDcsnAuth) {
            this.rmvCauseDcsnAuth = rmvCauseDcsnAuth;
        }

        @Override
        public String toString() {
            return "AbnmlBizInfo{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", abnInfoNb=" + abnInfoNb +
                    ", abnmlCause='" + abnmlCause + '\'' +
                    ", abnmlDate='" + abnmlDate + '\'' +
                    ", abnmlCauseDcsnAuth='" + abnmlCauseDcsnAuth + '\'' +
                    ", rmvCause='" + rmvCause + '\'' +
                    ", rmvDate='" + rmvDate + '\'' +
                    ", rmvCauseDcsnAuth='" + rmvCauseDcsnAuth + '\'' +
                    '}';
        }
    }

    public static class IllDscrtInfo{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="ILL_INFO_NB")
        private Integer illInfoNb;

        @JSONField(name = "ILL_DSCRT_CAUSE")
        private String illDscrtCause;

        @JSONField(name = "ABNML_DATE")
        private String abnmlDate;

        @JSONField(name = "ABNML_CAUSE_DCSN_AUTH")
        private String abnmlCauseDcsnAuth;

        @JSONField(name = "RMV_CAUSE")
        private String rmvCause;

        @JSONField(name = "RMV_DATE")
        private String rmvDate;

        @JSONField(name = "RMV_CAUSE_DCSN_AUTH")
        private String rmvCauseDcsnAuth;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public Integer getIllInfoNb() {
            return illInfoNb;
        }

        public void setIllInfoNb(Integer illInfoNb) {
            this.illInfoNb = illInfoNb;
        }

        public String getIllDscrtCause() {
            return illDscrtCause;
        }

        public void setIllDscrtCause(String illDscrtCause) {
            this.illDscrtCause = illDscrtCause;
        }

        public String getAbnmlDate() {
            return abnmlDate;
        }

        public void setAbnmlDate(String abnmlDate) {
            this.abnmlDate = abnmlDate;
        }

        public String getAbnmlCauseDcsnAuth() {
            return abnmlCauseDcsnAuth;
        }

        public void setAbnmlCauseDcsnAuth(String abnmlCauseDcsnAuth) {
            this.abnmlCauseDcsnAuth = abnmlCauseDcsnAuth;
        }

        public String getRmvCause() {
            return rmvCause;
        }

        public void setRmvCause(String rmvCause) {
            this.rmvCause = rmvCause;
        }

        public String getRmvDate() {
            return rmvDate;
        }

        public void setRmvDate(String rmvDate) {
            this.rmvDate = rmvDate;
        }

        public String getRmvCauseDcsnAuth() {
            return rmvCauseDcsnAuth;
        }

        public void setRmvCauseDcsnAuth(String rmvCauseDcsnAuth) {
            this.rmvCauseDcsnAuth = rmvCauseDcsnAuth;
        }

        @Override
        public String toString() {
            return "IllDscrtInfo{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", illInfoNb=" + illInfoNb +
                    ", illDscrtCause='" + illDscrtCause + '\'' +
                    ", abnmlDate='" + abnmlDate + '\'' +
                    ", abnmlCauseDcsnAuth='" + abnmlCauseDcsnAuth + '\'' +
                    ", rmvCause='" + rmvCause + '\'' +
                    ", rmvDate='" + rmvDate + '\'' +
                    ", rmvCauseDcsnAuth='" + rmvCauseDcsnAuth + '\'' +
                    '}';
        }
    }

    public static class LicNull{

        @JSONField(name = "OrgnlRcvgMsgId")
        private String orgnlRcvgMsgId;

        @JSONField(name ="PGNB")
        private Integer pgNb;

        @JSONField(name ="LIC_INFO_NB")
        private Integer licInfoNb;

        @JSONField(name = "ORGNL_OR_CP")
        private String orgnlOrCp;

        @JSONField(name = "LIC_NULL_STM_DT")
        private String licNullStmDt;

        @JSONField(name = "RPL_STS")
        private String rplSts;

        @JSONField(name = "RPL_DT")
        private String rplDt;

        @JSONField(name = "LIC_CP_NB")
        private String licCpNb;

        @JSONField(name = "LIC_NULL_STM_CNTT")
        private String licNullStmCntt;

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public Integer getLicInfoNb() {
            return licInfoNb;
        }

        public Integer getPgNb() {
            return pgNb;
        }

        public void setPgNb(Integer pgNb) {
            this.pgNb = pgNb;
        }

        public void setLicInfoNb(Integer licInfoNb) {
            this.licInfoNb = licInfoNb;
        }

        public String getOrgnlOrCp() {
            return orgnlOrCp;
        }

        public void setOrgnlOrCp(String orgnlOrCp) {
            this.orgnlOrCp = orgnlOrCp;
        }

        public String getLicNullStmDt() {
            return licNullStmDt;
        }

        public void setLicNullStmDt(String licNullStmDt) {
            this.licNullStmDt = licNullStmDt;
        }

        public String getRplSts() {
            return rplSts;
        }

        public void setRplSts(String rplSts) {
            this.rplSts = rplSts;
        }

        public String getRplDt() {
            return rplDt;
        }

        public void setRplDt(String rplDt) {
            this.rplDt = rplDt;
        }

        public String getLicCpNb() {
            return licCpNb;
        }

        public void setLicCpNb(String licCpNb) {
            this.licCpNb = licCpNb;
        }

        public String getLicNullStmCntt() {
            return licNullStmCntt;
        }

        public void setLicNullStmCntt(String licNullStmCntt) {
            this.licNullStmCntt = licNullStmCntt;
        }

        @Override
        public String toString() {
            return "LicNull{" +
                    "orgnlRcvgMsgId='" + orgnlRcvgMsgId + '\'' +
                    ", pgNb=" + pgNb +
                    ", licInfoNb=" + licInfoNb +
                    ", orgnlOrCp='" + orgnlOrCp + '\'' +
                    ", licNullStmDt='" + licNullStmDt + '\'' +
                    ", rplSts='" + rplSts + '\'' +
                    ", rplDt='" + rplDt + '\'' +
                    ", licCpNb='" + licCpNb + '\'' +
                    ", licNullStmCntt='" + licNullStmCntt + '\'' +
                    '}';
        }
    }
}
