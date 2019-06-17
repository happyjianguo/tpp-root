DROP TABLE mivs_cmonconf_info;
CREATE TABLE mivs_cmonconf_info(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
tran_date NVARCHAR2(10) NULL ,
seq_no NVARCHAR2(22) NOT NULL ,
tran_time NVARCHAR2(10) NULL,
msg_id NVARCHAR2(35) NOT NULL ,
cre_dt_tm NVARCHAR2(20) NULL ,
instg_drct_pty NVARCHAR2(14) NULL ,
instg_pty NVARCHAR2(14) NULL ,
instd_drct_pty NVARCHAR2(14) NULL ,
instd_pty NVARCHAR2(14) NULL ,
system_code NVARCHAR2(10) NULL,
remark NVARCHAR2(256) NULL,
orgnl_msg_id NVARCHAR2(35) NULL,
orgnl_instg_pty NVARCHAR2(14) NULL,
orgnl_mt NVARCHAR2(35) NULL,

proc_sts NVARCHAR2(4) NULL,
proc_cd NVARCHAR2(8) NULL,
pty_id NVARCHAR2(14) NULL,
pty_prc_cd NVARCHAR2(4) NULL,
rjct_inf NVARCHAR2(120) NULL,
prc_dt NVARCHAR2(22) NULL,
netg_rnd NVARCHAR2(4) NULL
)

;
COMMENT ON TABLE mivs_cmonconf_info IS '企业信息联网核查_通用处理确认报文主表';
COMMENT ON COLUMN mivs_cmonconf_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_cmonconf_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_cmonconf_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_cmonconf_info.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_cmonconf_info.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_cmonconf_info.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_cmonconf_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_cmonconf_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_cmonconf_info.instg_drct_pty IS '发起直接参与机构'
COMMENT ON COLUMN mivs_cmonconf_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_cmonconf_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_cmonconf_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_cmonconf_info.system_code IS '系统编号';
COMMENT ON COLUMN mivs_cmonconf_info.remark IS '备注';
COMMENT ON COLUMN mivs_cmonconf_info.orgnl_msg_id IS '原报文标识号';
COMMENT ON COLUMN mivs_cmonconf_info.orgnl_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_cmonconf_info.orgnl_mt IS '原报文类型';

COMMENT ON COLUMN mivs_cmonconf_info.proc_sts IS '申请报文拒绝状态';
COMMENT ON COLUMN mivs_cmonconf_info.proc_cd IS '申请报文拒绝码';
COMMENT ON COLUMN mivs_cmonconf_info.pty_id IS '拒绝业务的参与机构行号';
COMMENT ON COLUMN mivs_cmonconf_info.pty_prc_cd IS '参与机构业务拒绝码';
COMMENT ON COLUMN mivs_cmonconf_info.rjct_inf IS '申请报文拒绝信息';
COMMENT ON COLUMN mivs_cmonconf_info.prc_dt IS '处理日期（终态日期）';
COMMENT ON COLUMN mivs_cmonconf_info.netg_rnd IS '轧差场次';

ALTER TABLE mivs_cmonconf_info ADD PRIMARY KEY (plat_date, plat_trace);