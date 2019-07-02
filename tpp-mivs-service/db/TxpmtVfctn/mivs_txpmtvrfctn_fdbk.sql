DROP TABLE mivs_txpmtvrfctn_fdbk;
CREATE TABLE mivs_txpmtvrfctn_fdbk(
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

co_nm NVARCHAR2(300) NULL ,
uni_soc_cdt_cd NVARCHAR2(18) NULL,
txpyr_id_nb NVARCHAR2(20) NULL,
rslt NVARCHAR2(4) NULL,
data_resrc_dt NVARCHAR2(20) NULL,
txpmt_inf CLOB NULL,
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
remark1 NVARCHAR2(100) NULL,
remark2 NVARCHAR2(100) NULL,
remark3 NVARCHAR2(100) NULL
);


COMMENT ON TABLE mivs_txpmtvrfctn_fdbk IS '企业信息联网核查_手机号核查业务信息表';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.user_id IS '柜员号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.branch_id IS '机构号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.mivs_sts IS '业务处理状态
00-已发
01-已收到990回执
02-已收到911回执处理失败
03-已收到业务回执处理失败
04-已收到业务回执 处理成功
05-已收到人行业务回执入库成功，但未返回ESB ';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.sys_ind IS '核查系统标识';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.orig_dlv_msgid IS '原请求报文标识号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.orig_rcv_msgid IS '原应答报文标识号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.co_nm IS '单位名称';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.txpyr_id_nb IS '纳税人识别号';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.rslt IS '纳税信息核查结果';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.data_resrc_dt IS '数据源日期';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.txpmt_inf IS '税务信息';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.cntt IS '疑义反馈内容';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.contact_nm IS '联系人姓名';
COMMENT ON COLUMN mivs_txpmtvrfctn_fdbk.contact_nb IS '联系人电话';

ALTER TABLE mivs_txpmtvrfctn_fdbk ADD PRIMARY KEY (plat_date, plat_trace);

/*
select dbms_lob.substr(txpmt_inf) from mivs_txpmtvrfctn_fdbk;
*/