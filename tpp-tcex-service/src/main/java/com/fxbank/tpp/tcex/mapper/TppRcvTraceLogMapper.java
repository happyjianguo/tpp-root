package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TppRcvTraceLog;
import java.util.List;

public interface TppRcvTraceLogMapper extends MyMapper {
    List<TppRcvTraceLog> selectAll();
}