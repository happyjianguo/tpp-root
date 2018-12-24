package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TppRcvTraceLog;
import java.util.List;

public interface TppRcvTraceLogMapper extends MyMapper<TppRcvTraceLog> {
    List<TppRcvTraceLog> selectAll();
    List<TppRcvTraceLog> selectRcvTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
}