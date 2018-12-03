package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class SinglCrdtInfoModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public SinglCrdtInfoModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@NotNull(message = "tx_brno交易机构不能为空")
	private String tx_brno; //交易机构
	
	@NotNull(message = "up_brno上级机构不能为空")
	private String up_brno; //上级机构
	
	@NotNull(message = "snd_date发送方日期不能为空")
	@Size(min = 8, max = 8,message = "snd_date发送方日期长度必须为8")
	private String snd_date; //发送方日期
	
	@NotNull(message = "snd_time发送方时间不能为空")
	@Size(min = 6, max = 6,message = "snd_time发送方时间长度必须为6")
	private String snd_time;//发送方时间 
	
	@NotNull(message = "snd_seqno发送方流水不能为空")
	@Size(max = 32,message = "snd_seqno发送方流水长度不能大于32")
	private String snd_seqno;//发送方流水
	
	@NotNull(message = "snd_unitno公积金机构号不能为空")
	@Size(max = 15,message = "snd_unitno公积金机构号长度不能大于15")
	private String snd_unitno;//公积金机构号
	
	@NotNull(message = "snd_node发送方节点号不能为空")
	@Size(max = 6,message = "snd_node发送方节点号最大长度必须为6")
	private String snd_node;//发送方节点号
	
	@NotNull(message = "rcv_node接收方节点号不能为空")
	@Size(max = 6,message = "rcv_node接收方节点号最大长度必须为6")
	private String rcv_node;//接收方节点号
	
	@NotNull(message = "bdc_date结算系统日期不能为空")
	@Size(min = 8, max = 8,message = "bdc_date结算系统日期长度必须为8")
	private String bdc_date;//结算系统日期
	
	@Size(max = 6,message = "bdc_time结算系统时间长度不能大于6")
	private String bdc_time;//结算系统时间
	
	@NotNull(message = "bdc_seqno结算系统流水号不能为空")
	@Size(max = 32,message = "bdc_seqno结算系统流水号长度不能大于32")
	private String bdc_seqno;//结算系统流水号
	
	@Size(max = 32,message = "cus_no银行客户编号长度不能大于32")
	private String cus_no;//银行客户编号
	
	@NotNull(message = "opr_no操作员编号不能为空")
	@Size(max = 21,message = "opr_no操作员编号长度不能大于21")
	private String opr_no;//操作员编号
	
	@NotNull(message = "curr_no币种不能为空")
	@Size(max = 3,message = "curr_no币种长度不能大于3")
	private String curr_no;//币种，156
	
	@Size(max = 1,message = "curr_iden钞汇鉴别长度不能大于1")
	private String curr_iden;//钞汇鉴别：1、钞；2、汇
	
	@NotNull(message = "settle_type结算模式不能为空")
	@Size(min = 1, max = 1,message = "settle_type结算模式长度必须为1")
	private String settle_type;//结算模式：1、同行；2、跨行实时；3、跨行非实时
	
	@NotNull(message = "bus_type业务类型不能为空")
	@Size(max = 6,message = "bus_type;//业务类型长度不能大于6")
	private String bus_type;//业务类型
	
	@NotNull(message = "de_acctno付方账号不能为空")
	@Size(max = 32,message = "de_acctno付方账号长度不能大于32")
	private String de_acctno;//付方账号
	
	@NotNull(message = "de_acctname付方户名不能为空")
	@Size(max = 32,message = "de_acctname;//付方户名长度必须为6")
	private String de_acctname;//付方户名
	
	@NotNull(message = "de_acctclass付方账户类别不能为空")
	@Size(max = 1,message = "de_acctclass付方账户类别长不能大于1")
	private String de_acctclass;//付方账户类别：1、对私；2、对公
	
	@NotNull(message = "cap_amt本金发生额不能为空")
	@Digits(integer = 14, fraction = 2,message = "cap_amt本金发生额格式不正确")
	private String cap_amt;//本金发生额
	
	@Size(max = 32,message = "de_intacctno付息户账号长度不能大于32")
	private String de_intacctno;//付息户账号
	
	@Size(max = 255,message = "de_intacctname付息户户名长度不能大于255")
	private String de_intacctname;//付息户户名
	
	@Size(max = 1,message = "de_intacctclass付息户类别长度不能大于1")
	private String de_intacctclass;//付息户类别:1、对私；2、对公
	
	@Size(max = 32,message = "de_intcracct利息收方账号长度不能大于32")
	private String de_intcracct;//利息收方账号
	
	private String int_amt;//利息发生额
	
	@NotNull(message = "cr_acctno收方账号不能为空")
	@Size(max = 32,message = "cr_acctno收方账号长度不能大于32")
	private String cr_acctno;//收方账号
	
	@NotNull(message = "cr_acctname收方户名不能为空")
	@Size(max = 255,message = "cr_acctname收方户名长度不能大于255")
	private String cr_acctname;//收方户名
	
	@NotNull(message = "cr_acctclass收方账户类别不能为空")
	@Size(max = 1,message = "cr_acctclass收方账户类别长度不能大于1")
	private String cr_acctclass;//收方账户类别:1、对私；2、对公
	
	@NotNull(message = "cr_bankclass收方账户行别不能为空")
	@Size(max = 1,message = "cr_bankclass收方账户行别长度不能大于1")
	private String cr_bankclass;//收方账户行别:0、本行；1、跨行
	
	@Size(max = 60,message = "cr_bankname收方行名长度不能大于60")
	private String cr_bankname;//收方行名
	
	@Size(max = 12,message = "cr_chgno收方联行号长度不能大于12")
	private String cr_chgno;//收方联行号
	
	@NotNull(message = "amt交易金额不能为空")
	@Digits(integer = 14, fraction = 2,message = "amt交易金额格式不正确")
	private String amt;//交易金额
	
	@Size(max = 32,message = "ref_acctno业务明细账号最大长度必须为32")
	private String ref_acctno;//业务明细账号
	
	@Size(max = 32,message = "ref_seqno1业务明细流水号1最大长度必须为32")
	private String ref_seqno1;//业务明细流水号1
	
	@Size(max = 32,message = "ref_seqno2业务明细流水号2最大长度必须为32")
	private String ref_seqno2;//业务明细流水号2
	
	@NotNull(message = "summary摘要不能为空")
	@Size(min = 1, max = 60,message = "summary摘要最大长度必须为60")
	private String summary;//摘要
	
	@Size(max = 255,message = "remark备注最大长度必须为255")
	private String remark;//备注
	
	public String getTx_brno() {
		return tx_brno;
	}

	public void setTx_brno(String tx_brno) {
		this.tx_brno = tx_brno;
	}

	public String getUp_brno() {
		return up_brno;
	}

	public void setUp_brno(String up_brno) {
		this.up_brno = up_brno;
	}

	public String getSnd_date() {
		return snd_date;
	}

	public void setSnd_date(String snd_date) {
		this.snd_date = snd_date;
	}

	public String getSnd_time() {
		return snd_time;
	}

	public void setSnd_time(String snd_time) {
		this.snd_time = snd_time;
	}

	public String getSnd_seqno() {
		return snd_seqno;
	}

	public void setSnd_seqno(String snd_seqno) {
		this.snd_seqno = snd_seqno;
	}

	public String getSnd_unitno() {
		return snd_unitno;
	}

	public void setSnd_unitno(String snd_unitno) {
		this.snd_unitno = snd_unitno;
	}

	public String getSnd_node() {
		return snd_node;
	}

	public void setSnd_node(String snd_node) {
		this.snd_node = snd_node;
	}

	public String getRcv_node() {
		return rcv_node;
	}

	public void setRcv_node(String rcv_node) {
		this.rcv_node = rcv_node;
	}

	public String getBdc_date() {
		return bdc_date;
	}

	public void setBdc_date(String bdc_date) {
		this.bdc_date = bdc_date;
	}

	public String getBdc_time() {
		return bdc_time;
	}

	public void setBdc_time(String bdc_time) {
		this.bdc_time = bdc_time;
	}

	public String getBdc_seqno() {
		return bdc_seqno;
	}

	public void setBdc_seqno(String bdc_seqno) {
		this.bdc_seqno = bdc_seqno;
	}

	public String getCus_no() {
		return cus_no;
	}

	public void setCus_no(String cus_no) {
		this.cus_no = cus_no;
	}

	public String getOpr_no() {
		return opr_no;
	}

	public void setOpr_no(String opr_no) {
		this.opr_no = opr_no;
	}

	public String getCurr_no() {
		return curr_no;
	}

	public void setCurr_no(String curr_no) {
		this.curr_no = curr_no;
	}

	public String getCurr_iden() {
		return curr_iden;
	}

	public void setCurr_iden(String curr_iden) {
		this.curr_iden = curr_iden;
	}

	public String getSettle_type() {
		return settle_type;
	}

	public void setSettle_type(String settle_type) {
		this.settle_type = settle_type;
	}

	public String getBus_type() {
		return bus_type;
	}

	public void setBus_type(String bus_type) {
		this.bus_type = bus_type;
	}

	public String getDe_acctno() {
		return de_acctno;
	}

	public void setDe_acctno(String de_acctno) {
		this.de_acctno = de_acctno;
	}

	public String getDe_acctname() {
		return de_acctname;
	}

	public void setDe_acctname(String de_acctname) {
		this.de_acctname = de_acctname;
	}

	public String getDe_acctclass() {
		return de_acctclass;
	}

	public void setDe_acctclass(String de_acctclass) {
		this.de_acctclass = de_acctclass;
	}

	public String getCap_amt() {
		return cap_amt;
	}

	public void setCap_amt(String cap_amt) {
		this.cap_amt = cap_amt;
	}

	public String getDe_intacctno() {
		return de_intacctno;
	}

	public void setDe_intacctno(String de_intacctno) {
		this.de_intacctno = de_intacctno;
	}

	public String getDe_intacctname() {
		return de_intacctname;
	}

	public void setDe_intacctname(String de_intacctname) {
		this.de_intacctname = de_intacctname;
	}

	public String getDe_intacctclass() {
		return de_intacctclass;
	}

	public void setDe_intacctclass(String de_intacctclass) {
		this.de_intacctclass = de_intacctclass;
	}

	public String getDe_intcracct() {
		return de_intcracct;
	}

	public void setDe_intcracct(String de_intcracct) {
		this.de_intcracct = de_intcracct;
	}

	public String getInt_amt() {
		return int_amt;
	}

	public void setInt_amt(String int_amt) {
		this.int_amt = int_amt;
	}

	public String getCr_acctno() {
		return cr_acctno;
	}

	public void setCr_acctno(String cr_acctno) {
		this.cr_acctno = cr_acctno;
	}

	public String getCr_acctname() {
		return cr_acctname;
	}

	public void setCr_acctname(String cr_acctname) {
		this.cr_acctname = cr_acctname;
	}

	public String getCr_acctclass() {
		return cr_acctclass;
	}

	public void setCr_acctclass(String cr_acctclass) {
		this.cr_acctclass = cr_acctclass;
	}

	public String getCr_bankclass() {
		return cr_bankclass;
	}

	public void setCr_bankclass(String cr_bankclass) {
		this.cr_bankclass = cr_bankclass;
	}

	public String getCr_bankname() {
		return cr_bankname;
	}

	public void setCr_bankname(String cr_bankname) {
		this.cr_bankname = cr_bankname;
	}

	public String getCr_chgno() {
		return cr_chgno;
	}

	public void setCr_chgno(String cr_chgno) {
		this.cr_chgno = cr_chgno;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getRef_acctno() {
		return ref_acctno;
	}

	public void setRef_acctno(String ref_acctno) {
		this.ref_acctno = ref_acctno;
	}

	public String getRef_seqno1() {
		return ref_seqno1;
	}

	public void setRef_seqno1(String ref_seqno1) {
		this.ref_seqno1 = ref_seqno1;
	}

	public String getRef_seqno2() {
		return ref_seqno2;
	}

	public void setRef_seqno2(String ref_seqno2) {
		this.ref_seqno2 = ref_seqno2;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
}
