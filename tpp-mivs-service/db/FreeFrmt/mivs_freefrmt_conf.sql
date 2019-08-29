DROP TABLE mivs_freefrmt_conf;
CREATE TABLE mivs_freefrmt_conf(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
system_id NVARCHAR2(30) NULL,
tran_date NVARCHAR2(10) NULL ,
seq_no NVARCHAR2(60) NOT NULL ,
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

orig_dlv_msgid NVARCHAR2(35) NULL ,
orig_instg_drct_pty NVARCHAR2(14) NULL ,
orig_instg_pty NVARCHAR2(14) NULL ,

msg_cntt NVARCHAR2(500) NULL,
rcv_msg_id NVARCHAR2(35) NULL,
msg_prc_cd NVARCHAR2(8) NULL
)

;
COMMENT ON TABLE mivs_freefrmt_conf IS '企业信息联网核查_手机号核查业务信息表';
COMMENT ON COLUMN mivs_freefrmt_conf.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_freefrmt_conf.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_freefrmt_conf.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_freefrmt_conf.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_freefrmt_conf.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_freefrmt_conf.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_freefrmt_conf.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_freefrmt_conf.user_id IS '柜员号';
COMMENT ON COLUMN mivs_freefrmt_conf.branch_id IS '机构号';
COMMENT ON COLUMN mivs_freefrmt_conf.mivs_sts IS '业务处理状态
00-已发
01-已收到990回执
02-已收到911回执处理失败
03-已收到业务回执处理失败
04-已收到业务回执 处理成功
05-已收到人行业务回执入库成功，但未返回ESB ';
COMMENT ON COLUMN mivs_freefrmt_conf.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_freefrmt_conf.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_freefrmt_conf.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_freefrmt_conf.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_freefrmt_conf.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.orig_dlv_msgid IS '核查系统标识';
COMMENT ON COLUMN mivs_freefrmt_conf.orig_instg_drct_pty IS '原发起直接参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.orig_instg_pty IS '原发起参与机构';
COMMENT ON COLUMN mivs_freefrmt_conf.msg_cntt IS '回复内容';

ALTER TABLE mivs_freefrmt_conf ADD PRIMARY KEY (plat_date, plat_trace);