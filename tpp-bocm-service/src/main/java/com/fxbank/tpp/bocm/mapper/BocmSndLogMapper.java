package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmSndLog;

public interface BocmSndLogMapper extends MyMapper<BocmSndLog> {
    List<BocmSndLog> selectAll();
    List<BocmSndLog> selectSndTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
    String selectDtSndTotalNum(@Param("date") String date, @Param("flag") String flag);
    String selectDtSndTotalSum(@Param("date") String date,@Param("flag") String flag);
    List<BocmSndLog> selectCheckedTrace(String date);
    String selectTraceNum(@Param("date") String date,@Param("checkFlag") String checkFlag);
    
    String selectChkSndTotalSum(@Param("date") String date);
}