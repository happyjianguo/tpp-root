drop table PAF_BATCH_CRDT_DETAIL;
create table PAF_BATCH_CRDT_DETAIL
(
  BATCH_NO     VARCHAR2(20 CHAR) not null,
  SEQ_NO       VARCHAR2(20 CHAR) not null,
  TX_AMT       NUMBER,
  CR_ACCTNO    VARCHAR2(32 CHAR),
  CR_ACCTNAME  VARCHAR2(255 CHAR),
  CR_CHGNO     VARCHAR2(12 CHAR),
  CR_OPNCHGNO  VARCHAR2(12 CHAR),
  CR_ADDR      VARCHAR2(80 CHAR),
  SUMMARY      VARCHAR2(60 CHAR),
  REF_ACCTNO   VARCHAR2(32 CHAR),
  REF_SEQNO1   VARCHAR2(32 CHAR),
  REF_SEQNO2   VARCHAR2(32 CHAR),
  CAP_AMT      NUMBER,
  INT_AMT      NUMBER,
  TX_STATUS    VARCHAR2(1 CHAR),
  HOST_SEQNO   VARCHAR2(32 CHAR),
  HOST_RSPCODE VARCHAR2(20 CHAR),
  HOST_RSPMSG  VARCHAR2(120 CHAR)
);
-- Add comments to the columns 
comment on table PAF_BATCH_CRDT_DETAIL
  is '公积金批量付款附表';
comment on column PAF_BATCH_CRDT_DETAIL.BATCH_NO
  is '批量编号';
comment on column PAF_BATCH_CRDT_DETAIL.SEQ_NO
  is '序号';
comment on column PAF_BATCH_CRDT_DETAIL.TX_AMT
  is '金额';
comment on column PAF_BATCH_CRDT_DETAIL.CR_ACCTNO
  is '收方账号';
comment on column PAF_BATCH_CRDT_DETAIL.CR_ACCTNAME
  is '收方户名 ';
comment on column PAF_BATCH_CRDT_DETAIL.CR_CHGNO
  is '收方联行号 ';
comment on column PAF_BATCH_CRDT_DETAIL.CR_OPNCHGNO
  is '收方开户行联行号 ';
comment on column PAF_BATCH_CRDT_DETAIL.CR_ADDR
  is '收方地址 ';
comment on column PAF_BATCH_CRDT_DETAIL.SUMMARY
  is '摘要';
comment on column PAF_BATCH_CRDT_DETAIL.REF_ACCTNO
  is '业务明细账号';
comment on column PAF_BATCH_CRDT_DETAIL.REF_SEQNO1
  is '业务明细流水号 1';
comment on column PAF_BATCH_CRDT_DETAIL.REF_SEQNO2
  is '业务明细流水号 2';
comment on column PAF_BATCH_CRDT_DETAIL.CAP_AMT
  is '本金发生额';
comment on column PAF_BATCH_CRDT_DETAIL.INT_AMT
  is '利息发生额';
comment on column PAF_BATCH_CRDT_DETAIL.TX_STATUS
  is '交易状态：0、登记；1、处理中；2、记账成功；3、记账失败';
comment on column PAF_BATCH_CRDT_DETAIL.HOST_SEQNO
  is '核心系统流水号';
comment on column PAF_BATCH_CRDT_DETAIL.HOST_RSPCODE
  is '核心系统响应代码';
comment on column PAF_BATCH_CRDT_DETAIL.HOST_RSPMSG
  is '核心系统响应信息';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PAF_BATCH_CRDT_DETAIL add primary key (BATCH_NO,SEQ_NO);