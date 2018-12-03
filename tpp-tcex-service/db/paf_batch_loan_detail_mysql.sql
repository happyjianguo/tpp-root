drop table paf_batch_loan_detail;
create table paf_batch_loan_detail(
	batch_no varchar(20) comment '批量编号',
	seq_no varchar(20) comment '序号',
	tx_amt  decimal(16,2) comment '预计扣款金额' ,
	su_amt  decimal(16,2) comment '实际扣款金额' ,
	de_acctno varchar(32) comment '付方账号',
	de_acctname varchar(255) comment '付方户名 ',
	de_chgno varchar(12) comment '付方联行号 ',
	de_opnchgno varchar(12) comment '付方开户行联行号 ',
	de_addr varchar(80) comment '付方地址 ',
	pro_no varchar(32) comment '多方协议号 ',
	engh_flag varchar(80) comment '足额标识0、足额；1、非足额 ',
	summary varchar(60) comment '摘要', 
	tx_status varchar(1) comment '交易状态：0、登记；1、处理中；2、记账成功；3、记账失败' ,
	host_seqno varchar(32) comment '核心系统流水号' ,
	host_rspcode varchar(20) comment '核心系统响应代码' ,
	host_rspmsg	 varchar(120) comment '核心系统响应信息'
) comment='公积金批量贷款扣款附表' ;
ALTER TABLE paf_batch_loan_detail ADD PRIMARY KEY PK_paf_batch_loan_detail(batch_no,seq_no);
