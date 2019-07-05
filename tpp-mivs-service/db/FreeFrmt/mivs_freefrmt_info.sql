DROP TABLE mivs_freefrmt_info;
CREATE TABLE mivs_freefrmt_info(
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
rply_flag NVARCHAR2(4) NULL,
msg_cntt NVARCHAR2(500) NULL,
isornot_rsp NVARCHAR2(4) NULL
);

COMMENT ON TABLE mivs_freefrmt_info IS '企业信息联网核查_企业通知业务登记表';
COMMENT ON COLUMN mivs_freefrmt_info.plat_date IS '平台日期';
COMMENT ON COLUMN mivs_freefrmt_info.plat_trace IS '平台流水';
COMMENT ON COLUMN mivs_freefrmt_info.plat_time IS '平台时间';
COMMENT ON COLUMN mivs_freefrmt_info.mivs_sts IS '业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功';
COMMENT ON COLUMN mivs_freefrmt_info.check_type IS '异常核查通知类型: COS：企业异常核查， AGT：机构异常核查';
COMMENT ON COLUMN mivs_freefrmt_info.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_freefrmt_info.cre_dt_tm IS '报文发送时间';
COMMENT ON COLUMN mivs_freefrmt_info.instg_drct_pty IS '发起直接参与机构';
COMMENT ON COLUMN mivs_freefrmt_info.instg_pty IS '发起参与机构';
COMMENT ON COLUMN mivs_freefrmt_info.instd_drct_pty IS '接收直接参与机构';
COMMENT ON COLUMN mivs_freefrmt_info.instd_pty IS '接收参与机构';
COMMENT ON COLUMN mivs_freefrmt_info.rply_flag IS '回复标志';
COMMENT ON COLUMN mivs_freefrmt_info.msg_cntt IS '信息内容';
COMMENT ON COLUMN mivs_freefrmt_info.isornot_rsp IS '是否已回复: YES：已回复，NOT：未回复';

ALTER TABLE mivs_freefrmt_info ADD PRIMARY KEY (plat_date, plat_trace, msg_id);