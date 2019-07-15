DROP TABLE bocm_chk_status;
CREATE TABLE bocm_chk_status(
chk_date NUMBER(11) NOT NULL ,
host_status NUMBER(6) NULL ,
host_tx_cnt NUMBER(8) NULL ,
host_tx_amt NUMBER NULL,
bocm_status NUMBER(6) NULL ,
bocm_tx_cnt NUMBER(8) NULL ,
bocm_tx_amt NUMBER NULL,
plat_status NUMBER(6) NULL ,
plat_tx_cnt NUMBER(8) NULL ,
plat_tx_amt NUMBER NULL,
tx_branch NVARCHAR2(20) NULL ,
tx_tel NVARCHAR2(30) NULL 
);

COMMENT ON TABLE bocm_chk_status IS '对账状态表';
COMMENT ON COLUMN bocm_chk_status.chk_date IS '对账日期';
COMMENT ON COLUMN bocm_chk_status.host_status IS '核心对账状态  0未对账，1已对账';
COMMENT ON COLUMN bocm_chk_status.host_tx_cnt IS '我行为主交易笔数';
COMMENT ON COLUMN bocm_chk_status.host_tx_amt IS '我行为主交易金额';
COMMENT ON COLUMN bocm_chk_status.bocm_status IS '交行对账状态  0未对账，1已对账';
COMMENT ON COLUMN bocm_chk_status.bocm_tx_cnt IS '交行为主交易笔数';
COMMENT ON COLUMN bocm_chk_status.bocm_tx_amt IS '交行为主交易金额';
COMMENT ON COLUMN bocm_chk_status.plat_status IS '我行对账状态  0未对账，1已对账';
COMMENT ON COLUMN bocm_chk_status.plat_tx_cnt IS '我行为主交易笔数';
COMMENT ON COLUMN bocm_chk_status.plat_tx_amt IS '我行为主交易金额';
COMMENT ON COLUMN bocm_chk_status.tx_branch IS '交易机构';
COMMENT ON COLUMN bocm_chk_status.tx_tel IS '交易柜员号';


ALTER TABLE bocm_chk_status ADD PRIMARY KEY (chk_date);