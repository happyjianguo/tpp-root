DROP TABLE mivs_sysstsntfctn_info;
CREATE TABLE mivs_sysstsntfctn_info(
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
cur_sys_dt NVARCHAR2(30) NULL,
nxt_sys_dt NVARCHAR2(30) NULL ,
sys_ind NVARCHAR2(4) NULL ,
svc_ind NVARCHAR2(4) NULL,
nxt_sys_op_tm NVARCHAR2(30) NULL,
nxt_sys_cl_tm NVARCHAR2(30) NULL,
remark1 NVARCHAR2(500) NULL ,
remark2 NVARCHAR2(500) NULL ,
remark3 NVARCHAR2(500) NULL
);

COMMENT ON TABLE mivs_sysstsntfctn_info IS '企业信息联网核查_企业异常核查信息登记表';
COMMENT ON COLUMN mivs_sysstsntfctn_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_sysstsntfctn_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_sysstsntfctn_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_sysstsntfctn_info.mivs_sts IS '业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_sysstsntfctn_info.check_type IS '异常核查通知类型: COS：企业异常核查， AGT：机构异常核查';
COMMENT ON COLUMN mivs_sysstsntfctn_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_sysstsntfctn_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_sysstsntfctn_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_sysstsntfctn_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_sysstsntfctn_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_sysstsntfctn_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_sysstsntfctn_info.cur_sys_dt IS '系统当前日期';
COMMENT ON COLUMN mivs_sysstsntfctn_info.nxt_sys_dt IS '系统下一日期';
COMMENT ON COLUMN mivs_sysstsntfctn_info.sys_ind IS '系统类型';
COMMENT ON COLUMN mivs_sysstsntfctn_info.svc_ind IS '系统下一日期受理业务状态';
COMMENT ON COLUMN mivs_sysstsntfctn_info.nxt_sys_op_tm IS '系统下一日期开始受理时间';
COMMENT ON COLUMN mivs_sysstsntfctn_info.nxt_sys_cl_tm IS '系统下一日期结束受理时间';
COMMENT ON COLUMN mivs_sysstsntfctn_info.remark1 IS '备用字段1';
COMMENT ON COLUMN mivs_sysstsntfctn_info.remark2 IS '备用字段2';
COMMENT ON COLUMN mivs_sysstsntfctn_info.remark3 IS '备用字段3';

ALTER TABLE mivs_sysstsntfctn_info ADD PRIMARY KEY (plat_date, plat_trace);