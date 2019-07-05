package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;

/**
 * @Description: 企业通知信息
 * @Author: 王鹏
 * @Date: 2019/7/3 16:48
 */
public class MivsFreeFrmtModel implements Serializable {
    private static final long serialVersionUID = 6491276069833161588L;
    private Integer plat_date; //平台日期
    private Integer plat_trace; //平台流水
    private Integer plat_time; //平台时间
    private String mivs_sts; //业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功，06-已收到人行请求，07-已回执
    private String msg_id; //报文标识号
    private String cre_dt_tm; //报文发送时间
    private String instg_drct_pty; //发起直接参与机构
    private String instg_pty; //发起参与机构
    private String instd_drct_pty; //接收直接参与机构
    private String instd_pty; //接收参与机构
    private String rply_flag ; //回复标志
    private String msg_cntt ; //信息内容
    private String isornot_rsp; //是否已回复

    private String system_id ;
    private String tran_date ;
    private String seq_no ;
    private String tran_time ;
    private String user_id;
    private String branch_id ;
    private String drct_pty_nm ;
    private String pty_nm ;
    private String orig_dlv_msgid ;
    private String orig_instg_drct_pty ;
    private String orig_instg_pty ;
    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String rjct_inf; //申请报文拒绝信息

    private Integer start_dt;
    private Integer end_dt;

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

    public String getRply_flag() {
        return rply_flag;
    }

    public void setRply_flag(String rply_flag) {
        this.rply_flag = rply_flag;
    }

    public String getMsg_cntt() {
        return msg_cntt;
    }

    public void setMsg_cntt(String msg_cntt) {
        this.msg_cntt = msg_cntt;
    }

    public String getIsornot_rsp() {
        return isornot_rsp;
    }

    public void setIsornot_rsp(String isornot_rsp) {
        this.isornot_rsp = isornot_rsp;
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

    public String getDrct_pty_nm() {
        return drct_pty_nm;
    }

    public void setDrct_pty_nm(String drct_pty_nm) {
        this.drct_pty_nm = drct_pty_nm;
    }

    public String getPty_nm() {
        return pty_nm;
    }

    public void setPty_nm(String pty_nm) {
        this.pty_nm = pty_nm;
    }

    public String getOrig_dlv_msgid() {
        return orig_dlv_msgid;
    }

    public void setOrig_dlv_msgid(String orig_dlv_msgid) {
        this.orig_dlv_msgid = orig_dlv_msgid;
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

    @Override
    public String toString() {
        return "MivsFreeFrmtModel{" +
                "plat_date=" + plat_date +
                ", plat_trace=" + plat_trace +
                ", plat_time=" + plat_time +
                ", mivs_sts='" + mivs_sts + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", cre_dt_tm='" + cre_dt_tm + '\'' +
                ", instg_drct_pty='" + instg_drct_pty + '\'' +
                ", instg_pty='" + instg_pty + '\'' +
                ", instd_drct_pty='" + instd_drct_pty + '\'' +
                ", instd_pty='" + instd_pty + '\'' +
                ", rply_flag='" + rply_flag + '\'' +
                ", msg_cntt='" + msg_cntt + '\'' +
                ", isornot_rsp='" + isornot_rsp + '\'' +
                ", system_id='" + system_id + '\'' +
                ", tran_date='" + tran_date + '\'' +
                ", seq_no='" + seq_no + '\'' +
                ", tran_time='" + tran_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", drct_pty_nm='" + drct_pty_nm + '\'' +
                ", pty_nm='" + pty_nm + '\'' +
                ", orig_dlv_msgid='" + orig_dlv_msgid + '\'' +
                ", orig_instg_drct_pty='" + orig_instg_drct_pty + '\'' +
                ", orig_instg_pty='" + orig_instg_pty + '\'' +
                ", proc_sts='" + proc_sts + '\'' +
                ", proc_cd='" + proc_cd + '\'' +
                ", rjct_inf='" + rjct_inf + '\'' +
                ", start_dt=" + start_dt +
                ", end_dt=" + end_dt +
                '}';
    }
}
