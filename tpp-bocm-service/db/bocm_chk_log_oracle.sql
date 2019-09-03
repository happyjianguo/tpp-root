DROP TABLE bocm_chk_log;
CREATE TABLE bocm_chk_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
tran_type NVARCHAR2(10) NULL ,
settle_date NUMBER(11) NULL ,
settle_branch NVARCHAR2(50) NULL ,
host_date NUMBER(11) NULL ,
host_traceno NVARCHAR2(20) NULL ,
ccy NVARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
accountno NVARCHAR2(50) NULL ,
reversal NVARCHAR2(10) NULL ,
tx_status NVARCHAR2(10) NULL ,
direction NVARCHAR2(1) NULL 
)

;
COMMENT ON TABLE bocm_chk_log IS '交通柜面通日终对账日志';
COMMENT ON COLUMN bocm_chk_log.plat_date IS '渠道日期';
COMMENT ON COLUMN bocm_chk_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN bocm_chk_log.tran_type IS '交易类型';
COMMENT ON COLUMN bocm_chk_log.settle_date IS '清算日期';
COMMENT ON COLUMN bocm_chk_log.settle_branch IS '清算机构';
COMMENT ON COLUMN bocm_chk_log.host_date IS '核心交易日期';
COMMENT ON COLUMN bocm_chk_log.host_traceno IS '核心流水号';
COMMENT ON COLUMN bocm_chk_log.ccy IS '交易币种';
COMMENT ON COLUMN bocm_chk_log.tx_amt IS '交易金额';
COMMENT ON COLUMN bocm_chk_log.accountno IS '交易账户';
COMMENT ON COLUMN bocm_chk_log.reversal IS '冲正标志';
COMMENT ON COLUMN bocm_chk_log.tx_status IS '交易状态；00-成功 02-冲正';
COMMENT ON COLUMN bocm_chk_log.direction IS '来往账标识';

ALTER TABLE bocm_chk_log ADD PRIMARY KEY (plat_date, plat_trace);