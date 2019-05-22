DROP TABLE bocm_rcv_log;
CREATE TABLE bocm_rcv_log (
plat_date NUMBER(8) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(6) NULL ,
cur_time NUMBER(16) NULL ,
source_type NVARCHAR2(20) NULL ,
tx_branch NVARCHAR2(20) NULL ,
tx_ind NVARCHAR2(2) NULL ,
tx_code NVARCHAR2(10) NULL ,
dc_flag NVARCHAR2(2) NULL ,
tx_amt NUMBER NULL ,
fee_flag NVARCHAR2(2) NULL ,
fee NUMBER NULL ,
host_date NUMBER(8) NULL ,
host_traceno NVARCHAR2(20) NULL ,
snd_bankno NVARCHAR2(12) NULL ,
rcv_bankno NVARCHAR2(12) NULL ,
payer_bank NVARCHAR2(12) NULL ,
payer_acttp NVARCHAR2(2) NULL ,
payer_acno NVARCHAR2(50) NULL ,
payer_name NVARCHAR2(100) NULL ,
payee_bank NVARCHAR2(12) NULL ,
payee_acttp NVARCHAR2(2) NULL ,
payee_acno NVARCHAR2(50) NULL ,
payee_name NVARCHAR2(100) NULL ,
bocm_branch NVARCHAR2(20) NULL ,
bocm_date NUMBER(8) NULL ,
bocm_time NUMBER(6) NULL ,
bocm_traceno NVARCHAR2(14) NULL ,
check_flag NVARCHAR2(10) NULL ,
host_state NVARCHAR2(10) NULL ,
bocm_state NVARCHAR2(10) NULL ,
tx_tel NVARCHAR2(30) NULL ,
chk_tel NVARCHAR2(30) NULL ,
auth_tel NVARCHAR2(30) NULL ,
print NVARCHAR2(10) NULL ,
info NVARCHAR2(255) NULL ,
ret_code NVARCHAR2(20) NULL ,
ret_msg NVARCHAR2(200) NULL ,
host_branch NVARCHAR2(20) NULL 
)

;
COMMENT ON TABLE bocm_rcv_log IS '交通柜面通接收流水日志';
COMMENT ON COLUMN bocm_rcv_log.plat_date IS '渠道日期';
COMMENT ON COLUMN bocm_rcv_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN bocm_rcv_log.plat_time IS '交易时间';
COMMENT ON COLUMN bocm_rcv_log.cur_time IS '交易时间戳';
COMMENT ON COLUMN bocm_rcv_log.source_type IS '交易渠道';
COMMENT ON COLUMN bocm_rcv_log.tx_branch IS '交易机构';
COMMENT ON COLUMN bocm_rcv_log.tx_ind IS '现转标志；0现金、1转账、2 普通转账、3 隔日转账、9 其他';
COMMENT ON COLUMN bocm_rcv_log.tx_code IS '交易代码';
COMMENT ON COLUMN bocm_rcv_log.dc_flag IS '通存通兑标志；0通存、1通兑';
COMMENT ON COLUMN bocm_rcv_log.tx_amt IS '交易金额';
COMMENT ON COLUMN bocm_rcv_log.fee_flag IS '手续费收取方式';
COMMENT ON COLUMN bocm_rcv_log.fee IS '开户行手续费';
COMMENT ON COLUMN bocm_rcv_log.host_date IS '核心日期';
COMMENT ON COLUMN bocm_rcv_log.host_traceno IS '核心流水';
COMMENT ON COLUMN bocm_rcv_log.snd_bankno IS '发起行行号';
COMMENT ON COLUMN bocm_rcv_log.rcv_bankno IS '接收行行号';
COMMENT ON COLUMN bocm_rcv_log.payer_bank IS '付款人开户行';
COMMENT ON COLUMN bocm_rcv_log.payer_acttp IS '付款人账户类型';
COMMENT ON COLUMN bocm_rcv_log.payer_acno IS '付款人账户';
COMMENT ON COLUMN bocm_rcv_log.payer_name IS '付款人户名';
COMMENT ON COLUMN bocm_rcv_log.payee_bank IS '收款款人开户行';
COMMENT ON COLUMN bocm_rcv_log.payee_acttp IS '收款人账户类型';
COMMENT ON COLUMN bocm_rcv_log.payee_acno IS '收款人账户';
COMMENT ON COLUMN bocm_rcv_log.payee_name IS '收款人户名';
COMMENT ON COLUMN bocm_rcv_log.bocm_branch IS '交通银行记账机构';
COMMENT ON COLUMN bocm_rcv_log.bocm_date IS '交通银行日期';
COMMENT ON COLUMN bocm_rcv_log.bocm_time IS '交通银行时间';
COMMENT ON COLUMN bocm_rcv_log.bocm_traceno IS '交通银行流水';  
COMMENT ON COLUMN bocm_rcv_log.check_flag IS '对账标志，0-不对账，1-未对账，2-已对账，3-核心多，4-渠道多';
COMMENT ON COLUMN bocm_rcv_log.host_state IS '核心记账状态，        0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
COMMENT ON COLUMN bocm_rcv_log.bocm_state IS '交通银行记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
COMMENT ON COLUMN bocm_rcv_log.tx_tel IS '交易柜员';
COMMENT ON COLUMN bocm_rcv_log.chk_tel IS '复核员';
COMMENT ON COLUMN bocm_rcv_log.auth_tel IS '授权员';
COMMENT ON COLUMN bocm_rcv_log.print IS '打印次数';
COMMENT ON COLUMN bocm_rcv_log.info IS '摘要';
COMMENT ON COLUMN bocm_rcv_log.ret_code IS '核心反馈响应码';
COMMENT ON COLUMN bocm_rcv_log.ret_msg IS '核心反馈响应信息';
COMMENT ON COLUMN bocm_rcv_log.host_branch IS '核心记账机构';

ALTER TABLE bocm_rcv_log ADD PRIMARY KEY (plat_date, plat_trace);
Create Index bocm_rcv_log_index1 On bocm_rcv_log(bocm_traceno);