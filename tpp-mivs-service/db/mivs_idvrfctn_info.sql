DROP TABLE mivs_idvrfctn_info;
CREATE TABLE mivs_idvrfctn_info(
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
mob_nb NVARCHAR2(20) NULL ,
nm NVARCHAR2(140) NULL ,
id_tp NVARCHAR2(4) NULL,
id NVARCHAR2(35) NULL,
uni_soc_cdt_cd NVARCHAR2(18) NULL,
biz_reg_nb NVARCHAR2(15) NULL,
op_nm NVARCHAR2(140) NULL,
rslt NVARCHAR2(4) NULL,
mob_crr NVARCHAR2(4) NULL,
loc_mob_nb NVARCHAR2(4) NULL,
loc_nm_mob_nb NVARCHAR2(20) NULL,
cd_tp NVARCHAR2(4) NULL,
sts NVARCHAR2(4) NULL,
proc_sts NVARCHAR2(4) NULL,
proc_cd NVARCHAR2(8) NULL,
rjct_inf NVARCHAR2(120) NULL,
remark1 NVARCHAR2(100) NULL,
remark2 NVARCHAR2(100) NULL,
remark3 NVARCHAR2(100) NULL
)

;
COMMENT ON TABLE mivs_idvrfctn_info IS '企业信息联网核查_手机号核查业务信息表';
COMMENT ON COLUMN mivs_idvrfctn_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_idvrfctn_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_idvrfctn_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_idvrfctn_info.system_id IS '发起方系统编码';
COMMENT ON COLUMN mivs_idvrfctn_info.tran_date IS '交易日期';
COMMENT ON COLUMN mivs_idvrfctn_info.seq_no IS '渠道流水';
COMMENT ON COLUMN mivs_idvrfctn_info.tran_time IS '交易时间';
COMMENT ON COLUMN mivs_idvrfctn_info.user_id IS '柜员号';
COMMENT ON COLUMN mivs_idvrfctn_info.branch_id IS '机构号';
COMMENT ON COLUMN mivs_idvrfctn_info.mivs_sts IS '业务处理状态
00-已发
01-已收到990回执
02-已收到911回执处理失败
03-已收到业务回执处理失败
04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_idvrfctn_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_idvrfctn_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_idvrfctn_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_idvrfctn_info.drct_pty_nm IS '发起直接参与机构行名';
COMMENT ON COLUMN mivs_idvrfctn_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_idvrfctn_info.pty_nm IS '发起参与机构行名';
COMMENT ON COLUMN mivs_idvrfctn_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_idvrfctn_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_idvrfctn_info.mob_nb IS '手机号';
COMMENT ON COLUMN mivs_idvrfctn_info.nm IS '姓名';
COMMENT ON COLUMN mivs_idvrfctn_info.id_tp IS '证件类型
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
COMMENT ON COLUMN mivs_idvrfctn_info.id IS '证件号码';
COMMENT ON COLUMN mivs_idvrfctn_info.uni_soc_cdt_cd IS '统一社会信用代码';
COMMENT ON COLUMN mivs_idvrfctn_info.biz_reg_nb IS '工商注册号';
COMMENT ON COLUMN mivs_idvrfctn_info.op_nm IS '操作员姓名';
COMMENT ON COLUMN mivs_idvrfctn_info.rslt IS '手机号核查结果
MCHD:要素核查匹配正确
WIDT:要素核查条件中的电话号码存在，证件类型不一致
WIDN:要素核查条件中的电话号码存在，证件类型一致,证件号码不一致
WNAM:要素核查条件中的电话号码存在，证件类型一致,证件号码一致，姓名不一致
NTFD:电话号码匹配失败';
COMMENT ON COLUMN mivs_idvrfctn_info.mob_crr IS '手机运营商
1000 中国电信
2000 中国移动
3000 中国联通
0000 虚拟运营商整体
0001 苏州蜗牛数字科技股份有限公司
0002 远特（北京）通信技术有限公司
0003 北京分享在线网络技术有限公司
0004 北京迪信通通信服务有限公司
0005 深圳市爱施德股份有限公司
0006 天音通信有限公司
0007 浙江连连科技有限公司
0008 北京乐语世纪通信设备连锁有限公司
0009 北京华翔联信科技有限公司
0010 北京京东叁佰陆拾度电子商务有限公司
0011 北京北纬通信科技股份有限公司
0012 北京万网志成科技有限公司
0013 巴士在线控股有限公司
0014 话机世界数码连锁集团股份有限公司
0015 厦门三五互联科技股份有限公司
0016 北京国美电器有限公司
0017 苏宁云商集团股份有限公司
0018 中期集团有限公司
0019 长江时代通信股份有限公司
0020 贵阳朗玛信息技术股份有限公司
0021 深圳市中兴视通科技有限公司
0022 用友移动通信技术服务有限公司
0023 中邮世纪（北京）通信技术有限公司
0024 北京世纪互联宽带数据中心有限公司
0025 银盛电子支付科技有限公司
0026 红豆集团有限公司
0027 深圳星美圣典文化传媒集团有限公司
0028 合一信息技术（北京）有限公司
0029 青岛日日顺网络科技有限公司
0030 北京青牛科技有限公司
0031 小米科技有限责任公司
0032 郑州市讯捷贸易有限公司
0033 二六三网络通信股份有限公司
0034 海南海航信息技术有限公司
0035 北京联想调频科技有限公司
0036 广东恒大和通信科技股份有限公司
0037 青岛丰信通信有限公司
0038 凤凰资产管理有限公司
0039 深圳平安通信科技有限公司
0040 民生电子商务有限责任公司
0041 鹏博士电信传媒集团股份有限公司
0042 广州博元讯息服务有限公司';
COMMENT ON COLUMN mivs_idvrfctn_info.loc_mob_nb IS '手机号归属地代码';
COMMENT ON COLUMN mivs_idvrfctn_info.loc_nm_mob_nb IS '手机号归属地名称';
COMMENT ON COLUMN mivs_idvrfctn_info.cd_tp IS '客户类型
INDV：个人用户
ENTY：单位用户';
COMMENT ON COLUMN mivs_idvrfctn_info.sts IS '手机号码状态
ENBL 正常
DSBL 停机';
COMMENT ON COLUMN mivs_idvrfctn_info.proc_sts IS '申请报文拒绝状态';
COMMENT ON COLUMN mivs_idvrfctn_info.proc_cd IS '申请报文拒绝码';
COMMENT ON COLUMN mivs_idvrfctn_info.rjct_inf IS '申请报文拒绝信息';
COMMENT ON COLUMN mivs_idvrfctn_info.remark1 IS '备用字段1';
COMMENT ON COLUMN mivs_idvrfctn_info.remark2 IS '备用字段2';
COMMENT ON COLUMN mivs_idvrfctn_info.remark3 IS '备用字段3';

ALTER TABLE mivs_idvrfctn_info ADD PRIMARY KEY (plat_date, plat_trace);