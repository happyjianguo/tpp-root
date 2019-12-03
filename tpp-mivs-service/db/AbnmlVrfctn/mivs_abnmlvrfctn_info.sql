DROP TABLE mivs_abnmlvrfctn_info;
CREATE TABLE mivs_abnmlvrfctn_info(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
check_type NVARCHAR2(4) NULL,
mivs_sts NVARCHAR2(4) NULL,
msg_id NVARCHAR2(35) NOT NULL ,
cre_dt_tm NVARCHAR2(20) NULL ,
instg_drct_pty NVARCHAR2(14) NULL ,
instg_pty NVARCHAR2(14) NULL ,
instd_drct_pty NVARCHAR2(14) NULL ,
instd_pty NVARCHAR2(14) NULL ,
branch_id NVARCHAR2(30) NULL,
orgnl_instg_pty NVARCHAR2(14) NULL ,
co_nm NVARCHAR2(20) NULL ,
uni_soc_cdt_cd NVARCHAR2(18) NULL,
mob_nb NVARCHAR2(20) NULL,
nm NVARCHAR2(140) NULL,
abnml_type NVARCHAR2(4) NULL ,
descrip NVARCHAR2(500) NULL
);

COMMENT ON TABLE mivs_abnmlvrfctn_info IS '企业信息联网核查_企业异常核查信息登记表';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.mivs_sts IS '业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.check_type IS '异常核查通知类型: COS：企业异常核查， AGT：机构异常核查';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.branch_id IS '机构号';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.orgnl_instg_pty IS '银行营业网点或非银行支付机构行号';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.abnml_type IS '异常核查类型:MIIT-手机号码核查异常,CSAT-纳税信息核查异常,SAMR-工商登记信息核查异常';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.descrip IS '异常内容说明';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.co_nm IS '单位名称';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.mob_nb IS '手机号';
COMMENT ON COLUMN mivs_abnmlvrfctn_info.nm IS '姓名';

ALTER TABLE mivs_abnmlvrfctn_info ADD PRIMARY KEY (plat_date, plat_trace);