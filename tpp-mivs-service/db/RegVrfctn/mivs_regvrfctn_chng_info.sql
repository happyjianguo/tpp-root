DROP TABLE mivs_regvrfctn_chng_info;
CREATE TABLE mivs_regvrfctn_chng_info(
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
chng_info_nb NUMBER NOT NULL,
chng_itm NVARCHAR2(200) NULL ,
bf_chng NVARCHAR2(50) NULL,
aft_chng NVARCHAR2(30) NULL,
dt_of_chng NVARCHAR2(30) NULL
);

COMMENT ON TABLE mivs_regvrfctn_chng_info IS '企业信息联网核查_登记信息核查变更信息业务信息附表';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.orig_msg_id IS '原申请报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.chng_info_nb IS '条数号';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.chng_itm IS '变更事项';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.bf_chng IS '变更前内容';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.aft_chng IS '变更后内容';
COMMENT ON COLUMN mivs_regvrfctn_chng_info.dt_of_chng IS '变更日期';


ALTER TABLE mivs_regvrfctn_chng_info ADD PRIMARY KEY (instg_pty, msg_id, chng_info_nb);
alter table mivs_regvrfctn_chng_info modify(BF_CHNG NVARCHAR2(200));
