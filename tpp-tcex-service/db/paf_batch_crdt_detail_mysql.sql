drop table paf_batch_crdt_detail;
create table paf_batch_crdt_detail(
	batch_no varchar(20) comment '批量编号',
	seq_no varchar(20) comment '序号',
	tx_amt  decimal(16,2) comment '金额' ,
	cr_acctno varchar(32) comment '收方账号',
	cr_acctname varchar(255) comment '收方户名 ',
	cr_chgno varchar(12) comment '收方联行号 ',
	cr_opnchgno varchar(12) comment '收方开户行联行号 ',
	cr_addr varchar(80) comment '收方地址 ',
	summary varchar(60) comment '摘要', 
	ref_acctno varchar(32) comment '业务明细账号', 
	ref_seqno1 varchar(32) comment '业务明细流水号 1',
	ref_seqno2 varchar(32) comment '业务明细流水号 2',
	cap_amt  decimal(16,2) comment '本金发生额' ,
	int_amt decimal(16,2) comment '利息发生额',
	tx_status varchar(1) comment '交易状态：0、登记；1、处理中；2、记账成功；3、记账失败' ,
	host_seqno varchar(32) comment '核心系统流水号' ,
	host_rspcode varchar(20) comment '核心系统响应代码' ,
	host_rspmsg	 varchar(120) comment '核心系统响应信息'
) comment='公积金批量付款附表' ;
ALTER TABLE paf_batch_crdt_detail ADD PRIMARY KEY PK_paf_batch_crdt_detail (batch_no,seq_no);
--CREATE UNIQUE INDEX paf_batch_crdt_master_index1 ON paf_batch_crdt_master (batch_no) ;
