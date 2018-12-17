package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TppDayCheckLog;
import java.util.List;

public interface TppDayCheckLogMapper extends MyMapper<TppDayCheckLog> {
    List<TppDayCheckLog> selectAll();
}