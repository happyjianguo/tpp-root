package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TcexSndLog;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TcexSndLogMapper extends MyMapper<TcexSndLog> {
    List<TcexSndLog> selectAll();
    List<TcexSndLog> selectSndTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
    String selectDtSndTotalNum(@Param("date") String date, @Param("flag") String flag);
    String selectDtSndTotalSum(@Param("date") String date,@Param("flag") String flag);
    List<TcexSndLog> selectCheckedTrace(String date);
    String selectTraceNum(String date,String checkFlag);
}