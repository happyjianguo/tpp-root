package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkLog;

public interface BocmChkLogMapper extends MyMapper<BocmChkLog> {
    List<BocmChkLog> selectAll();
    //删除对账信息
	void deleteAll();
	
}