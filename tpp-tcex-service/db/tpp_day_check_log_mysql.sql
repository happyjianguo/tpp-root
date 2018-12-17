DROP TABLE IF EXISTS tpp_day_check_log;
CREATE TABLE tpp_day_check_log (
plat_date  int(11) COMMENT '渠道日期' ,
plat_trace  int(11) COMMENT '渠道流水' ,
settle_date  int(11) COMMENT '清算日期' ,
settle_branch  varchar(50)  COMMENT '清算机构' ,
host_date  int(11)  COMMENT '核心交易日期' ,
host_traceno  varchar(20)  COMMENT '核心流水号' ,
ccy  varchar(10)  COMMENT '交易币种' ,
tx_amt  decimal(16,2)  COMMENT '交易金额' ,
accountno  varchar(50)  COMMENT '交易账户' ,
reversal  varchar(10)  COMMENT '冲正标志' ,
tx_status  varchar(10)  COMMENT '交易状态；00-成功 02-冲正' 
)comment='村镇柜面通日终对账日志' ;
ALTER TABLE tpp_day_check_log ADD PRIMARY KEY tpp_day_check_log (plat_date,plat_trace);