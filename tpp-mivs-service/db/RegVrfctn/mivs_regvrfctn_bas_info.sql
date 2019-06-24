DROP TABLE mivs_regvrfctn_bas_info;
CREATE TABLE mivs_regvrfctn_bas_info(
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
bas_info_nb NUMBER NULL,
market_type NVARCHAR2(10) NULL,

uni_soc_cdt_cd NVARCHAR2(18) NULL,
co_tp NVARCHAR2(128) NULL,
reg_sts NVARCHAR2(128) NULL,
reg_auth NVARCHAR2(128) NULL,
biz_scp CLOB NULL,
dt_appr NVARCHAR2(20) NULL,
ent_nm NVARCHAR2(100) NULL ,
dom NVARCHAR2(512) NULL,
reg_cptl NVARCHAR2(30) NULL,
dt_est NVARCHAR2(20) NULL,
op_prd_from NVARCHAR2(20) NULL,
op_prd_to NVARCHAR2(20) NULL,
nm_of_lgl_prsn NVARCHAR2(200) NULL,

tra_nm NVARCHAR2(256) NULL,
op_loc NVARCHAR2(200) NULL,
fd_amt NVARCHAR2(30) NULL,
dt_reg NVARCHAR2(20) NULL,
nm NVARCHAR2(20) NULL
);

COMMENT ON TABLE mivs_regvrfctn_bas_info IS '企业信息联网核查_登记信息核查照面信息业务信息附表';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.orig_msg_id IS '原申请报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.bas_info_nb IS '条数号';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.ent_nm IS '企业名称';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.co_tp IS '市场主体类型';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.dom IS '住所';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.reg_cptl IS '注册资本(金)';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.dt_est IS '成立日期';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.op_prd_from IS '经营期限自';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.op_prd_to IS '经营期限至';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.reg_sts IS '登记状态';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.nm_of_lgl_prsn IS '法定代表人或单位负责人姓名';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.reg_auth IS '登记机关';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.biz_scp IS '经营范围';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.dt_appr IS '核准日期';

COMMENT ON COLUMN mivs_regvrfctn_bas_info.tra_nm IS '字号名称';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.op_loc IS '经营场所';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.fd_amt IS '资金数额';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.dt_reg IS '成立日期';
COMMENT ON COLUMN mivs_regvrfctn_bas_info.nm IS '经营者姓名';


ALTER TABLE mivs_regvrfctn_bas_info ADD PRIMARY KEY (instg_pty, msg_id, bas_info_nb);