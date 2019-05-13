package com.fxbank.tpp.bocm.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkLog;
import java.util.List;

public interface BocmChkLogMapper extends MyMapper<BocmChkLog> {
    List<BocmChkLog> selectAll();
	void deleteAll(String direction);
}