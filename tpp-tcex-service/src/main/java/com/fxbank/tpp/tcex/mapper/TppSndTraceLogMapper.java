package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TppSndTraceLog;
import java.util.List;

public interface TppSndTraceLogMapper extends MyMapper {
    List<TppSndTraceLog> selectAll();
}