DROP TABLE mivs_regvrfctn_info;
CREATE TABLE mivs_regvrfctn_info(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
system_id NVARCHAR2(30) NULL,
tran_date NVARCHAR2(10) NULL ,
seq_no NVARCHAR2(22) NOT NULL ,
tran_time NVARCHAR2(10) NULL,
user_id NVARCHAR2(20) NULL,
branch_id NVARCHAR2(30) NULL,
mivs_sts NVARCHAR2(4) NULL,
msg_id NVARCHAR2(35) NOT NULL ,
cre_dt_tm NVARCHAR2(20) NULL ,
instg_drct_pty NVARCHAR2(14) NULL ,
drct_pty_nm NVARCHAR2(140) NULL ,
instg_pty NVARCHAR2(14) NULL ,
pty_nm NVARCHAR2(140) NULL ,
instd_drct_pty NVARCHAR2(14) NULL ,
instd_pty NVARCHAR2(14) NULL ,
agt_nm NVARCHAR2(140) NULL,
agt_id NVARCHAR2(35) NULL,
op_nm NVARCHAR2(140) NULL,
market_type NVARCHAR2(10) NULL,
uni_soc_cdt_cd NVARCHAR2(18) NULL ,
ent_nm NVARCHAR2(100) NULL ,
nm_of_lgl_prsn NVARCHAR2(140) NULL,
id_of_lgl_prsn NVARCHAR2(35) NULL,
tra_nm NVARCHAR2(256) NULL,
nm NVARCHAR2(200) NULL,
id NVARCHAR2(35) NULL,
pg_nb NUMBER NULL,
last_pg_ind NVARCHAR2(20) NULL,
rslt NVARCHAR2(4) NULL,
data_resrc_dt NVARCHAR2(20) NULL,

bas_info_cnt NUMBER NULL,
co_shrhdrfnd_info_cnt NUMBER NULL,
dir_supsrsgr_info_cnt NUMBER NULL,
chng_info_cnt NUMBER NULL,
abnml_biz_info_cnt NUMBER NULL,
ill_dscrt_info_cnt NUMBER NULL,
lic_null_cnt NUMBER NULL,

proc_sts NVARCHAR2(4) NULL,
proc_cd NVARCHAR2(8) NULL,
rjct_inf NVARCHAR2(120) NULL,
remark1 NVARCHAR2(100) NULL,
remark2 NVARCHAR2(100) NULL,
remark3 NVARCHAR2(100) NULL
);

COMMENT ON TABLE mivs_regvrfctn_info IS '企业信息联网核查_登记信息核查业务信息主表';
COMMENT ON COLUMN mivs_regvrfctn_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_info.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_regvrfctn_info.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_regvrfctn_info.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_regvrfctn_info.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_regvrfctn_info.user_id IS '柜员号';
COMMENT ON COLUMN mivs_regvrfctn_info.branch_id IS '机构号';
COMMENT ON COLUMN mivs_regvrfctn_info.mivs_sts IS '业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_regvrfctn_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_info.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_regvrfctn_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_info.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_regvrfctn_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_regvrfctn_info.market_type IS '市场主体类型：ENT-企业，SLF-个体工商户';
COMMENT ON COLUMN mivs_regvrfctn_info.nm_of_lgl_prsn IS '法定代表人或单位负责人姓名';
COMMENT ON COLUMN mivs_regvrfctn_info.id_of_lgl_prsn IS '法定代表人或单位负责人身份证件号';
COMMENT ON COLUMN mivs_regvrfctn_info.ent_nm IS '企业名称';
COMMENT ON COLUMN mivs_regvrfctn_info.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_regvrfctn_info.agt_nm IS '代理人姓名';
COMMENT ON COLUMN mivs_regvrfctn_info.agt_id IS '代理人身份证件号码';
COMMENT ON COLUMN mivs_regvrfctn_info.op_nm IS '操作员姓名';
COMMENT ON COLUMN mivs_regvrfctn_info.pg_nb IS '本报文页码';
COMMENT ON COLUMN mivs_regvrfctn_info.last_pg_ind IS '最后一页指示符';

COMMENT ON COLUMN mivs_regvrfctn_info.bas_info_cnt IS '照面信息部分，此部分在分片报文中每次均出现';
COMMENT ON COLUMN mivs_regvrfctn_info.co_shrhdrfnd_info_cnt IS '企业股东及出资信息部分';
COMMENT ON COLUMN mivs_regvrfctn_info.dir_supsrsgr_info_cnt IS '董事监事及高管信息';
COMMENT ON COLUMN mivs_regvrfctn_info.chng_info_cnt IS '变更信息';
COMMENT ON COLUMN mivs_regvrfctn_info.abnml_biz_info_cnt IS '异常经营信息';
COMMENT ON COLUMN mivs_regvrfctn_info.ill_dscrt_info_cnt IS '严重违法失信信息';
COMMENT ON COLUMN mivs_regvrfctn_info.lic_null_cnt IS '营业执照作废声明';

COMMENT ON COLUMN mivs_regvrfctn_info.proc_sts IS '申请报文拒绝状态';
COMMENT ON COLUMN mivs_regvrfctn_info.proc_cd IS '申请报文拒绝码';
COMMENT ON COLUMN mivs_regvrfctn_info.rjct_inf IS '申请报文拒绝信息';
COMMENT ON COLUMN mivs_regvrfctn_info.remark1 IS '备用字段1';
COMMENT ON COLUMN mivs_regvrfctn_info.remark2 IS '备用字段2';
COMMENT ON COLUMN mivs_regvrfctn_info.remark3 IS '备用字段3';

ALTER TABLE mivs_regvrfctn_info ADD PRIMARY KEY (plat_date, plat_trace);