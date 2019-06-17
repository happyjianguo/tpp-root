package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;

/**
 * @Description: 900
 * @Author: 王鹏
 * @Date: 2019/6/17 9:09
 */
public class MivsCmonConfModel implements Serializable {
    private static final long serialVersionUID = 61048857904471L;
    private Integer plat_date; //平台日期
    private Integer plat_trace; //平台流水
    private Integer plat_time; //平台时间
    private String tran_date; //交易日期
    private String seq_no; //渠道流水
    private String tran_time; //交易时间
    private String msg_id; //报文标识号
    private String cre_dt_tm; //报文发送时间
    private String instg_drct_pty; //发起直接参与机构
    private String instg_pty; //发起参与机构
    private String instd_drct_pty; //接收直接参与机构
    private String instd_pty; //接收参与机构
    private String system_code; //发起方系统编码
    private String remark; //
    private String orgnl_msg_id;
    private String orgnl_instg_pty;
    private String orgnl_mt;
    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String pty_id;//拒绝业务的参与机构行号
    private String pty_prc_cd; //参与机构业务拒绝码
    private String rjct_inf; //申请报文拒绝信息
    private String prc_dt; //处理日期（终态日期）
    private String netg_rnd; //轧差场次

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

    public String getInstg_pty() {
        return instg_pty;
    }

    public void setInstg_pty(String instg_pty) {
        this.instg_pty = instg_pty;
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

    public String getSystem_code() {
        return system_code;
    }

    public void setSystem_code(String system_code) {
        this.system_code = system_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgnl_msg_id() {
        return orgnl_msg_id;
    }

    public void setOrgnl_msg_id(String orgnl_msg_id) {
        this.orgnl_msg_id = orgnl_msg_id;
    }

    public String getOrgnl_instg_pty() {
        return orgnl_instg_pty;
    }

    public void setOrgnl_instg_pty(String orgnl_instg_pty) {
        this.orgnl_instg_pty = orgnl_instg_pty;
    }

    public String getOrgnl_mt() {
        return orgnl_mt;
    }

    public void setOrgnl_mt(String orgnl_mt) {
        this.orgnl_mt = orgnl_mt;
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

    public String getRjct_inf() {
        return rjct_inf;
    }

    public void setRjct_inf(String rjct_inf) {
        this.rjct_inf = rjct_inf;
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

    @Override
    public String toString() {
        return "MivsCmonConfModel{" +
                "plat_date=" + plat_date +
                ", plat_trace=" + plat_trace +
                ", plat_time=" + plat_time +
                ", tran_date='" + tran_date + '\'' +
                ", seq_no='" + seq_no + '\'' +
                ", tran_time='" + tran_time + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", cre_dt_tm='" + cre_dt_tm + '\'' +
                ", instg_drct_pty='" + instg_drct_pty + '\'' +
                ", instg_pty='" + instg_pty + '\'' +
                ", instd_drct_pty='" + instd_drct_pty + '\'' +
                ", instd_pty='" + instd_pty + '\'' +
                ", system_code='" + system_code + '\'' +
                ", remark='" + remark + '\'' +
                ", orgnl_msg_id='" + orgnl_msg_id + '\'' +
                ", orgnl_instg_pty='" + orgnl_instg_pty + '\'' +
                ", orgnl_mt='" + orgnl_mt + '\'' +
                ", proc_sts='" + proc_sts + '\'' +
                ", proc_cd='" + proc_cd + '\'' +
                ", pty_id='" + pty_id + '\'' +
                ", pty_prc_cd='" + pty_prc_cd + '\'' +
                ", rjct_inf='" + rjct_inf + '\'' +
                ", prc_dt='" + prc_dt + '\'' +
                ", netg_rnd='" + netg_rnd + '\'' +
                '}';
    }
}
