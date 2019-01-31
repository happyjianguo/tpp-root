DROP TABLE tcex_rcv_log;
CREATE TABLE tcex_rcv_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL ,
source_type NVARCHAR2(20) NULL ,
tx_branch NVARCHAR2(20) NULL ,
tx_ind NVARCHAR2(10) NULL ,
dc_flag NVARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
host_date NUMBER(11) NULL ,
host_traceno NVARCHAR2(20) NULL ,
payer_acno NVARCHAR2(50) NULL ,
payer_name NVARCHAR2(100) NULL ,
payee_acno NVARCHAR2(50) NULL ,
payee_name NVARCHAR2(100) NULL ,
town_branch NVARCHAR2(20) NULL ,
town_date NUMBER(11) NULL ,
town_traceno NVARCHAR2(20) NULL ,
check_flag NVARCHAR2(10) NULL ,
host_state NVARCHAR2(10) NULL ,
town_state NVARCHAR2(10) NULL ,
tx_tel NVARCHAR2(30) NULL ,
chk_tel NVARCHAR2(30) NULL ,
auth_tel NVARCHAR2(30) NULL ,
print NVARCHAR2(10) NULL ,
info NVARCHAR2(255) NULL ,
ret_code NVARCHAR2(20) NULL ,
ret_msg NVARCHAR2(200) NULL ,
town_flag NVARCHAR2(5) NULL ,
host_branch NVARCHAR2(20) NULL 
)

;
COMMENT ON TABLE tcex_rcv_log IS '村镇柜面通接收流水日志';
COMMENT ON COLUMN tcex_rcv_log.plat_date IS '渠道日期';
COMMENT ON COLUMN tcex_rcv_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN tcex_rcv_log.plat_time IS '交易时间';
COMMENT ON COLUMN tcex_rcv_log.source_type IS '交易渠道';
COMMENT ON COLUMN tcex_rcv_log.tx_branch IS '交易机构';
COMMENT ON COLUMN tcex_rcv_log.tx_ind IS '现转标志；0现金、1转账';
COMMENT ON COLUMN tcex_rcv_log.dc_flag IS '通存通兑标志；0通存、1通兑';
COMMENT ON COLUMN tcex_rcv_log.tx_amt IS '交易金额';
COMMENT ON COLUMN tcex_rcv_log.host_date IS '核心日期';
COMMENT ON COLUMN tcex_rcv_log.host_traceno IS '核心流水';
COMMENT ON COLUMN tcex_rcv_log.payer_acno IS '付款人账户';
COMMENT ON COLUMN tcex_rcv_log.payer_name IS '付款人户名';
COMMENT ON COLUMN tcex_rcv_log.payee_acno IS '收款人账户';
COMMENT ON COLUMN tcex_rcv_log.payee_name IS '收款人户名';
COMMENT ON COLUMN tcex_rcv_log.town_branch IS '村镇记账机构';
COMMENT ON COLUMN tcex_rcv_log.town_date IS '村镇日期';
COMMENT ON COLUMN tcex_rcv_log.town_traceno IS '村镇流水';
COMMENT ON COLUMN tcex_rcv_log.check_flag IS '对账标志，1-未对账，2-已对账';
COMMENT ON COLUMN tcex_rcv_log.host_state IS '核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时';
COMMENT ON COLUMN tcex_rcv_log.town_state IS '村镇记账状态，0-登记';
COMMENT ON COLUMN tcex_rcv_log.tx_tel IS '交易柜员';
COMMENT ON COLUMN tcex_rcv_log.chk_tel IS '复核员';
COMMENT ON COLUMN tcex_rcv_log.auth_tel IS '授权员';
COMMENT ON COLUMN tcex_rcv_log.print IS '打印次数';
COMMENT ON COLUMN tcex_rcv_log.info IS '摘要';
COMMENT ON COLUMN tcex_rcv_log.ret_code IS '核心反馈响应码';
COMMENT ON COLUMN tcex_rcv_log.ret_msg IS '核心反馈响应信息';
COMMENT ON COLUMN tcex_rcv_log.town_flag IS '村镇标志';
COMMENT ON COLUMN tcex_rcv_log.host_branch IS '核心记账机构';

ALTER TABLE tcex_rcv_log ADD PRIMARY KEY (plat_date, plat_trace);