DROP TABLE mivs_idvrfctn_fdbk;
CREATE TABLE mivs_idvrfctn_fdbk(
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

mob_nb NVARCHAR2(20) NULL ,
nm NVARCHAR2(140) NULL ,
id_tp NVARCHAR2(4) NULL,
id NVARCHAR2(35) NULL,
uni_soc_cdt_cd NVARCHAR2(18) NULL,
biz_reg_nb NVARCHAR2(15) NULL,
rslt NVARCHAR2(4) NULL,
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
)

;
COMMENT ON TABLE mivs_idvrfctn_fdbk IS '企业信息联网核查_手机号核查业务信息表';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.user_id IS '柜员号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.branch_id IS '机构号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.mivs_sts IS '业务处理状态
00-已发
01-已收到990回执
02-已收到911回执处理失败
03-已收到业务回执处理失败
04-已收到业务回执 处理成功
05-已收到人行业务回执入库成功，但未返回ESB ';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.sys_ind IS '核查系统标识';--MIIT 手机号核查
COMMENT ON COLUMN mivs_idvrfctn_fdbk.orig_dlv_msgid IS '原请求报文标识号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.orig_rcv_msgid IS '原应答报文标识号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.mob_nb IS '手机号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.nm IS '姓名';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.id_tp IS '证件类型
IC00 身份证
IC02 军官证
IC03 士兵证
IC04 港澳往来通行证
IC05 临时身份证
IC06 户口簿
IC08 外国人永久居留证
IC20 台湾往来通行证
IC21 外国公民护照
IC22 港澳居民居住证
IC23 台湾居民居住证
IC24 中国人民武装警察身份证件';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.id IS '证件号码';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.biz_reg_nb IS '工商注册号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.rslt IS '手机号核查结果
MCHD:要素核查匹配正确
WIDT:要素核查条件中的电话号码存在，证件类型不一致
WIDN:要素核查条件中的电话号码存在，证件类型一致,证件号码不一致
WNAM:要素核查条件中的电话号码存在，证件类型一致,证件号码一致，姓名不一致
NTFD:电话号码匹配失败';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.cntt IS '疑义反馈内容';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.contact_nm IS '联系人姓名';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.contact_nb IS '联系人电话';

COMMENT ON COLUMN mivs_idvrfctn_fdbk.proc_sts IS '申请报文拒绝状态';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.proc_cd IS '申请报文拒绝码';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.pty_id IS '拒绝业务的参与机构行号';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.pty_prc_cd IS '参与机构业务拒绝码';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.rjct_inf IS '申请报文拒绝信息';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.prc_dt IS '处理日期（终态日期）';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.netg_rnd IS '轧差场次';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.remark1 IS '备用字段1';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.remark2 IS '备用字段2';
COMMENT ON COLUMN mivs_idvrfctn_fdbk.remark3 IS '备用字段3';

ALTER TABLE mivs_idvrfctn_fdbk ADD PRIMARY KEY (plat_date, plat_trace);