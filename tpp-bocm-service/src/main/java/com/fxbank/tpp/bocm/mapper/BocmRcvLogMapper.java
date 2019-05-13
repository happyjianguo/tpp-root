package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;

public interface BocmRcvLogMapper extends MyMapper<BocmRcvLog> {
    List<BocmRcvLog> selectAll();
    List<BocmRcvLog> selectRcvTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
    String selectDtRcvTotalNum(@Param("date") String date, @Param("flag") String flag);
    String selectDtRcvTotalSum(@Param("date") String date, @Param("flag") String flag);
    List<BocmRcvLog> selectCheckedTrace(@Param("date") String date);
    String selectTraceNum(@Param("date") String date,@Param("checkFlag") String checkFlag);
    
    String selectChkRcvTotalSum(@Param("date") String date);
}