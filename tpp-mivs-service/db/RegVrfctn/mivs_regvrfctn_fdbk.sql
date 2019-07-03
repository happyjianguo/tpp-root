DROP TABLE mivs_regvrfctn_fdbk;
CREATE TABLE mivs_regvrfctn_fdbk(
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
rcv_msg_id NVARCHAR2(35) NULL ,
rcv_cre_dt_tm NVARCHAR2(20) NULL ,

sys_ind NVARCHAR2(4) NULL ,
orig_dlv_msgid NVARCHAR2(35) NULL ,
orig_rcv_msgid NVARCHAR2(35) NULL,

uni_soc_cdt_cd NVARCHAR2(18) NULL ,
ent_nm NVARCHAR2(100) NULL ,
nm_of_lgl_prsn NVARCHAR2(140) NULL,
id_of_lgl_prsn NVARCHAR2(35) NULL,
tra_nm NVARCHAR2(256) NULL,
nm NVARCHAR2(200) NULL,
id NVARCHAR2(35) NULL,

rslt NVARCHAR2(4) NULL,
data_resrc_dt NVARCHAR2(20) NULL,
cntt NVARCHAR2(256) NULL,
contact_nm NVARCHAR2(140) NULL,
contact_nb NVARCHAR2(30) NULL,

proc_sts NVARCHAR2(4) NULL,
proc_cd NVARCHAR2(8) NULL,
pty_id NVARCHAR2(14) NULL,
pty_prc_cd NVARCHAR2(4) NULL,
rjct_inf NVARCHAR2(120) NULL,
prc_dt NVARCHAR2(22) NULL,
netg_rnd NVARCHAR2(4) NULL,
bas_info CLOB NULL,
cos_info CLOB NULL,
dir_info CLOB NULL,
chng_info CLOB NULL,
abn_info CLOB NULL,
ill_info CLOB NULL,
lic_info CLOB NULL,
remark1 NVARCHAR2(100) NULL,
remark2 NVARCHAR2(100) NULL,
remark3 NVARCHAR2(100) NULL
);


COMMENT ON TABLE mivs_regvrfctn_fdbk IS '企业信息联网核查_登记信息核查反馈业务信息表';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.user_id IS '柜员号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.branch_id IS '机构号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.mivs_sts IS '业务处理状态
00-已发
01-已收到990回执
02-已收到911回执处理失败
03-已收到业务回执处理失败
04-已收到业务回执 处理成功
05-已收到人行业务回执入库成功，但未返回ESB ';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.sys_ind IS '核查系统标识';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.orig_dlv_msgid IS '原请求报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.orig_rcv_msgid IS '原应答报文标识号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.nm_of_lgl_prsn IS '法定代表人或单位负责人姓名';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.id_of_lgl_prsn IS '法定代表人或单位负责人身份证件号';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.ent_nm IS '企业名称';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.rslt IS '纳税信息核查结果';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.data_resrc_dt IS '数据源日期';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.cntt IS '疑义反馈内容';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.contact_nm IS '联系人姓名';
COMMENT ON COLUMN mivs_regvrfctn_fdbk.contact_nb IS '联系人电话';

ALTER TABLE mivs_regvrfctn_fdbk ADD PRIMARY KEY (plat_date, plat_trace);

/*
select dbms_lob.substr(bas_info) from mivs_regvrfctn_fdbk;
*/