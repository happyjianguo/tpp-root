package com.fxbank.tpp.mivs.model.mivstables;

import sun.rmi.runtime.Log;

import java.io.Serializable;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/5/21 16:22
 */
public class MivsIdVrfctnInfoTable implements Serializable {
    private static final long serialVersionUID = 1554985453452624306L;
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
    private String mob_nb; //手机号
    private String nm; //姓名
    private String id_tp; //证件类型
    private String id; //证件号码
    private String uni_soc_cdt_cd; //统一社会信用代码
    private String biz_reg_nb; //工商注册号
    private String op_nm; //操作员姓名
    private String rslt; //手机号核查结果
    private String mob_crr; //手机运营商
    private String loc_mob_nb; //手机号归属地代码
    private String loc_nm_mob_nb; //手机号归属地名称
    private String cd_tp; //客户类型
    private String sts; //手机号码状态
    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String rjct_inf; //申请报文拒绝信息
    private String remark1; //备用字段1
    private String remark2; //备用字段2
    private String remark3; //备用字段3

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

    public String getMob_nb() {
        return mob_nb;
    }

    public void setMob_nb(String mob_nb) {
        this.mob_nb = mob_nb;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getId_tp() {
        return id_tp;
    }

    public void setId_tp(String id_tp) {
        this.id_tp = id_tp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUni_soc_cdt_cd() {
        return uni_soc_cdt_cd;
    }

    public void setUni_soc_cdt_cd(String uni_soc_cdt_cd) {
        this.uni_soc_cdt_cd = uni_soc_cdt_cd;
    }

    public String getBiz_reg_nb() {
        return biz_reg_nb;
    }

    public void setBiz_reg_nb(String biz_reg_nb) {
        this.biz_reg_nb = biz_reg_nb;
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

    public String getMob_crr() {
        return mob_crr;
    }

    public void setMob_crr(String mob_crr) {
        this.mob_crr = mob_crr;
    }

    public String getLoc_mob_nb() {
        return loc_mob_nb;
    }

    public void setLoc_mob_nb(String loc_mob_nb) {
        this.loc_mob_nb = loc_mob_nb;
    }

    public String getLoc_nm_mob_nb() {
        return loc_nm_mob_nb;
    }

    public void setLoc_nm_mob_nb(String loc_nm_mob_nb) {
        this.loc_nm_mob_nb = loc_nm_mob_nb;
    }

    public String getCd_tp() {
        return cd_tp;
    }

    public void setCd_tp(String cd_tp) {
        this.cd_tp = cd_tp;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
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

    @Override
    public String toString() {
        return "MivsIdVrfctnTable{" +
                "plat_date=" + plat_date +
                ", plat_trace=" + plat_trace +
                ", plat_time=" + plat_time +
                ", system_id='" + system_id + '\'' +
                ", tran_date='" + tran_date + '\'' +
                ", seq_no='" + seq_no + '\'' +
                ", tran_time='" + tran_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", mivs_sts='" + mivs_sts + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", cre_dt_tm='" + cre_dt_tm + '\'' +
                ", instg_drct_pty='" + instg_drct_pty + '\'' +
                ", drct_pty_nm='" + drct_pty_nm + '\'' +
                ", instg_pty='" + instg_pty + '\'' +
                ", pty_nm='" + pty_nm + '\'' +
                ", instd_drct_pty='" + instd_drct_pty + '\'' +
                ", instd_pty='" + instd_pty + '\'' +
                ", mob_nb='" + mob_nb + '\'' +
                ", nm='" + nm + '\'' +
                ", id_tp='" + id_tp + '\'' +
                ", id='" + id + '\'' +
                ", uni_soc_cdt_cd='" + uni_soc_cdt_cd + '\'' +
                ", biz_reg_nb='" + biz_reg_nb + '\'' +
                ", op_nm='" + op_nm + '\'' +
                ", rslt='" + rslt + '\'' +
                ", mob_crr='" + mob_crr + '\'' +
                ", loc_mob_nb='" + loc_mob_nb + '\'' +
                ", loc_nm_mob_nb='" + loc_nm_mob_nb + '\'' +
                ", cd_tp='" + cd_tp + '\'' +
                ", sts='" + sts + '\'' +
                ", proc_sts='" + proc_sts + '\'' +
                ", proc_cd='" + proc_cd + '\'' +
                ", rjct_inf='" + rjct_inf + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }
}
