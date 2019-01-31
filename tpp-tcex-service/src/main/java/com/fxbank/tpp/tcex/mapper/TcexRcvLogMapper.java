package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TcexRcvLog;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TcexRcvLogMapper extends MyMapper<TcexRcvLog> {
    List<TcexRcvLog> selectAll();
    List<TcexRcvLog> selectRcvTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
    String selectDtRcvTotalNum(@Param("date") String date, @Param("flag") String flag);
    String selectDtRcvTotalSum(@Param("date") String date, @Param("flag") String flag);
}