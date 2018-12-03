drop table PAF_BATCH_LOAN_MASTER;
-- Create table
create table PAF_BATCH_LOAN_MASTER
(
  TX_BRNO          NVARCHAR2(10),
  PLAT_DATE        NUMBER(11) not null,
  PLAT_TIME        NUMBER(11),
  PLAT_TRACE       NUMBER(11) not null,
  UP_BRNO          NVARCHAR2(10),
  LOG_ID           NVARCHAR2(60),
  LOG_DEV          NVARCHAR2(20),
  SND_DATE         NVARCHAR2(8),
  SND_TIME         NVARCHAR2(6),
  SND_SEQNO        NVARCHAR2(32),
  SND_UNITNO       NVARCHAR2(15),
  SND_NODE         NVARCHAR2(6),
  RCV_NODE         NVARCHAR2(6),
  BDC_DATE         NVARCHAR2(8),
  BDC_TIME         NVARCHAR2(6),
  BDC_SEQNO        NVARCHAR2(32),
  CUS_NO           NVARCHAR2(32),
  OPR_NO           NVARCHAR2(21),
  TX_STATUS        NVARCHAR2(1),
  TX_MSG           NVARCHAR2(120),
  SUCC_HOSTSEQNO   NVARCHAR2(32),
  SUCC_HOSTRSPCODE NVARCHAR2(20),
  SUCC_HOSTRSPMSG  NVARCHAR2(120),
  BATCH_FILENAME   NVARCHAR2(120),
  BATCH_NO         NVARCHAR2(20),
  CURR_NO          NVARCHAR2(3),
  CURR_IDEN        NVARCHAR2(1),
  FILE_TYPE        NVARCHAR2(1),
  BATCH_PRJNO      NUMBER(11),
  CR_ACCTNO        NVARCHAR2(32),
  CR_ACCTNAME      NVARCHAR2(255),
  CR_ACCTCLASS     NVARCHAR2(1),
  BANK_ACCNO       NVARCHAR2(32),
  TOTAL_NUM        NUMBER(11),
  TOTAL_AMT        NUMBER,
  SUCC_NUM         NUMBER(11),
  SUCC_AMT         NUMBER,
  FAIL_NUM         NUMBER(11),
  FAIL_AMT         NUMBER,
  REMARK           NVARCHAR2(255)
);
-- Add comments to the table 
comment on table PAF_BATCH_LOAN_MASTER
  is '公积金批量贷款扣款主表';
-- Add comments to the columns 
comment on column PAF_BATCH_LOAN_MASTER.TX_BRNO
  is '交易机构';
comment on column PAF_BATCH_LOAN_MASTER.PLAT_DATE
  is '平台日期';
comment on column PAF_BATCH_LOAN_MASTER.PLAT_TIME
  is '平台时间';
comment on column PAF_BATCH_LOAN_MASTER.PLAT_TRACE
  is '平台流水';
comment on column PAF_BATCH_LOAN_MASTER.UP_BRNO
  is '上级机构';
comment on column PAF_BATCH_LOAN_MASTER.LOG_ID
  is '日志ID';
comment on column PAF_BATCH_LOAN_MASTER.LOG_DEV
  is '日志设备';
comment on column PAF_BATCH_LOAN_MASTER.SND_DATE
  is '发送方日期';
comment on column PAF_BATCH_LOAN_MASTER.SND_TIME
  is '发送方时间';
comment on column PAF_BATCH_LOAN_MASTER.SND_SEQNO
  is '发送方流水';
comment on column PAF_BATCH_LOAN_MASTER.SND_UNITNO
  is '公积金机构号';
comment on column PAF_BATCH_LOAN_MASTER.SND_NODE
  is '发送方节点号';
comment on column PAF_BATCH_LOAN_MASTER.RCV_NODE
  is '接收方节点号';
comment on column PAF_BATCH_LOAN_MASTER.BDC_DATE
  is '结算系统日期';
comment on column PAF_BATCH_LOAN_MASTER.BDC_TIME
  is '结算系统时间';
comment on column PAF_BATCH_LOAN_MASTER.BDC_SEQNO
  is '结算系统流水';
comment on column PAF_BATCH_LOAN_MASTER.CUS_NO
  is '银行客户编号';
comment on column PAF_BATCH_LOAN_MASTER.OPR_NO
  is '操作员编号';
comment on column PAF_BATCH_LOAN_MASTER.TX_STATUS
  is '交易状态：0、接收；1、登记；2、处理中；3、处理完成；4、转入公积金账户；9、处理失败';
comment on column PAF_BATCH_LOAN_MASTER.TX_MSG
  is '交易处理信息';
comment on column PAF_BATCH_LOAN_MASTER.SUCC_HOSTSEQNO
  is '成功明细核心系统流水号';
comment on column PAF_BATCH_LOAN_MASTER.SUCC_HOSTRSPCODE
  is '成功明细核心系统响应代码';
comment on column PAF_BATCH_LOAN_MASTER.SUCC_HOSTRSPMSG
  is '成功明细核心系统响应信息';
comment on column PAF_BATCH_LOAN_MASTER.BATCH_FILENAME
  is '批量文件名';
comment on column PAF_BATCH_LOAN_MASTER.BATCH_NO
  is '批量编号CRDT开头，贷款是LOAN开头';
comment on column PAF_BATCH_LOAN_MASTER.CURR_NO
  is '币种，156';
comment on column PAF_BATCH_LOAN_MASTER.CURR_IDEN
  is '钞汇鉴别：1、钞；2、汇';
comment on column PAF_BATCH_LOAN_MASTER.FILE_TYPE
  is '文件类型：1、同行；2、跨行；3、混合';
comment on column PAF_BATCH_LOAN_MASTER.BATCH_PRJNO
  is '批量项目编号';
comment on column PAF_BATCH_LOAN_MASTER.CR_ACCTNO
  is '收方账号';
comment on column PAF_BATCH_LOAN_MASTER.CR_ACCTNAME
  is '收方户名';
comment on column PAF_BATCH_LOAN_MASTER.CR_ACCTCLASS
  is '收方账户类别：1、对私；2、对公';
comment on column PAF_BATCH_LOAN_MASTER.BANK_ACCNO
  is '银行内部过渡户';
comment on column PAF_BATCH_LOAN_MASTER.TOTAL_NUM
  is '总笔数';
comment on column PAF_BATCH_LOAN_MASTER.TOTAL_AMT
  is '总金额';
comment on column PAF_BATCH_LOAN_MASTER.SUCC_NUM
  is '成功总笔数';
comment on column PAF_BATCH_LOAN_MASTER.SUCC_AMT
  is '成功总金额';
comment on column PAF_BATCH_LOAN_MASTER.FAIL_NUM
  is '失败总笔数';
comment on column PAF_BATCH_LOAN_MASTER.FAIL_AMT
  is '失败总金额';
comment on column PAF_BATCH_LOAN_MASTER.REMARK
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PAF_BATCH_LOAN_MASTER add primary key (PLAT_DATE, PLAT_TRACE);
create unique index PAF_BATCH_LOAN_MASTER_INDEX1 on PAF_BATCH_LOAN_MASTER (BATCH_NO);