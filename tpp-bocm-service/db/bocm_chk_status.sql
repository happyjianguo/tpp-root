DROP TABLE bocm_chk_status;
CREATE TABLE bocm_chk_status(
chk_date NUMBER(11) NOT NULL ,
host_status NUMBER(6) NOT NULL ,
bocm_status NUMBER(6) NOT NULL ,
plat_status NUMBER(6) NOT NULL 
);

COMMENT ON TABLE bocm_chk_status IS '对账状态表';
COMMENT ON COLUMN bocm_chk_status.chk_date IS '对账日期';
COMMENT ON COLUMN bocm_chk_status.host_status IS '核心对账状态  0未对账，1已对账';
COMMENT ON COLUMN bocm_chk_status.bocm_status IS '交行对账状态  0未对账，1已对账';
COMMENT ON COLUMN bocm_chk_status.plat_status IS '我行对账状态  0未对账，1已对账';

ALTER TABLE bocm_chk_status ADD PRIMARY KEY (chk_date);