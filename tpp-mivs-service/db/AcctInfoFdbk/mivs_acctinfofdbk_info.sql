DROP TABLE mivs_acctinfofdbk_info;
CREATE TABLE mivs_acctinfofdbk_info(
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

ent_nm NVARCHAR2(100) NULL ,
tra_nm NVARCHAR2(256) NULL ,
uni_soc_cdt_cd NVARCHAR2(18) NULL,
acct_sts NVARCHAR2(4) NULL,
chng_dt NVARCHAR2(140) NULL,

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
)

;
COMMENT ON TABLE mivs_acctinfofdbk_info IS '企业信息联网核查_企业开销户状态反馈主表';
COMMENT ON COLUMN mivs_acctinfofdbk_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_acctinfofdbk_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_acctinfofdbk_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_acctinfofdbk_info.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_acctinfofdbk_info.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_acctinfofdbk_info.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_acctinfofdbk_info.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_acctinfofdbk_info.user_id IS '柜员号';
COMMENT ON COLUMN mivs_acctinfofdbk_info.branch_id IS '机构号';
COMMENT ON COLUMN mivs_acctinfofdbk_info.mivs_sts IS '业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_acctinfofdbk_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_acctinfofdbk_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_acctinfofdbk_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_acctinfofdbk_info.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_acctinfofdbk_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_acctinfofdbk_info.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_acctinfofdbk_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_acctinfofdbk_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_acctinfofdbk_info.ent_nm IS '企业名称';
COMMENT ON COLUMN mivs_acctinfofdbk_info.tra_nm IS '个体户名称';
COMMENT ON COLUMN mivs_acctinfofdbk_info.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_acctinfofdbk_info.acct_sts IS '账户状态标识';
COMMENT ON COLUMN mivs_acctinfofdbk_info.chng_dt IS '变更日期';

COMMENT ON COLUMN mivs_acctinfofdbk_info.proc_sts IS '申请报文拒绝状态';
COMMENT ON COLUMN mivs_acctinfofdbk_info.proc_cd IS '申请报文拒绝码';
COMMENT ON COLUMN mivs_acctinfofdbk_info.pty_id IS '拒绝业务的参与机构行号';
COMMENT ON COLUMN mivs_acctinfofdbk_info.pty_prc_cd IS '参与机构业务拒绝码';
COMMENT ON COLUMN mivs_acctinfofdbk_info.rjct_inf IS '申请报文拒绝信息';
COMMENT ON COLUMN mivs_acctinfofdbk_info.prc_dt IS '处理日期（终态日期）';
COMMENT ON COLUMN mivs_acctinfofdbk_info.netg_rnd IS '轧差场次';
COMMENT ON COLUMN mivs_acctinfofdbk_info.remark1 IS '备用字段1';
COMMENT ON COLUMN mivs_acctinfofdbk_info.remark2 IS '备用字段2';
COMMENT ON COLUMN mivs_acctinfofdbk_info.remark3 IS '备用字段3';

ALTER TABLE mivs_acctinfofdbk_info ADD PRIMARY KEY (plat_date, plat_trace);