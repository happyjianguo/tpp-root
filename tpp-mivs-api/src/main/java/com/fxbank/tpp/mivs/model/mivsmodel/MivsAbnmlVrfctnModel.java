package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;

/**
 * @Description: 机构异常核查通知表
 * @Author: 王鹏
 * @Date: 2019/6/20 7:23
 */
public class MivsAbnmlVrfctnModel implements Serializable {
    private static final long serialVersionUID = 4147490111692045410L;
    private Integer plat_date; //平台日期
    private Integer plat_trace; //平台流水
    private Integer plat_time; //平台时间
    private String check_type; //异常核查通知类型: COS：企业异常核查， AGT：机构异常核查
    private String mivs_sts; //业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功，06-已收到人行请求，07-已回执
    private String msg_id; //报文标识号
    private String cre_dt_tm; //报文发送时间
    private String instg_drct_pty; //发起直接参与机构
    private String instg_pty; //发起参与机构
    private String instd_drct_pty; //接收直接参与机构
    private String instd_pty; //接收参与机构
    private String branch_id ;
    private String orgnl_instg_pty ;
    private String co_nm ;
    private String uni_soc_cdt_cd ;
    private String mob_nb ;
    private String nm ;
    private String abnml_type ;
    private String descrip ;

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

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
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

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getOrgnl_instg_pty() {
        return orgnl_instg_pty;
    }

    public void setOrgnl_instg_pty(String orgnl_instg_pty) {
        this.orgnl_instg_pty = orgnl_instg_pty;
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

    public String getAbnml_type() {
        return abnml_type;
    }

    public void setAbnml_type(String abnml_type) {
        this.abnml_type = abnml_type;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return "MivsAbnmlVrfctnModel{" +
                "plat_date=" + plat_date +
                ", plat_trace=" + plat_trace +
                ", plat_time=" + plat_time +
                ", check_type='" + check_type + '\'' +
                ", mivs_sts='" + mivs_sts + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", cre_dt_tm='" + cre_dt_tm + '\'' +
                ", instg_drct_pty='" + instg_drct_pty + '\'' +
                ", instg_pty='" + instg_pty + '\'' +
                ", instd_drct_pty='" + instd_drct_pty + '\'' +
                ", instd_pty='" + instd_pty + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", orgnl_instg_pty='" + orgnl_instg_pty + '\'' +
                ", co_nm='" + co_nm + '\'' +
                ", uni_soc_cdt_cd='" + uni_soc_cdt_cd + '\'' +
                ", mob_nb='" + mob_nb + '\'' +
                ", nm='" + nm + '\'' +
                ", abnml_type='" + abnml_type + '\'' +
                ", descrip='" + descrip + '\'' +
                '}';
    }
}
