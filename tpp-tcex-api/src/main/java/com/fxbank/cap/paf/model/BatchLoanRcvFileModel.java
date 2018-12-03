package com.fxbank.cap.paf.model;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class BatchLoanRcvFileModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public BatchLoanRcvFileModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
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
	
	@NotNull(message = "tx_status交易状态不能为空")
	@Size(max = 1,message = "tx_status交易状态长度不能大于1")
	private String tx_status;//交易状态
	
	@Size(max = 120,message = "tx_msg交易处理信息长度不能大于120")
	private String tx_msg;//交易处理信息
	
	@NotNull(message = "batch_filename批量文件名不能为空")
	@Size(min = 1, max = 120,message = "batch_filename批量文件名长度不能大于120")
	private String batch_filename;//批量文件名
	
	@NotNull(message = "file_type文件类型不能为空")
	@Size(max = 1,message = "file_type文件类型长度不能大于1")
	private String file_type;//文件类型
	
	@NotNull(message = "curr_no币种不能为空")
	@Size(max = 3,message = "curr_no币种长度不能大于3")
	private String curr_no; //币种
	
	@Size(max = 1,message = "curr_iden钞汇鉴别长度不能大于1")
	private String curr_iden;//钞汇鉴别1：钞2：汇
	
	private String batch_prjno;//批量项目编号
	
	@NotNull(message = "cr_acctno收方账号不能为空")
	@Size(max = 32,message = "cr_acctno收方账号长度不能大于32")
	private String cr_acctno;//收方账号
	
	@NotNull(message = "cr_acctname收方户名不能为空")
	@Size(max = 255,message = "cr_acctname收方户名长度不能大于255")
	private String cr_acctname;//收方户名
	
	@NotNull(message = "cr_acctclass收方账户类别不能为空")
	@Size(max = 1,message = "cr_acctclass收方账户类别长度不能大于1")
	private String cr_acctclass;//收方账户类别
	
	@Size(max = 32,message = "bank_accno银行内部过渡户长度不能大于32")
	private String bank_accno;//银行内部过渡户
	
	@NotNull(message = "total_num总笔数不能为空")
	private String total_num;//总笔数
	
	@NotNull(message = "total_amt总金额不能为空")
	@Digits(integer = 14, fraction = 2,message = "total_amt总金额格式不正确")
	private String total_amt;//总金额
	
	@NotNull(message = "batch_no批量编号不能为空")
	@Size(max = 20,message = "batch_no批量编号长度不能大于20")
	private String batch_no;//批量编号CRDT开头，贷款是LOAN开头

	@Size(max = 255,message = "长度不能大于")
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

	public String getTx_status() {
		return tx_status;
	}

	public void setTx_status(String tx_status) {
		this.tx_status = tx_status;
	}

	public String getTx_msg() {
		return tx_msg;
	}

	public void setTx_msg(String tx_msg) {
		this.tx_msg = tx_msg;
	}

	public String getBatch_filename() {
		return batch_filename;
	}

	public void setBatch_filename(String batch_filename) {
		this.batch_filename = batch_filename;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
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

	public String getBatch_prjno() {
		return batch_prjno;
	}

	public void setBatch_prjno(String batch_prjno) {
		this.batch_prjno = batch_prjno;
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

	public String getBank_accno() {
		return bank_accno;
	}

	public void setBank_accno(String bank_accno) {
		this.bank_accno = bank_accno;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public String getTotal_amt() {
		return total_amt;
	}

	public void setTotal_amt(String total_amt) {
		this.total_amt = total_amt;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
