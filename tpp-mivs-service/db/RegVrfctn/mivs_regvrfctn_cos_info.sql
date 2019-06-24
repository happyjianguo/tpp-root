DROP TABLE mivs_regvrfctn_cos_info;
CREATE TABLE mivs_regvrfctn_cos_info(
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
co_shrhdrfnd_info_nb NUMBER NOT NULL,

natl_prsn_flag NVARCHAR2(4) NULL,
invtr_nm NVARCHAR2(200) NULL ,
invtr_id NVARCHAR2(50) NULL,
subscr_cptl_con_amt NVARCHAR2(30) NULL,
actl_cptl_con_amt NVARCHAR2(30) NULL,
subscr_cptl_con_fm NVARCHAR2(200) NULL,
subscr_cptl_con_dt NVARCHAR2(20) NULL
);

COMMENT ON TABLE mivs_regvrfctn_cos_info IS '企业信息联网核查_登记信息核查企业股东及出资信息业务信息附表';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.orig_msg_id IS '原申请报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.co_shrhdrfnd_info_nb IS '条数号';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.natl_prsn_flag IS '自然人标识';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.invtr_nm IS '投资人名称';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.invtr_id IS '投资人证件号码或证件编号';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.subscr_cptl_con_amt IS '认缴出资额';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.actl_cptl_con_amt IS '实缴出资额';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.subscr_cptl_con_fm IS '认缴出资方式';
COMMENT ON COLUMN mivs_regvrfctn_cos_info.subscr_cptl_con_dt IS '认缴出资日期';


ALTER TABLE mivs_regvrfctn_cos_info ADD PRIMARY KEY (instg_pty, msg_id, co_shrhdrfnd_info_nb);