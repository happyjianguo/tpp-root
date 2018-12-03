drop table PAF_BATCH_CRDT_MASTER;
-- Create table
create table PAF_BATCH_CRDT_MASTER
(
  TX_BRNO          VARCHAR2(10 CHAR),
  PLAT_DATE        NUMBER(11) not null,
  PLAT_TIME        NUMBER(11),
  PLAT_TRACE       NUMBER(11) not null,
  UP_BRNO          VARCHAR2(10 CHAR),
  LOG_ID           VARCHAR2(60 CHAR),
  LOG_DEV          VARCHAR2(20 CHAR),
  SND_DATE         VARCHAR2(8 CHAR),
  SND_TIME         VARCHAR2(6 CHAR),
  SND_SEQNO        VARCHAR2(32 CHAR),
  SND_UNITNO       VARCHAR2(15 CHAR),
  SND_NODE         VARCHAR2(6 CHAR),
  RCV_NODE         VARCHAR2(6 CHAR),
  BDC_DATE         VARCHAR2(8 CHAR),
  BDC_TIME         VARCHAR2(6 CHAR),
  BDC_SEQNO        VARCHAR2(32 CHAR),
  CUS_NO           VARCHAR2(32 CHAR),
  OPR_NO           VARCHAR2(21 CHAR),
  TX_STATUS        VARCHAR2(1 CHAR),
  TX_MSG           VARCHAR2(120 CHAR),
  SUCC_HOSTSEQNO   VARCHAR2(32 CHAR),
  SUCC_HOSTRSPCODE VARCHAR2(20 CHAR),
  SUCC_HOSTRSPMSG  VARCHAR2(120 CHAR),
  ROLL_HOSTSEQNO   VARCHAR2(32 CHAR),
  ROLL_HOSTRSPCODE VARCHAR2(20 CHAR),
  ROLL_HOSTRSPMSG  VARCHAR2(120 CHAR),
  BATCH_FILENAME   VARCHAR2(120 CHAR),
  BATCH_NO         VARCHAR2(20 CHAR),
  CURR_NO          VARCHAR2(3 CHAR),
  CURR_IDEN        VARCHAR2(1 CHAR),
  FILE_TYPE        VARCHAR2(1 CHAR),
  BUS_TYPE         VARCHAR2(6 CHAR),
  BATCH_PRJNO      NUMBER(11),
  DE_ACCTNO        VARCHAR2(32 CHAR),
  DE_ACCTNAME      VARCHAR2(255 CHAR),
  DE_ACCTCLASS     VARCHAR2(1 CHAR),
  CAP_AMT          NUMBER,
  DE_INTACCTNO     VARCHAR2(32 CHAR),
  DE_INTACCTNAME   VARCHAR2(255 CHAR),
  DE_INTACCTCLASS  VARCHAR2(1 CHAR),
  DE_INTCRACCT     VARCHAR2(32 CHAR),
  INT_AMT          NUMBER,
  BANK_ACCNO       VARCHAR2(32 CHAR),
  TOTAL_NUM        NUMBER(11),
  TOTAL_AMT        NUMBER,
  SUCC_NUM         NUMBER(11),
  SUCC_AMT         NUMBER,
  FAIL_NUM         NUMBER(11),
  FAIL_AMT         NUMBER,
  REMARK           VARCHAR2(255 CHAR)
);
-- Add comments to the table 
comment on table PAF_BATCH_CRDT_MASTER
  is '公积金批量付款主表';
-- Add comments to the columns 
comment on column PAF_BATCH_CRDT_MASTER.TX_BRNO
  is '交易机构';
comment on column PAF_BATCH_CRDT_MASTER.PLAT_DATE
  is '平台日期';
comment on column PAF_BATCH_CRDT_MASTER.PLAT_TIME
  is '平台时间';
comment on column PAF_BATCH_CRDT_MASTER.PLAT_TRACE
  is '平台流水';
comment on column PAF_BATCH_CRDT_MASTER.UP_BRNO
  is '上级机构';
comment on column PAF_BATCH_CRDT_MASTER.LOG_ID
  is '日志ID';
comment on column PAF_BATCH_CRDT_MASTER.LOG_DEV
  is '日志设备';
comment on column PAF_BATCH_CRDT_MASTER.SND_DATE
  is '发送方日期';
comment on column PAF_BATCH_CRDT_MASTER.SND_TIME
  is '发送方时间';
comment on column PAF_BATCH_CRDT_MASTER.SND_SEQNO
  is '发送方流水';
comment on column PAF_BATCH_CRDT_MASTER.SND_UNITNO
  is '公积金机构号';
comment on column PAF_BATCH_CRDT_MASTER.SND_NODE
  is '发送方节点号';
comment on column PAF_BATCH_CRDT_MASTER.RCV_NODE
  is '接收方节点号';
comment on column PAF_BATCH_CRDT_MASTER.BDC_DATE
  is '结算系统日期';
comment on column PAF_BATCH_CRDT_MASTER.BDC_TIME
  is '结算系统时间';
comment on column PAF_BATCH_CRDT_MASTER.BDC_SEQNO
  is '结算系统流水';
comment on column PAF_BATCH_CRDT_MASTER.CUS_NO
  is '银行客户编号';
comment on column PAF_BATCH_CRDT_MASTER.OPR_NO
  is '操作员编号';
comment on column PAF_BATCH_CRDT_MASTER.TX_STATUS
  is '交易状态：0、接收；1、登记；2、核心转出总金额成功；3、处理中；4、处理完成；5、核心回退失败金额成功；9、处理失败';
comment on column PAF_BATCH_CRDT_MASTER.TX_MSG
  is '交易处理信息';
comment on column PAF_BATCH_CRDT_MASTER.SUCC_HOSTSEQNO
  is '成功明细核心系统流水号';
comment on column PAF_BATCH_CRDT_MASTER.SUCC_HOSTRSPCODE
  is '成功明细核心系统响应代码';
comment on column PAF_BATCH_CRDT_MASTER.SUCC_HOSTRSPMSG
  is '成功明细核心系统响应信息';
comment on column PAF_BATCH_CRDT_MASTER.ROLL_HOSTSEQNO
  is '回退明细核心系统流水号';
comment on column PAF_BATCH_CRDT_MASTER.ROLL_HOSTRSPCODE
  is '回退明细核心系统响应代码';
comment on column PAF_BATCH_CRDT_MASTER.ROLL_HOSTRSPMSG
  is '回退明细核心系统响应信息';
comment on column PAF_BATCH_CRDT_MASTER.BATCH_FILENAME
  is '批量文件名';
comment on column PAF_BATCH_CRDT_MASTER.BATCH_NO
  is '批量编号CRDT开头，贷款是LOAN开头';
comment on column PAF_BATCH_CRDT_MASTER.CURR_NO
  is '币种，156';
comment on column PAF_BATCH_CRDT_MASTER.CURR_IDEN
  is '钞汇鉴别：1、钞；2、汇';
comment on column PAF_BATCH_CRDT_MASTER.FILE_TYPE
  is '文件类型：1、同行；2、跨行；3、混合';
comment on column PAF_BATCH_CRDT_MASTER.BUS_TYPE
  is '业务类型';
comment on column PAF_BATCH_CRDT_MASTER.BATCH_PRJNO
  is '批量项目编号';
comment on column PAF_BATCH_CRDT_MASTER.DE_ACCTNO
  is '付方账号';
comment on column PAF_BATCH_CRDT_MASTER.DE_ACCTNAME
  is '付方户名';
comment on column PAF_BATCH_CRDT_MASTER.DE_ACCTCLASS
  is '付方账户类别：1、对私；2、对公';
comment on column PAF_BATCH_CRDT_MASTER.CAP_AMT
  is '本金发生额';
comment on column PAF_BATCH_CRDT_MASTER.DE_INTACCTNO
  is '付息户账号 ';
comment on column PAF_BATCH_CRDT_MASTER.DE_INTACCTNAME
  is '付息户户名 ';
comment on column PAF_BATCH_CRDT_MASTER.DE_INTACCTCLASS
  is '付息户类别:1、对私；2、对公';
comment on column PAF_BATCH_CRDT_MASTER.DE_INTCRACCT
  is '利息收方账号';
comment on column PAF_BATCH_CRDT_MASTER.INT_AMT
  is '利息发生额';
comment on column PAF_BATCH_CRDT_MASTER.BANK_ACCNO
  is '银行内部过渡户';
comment on column PAF_BATCH_CRDT_MASTER.TOTAL_NUM
  is '总笔数';
comment on column PAF_BATCH_CRDT_MASTER.TOTAL_AMT
  is '总金额';
comment on column PAF_BATCH_CRDT_MASTER.SUCC_NUM
  is '成功总笔数';
comment on column PAF_BATCH_CRDT_MASTER.SUCC_AMT
  is '成功总金额';
comment on column PAF_BATCH_CRDT_MASTER.FAIL_NUM
  is '失败总笔数';
comment on column PAF_BATCH_CRDT_MASTER.FAIL_AMT
  is '失败总金额';
comment on column PAF_BATCH_CRDT_MASTER.REMARK
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PAF_BATCH_CRDT_MASTER add primary key (PLAT_DATE, PLAT_TRACE);
create unique index PAF_BATCH_CRDT_MASTER_INDEX1 on PAF_BATCH_CRDT_MASTER (BATCH_NO);