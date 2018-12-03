drop table paf_single_dbit;
create table PAF_SINGLE_DBIT
(
  TX_BRNO      VARCHAR2(10 CHAR),
  PLAT_DATE    NUMBER(11) not null,
  PLAT_TIME    NUMBER(11),
  PLAT_TRACE   NUMBER(11) not null,
  UP_BRNO      VARCHAR2(10 CHAR),
  LOG_ID       VARCHAR2(60 CHAR),
  LOG_DEV      VARCHAR2(20 CHAR),
  SND_DATE     VARCHAR2(8 CHAR),
  SND_TIME     VARCHAR2(6 CHAR),
  SND_SEQNO    VARCHAR2(32 CHAR),
  SND_UNITNO   VARCHAR2(15 CHAR),
  SND_NODE     VARCHAR2(6 CHAR),
  RCV_NODE     VARCHAR2(6 CHAR),
  BDC_DATE     VARCHAR2(8 CHAR),
  BDC_TIME     VARCHAR2(6 CHAR),
  BDC_SEQNO    VARCHAR2(32 CHAR),
  CUS_NO       VARCHAR2(32 CHAR),
  OPR_NO       VARCHAR2(21 CHAR),
  TX_STATUS    VARCHAR2(1 CHAR),
  CAP_SEQNO    VARCHAR2(32 CHAR),
  CAP_HOSTCODE VARCHAR2(20 CHAR),
  CAP_HOSTMSG  VARCHAR2(120 CHAR),
  INT_SEQNO    VARCHAR2(32 CHAR),
  INT_HOSTCODE VARCHAR2(20 CHAR),
  INT_HOSTMSG  VARCHAR2(120 CHAR),
  CURR_NO      VARCHAR2(3 CHAR),
  CURR_IDEN    VARCHAR2(1 CHAR),
  SETTLE_TYPE  VARCHAR2(1 CHAR),
  BUS_TYPE     VARCHAR2(6 CHAR),
  CR_ACCTNO    VARCHAR2(32 CHAR),
  CR_ACCTNAME  VARCHAR2(255 CHAR),
  CR_ACCTCLASS VARCHAR2(1 CHAR),
  DE_ACCTNO    VARCHAR2(32 CHAR),
  DE_ACCTNAME  VARCHAR2(255 CHAR),
  DE_ACCTCLASS VARCHAR2(1 CHAR),
  DE_BANKNAME  VARCHAR2(60 CHAR),
  DE_CHGNO     VARCHAR2(60 CHAR),
  DE_BANKCLASS VARCHAR2(1 CHAR),
  CON_AGRNO    VARCHAR2(32 CHAR),
  AMT          NUMBER,
  REF_ACCTNO   VARCHAR2(32 CHAR),
  REF_SEQNO    VARCHAR2(32 CHAR),
  SUMMARY      VARCHAR2(60 CHAR),
  REMARK       VARCHAR2(255 CHAR)
);
comment on column PAF_SINGLE_DBIT.TX_BRNO
  is '交易机构';
comment on column PAF_SINGLE_DBIT.PLAT_DATE
  is '平台日期';
comment on column PAF_SINGLE_DBIT.PLAT_TIME
  is '平台时间';
comment on column PAF_SINGLE_DBIT.PLAT_TRACE
  is '平台流水';
comment on column PAF_SINGLE_DBIT.UP_BRNO
  is '上级机构';
comment on column PAF_SINGLE_DBIT.LOG_ID
  is '日志ID';
comment on column PAF_SINGLE_DBIT.LOG_DEV
  is '日志设备';
comment on column PAF_SINGLE_DBIT.SND_DATE
  is '发送方日期';
comment on column PAF_SINGLE_DBIT.SND_TIME
  is '发送方时间';
comment on column PAF_SINGLE_DBIT.SND_SEQNO
  is '发送方流水';
comment on column PAF_SINGLE_DBIT.SND_UNITNO
  is '公积金机构号';
comment on column PAF_SINGLE_DBIT.SND_NODE
  is '发送方节点号';
comment on column PAF_SINGLE_DBIT.RCV_NODE
  is '接收方节点号';
comment on column PAF_SINGLE_DBIT.BDC_DATE
  is '结算系统日期';
comment on column PAF_SINGLE_DBIT.BDC_TIME
  is '结算系统时间';
comment on column PAF_SINGLE_DBIT.BDC_SEQNO
  is '结算系统流水';
comment on column PAF_SINGLE_DBIT.CUS_NO
  is '银行客户编号';
comment on column PAF_SINGLE_DBIT.OPR_NO
  is '操作员编号';
comment on column PAF_SINGLE_DBIT.TX_STATUS
  is '交易状态：0、登记；1、本金记账成功；2、利息记账成功；3、实时交易成功；4、实时部分成功；5、异步受理成功；9、失败';
comment on column PAF_SINGLE_DBIT.CAP_SEQNO
  is '本金核心系统流水号';
comment on column PAF_SINGLE_DBIT.CAP_HOSTCODE
  is '本金核心系统响应代码';
comment on column PAF_SINGLE_DBIT.CAP_HOSTMSG
  is '本金核心系统响应信息';
comment on column PAF_SINGLE_DBIT.INT_SEQNO
  is '利息核心系统流水号';
comment on column PAF_SINGLE_DBIT.INT_HOSTCODE
  is '利息核心系统响应代码';
comment on column PAF_SINGLE_DBIT.INT_HOSTMSG
  is '利息核心系统响应信息';
comment on column PAF_SINGLE_DBIT.CURR_NO
  is '币种，156';
comment on column PAF_SINGLE_DBIT.CURR_IDEN
  is '钞汇鉴别：1、钞；2、汇';
comment on column PAF_SINGLE_DBIT.SETTLE_TYPE
  is '结算模式：1、同行；2、跨行实时；3、跨行非实时';
comment on column PAF_SINGLE_DBIT.BUS_TYPE
  is '业务类型';
comment on column PAF_SINGLE_DBIT.CR_ACCTNO
  is '收方账号';
comment on column PAF_SINGLE_DBIT.CR_ACCTNAME
  is '收方户名 ';
comment on column PAF_SINGLE_DBIT.CR_ACCTCLASS
  is '收方账户类别:1、对私；2、对公';
comment on column PAF_SINGLE_DBIT.DE_ACCTNO
  is '付方账号';
comment on column PAF_SINGLE_DBIT.DE_ACCTNAME
  is '付方户名';
comment on column PAF_SINGLE_DBIT.DE_ACCTCLASS
  is '付方账户类别：1、对私；2、对公';
comment on column PAF_SINGLE_DBIT.DE_BANKNAME
  is '付方行名';
comment on column PAF_SINGLE_DBIT.DE_CHGNO
  is '付方联行号';
comment on column PAF_SINGLE_DBIT.DE_BANKCLASS
  is '付方账户行别:0、本行；1、跨行';
comment on column PAF_SINGLE_DBIT.CON_AGRNO
  is '多方协议号';
comment on column PAF_SINGLE_DBIT.AMT
  is '交易金额';
comment on column PAF_SINGLE_DBIT.REF_ACCTNO
  is '业务明细账号';
comment on column PAF_SINGLE_DBIT.REF_SEQNO
  is '业务明细流水号';
comment on column PAF_SINGLE_DBIT.SUMMARY
  is '摘要';
comment on column PAF_SINGLE_DBIT.REMARK
  is '备注';
alter table PAF_SINGLE_DBIT
  add constraint PK_PAF_SINGLE_DBIT primary key (PLAT_DATE, PLAT_TRACE);