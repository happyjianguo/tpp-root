package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;

public interface BocmRcvLogMapper extends MyMapper<BocmRcvLog> {
    List<BocmRcvLog> selectAll();
    //获取来账流水
    List<BocmRcvLog> selectRcvTrace(String begDate,String endDate,String minAmt,String maxAmt,String brnoFlag);
    //获取已对账流水列表
    List<BocmRcvLog> selectCheckedTrace(@Param("date") String date);
    //获取来账流水笔数
    String selectTraceNum(@Param("date") String date,@Param("checkFlag") String checkFlag);
    //获取来账流水总金额
    String selectChkRcvTotalSum(@Param("date") String date);
}