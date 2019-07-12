DROP TABLE bocm_chk_err;
CREATE TABLE bocm_chk_err(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
tx_code NVARCHAR2(6) NULL ,
tx_source NVARCHAR2(6) NULL ,
host_date NUMBER(11) NULL,
host_traceno NVARCHAR2(20) NULL ,
tx_date NUMBER(11) NOT NULL ,
snd_bankno NVARCHAR2(12) NULL ,
tx_branch NVARCHAR2(10) NULL ,
tx_tel NVARCHAR2(10) NULL ,
tx_ind NVARCHAR2(6) NULL ,
proxy_flag NVARCHAR2(2) NULL ,
proxy_fee NUMBER NULL ,
pre_host_state NVARCHAR2(10) NULL ,
re_host_state NVARCHAR2(10) NULL ,
dc_flag NVARCHAR2(10) NULL ,
direction NVARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
payer_bank NVARCHAR2(12) NULL ,
payer_acno NVARCHAR2(50) NULL ,
payer_name NVARCHAR2(100) NULL ,
payee_bank NVARCHAR2(12) NULL ,
payee_acno NVARCHAR2(50) NULL ,
payee_name NVARCHAR2(100) NULL ,
host_state NVARCHAR2(2) NULL ,
bocm_state NVARCHAR2(2) NULL ,
check_flag NVARCHAR2(10) NULL ,
msg NVARCHAR2(200) NULL 
)

;
COMMENT ON TABLE bocm_chk_err IS '交通柜面通对账问题数据表';
COMMENT ON COLUMN bocm_chk_err.plat_date IS '渠道日期';
COMMENT ON COLUMN bocm_chk_err.plat_trace IS '渠道流水';
COMMENT ON COLUMN bocm_chk_err.tx_code IS '交易代码';
COMMENT ON COLUMN bocm_chk_err.tx_source IS '交易来源';
COMMENT ON COLUMN bocm_chk_err.host_date IS '主机日期';
COMMENT ON COLUMN bocm_chk_err.host_traceno IS '主机流水';
COMMENT ON COLUMN bocm_chk_err.tx_date IS '交易时间';
COMMENT ON COLUMN bocm_chk_err.snd_bankno IS '受理行行号';
COMMENT ON COLUMN bocm_chk_err.tx_branch IS '交易机构';
COMMENT ON COLUMN bocm_chk_err.tx_tel IS '交易柜员';
COMMENT ON COLUMN bocm_chk_err.tx_ind IS '业务模式';
COMMENT ON COLUMN bocm_chk_err.tx_amt IS '交易金额';
COMMENT ON COLUMN bocm_chk_err.proxy_flag IS '代理手续费收取标志 规则：交易受理方收取代理手续费0 （交行支付代理手续费）1 （交行收取代理手续费）';
COMMENT ON COLUMN bocm_chk_err.proxy_fee IS '代理手续费';
COMMENT ON COLUMN bocm_chk_err.pre_host_state IS '调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
COMMENT ON COLUMN bocm_chk_err.re_host_state IS '调整前核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
COMMENT ON COLUMN bocm_chk_err.payer_bank IS '付款人开户行';
COMMENT ON COLUMN bocm_chk_err.payer_acno IS '付款人账户';
COMMENT ON COLUMN bocm_chk_err.payer_name IS '付款人户名';
COMMENT ON COLUMN bocm_chk_err.payee_bank IS '收款款人开户行';
COMMENT ON COLUMN bocm_chk_err.payee_acno IS '收款人账户';
COMMENT ON COLUMN bocm_chk_err.payee_name IS '收款人户名';
COMMENT ON COLUMN bocm_chk_err.bocm_state IS '交行核心记账状态，0-登记，1-成功，2-失败，3-超时，4-冲正成功，5-冲正失败，6-冲正超时';
COMMENT ON COLUMN bocm_chk_err.dc_flag IS '通存通兑标志；0通存、1通兑';
COMMENT ON COLUMN bocm_chk_err.check_flag IS '对账标志，1-未对账，2-已对账，3-核心多，4-渠道多';
COMMENT ON COLUMN bocm_chk_err.direction IS '来往帐标志，I：来账，O：往帐';
COMMENT ON COLUMN bocm_chk_err.msg IS '描述';

ALTER TABLE bocm_chk_err ADD PRIMARY KEY (plat_date, plat_trace);