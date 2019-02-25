DROP TABLE tcex_chk_err;
CREATE TABLE tcex_chk_err(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
pre_host_state NVARCHAR2(10) NULL ,
re_host_state NVARCHAR2(10) NULL ,
dc_flag NVARCHAR2(10) NULL ,
check_flag NVARCHAR2(10) NULL ,
direction NVARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
payer_acno NVARCHAR2(50) NULL ,
payer_name NVARCHAR2(100) NULL ,
payee_acno NVARCHAR2(50) NULL ,
payee_name NVARCHAR2(100) NULL ,
msg NVARCHAR2(200) NULL 
)

;
COMMENT ON TABLE tcex_chk_err IS '村镇柜面通对账问题数据表';
COMMENT ON COLUMN tcex_chk_err.plat_date IS '渠道日期';
COMMENT ON COLUMN tcex_chk_err.plat_trace IS '渠道流水';
COMMENT ON COLUMN tcex_chk_err.pre_host_state IS '调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时';
COMMENT ON COLUMN tcex_chk_err.re_host_state IS '调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-存款确认，5-冲正成功，6-冲正失败，7-冲正超时';
COMMENT ON COLUMN tcex_chk_err.dc_flag IS '通存通兑标志；0通存、1通兑';
COMMENT ON COLUMN tcex_chk_err.check_flag IS '对账标志，1-未对账，2-已对账，3-核心多，4-渠道多';
COMMENT ON COLUMN tcex_chk_err.direction IS '来往帐标志，I：来账，O：往帐';
COMMENT ON COLUMN tcex_chk_err.tx_amt IS '交易金额';
COMMENT ON COLUMN tcex_chk_err.payer_acno IS '付款人账户';
COMMENT ON COLUMN tcex_chk_err.payer_name IS '付款人户名';
COMMENT ON COLUMN tcex_chk_err.payee_acno IS '收款人账户';
COMMENT ON COLUMN tcex_chk_err.payee_name IS '收款人户名';
COMMENT ON COLUMN tcex_chk_err.msg IS '描述';

ALTER TABLE tcex_chk_err ADD PRIMARY KEY (plat_date, plat_trace);