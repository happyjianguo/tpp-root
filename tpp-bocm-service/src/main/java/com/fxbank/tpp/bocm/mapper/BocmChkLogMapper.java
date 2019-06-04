package com.fxbank.tpp.bocm.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkLog;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BocmChkLogMapper extends MyMapper<BocmChkLog> {
    List<BocmChkLog> selectAll();
	void deleteAll();
}