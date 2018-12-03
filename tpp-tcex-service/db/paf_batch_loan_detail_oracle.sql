drop table PAF_BATCH_LOAN_DETAIL;
-- Create table
create table PAF_BATCH_LOAN_DETAIL
(
  BATCH_NO     NVARCHAR2(20) not null,
  SEQ_NO       NVARCHAR2(20) not null,
  TX_AMT       NUMBER,
  SU_AMT       NUMBER,
  DE_ACCTNO    NVARCHAR2(32),
  DE_ACCTNAME  NVARCHAR2(255),
  DE_CHGNO     NVARCHAR2(12),
  DE_OPNCHGNO  NVARCHAR2(12),
  DE_ADDR      NVARCHAR2(80),
  PRO_NO       NVARCHAR2(32),
  ENGH_FLAG    NVARCHAR2(80),
  SUMMARY      NVARCHAR2(60),
  TX_STATUS    NVARCHAR2(1),
  HOST_SEQNO   NVARCHAR2(32),
  HOST_RSPCODE NVARCHAR2(20),
  HOST_RSPMSG  NVARCHAR2(120)
);
-- Add comments to the table 
comment on table PAF_BATCH_LOAN_DETAIL
  is '公积金批量贷款扣款附表';
-- Add comments to the columns 
comment on column PAF_BATCH_LOAN_DETAIL.BATCH_NO
  is '批量编号';
comment on column PAF_BATCH_LOAN_DETAIL.SEQ_NO
  is '序号';
comment on column PAF_BATCH_LOAN_DETAIL.TX_AMT
  is '预计扣款金额';
comment on column PAF_BATCH_LOAN_DETAIL.SU_AMT
  is '实际扣款金额';
comment on column PAF_BATCH_LOAN_DETAIL.DE_ACCTNO
  is '付方账号';
comment on column PAF_BATCH_LOAN_DETAIL.DE_ACCTNAME
  is '付方户名 ';
comment on column PAF_BATCH_LOAN_DETAIL.DE_CHGNO
  is '付方联行号 ';
comment on column PAF_BATCH_LOAN_DETAIL.DE_OPNCHGNO
  is '付方开户行联行号 ';
comment on column PAF_BATCH_LOAN_DETAIL.DE_ADDR
  is '付方地址 ';
comment on column PAF_BATCH_LOAN_DETAIL.PRO_NO
  is '多方协议号 ';
comment on column PAF_BATCH_LOAN_DETAIL.ENGH_FLAG
  is '足额标识0、足额；1、非足额 ';
comment on column PAF_BATCH_LOAN_DETAIL.SUMMARY
  is '摘要';
comment on column PAF_BATCH_LOAN_DETAIL.TX_STATUS
  is '交易状态：0、登记；1、处理中；2、记账成功；3、记账失败';
comment on column PAF_BATCH_LOAN_DETAIL.HOST_SEQNO
  is '核心系统流水号';
comment on column PAF_BATCH_LOAN_DETAIL.HOST_RSPCODE
  is '核心系统响应代码';
comment on column PAF_BATCH_LOAN_DETAIL.HOST_RSPMSG
  is '核心系统响应信息';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PAF_BATCH_LOAN_DETAIL add primary key (BATCH_NO, SEQ_NO);
