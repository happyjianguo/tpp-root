package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TcexChkLog;
import java.util.List;

public interface TcexChkLogMapper extends MyMapper<TcexChkLog> {
    List<TcexChkLog> selectAll();
	void deleteAll(String direction);
}