drop table paf_single_crdt;
create table PAF_SINGLE_CRDT
(
  TX_BRNO         VARCHAR2(10 CHAR),
  PLAT_DATE       NUMBER(11) not null,
  PLAT_TIME       NUMBER(11),
  PLAT_TRACE      NUMBER(11) not null,
  UP_BRNO         VARCHAR2(10 CHAR),
  LOG_ID          VARCHAR2(60 CHAR),
  LOG_DEV         VARCHAR2(20 CHAR),
  SND_DATE        VARCHAR2(8 CHAR),
  SND_TIME        VARCHAR2(6 CHAR),
  SND_SEQNO       VARCHAR2(32 CHAR),
  SND_UNITNO      VARCHAR2(15 CHAR),
  SND_NODE        VARCHAR2(6 CHAR),
  RCV_NODE        VARCHAR2(6 CHAR),
  BDC_DATE        VARCHAR2(8 CHAR),
  BDC_TIME        VARCHAR2(6 CHAR),
  BDC_SEQNO       VARCHAR2(32 CHAR),
  CUS_NO          VARCHAR2(32 CHAR),
  OPR_NO          VARCHAR2(21 CHAR),
  TX_STATUS       VARCHAR2(1 CHAR),
  CAP_SEQNO       VARCHAR2(32 CHAR),
  CAP_HOSTCODE    VARCHAR2(20 CHAR),
  CAP_HOSTMSG     VARCHAR2(120 CHAR),
  INT_SEQNO       VARCHAR2(32 CHAR),
  INT_HOSTCODE    VARCHAR2(20 CHAR),
  INT_HOSTMSG     VARCHAR2(120 CHAR),
  CURR_NO         VARCHAR2(3 CHAR),
  CURR_IDEN       VARCHAR2(1 CHAR),
  SETTLE_TYPE     VARCHAR2(1 CHAR),
  BUS_TYPE        VARCHAR2(6 CHAR),
  DE_ACCTNO       VARCHAR2(32 CHAR),
  DE_ACCTNAME     VARCHAR2(255 CHAR),
  DE_ACCTCLASS    VARCHAR2(1 CHAR),
  CAP_AMT         NUMBER,
  DE_INTACCTNO    VARCHAR2(32 CHAR),
  DE_INTACCTNAME  VARCHAR2(255 CHAR),
  DE_INTACCTCLASS VARCHAR2(1 CHAR),
  DE_INTCRACCT    VARCHAR2(32 CHAR),
  INT_AMT         NUMBER,
  CR_ACCTNO       VARCHAR2(32 CHAR),
  CR_ACCTNAME     VARCHAR2(255 CHAR),
  CR_ACCTCLASS    VARCHAR2(1 CHAR),
  CR_BANKCLASS    VARCHAR2(1 CHAR),
  CR_BANKNAME     VARCHAR2(60 CHAR),
  CR_CHGNO        VARCHAR2(12 CHAR),
  AMT             NUMBER,
  REF_ACCTNO      VARCHAR2(32 CHAR),
  REF_SEQNO1      VARCHAR2(32 CHAR),
  REF_SEQNO2      VARCHAR2(32 CHAR),
  SUMMARY         VARCHAR2(60 CHAR),
  REMARK          VARCHAR2(255 CHAR)
);
comment on column PAF_SINGLE_CRDT.TX_BRNO
  is '交易机构';
comment on column PAF_SINGLE_CRDT.PLAT_DATE
  is '平台日期';
comment on column PAF_SINGLE_CRDT.PLAT_TIME
  is '平台时间';
comment on column PAF_SINGLE_CRDT.PLAT_TRACE
  is '平台流水';
comment on column PAF_SINGLE_CRDT.UP_BRNO
  is '上级机构';
comment on column PAF_SINGLE_CRDT.LOG_ID
  is '日志ID';
comment on column PAF_SINGLE_CRDT.LOG_DEV
  is '日志设备';
comment on column PAF_SINGLE_CRDT.SND_DATE
  is '发送方日期';
comment on column PAF_SINGLE_CRDT.SND_TIME
  is '发送方时间';
comment on column PAF_SINGLE_CRDT.SND_SEQNO
  is '发送方流水';
comment on column PAF_SINGLE_CRDT.SND_UNITNO
  is '公积金机构号';
comment on column PAF_SINGLE_CRDT.SND_NODE
  is '发送方节点号';
comment on column PAF_SINGLE_CRDT.RCV_NODE
  is '接收方节点号';
comment on column PAF_SINGLE_CRDT.BDC_DATE
  is '结算系统日期';
comment on column PAF_SINGLE_CRDT.BDC_TIME
  is '结算系统时间';
comment on column PAF_SINGLE_CRDT.BDC_SEQNO
  is '结算系统流水';
comment on column PAF_SINGLE_CRDT.CUS_NO
  is '银行客户编号';
comment on column PAF_SINGLE_CRDT.OPR_NO
  is '操作员编号';
comment on column PAF_SINGLE_CRDT.TX_STATUS
  is '交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败';
comment on column PAF_SINGLE_CRDT.CAP_SEQNO
  is '本金核心系统流水号';
comment on column PAF_SINGLE_CRDT.CAP_HOSTCODE
  is '本金核心系统响应代码';
comment on column PAF_SINGLE_CRDT.CAP_HOSTMSG
  is '本金核心系统响应信息';
comment on column PAF_SINGLE_CRDT.INT_SEQNO
  is '利息核心系统流水号';
comment on column PAF_SINGLE_CRDT.INT_HOSTCODE
  is '利息核心系统响应代码';
comment on column PAF_SINGLE_CRDT.INT_HOSTMSG
  is '利息核心系统响应信息';
comment on column PAF_SINGLE_CRDT.CURR_NO
  is '币种，156';
comment on column PAF_SINGLE_CRDT.CURR_IDEN
  is '钞汇鉴别：1、钞；2、汇';
comment on column PAF_SINGLE_CRDT.SETTLE_TYPE
  is '结算模式：1、同行；2、跨行实时；3、跨行非实时';
comment on column PAF_SINGLE_CRDT.BUS_TYPE
  is '业务类型';
comment on column PAF_SINGLE_CRDT.DE_ACCTNO
  is '付方账号';
comment on column PAF_SINGLE_CRDT.DE_ACCTNAME
  is '付方户名';
comment on column PAF_SINGLE_CRDT.DE_ACCTCLASS
  is '付方账户类别：1、对私；2、对公';
comment on column PAF_SINGLE_CRDT.CAP_AMT
  is '本金发生额';
comment on column PAF_SINGLE_CRDT.DE_INTACCTNO
  is '付息户账号 ';
comment on column PAF_SINGLE_CRDT.DE_INTACCTNAME
  is '付息户户名 ';
comment on column PAF_SINGLE_CRDT.DE_INTACCTCLASS
  is '付息户类别:1、对私；2、对公';
comment on column PAF_SINGLE_CRDT.DE_INTCRACCT
  is '利息收方账号';
comment on column PAF_SINGLE_CRDT.INT_AMT
  is '利息发生额';
comment on column PAF_SINGLE_CRDT.CR_ACCTNO
  is '收方账号';
comment on column PAF_SINGLE_CRDT.CR_ACCTNAME
  is '收方户名 ';
comment on column PAF_SINGLE_CRDT.CR_ACCTCLASS
  is '收方账户类别:1、对私；2、对公';
comment on column PAF_SINGLE_CRDT.CR_BANKCLASS
  is '收方账户行别:0、本行；1、跨行';
comment on column PAF_SINGLE_CRDT.CR_BANKNAME
  is '收方行名 ';
comment on column PAF_SINGLE_CRDT.CR_CHGNO
  is '收方联行号 ';
comment on column PAF_SINGLE_CRDT.AMT
  is '交易金额';
comment on column PAF_SINGLE_CRDT.REF_ACCTNO
  is '业务明细账号';
comment on column PAF_SINGLE_CRDT.REF_SEQNO1
  is '业务明细流水号 1';
comment on column PAF_SINGLE_CRDT.REF_SEQNO2
  is '业务明细流水号 2';
comment on column PAF_SINGLE_CRDT.SUMMARY
  is '摘要';
comment on column PAF_SINGLE_CRDT.REMARK
  is '备注';
alter table PAF_SINGLE_CRDT
add constraint PK_PAF_SINGLE_CRDT primary key (PLAT_DATE, PLAT_TRACE);