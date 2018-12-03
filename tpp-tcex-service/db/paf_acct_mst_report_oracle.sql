DROP TABLE IF EXISTS PAF_ACCT_MST_REPORT;
-- Create table
create table PAF_ACCT_MST_REPORT
(
  ID            NUMBER(11) not null,
  FILE_NAME     NVARCHAR2(100),
  DEPART_CODE   NVARCHAR2(5),
  CENTER_NO     NVARCHAR2(20),
  REPORT_TIME   NVARCHAR2(20),
  REPORT_USER   NUMBER(11),
  NUM           NVARCHAR2(20),
  ACCT_NO       NVARCHAR2(32),
  REFERENCE     NVARCHAR2(32),
  TRAN_CODE     NVARCHAR2(6),
  OTH_ACCT_NO   NVARCHAR2(32),
  OTH_ACCT_NAME NVARCHAR2(255),
  TRAN_AMT      NUMBER,
  TRAN_DATE     NVARCHAR2(8),
  TRAN_TIME     NVARCHAR2(6),
  AVAILABLE_AMT NUMBER,
  BRANCH        NVARCHAR2(20),
  REMARK        NVARCHAR2(255),
  CCY           NVARCHAR2(3),
  AMT_TYPE      NVARCHAR2(1),
  BALANCE       NUMBER,
  ODT_BALANCE   NUMBER,
  DOC_TYPE      NVARCHAR2(4),
  VOUCHER_NO    NVARCHAR2(16),
  OTH_BRANCH    NVARCHAR2(32),
  NARRATIVE     NVARCHAR2(60),
  REVERSAK      NVARCHAR2(1),
  SERIAL_NUM    NVARCHAR2(50),
  VOLUME_NUM    NVARCHAR2(50)
);
-- Add comments to the table 
comment on table PAF_ACCT_MST_REPORT
  is '账户变动通知表';
-- Add comments to the columns 
comment on column PAF_ACCT_MST_REPORT.ID
  is '流水id';
comment on column PAF_ACCT_MST_REPORT.FILE_NAME
  is '上送文件名';
comment on column PAF_ACCT_MST_REPORT.DEPART_CODE
  is '部门编码';
comment on column PAF_ACCT_MST_REPORT.CENTER_NO
  is '公积金中心编号';
comment on column PAF_ACCT_MST_REPORT.REPORT_TIME
  is '上送时间，格式:YYYYMMDDHHMMSS';
comment on column PAF_ACCT_MST_REPORT.REPORT_USER
  is '上送人';
comment on column PAF_ACCT_MST_REPORT.NUM
  is '序号';
comment on column PAF_ACCT_MST_REPORT.ACCT_NO
  is '帐号';
comment on column PAF_ACCT_MST_REPORT.REFERENCE
  is '银行主机流水号';
comment on column PAF_ACCT_MST_REPORT.TRAN_CODE
  is '交易代码';
comment on column PAF_ACCT_MST_REPORT.OTH_ACCT_NO
  is '交易对手账号';
comment on column PAF_ACCT_MST_REPORT.OTH_ACCT_NAME
  is '交易对手户名';
comment on column PAF_ACCT_MST_REPORT.TRAN_AMT
  is '金额';
comment on column PAF_ACCT_MST_REPORT.TRAN_DATE
  is '交易日期，格式:YYYYMMDD';
comment on column PAF_ACCT_MST_REPORT.TRAN_TIME
  is '交易时间，格式:HHMMSS';
comment on column PAF_ACCT_MST_REPORT.AVAILABLE_AMT
  is '可用金额';
comment on column PAF_ACCT_MST_REPORT.BRANCH
  is '开户机构号';
comment on column PAF_ACCT_MST_REPORT.REMARK
  is '备注';
comment on column PAF_ACCT_MST_REPORT.CCY
  is '币种，默认156';
comment on column PAF_ACCT_MST_REPORT.AMT_TYPE
  is '钞汇标志，1:钞 2:汇';
comment on column PAF_ACCT_MST_REPORT.BALANCE
  is '余额';
comment on column PAF_ACCT_MST_REPORT.ODT_BALANCE
  is '可透支余额';
comment on column PAF_ACCT_MST_REPORT.DOC_TYPE
  is '凭证种类';
comment on column PAF_ACCT_MST_REPORT.VOUCHER_NO
  is '凭证号码';
comment on column PAF_ACCT_MST_REPORT.OTH_BRANCH
  is '交易对手行号';
comment on column PAF_ACCT_MST_REPORT.NARRATIVE
  is '摘要';
comment on column PAF_ACCT_MST_REPORT.REVERSAK
  is '冲正标识，Y:冲正，N:未冲正';
comment on column PAF_ACCT_MST_REPORT.SERIAL_NUM
  is '笔号';
comment on column PAF_ACCT_MST_REPORT.VOLUME_NUM
  is '册号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PAF_ACCT_MST_REPORT add primary key (ID) using index;
-- Create/Recreate indexes 
create index PAF_ACCT_MST_REPORT_INDEX1 on PAF_ACCT_MST_REPORT (FILE_NAME);
create index PAF_ACCT_MST_REPORT_INDEX2 on PAF_ACCT_MST_REPORT (REPORT_TIME);
create index PAF_ACCT_MST_REPORT_INDEX3 on PAF_ACCT_MST_REPORT (ACCT_NO, REFERENCE);
