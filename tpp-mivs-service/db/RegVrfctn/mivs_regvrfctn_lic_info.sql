DROP TABLE mivs_regvrfctn_lic_info;
CREATE TABLE mivs_regvrfctn_lic_info(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
instg_pty NVARCHAR2(14) NOT NULL,
msg_id NVARCHAR2(35) NOT NULL ,
cre_dt_tm NVARCHAR2(20) NULL ,
orig_msg_id NVARCHAR2(35) NULL,
orig_instg_drct_pty NVARCHAR2(14) NULL,
orig_instg_pty NVARCHAR2(14) NULL,
pg_nb NUMBER(10) NULL,
lic_info_nb NUMBER NOT NULL,

orgnl_or_cp NVARCHAR2(4) NULL,
lic_null_stm_cntt CLOB NULL,
lic_null_stm_dt NVARCHAR2(30) NULL,
rpl_sts NVARCHAR2(4) NULL,
rpl_dt NVARCHAR2(30) NULL,
lic_cp_nb NVARCHAR2(50) NULL
);

COMMENT ON TABLE mivs_regvrfctn_lic_info IS '企业信息联网核查_登记信息核查照面信息业务信息附表';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.orig_msg_id IS '原申请报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.lic_info_nb IS '条数号';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.orgnl_or_cp IS '正副本标识';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.lic_null_stm_cntt IS '声明内容';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.lic_null_stm_dt IS '声明日期';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.rpl_sts IS '补领标识';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.rpl_dt IS '补领日期';
COMMENT ON COLUMN mivs_regvrfctn_lic_info.lic_cp_nb IS '营业执照副本编号';


ALTER TABLE mivs_regvrfctn_lic_info ADD PRIMARY KEY (instg_pty, msg_id, lic_info_nb);


