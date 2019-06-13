DROP TABLE mivs_regvrfctn_ill_info;
CREATE TABLE mivs_regvrfctn_ill_info(
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
ill_info_nb NUMBER NOT NULL,
ill_dscrt_cause NVARCHAR2(128) NULL ,
abnml_date NVARCHAR2(22) NULL,
abnml_cause_dcsn_auth NVARCHAR2(128) NULL,
rmv_cause NVARCHAR2(200) NULL,
rmv_date NVARCHAR2(22) NULL,
rmv_cause_dcsn_auth NVARCHAR2(128) NULL
);

COMMENT ON TABLE mivs_regvrfctn_ill_info IS '企业信息联网核查_登记信息核查照面信息业务信息附表';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.orig_msg_id IS '原申请报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.ill_info_nb IS '条数号';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.ill_dscrt_cause IS '列入事由或情形';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.abnml_date IS '列入日期';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.abnml_cause_dcsn_auth IS '列入决定机关';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.rmv_cause IS '移出经营异常名录原因';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.rmv_date IS '移出日期';
COMMENT ON COLUMN mivs_regvrfctn_ill_info.rmv_cause_dcsn_auth IS '移出决定机关';


ALTER TABLE mivs_regvrfctn_ill_info ADD PRIMARY KEY (instg_pty, msg_id, ill_info_nb);