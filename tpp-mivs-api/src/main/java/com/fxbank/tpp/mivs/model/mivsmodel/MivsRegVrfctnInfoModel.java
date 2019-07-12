package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 登记信息业务表
 * @Author: 王鹏
 * @Date: 2019/6/3 9:28
 */
public class MivsRegVrfctnInfoModel implements Serializable {
    private static final long serialVersionUID = 8191368292317279469L;

    private Integer plat_date; //平台日期
    private Integer plat_trace; //平台流水
    private Integer plat_time; //平台时间
    private String system_id; //发起方系统编码
    private String tran_date; //交易日期
    private String seq_no; //渠道流水
    private String tran_time; //交易时间
    private String user_id; //柜员号
    private String branch_id; //机构号
    private String mivs_sts; //业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功
    private String msg_id; //报文标识号
    private String cre_dt_tm; //报文发送时间
    private String instg_drct_pty; //发起直接参与机构
    private String drct_pty_nm; //发起直接参与机构行名
    private String instg_pty; //发起参与机构
    private String pty_nm; //发起参与机构行名
    private String instd_drct_pty; //接收直接参与机构
    private String instd_pty; //接收参与机构
    private String rcv_msg_id; //应答报文标识号
    private String rcv_cre_dt_tm; //应答报文发送时间
    private String market_type;//主体类型
    private String tra_nm;//字号名称
    private String nm;//经营者姓名
    private String id;//经营者证件号
    private String nm_of_lgl_prsn; //法定代表人或单位负责人姓名
    private String id_of_lgl_prsn; //法定代表人或单位负责人身份证件号
    private String ent_nm; //代理人姓名
    private String agt_id; //代理人身份证件号码
    private String agt_nm; //企业名称
    private String uni_soc_cdt_cd; //统一社会信用代码
    private String op_nm; //操作员姓名
    private Integer pg_nb; //本报文页码
    private String last_pg_ind; //最后一页指示符
    private String rslt; //登记信息核查结果
    private String data_resrc_dt; //数据源日期

    private Integer bas_info_cnt; //照面信息部分，此部分在分片报文中每次均出现
    private Integer co_shrhdrfnd_info_cnt; //企业股东及出资信息部分
    private Integer dir_supsrsgr_info_cnt; //董事监事及高管信息
    private Integer chng_info_cnt; //变更信息
    private Integer abnml_biz_info_cnt; //异常经营信息
    private Integer ill_dscrt_info_cnt; //严重违法失信信息
    private Integer lic_null_cnt; //营业执照作废声明

    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String rjct_inf; //申请报文拒绝信息
    private String fdbk_flag; //反馈标识
    private String remark1; //备用字段1
    private String remark2; //备用字段2
    private String remark3; //备用字段3

    private String bas_info;
    private String cos_info;
    private String dir_info;
    private String chng_info;
    private String abn_info;
    private String ill_info;
    private String lic_info;

    private Integer start_dt;
    private Integer end_dt;

    private String sys_ind;
    private String orig_dlv_msgid;
    private String orig_rcv_msgid;
    private String orig_instg_drct_pty; //原发起直接参与机构
    private String orig_instg_pty; //原发起参与机构
    private String txpmt_inf;
    private String cntt;
    private String contact_nm;
    private String contact_nb;
    private String pty_id;//拒绝业务的参与机构行号
    private String pty_prc_cd; //参与机构业务拒绝码
    private String prc_dt; //处理日期（终态日期）
    private String netg_rnd; //轧差场次

    private String detail_flag; //查询明细标志 YES:查询明细  NO:不查询明细

    //bas_info附表
    private List<BasInfo> basInfo;
    public static class BasInfo implements Serializable{
        private static final long serialVersionUID = -7952518821817088378L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //页数号
        private Integer bas_info_nb; //条数号
        private String market_type;
        private String ent_nm;
        private String uni_soc_cdt_cd;
        private String co_tp; //市场主体类型
        private String dom; //住所
        private String reg_cptl; //注册资本(金)
        private String dt_est; //成立日期
        private String op_prd_from; //经营期限自
        private String op_prd_to; //经营期限至
        private String reg_sts; //登记状态
        private String nm_of_lgl_prsn;//法定代表人或单位负责人姓名
        private String reg_auth; //登记机关
        private String biz_scp; //经营范围
        private String dt_appr; //核准日期tra_nm NVARCHAR2(256) NULL,
        private String tra_nm;
        private String op_loc;
        private String fd_amt;
        private String dt_reg;
        private String nm;

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getBas_info_nb() {
            return bas_info_nb;
        }

        public void setBas_info_nb(Integer bas_info_nb) {
            this.bas_info_nb = bas_info_nb;
        }

        public String getMarket_type() {
            return market_type;
        }

        public void setMarket_type(String market_type) {
            this.market_type = market_type;
        }

        public String getEnt_nm() {
            return ent_nm;
        }

        public void setEnt_nm(String ent_nm) {
            this.ent_nm = ent_nm;
        }

        public String getUni_soc_cdt_cd() {
            return uni_soc_cdt_cd;
        }

        public void setUni_soc_cdt_cd(String uni_soc_cdt_cd) {
            this.uni_soc_cdt_cd = uni_soc_cdt_cd;
        }

        public String getCo_tp() {
            return co_tp;
        }

        public void setCo_tp(String co_tp) {
            this.co_tp = co_tp;
        }

        public String getDom() {
            return dom;
        }

        public void setDom(String dom) {
            this.dom = dom;
        }

        public String getReg_cptl() {
            return reg_cptl;
        }

        public void setReg_cptl(String reg_cptl) {
            this.reg_cptl = reg_cptl;
        }

        public String getDt_est() {
            return dt_est;
        }

        public void setDt_est(String dt_est) {
            this.dt_est = dt_est;
        }

        public String getOp_prd_from() {
            return op_prd_from;
        }

        public void setOp_prd_from(String op_prd_from) {
            this.op_prd_from = op_prd_from;
        }

        public String getOp_prd_to() {
            return op_prd_to;
        }

        public void setOp_prd_to(String op_prd_to) {
            this.op_prd_to = op_prd_to;
        }

        public String getReg_sts() {
            return reg_sts;
        }

        public void setReg_sts(String reg_sts) {
            this.reg_sts = reg_sts;
        }

        public String getNm_of_lgl_prsn() {
            return nm_of_lgl_prsn;
        }

        public void setNm_of_lgl_prsn(String nm_of_lgl_prsn) {
            this.nm_of_lgl_prsn = nm_of_lgl_prsn;
        }

        public String getReg_auth() {
            return reg_auth;
        }

        public void setReg_auth(String reg_auth) {
            this.reg_auth = reg_auth;
        }

        public String getBiz_scp() {
            return biz_scp;
        }

        public void setBiz_scp(String biz_scp) {
            this.biz_scp = biz_scp;
        }

        public String getDt_appr() {
            return dt_appr;
        }

        public void setDt_appr(String dt_appr) {
            this.dt_appr = dt_appr;
        }

        public String getTra_nm() {
            return tra_nm;
        }

        public void setTra_nm(String tra_nm) {
            this.tra_nm = tra_nm;
        }

        public String getOp_loc() {
            return op_loc;
        }

        public void setOp_loc(String op_loc) {
            this.op_loc = op_loc;
        }

        public String getFd_amt() {
            return fd_amt;
        }

        public void setFd_amt(String fd_amt) {
            this.fd_amt = fd_amt;
        }

        public String getDt_reg() {
            return dt_reg;
        }

        public void setDt_reg(String dt_reg) {
            this.dt_reg = dt_reg;
        }

        public String getNm() {
            return nm;
        }

        public void setNm(String nm) {
            this.nm = nm;
        }

        @Override
        public String toString() {
            return "BasInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", bas_info_nb=" + bas_info_nb +
                    ", market_type='" + market_type + '\'' +
                    ", ent_nm='" + ent_nm + '\'' +
                    ", uni_soc_cdt_cd='" + uni_soc_cdt_cd + '\'' +
                    ", co_tp='" + co_tp + '\'' +
                    ", dom='" + dom + '\'' +
                    ", reg_cptl='" + reg_cptl + '\'' +
                    ", dt_est='" + dt_est + '\'' +
                    ", op_prd_from='" + op_prd_from + '\'' +
                    ", op_prd_to='" + op_prd_to + '\'' +
                    ", reg_sts='" + reg_sts + '\'' +
                    ", nm_of_lgl_prsn='" + nm_of_lgl_prsn + '\'' +
                    ", reg_auth='" + reg_auth + '\'' +
                    ", biz_scp='" + biz_scp + '\'' +
                    ", dt_appr='" + dt_appr + '\'' +
                    ", tra_nm='" + tra_nm + '\'' +
                    ", op_loc='" + op_loc + '\'' +
                    ", fd_amt='" + fd_amt + '\'' +
                    ", dt_reg='" + dt_reg + '\'' +
                    ", nm='" + nm + '\'' +
                    '}';
        }
    }

    //co_shrhdr_fnd_info附表
    private List<CoShrhdrFndInfo> coShrhdrFndInfo;
    public static class CoShrhdrFndInfo implements Serializable{
        private static final long serialVersionUID = -7221599556583482678L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //本报文页码
        private Integer co_shrhdrfnd_info_nb; //条数号
        private String natl_prsn_flag;//自然人标识
        private String invtr_nm; //投资人名称
        private String invtr_id; //投资人证件号码或证件编号
        private String subscr_cptl_con_amt; //认缴出资额
        private String actl_cptl_con_amt; //实缴出资额
        private String subscr_cptl_con_fm; //认缴出资方式
        private String subscr_cptl_con_dt; //认缴出资日期

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public String getNatl_prsn_flag() {
            return natl_prsn_flag;
        }

        public void setNatl_prsn_flag(String natl_prsn_flag) {
            this.natl_prsn_flag = natl_prsn_flag;
        }

        public Integer getCo_shrhdrfnd_info_nb() {
            return co_shrhdrfnd_info_nb;
        }

        public void setCo_shrhdrfnd_info_nb(Integer co_shrhdrfnd_info_nb) {
            this.co_shrhdrfnd_info_nb = co_shrhdrfnd_info_nb;
        }

        public String getInvtr_nm() {
            return invtr_nm;
        }

        public void setInvtr_nm(String invtr_nm) {
            this.invtr_nm = invtr_nm;
        }

        public String getInvtr_id() {
            return invtr_id;
        }

        public void setInvtr_id(String invtr_id) {
            this.invtr_id = invtr_id;
        }

        public String getSubscr_cptl_con_amt() {
            return subscr_cptl_con_amt;
        }

        public void setSubscr_cptl_con_amt(String subscr_cptl_con_amt) {
            this.subscr_cptl_con_amt = subscr_cptl_con_amt;
        }

        public String getActl_cptl_con_amt() {
            return actl_cptl_con_amt;
        }

        public void setActl_cptl_con_amt(String actl_cptl_con_amt) {
            this.actl_cptl_con_amt = actl_cptl_con_amt;
        }

        public String getSubscr_cptl_con_fm() {
            return subscr_cptl_con_fm;
        }

        public void setSubscr_cptl_con_fm(String subscr_cptl_con_fm) {
            this.subscr_cptl_con_fm = subscr_cptl_con_fm;
        }

        public String getSubscr_cptl_con_dt() {
            return subscr_cptl_con_dt;
        }

        public void setSubscr_cptl_con_dt(String subscr_cptl_con_dt) {
            this.subscr_cptl_con_dt = subscr_cptl_con_dt;
        }

        @Override
        public String toString() {
            return "CoShrhdrFndInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", co_shrhdrfnd_info_nb=" + co_shrhdrfnd_info_nb +
                    ", natl_prsn_flag='" + natl_prsn_flag + '\'' +
                    ", invtr_nm='" + invtr_nm + '\'' +
                    ", invtr_id='" + invtr_id + '\'' +
                    ", subscr_cptl_con_amt='" + subscr_cptl_con_amt + '\'' +
                    ", actl_cptl_con_amt='" + actl_cptl_con_amt + '\'' +
                    ", subscr_cptl_con_fm='" + subscr_cptl_con_fm + '\'' +
                    ", subscr_cptl_con_dt='" + subscr_cptl_con_dt + '\'' +
                    '}';
        }
    }

    //dir_supsrsgr_info附表
    private List<DirSupSrMgrInfo> dirSupSrMgrInfo;
    public static class DirSupSrMgrInfo implements Serializable{
        private static final long serialVersionUID = 7905341920241301081L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //本报文页码
        private Integer dir_supsrsgr_info_nb; //条数号
        private String nm; //姓名
        private String posn; //职务

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getDir_supsrsgr_info_nb() {
            return dir_supsrsgr_info_nb;
        }

        public void setDir_supsrsgr_info_nb(Integer dir_supsrsgr_info_nb) {
            this.dir_supsrsgr_info_nb = dir_supsrsgr_info_nb;
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
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", dir_supsrsgr_info_nb=" + dir_supsrsgr_info_nb +
                    ", nm='" + nm + '\'' +
                    ", posn='" + posn + '\'' +
                    '}';
        }
    }

    //chng_info附表
    private List<ChngInfo> chngInfo;
    public static class ChngInfo implements Serializable {
        private static final long serialVersionUID = 8452777290723943671L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //本报文页码
        private Integer chng_info_nb; //条数号
        private String chng_itm; //变更事项
        private String bf_chng; //变更前内容
        private String aft_chng; //变更后内容
        private String dt_of_chng; //变更日期

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getChng_info_nb() {
            return chng_info_nb;
        }

        public void setChng_info_nb(Integer chng_info_nb) {
            this.chng_info_nb = chng_info_nb;
        }

        public String getChng_itm() {
            return chng_itm;
        }

        public void setChng_itm(String chng_itm) {
            this.chng_itm = chng_itm;
        }

        public String getBf_chng() {
            return bf_chng;
        }

        public void setBf_chng(String bf_chng) {
            this.bf_chng = bf_chng;
        }

        public String getAft_chng() {
            return aft_chng;
        }

        public void setAft_chng(String aft_chng) {
            this.aft_chng = aft_chng;
        }

        public String getDt_of_chng() {
            return dt_of_chng;
        }

        public void setDt_of_chng(String dt_of_chng) {
            this.dt_of_chng = dt_of_chng;
        }

        @Override
        public String toString() {
            return "ChngInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", chng_info_nb=" + chng_info_nb +
                    ", chng_itm='" + chng_itm + '\'' +
                    ", bf_chng='" + bf_chng + '\'' +
                    ", aft_chng='" + aft_chng + '\'' +
                    ", dt_of_chng='" + dt_of_chng + '\'' +
                    '}';
        }
    }

    //abn_info附表
    private List<AbnmlBizInfo> abnmlBizInfo;
    public static class AbnmlBizInfo implements Serializable {
        private static final long serialVersionUID = -1847225499668341146L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //本报文页码
        private Integer abn_info_nb; //条数号
        private String abnml_cause; //列入经营异常名录原因类型
        private String abnml_date; //列入日期
        private String abnml_cause_dcsn_auth; //列入决定机关
        private String rmv_cause; //移出经营异常名录原因
        private String rmv_date; //移出日期
        private String rmv_cause_dcsn_auth; //移出决定机关

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getAbn_info_nb() {
            return abn_info_nb;
        }

        public void setAbn_info_nb(Integer abn_info_nb) {
            this.abn_info_nb = abn_info_nb;
        }

        public String getAbnml_cause() {
            return abnml_cause;
        }

        public void setAbnml_cause(String abnml_cause) {
            this.abnml_cause = abnml_cause;
        }

        public String getAbnml_date() {
            return abnml_date;
        }

        public void setAbnml_date(String abnml_date) {
            this.abnml_date = abnml_date;
        }

        public String getAbnml_cause_dcsn_auth() {
            return abnml_cause_dcsn_auth;
        }

        public void setAbnml_cause_dcsn_auth(String abnml_cause_dcsn_auth) {
            this.abnml_cause_dcsn_auth = abnml_cause_dcsn_auth;
        }

        public String getRmv_cause() {
            return rmv_cause;
        }

        public void setRmv_cause(String rmv_cause) {
            this.rmv_cause = rmv_cause;
        }

        public String getRmv_date() {
            return rmv_date;
        }

        public void setRmv_date(String rmv_date) {
            this.rmv_date = rmv_date;
        }

        public String getRmv_cause_dcsn_auth() {
            return rmv_cause_dcsn_auth;
        }

        public void setRmv_cause_dcsn_auth(String rmv_cause_dcsn_auth) {
            this.rmv_cause_dcsn_auth = rmv_cause_dcsn_auth;
        }

        @Override
        public String toString() {
            return "AbnmlBizInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", abn_info_nb=" + abn_info_nb +
                    ", abnml_cause='" + abnml_cause + '\'' +
                    ", abnml_date='" + abnml_date + '\'' +
                    ", abnml_cause_dcsn_auth='" + abnml_cause_dcsn_auth + '\'' +
                    ", rmv_cause='" + rmv_cause + '\'' +
                    ", rmv_date='" + rmv_date + '\'' +
                    ", rmv_cause_dcsn_auth='" + rmv_cause_dcsn_auth + '\'' +
                    '}';
        }
    }

    //ill_info附表
    private List<IllDscrtInfo> illDscrtInfo;
    public static class IllDscrtInfo implements Serializable {
        private static final long serialVersionUID = 8672851118502861988L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer pg_nb; //本报文页码
        private Integer ill_info_nb; //条数号
        private String ill_dscrt_cause; //列入事由或情形
        private String abnml_date; //列入日期
        private String abnml_cause_dcsn_auth; //列入决定机关
        private String rmv_cause; //移出经营异常名录原因
        private String rmv_date; //移出日期
        private String rmv_cause_dcsn_auth; //移出决定机关

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getIll_info_nb() {
            return ill_info_nb;
        }

        public void setIll_info_nb(Integer ill_info_nb) {
            this.ill_info_nb = ill_info_nb;
        }

        public String getIll_dscrt_cause() {
            return ill_dscrt_cause;
        }

        public void setIll_dscrt_cause(String ill_dscrt_cause) {
            this.ill_dscrt_cause = ill_dscrt_cause;
        }

        public String getAbnml_date() {
            return abnml_date;
        }

        public void setAbnml_date(String abnml_date) {
            this.abnml_date = abnml_date;
        }

        public String getAbnml_cause_dcsn_auth() {
            return abnml_cause_dcsn_auth;
        }

        public void setAbnml_cause_dcsn_auth(String abnml_cause_dcsn_auth) {
            this.abnml_cause_dcsn_auth = abnml_cause_dcsn_auth;
        }

        public String getRmv_cause() {
            return rmv_cause;
        }

        public void setRmv_cause(String rmv_cause) {
            this.rmv_cause = rmv_cause;
        }

        public String getRmv_date() {
            return rmv_date;
        }

        public void setRmv_date(String rmv_date) {
            this.rmv_date = rmv_date;
        }

        public String getRmv_cause_dcsn_auth() {
            return rmv_cause_dcsn_auth;
        }

        public void setRmv_cause_dcsn_auth(String rmv_cause_dcsn_auth) {
            this.rmv_cause_dcsn_auth = rmv_cause_dcsn_auth;
        }

        @Override
        public String toString() {
            return "IllDscrtInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb=" + pg_nb +
                    ", ill_info_nb=" + ill_info_nb +
                    ", ill_dscrt_cause='" + ill_dscrt_cause + '\'' +
                    ", abnml_date='" + abnml_date + '\'' +
                    ", abnml_cause_dcsn_auth='" + abnml_cause_dcsn_auth + '\'' +
                    ", rmv_cause='" + rmv_cause + '\'' +
                    ", rmv_date='" + rmv_date + '\'' +
                    ", rmv_cause_dcsn_auth='" + rmv_cause_dcsn_auth + '\'' +
                    '}';
        }
    }

    //lic_info附表
    private List<LicInfo> licInfo;
    public static class LicInfo implements Serializable{
        private static final long serialVersionUID = 4381644239343664448L;
        private Integer plat_date; //平台日期
        private Integer plat_trace; //平台流水
        private Integer plat_time; //平台时间
        private String instg_pty; //发起参与机构
        private String msg_id; //报文标识号
        private String cre_dt_tm; //报文发送时间
        private String orig_msg_id; //原申请报文标识号
        private String orig_instg_drct_pty; //原发起直接参与机构
        private String orig_instg_pty; //原发起参与机构
        private Integer lic_info_nb; //条数好
        private Integer pg_nb; //本报文页码
        private String orgnl_or_cp; //正副本标识
        private String  lic_null_stm_cntt; //声明内容
        private String lic_null_stm_dt; //声明日期
        private String rpl_sts; //补领标识
        private String rpl_dt; //补领日期
        private String lic_cp_nb; //营业执照副本编号

        public Integer getPlat_date() {
            return plat_date;
        }

        public void setPlat_date(Integer plat_date) {
            this.plat_date = plat_date;
        }

        public Integer getPlat_trace() {
            return plat_trace;
        }

        public void setPlat_trace(Integer plat_trace) {
            this.plat_trace = plat_trace;
        }

        public Integer getPlat_time() {
            return plat_time;
        }

        public void setPlat_time(Integer plat_time) {
            this.plat_time = plat_time;
        }

        public String getInstg_pty() {
            return instg_pty;
        }

        public void setInstg_pty(String instg_pty) {
            this.instg_pty = instg_pty;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getCre_dt_tm() {
            return cre_dt_tm;
        }

        public void setCre_dt_tm(String cre_dt_tm) {
            this.cre_dt_tm = cre_dt_tm;
        }

        public String getOrig_msg_id() {
            return orig_msg_id;
        }

        public void setOrig_msg_id(String orig_msg_id) {
            this.orig_msg_id = orig_msg_id;
        }

        public String getOrig_instg_drct_pty() {
            return orig_instg_drct_pty;
        }

        public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
            this.orig_instg_drct_pty = orig_instg_drct_pty;
        }

        public String getOrig_instg_pty() {
            return orig_instg_pty;
        }

        public void setOrig_instg_pty(String orig_instg_pty) {
            this.orig_instg_pty = orig_instg_pty;
        }

        public Integer getPg_nb() {
            return pg_nb;
        }

        public void setPg_nb(Integer pg_nb) {
            this.pg_nb = pg_nb;
        }

        public Integer getLic_info_nb() {
            return lic_info_nb;
        }

        public void setLic_info_nb(Integer lic_info_nb) {
            this.lic_info_nb = lic_info_nb;
        }

        public String getOrgnl_or_cp() {
            return orgnl_or_cp;
        }

        public void setOrgnl_or_cp(String orgnl_or_cp) {
            this.orgnl_or_cp = orgnl_or_cp;
        }

        public String getLic_null_stm_cntt() {
            return lic_null_stm_cntt;
        }

        public void setLic_null_stm_cntt(String lic_null_stm_cntt) {
            this.lic_null_stm_cntt = lic_null_stm_cntt;
        }

        public String getLic_null_stm_dt() {
            return lic_null_stm_dt;
        }

        public void setLic_null_stm_dt(String lic_null_stm_dt) {
            this.lic_null_stm_dt = lic_null_stm_dt;
        }

        public String getRpl_sts() {
            return rpl_sts;
        }

        public void setRpl_sts(String rpl_sts) {
            this.rpl_sts = rpl_sts;
        }

        public String getRpl_dt() {
            return rpl_dt;
        }

        public void setRpl_dt(String rpl_dt) {
            this.rpl_dt = rpl_dt;
        }

        public String getLic_cp_nb() {
            return lic_cp_nb;
        }

        public void setLic_cp_nb(String lic_cp_nb) {
            this.lic_cp_nb = lic_cp_nb;
        }

        @Override
        public String toString() {
            return "LicInfo{" +
                    "plat_date=" + plat_date +
                    ", plat_trace=" + plat_trace +
                    ", plat_time=" + plat_time +
                    ", instg_pty='" + instg_pty + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    ", cre_dt_tm='" + cre_dt_tm + '\'' +
                    ", orig_msg_id='" + orig_msg_id + '\'' +
                    ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                    ", orig_instg_pty='" + orig_instg_pty + '\'' +
                    ", pg_nb='" + pg_nb + '\'' +
                    ", lic_info_nb='" + lic_info_nb + '\'' +
                    ", orgnl_or_cp='" + orgnl_or_cp + '\'' +
                    ", lic_null_stm_cntt='" + lic_null_stm_cntt + '\'' +
                    ", lic_null_stm_dt='" + lic_null_stm_dt + '\'' +
                    ", rpl_sts='" + rpl_sts + '\'' +
                    ", rpl_dt='" + rpl_dt + '\'' +
                    ", lic_cp_nb='" + lic_cp_nb + '\'' +
                    '}';
        }
    }

    public Integer getPlat_date() {
        return plat_date;
    }

    public void setPlat_date(Integer plat_date) {
        this.plat_date = plat_date;
    }

    public Integer getPlat_trace() {
        return plat_trace;
    }

    public void setPlat_trace(Integer plat_trace) {
        this.plat_trace = plat_trace;
    }

    public Integer getPlat_time() {
        return plat_time;
    }

    public void setPlat_time(Integer plat_time) {
        this.plat_time = plat_time;
    }

    public String getSystem_id() {
        return system_id;
    }

    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getTran_time() {
        return tran_time;
    }

    public void setTran_time(String tran_time) {
        this.tran_time = tran_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getMivs_sts() {
        return mivs_sts;
    }

    public void setMivs_sts(String mivs_sts) {
        this.mivs_sts = mivs_sts;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getCre_dt_tm() {
        return cre_dt_tm;
    }

    public void setCre_dt_tm(String cre_dt_tm) {
        this.cre_dt_tm = cre_dt_tm;
    }

    public String getInstg_drct_pty() {
        return instg_drct_pty;
    }

    public void setInstg_drct_pty(String instg_drct_pty) {
        this.instg_drct_pty = instg_drct_pty;
    }

    public String getDrct_pty_nm() {
        return drct_pty_nm;
    }

    public void setDrct_pty_nm(String drct_pty_nm) {
        this.drct_pty_nm = drct_pty_nm;
    }

    public String getInstg_pty() {
        return instg_pty;
    }

    public void setInstg_pty(String instg_pty) {
        this.instg_pty = instg_pty;
    }

    public String getPty_nm() {
        return pty_nm;
    }

    public void setPty_nm(String pty_nm) {
        this.pty_nm = pty_nm;
    }

    public String getInstd_drct_pty() {
        return instd_drct_pty;
    }

    public void setInstd_drct_pty(String instd_drct_pty) {
        this.instd_drct_pty = instd_drct_pty;
    }

    public String getInstd_pty() {
        return instd_pty;
    }

    public void setInstd_pty(String instd_pty) {
        this.instd_pty = instd_pty;
    }

    public String getRcv_msg_id() {
        return rcv_msg_id;
    }

    public void setRcv_msg_id(String rcv_msg_id) {
        this.rcv_msg_id = rcv_msg_id;
    }

    public String getRcv_cre_dt_tm() {
        return rcv_cre_dt_tm;
    }

    public void setRcv_cre_dt_tm(String rcv_cre_dt_tm) {
        this.rcv_cre_dt_tm = rcv_cre_dt_tm;
    }

    public String getMarket_type() {
        return market_type;
    }

    public void setMarket_type(String market_type) {
        this.market_type = market_type;
    }

    public String getTra_nm() {
        return tra_nm;
    }

    public void setTra_nm(String tra_nm) {
        this.tra_nm = tra_nm;
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

    public String getNm_of_lgl_prsn() {
        return nm_of_lgl_prsn;
    }

    public void setNm_of_lgl_prsn(String nm_of_lgl_prsn) {
        this.nm_of_lgl_prsn = nm_of_lgl_prsn;
    }

    public String getId_of_lgl_prsn() {
        return id_of_lgl_prsn;
    }

    public void setId_of_lgl_prsn(String id_of_lgl_prsn) {
        this.id_of_lgl_prsn = id_of_lgl_prsn;
    }

    public String getEnt_nm() {
        return ent_nm;
    }

    public void setEnt_nm(String ent_nm) {
        this.ent_nm = ent_nm;
    }

    public String getAgt_id() {
        return agt_id;
    }

    public void setAgt_id(String agt_id) {
        this.agt_id = agt_id;
    }

    public String getAgt_nm() {
        return agt_nm;
    }

    public void setAgt_nm(String agt_nm) {
        this.agt_nm = agt_nm;
    }

    public String getUni_soc_cdt_cd() {
        return uni_soc_cdt_cd;
    }

    public void setUni_soc_cdt_cd(String uni_soc_cdt_cd) {
        this.uni_soc_cdt_cd = uni_soc_cdt_cd;
    }

    public String getOp_nm() {
        return op_nm;
    }

    public void setOp_nm(String op_nm) {
        this.op_nm = op_nm;
    }

    public Integer getPg_nb() {
        return pg_nb;
    }

    public void setPg_nb(Integer pg_nb) {
        this.pg_nb = pg_nb;
    }

    public String getLast_pg_ind() {
        return last_pg_ind;
    }

    public void setLast_pg_ind(String last_pg_ind) {
        this.last_pg_ind = last_pg_ind;
    }

    public String getRslt() {
        return rslt;
    }

    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    public String getData_resrc_dt() {
        return data_resrc_dt;
    }

    public void setData_resrc_dt(String data_resrc_dt) {
        this.data_resrc_dt = data_resrc_dt;
    }

    public Integer getBas_info_cnt() {
        return bas_info_cnt;
    }

    public void setBas_info_cnt(Integer bas_info_cnt) {
        this.bas_info_cnt = bas_info_cnt;
    }

    public Integer getCo_shrhdrfnd_info_cnt() {
        return co_shrhdrfnd_info_cnt;
    }

    public void setCo_shrhdrfnd_info_cnt(Integer co_shrhdrfnd_info_cnt) {
        this.co_shrhdrfnd_info_cnt = co_shrhdrfnd_info_cnt;
    }

    public Integer getDir_supsrsgr_info_cnt() {
        return dir_supsrsgr_info_cnt;
    }

    public void setDir_supsrsgr_info_cnt(Integer dir_supsrsgr_info_cnt) {
        this.dir_supsrsgr_info_cnt = dir_supsrsgr_info_cnt;
    }

    public Integer getChng_info_cnt() {
        return chng_info_cnt;
    }

    public void setChng_info_cnt(Integer chng_info_cnt) {
        this.chng_info_cnt = chng_info_cnt;
    }

    public Integer getAbnml_biz_info_cnt() {
        return abnml_biz_info_cnt;
    }

    public void setAbnml_biz_info_cnt(Integer abnml_biz_info_cnt) {
        this.abnml_biz_info_cnt = abnml_biz_info_cnt;
    }

    public Integer getIll_dscrt_info_cnt() {
        return ill_dscrt_info_cnt;
    }

    public void setIll_dscrt_info_cnt(Integer ill_dscrt_info_cnt) {
        this.ill_dscrt_info_cnt = ill_dscrt_info_cnt;
    }

    public Integer getLic_null_cnt() {
        return lic_null_cnt;
    }

    public void setLic_null_cnt(Integer lic_null_cnt) {
        this.lic_null_cnt = lic_null_cnt;
    }

    public String getProc_sts() {
        return proc_sts;
    }

    public void setProc_sts(String proc_sts) {
        this.proc_sts = proc_sts;
    }

    public String getProc_cd() {
        return proc_cd;
    }

    public void setProc_cd(String proc_cd) {
        this.proc_cd = proc_cd;
    }

    public String getRjct_inf() {
        return rjct_inf;
    }

    public void setRjct_inf(String rjct_inf) {
        this.rjct_inf = rjct_inf;
    }

    public String getFdbk_flag() {
        return fdbk_flag;
    }

    public void setFdbk_flag(String fdbk_flag) {
        this.fdbk_flag = fdbk_flag;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getBas_info() {
        return bas_info;
    }

    public void setBas_info(String bas_info) {
        this.bas_info = bas_info;
    }

    public String getCos_info() {
        return cos_info;
    }

    public void setCos_info(String cos_info) {
        this.cos_info = cos_info;
    }

    public String getDir_info() {
        return dir_info;
    }

    public void setDir_info(String dir_info) {
        this.dir_info = dir_info;
    }

    public String getChng_info() {
        return chng_info;
    }

    public void setChng_info(String chng_info) {
        this.chng_info = chng_info;
    }

    public String getAbn_info() {
        return abn_info;
    }

    public void setAbn_info(String abn_info) {
        this.abn_info = abn_info;
    }

    public String getIll_info() {
        return ill_info;
    }

    public void setIll_info(String ill_info) {
        this.ill_info = ill_info;
    }

    public String getLic_info() {
        return lic_info;
    }

    public void setLic_info(String lic_info) {
        this.lic_info = lic_info;
    }

    public Integer getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(Integer start_dt) {
        this.start_dt = start_dt;
    }

    public Integer getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(Integer end_dt) {
        this.end_dt = end_dt;
    }

    public String getSys_ind() {
        return sys_ind;
    }

    public void setSys_ind(String sys_ind) {
        this.sys_ind = sys_ind;
    }

    public String getOrig_dlv_msgid() {
        return orig_dlv_msgid;
    }

    public void setOrig_dlv_msgid(String orig_dlv_msgid) {
        this.orig_dlv_msgid = orig_dlv_msgid;
    }

    public String getOrig_rcv_msgid() {
        return orig_rcv_msgid;
    }

    public void setOrig_rcv_msgid(String orig_rcv_msgid) {
        this.orig_rcv_msgid = orig_rcv_msgid;
    }

    public String getOrig_instg_drct_pty() {
        return orig_instg_drct_pty;
    }

    public void setOrig_instg_drct_pty(String orig_instg_drct_pty) {
        this.orig_instg_drct_pty = orig_instg_drct_pty;
    }

    public String getOrig_instg_pty() {
        return orig_instg_pty;
    }

    public void setOrig_instg_pty(String orig_instg_pty) {
        this.orig_instg_pty = orig_instg_pty;
    }

    public String getTxpmt_inf() {
        return txpmt_inf;
    }

    public void setTxpmt_inf(String txpmt_inf) {
        this.txpmt_inf = txpmt_inf;
    }

    public String getCntt() {
        return cntt;
    }

    public void setCntt(String cntt) {
        this.cntt = cntt;
    }

    public String getContact_nm() {
        return contact_nm;
    }

    public void setContact_nm(String contact_nm) {
        this.contact_nm = contact_nm;
    }

    public String getContact_nb() {
        return contact_nb;
    }

    public void setContact_nb(String contact_nb) {
        this.contact_nb = contact_nb;
    }

    public String getPty_id() {
        return pty_id;
    }

    public void setPty_id(String pty_id) {
        this.pty_id = pty_id;
    }

    public String getPty_prc_cd() {
        return pty_prc_cd;
    }

    public void setPty_prc_cd(String pty_prc_cd) {
        this.pty_prc_cd = pty_prc_cd;
    }

    public String getPrc_dt() {
        return prc_dt;
    }

    public void setPrc_dt(String prc_dt) {
        this.prc_dt = prc_dt;
    }

    public String getNetg_rnd() {
        return netg_rnd;
    }

    public void setNetg_rnd(String netg_rnd) {
        this.netg_rnd = netg_rnd;
    }

    public String getDetail_flag() {
        return detail_flag;
    }

    public void setDetail_flag(String detail_flag) {
        this.detail_flag = detail_flag;
    }

    public List<BasInfo> getBasInfo() {
        return basInfo;
    }

    public void setBasInfo(List<BasInfo> basInfo) {
        this.basInfo = basInfo;
    }

    public List<CoShrhdrFndInfo> getCoShrhdrFndInfo() {
        return coShrhdrFndInfo;
    }

    public void setCoShrhdrFndInfo(List<CoShrhdrFndInfo> coShrhdrFndInfo) {
        this.coShrhdrFndInfo = coShrhdrFndInfo;
    }

    public List<DirSupSrMgrInfo> getDirSupSrMgrInfo() {
        return dirSupSrMgrInfo;
    }

    public void setDirSupSrMgrInfo(List<DirSupSrMgrInfo> dirSupSrMgrInfo) {
        this.dirSupSrMgrInfo = dirSupSrMgrInfo;
    }

    public List<ChngInfo> getChngInfo() {
        return chngInfo;
    }

    public void setChngInfo(List<ChngInfo> chngInfo) {
        this.chngInfo = chngInfo;
    }

    public List<AbnmlBizInfo> getAbnmlBizInfo() {
        return abnmlBizInfo;
    }

    public void setAbnmlBizInfo(List<AbnmlBizInfo> abnmlBizInfo) {
        this.abnmlBizInfo = abnmlBizInfo;
    }

    public List<IllDscrtInfo> getIllDscrtInfo() {
        return illDscrtInfo;
    }

    public void setIllDscrtInfo(List<IllDscrtInfo> illDscrtInfo) {
        this.illDscrtInfo = illDscrtInfo;
    }

    public List<LicInfo> getLicInfo() {
        return licInfo;
    }

    public void setLicInfo(List<LicInfo> licInfo) {
        this.licInfo = licInfo;
    }

}
