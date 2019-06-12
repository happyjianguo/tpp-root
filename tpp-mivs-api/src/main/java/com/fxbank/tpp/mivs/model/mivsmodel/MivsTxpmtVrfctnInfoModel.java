package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/23 10:19
 */
public class MivsTxpmtVrfctnInfoModel implements Serializable {
    private static final long serialVersionUID = 2247595290850032278L;
    //主表
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
    private String co_nm; //单位名称
    private String uni_soc_cdt_cd; //统一社会信用代码
    private String txpyr_id_nb; //纳税人识别号
    private String op_nm; //操作员姓名
    private String rslt; //手机号核查结果
    private String data_resrc_dt; //数据源日期
    private Integer txpmt_inf_cnt; //核查条数
    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String rjct_inf; //申请报文拒绝信息
    private String remark1; //备用字段1
    private String remark2; //备用字段2
    private String remark3; //备用字段3

    //附表
    private Integer txpmt_inf_nb; //条数号
    private String tx_auth_cd ; //税务机关代码
    private String tx_auth_nm ; //税务机关名称
    private String txpyr_sts ; //纳税人状态

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

    public String getCo_nm() {
        return co_nm;
    }

    public void setCo_nm(String co_nm) {
        this.co_nm = co_nm;
    }

    public String getUni_soc_cdt_cd() {
        return uni_soc_cdt_cd;
    }

    public void setUni_soc_cdt_cd(String uni_soc_cdt_cd) {
        this.uni_soc_cdt_cd = uni_soc_cdt_cd;
    }

    public String getTxpyr_id_nb() {
        return txpyr_id_nb;
    }

    public void setTxpyr_id_nb(String txpyr_id_nb) {
        this.txpyr_id_nb = txpyr_id_nb;
    }

    public String getOp_nm() {
        return op_nm;
    }

    public void setOp_nm(String op_nm) {
        this.op_nm = op_nm;
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

    public Integer getTxpmt_inf_cnt() {
        return txpmt_inf_cnt;
    }

    public void setTxpmt_inf_cnt(Integer txpmt_inf_cnt) {
        this.txpmt_inf_cnt = txpmt_inf_cnt;
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

    public Integer getTxpmt_inf_nb() {
        return txpmt_inf_nb;
    }

    public void setTxpmt_inf_nb(Integer txpmt_inf_nb) {
        this.txpmt_inf_nb = txpmt_inf_nb;
    }

    public String getTx_auth_cd() {
        return tx_auth_cd;
    }

    public void setTx_auth_cd(String tx_auth_cd) {
        this.tx_auth_cd = tx_auth_cd;
    }

    public String getTx_auth_nm() {
        return tx_auth_nm;
    }

    public void setTx_auth_nm(String tx_auth_nm) {
        this.tx_auth_nm = tx_auth_nm;
    }

    public String getTxpyr_sts() {
        return txpyr_sts;
    }

    public void setTxpyr_sts(String txpyr_sts) {
        this.txpyr_sts = txpyr_sts;
    }
}
