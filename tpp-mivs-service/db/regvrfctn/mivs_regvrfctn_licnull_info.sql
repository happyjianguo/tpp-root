

orgnl_or_cp NVARCHAR2(4) NULL,
lic_null_stm_cntt BLOB NULL,
lic_null_stm_dt NVARCHAR2(20) NULL,
rpl_sts NVARCHAR2(4) NULL,
rpl_dt NVARCHAR2(20) NULL,
lic_cp_nb NVARCHAR2(20) NULL,



COMMENT ON COLUMN mivs_regvrfctn_info.orgnl_or_cp IS '正副本标识';
COMMENT ON COLUMN mivs_regvrfctn_info.lic_null_stm_cntt IS '声明内容';
COMMENT ON COLUMN mivs_regvrfctn_info.lic_null_stm_dt IS '声明日期';
COMMENT ON COLUMN mivs_regvrfctn_info.rpl_sts IS '补领标识';
COMMENT ON COLUMN mivs_regvrfctn_info.rpl_dt IS '补领日期';
COMMENT ON COLUMN mivs_regvrfctn_info.lic_cp_nb IS '营业执照副本编号';