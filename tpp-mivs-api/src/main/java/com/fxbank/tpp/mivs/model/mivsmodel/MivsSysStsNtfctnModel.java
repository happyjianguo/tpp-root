package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/7/5 15:28
 */
public class MivsSysStsNtfctnModel implements Serializable {
    private static final long serialVersionUID = -233656681499364009L;
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

    private String cur_sys_dt; //系统当前日期
    private String nxt_sys_dt; //系统下一日期
    private String remark1;
    private String remark2;
    private String remark3;

    private Integer start_dt;
    private Integer end_dt;

    private String sys_ind; //系统类型
    private String svc_ind; //系统下一日期受理业务状态
    private String nxt_sys_op_tm; //系统下一日期开始受理时间
    private String nxt_sys_cl_tm; //系统下一日期结束受理时间

    public String getSys_ind() {
        return sys_ind;
    }

    public void setSys_ind(String sys_ind) {
        this.sys_ind = sys_ind;
    }

    public String getSvc_ind() {
        return svc_ind;
    }

    public void setSvc_ind(String svc_ind) {
        this.svc_ind = svc_ind;
    }

    public String getNxt_sys_op_tm() {
        return nxt_sys_op_tm;
    }

    public void setNxt_sys_op_tm(String nxt_sys_op_tm) {
        this.nxt_sys_op_tm = nxt_sys_op_tm;
    }

    public String getNxt_sys_cl_tm() {
        return nxt_sys_cl_tm;
    }

    public void setNxt_sys_cl_tm(String nxt_sys_cl_tm) {
        this.nxt_sys_cl_tm = nxt_sys_cl_tm;
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

    public String getCur_sys_dt() {
        return cur_sys_dt;
    }

    public void setCur_sys_dt(String cur_sys_dt) {
        this.cur_sys_dt = cur_sys_dt;
    }

    public String getNxt_sys_dt() {
        return nxt_sys_dt;
    }

    public void setNxt_sys_dt(String nxt_sys_dt) {
        this.nxt_sys_dt = nxt_sys_dt;
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
        return "MivsSysStsNtfctnModel{" +
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
                ", cur_sys_dt='" + cur_sys_dt + '\'' +
                ", nxt_sys_dt='" + nxt_sys_dt + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                ", start_dt=" + start_dt +
                ", end_dt=" + end_dt +
                '}';
    }
}
