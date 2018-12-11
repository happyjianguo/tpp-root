DROP TABLE IF EXISTS tpp_snd_trace_log;
CREATE TABLE tpp_snd_trace_log (
  plat_date int(11) COMMENT '渠道日期',
  plat_trace int(11) COMMENT '渠道流水',
  plat_time int(11)  COMMENT '交易时间',
  source_type varchar(20)  COMMENT '交易渠道',
  tx_branch varchar(20)  COMMENT '交易机构',
  tx_ind varchar(100)  COMMENT '现转标志',
  dc_flag varchar(10)  COMMENT '通存通兑标志',
  tx_amt decimal(16,2)  COMMENT '交易金额',
  host_date int(11)  COMMENT '核心日期',
  host_traceno varchar(20)  COMMENT '核心流水',
  payer_acno varchar(50)  COMMENT '付款人账户',
  payer_name varchar(100)  COMMENT '付款人户名',
  payee_acno varchar(50)  COMMENT '收款人账户',
  payee_name varchar(100)  COMMENT '收款人户名',
  town_branch varchar(20)  COMMENT '村镇机构',
  town_date int(11)  COMMENT '村镇日期',
  town_traceno varchar(20)  COMMENT '村镇流水',
  check_flag varchar(10)  COMMENT '对账标志',
  host_state varchar(10)  COMMENT '核心记账状态',
  town_state varchar(10)  COMMENT '村镇记账状态',
  tx_tel varchar(30)  COMMENT '交易柜员',
  chk_tel varchar(30)  COMMENT '复核员',
  auth_tel varchar(30)  COMMENT '授权员',
  print varchar(10)  COMMENT '打印次数',
  info varchar(255)  COMMENT '摘要'
) comment='村镇柜面通发送流水日志' ;
ALTER TABLE tpp_snd_trace_log ADD PRIMARY KEY tpp_snd_trace_log (plat_date,plat_trace);