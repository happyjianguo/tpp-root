package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmSndLog;

public interface BocmSndLogMapper extends MyMapper<BocmSndLog> {
    
	List<BocmSndLog> selectAll();
    //获取往账记录
    List<BocmSndLog> selectSndTrace(@Param("begDate") String begDate,@Param("endDate") String endDate,
    		@Param("begTrace") String begTrace,@Param("endTrace") String endTrace,
    		@Param("txAmt") String txAmt,@Param("hostStatus") String hostStatus,
    		@Param("txBranch") String txBranch);
    //获取对账流水笔数
    String selectDtSndTotalNum(@Param("date") String date, @Param("flag") String flag);
    //获取对账流水金额
    String selectDtSndTotalSum(@Param("date") String date,@Param("flag") String flag);
    //获取对账流水笔数
    List<BocmSndLog> selectCheckedTrace(String date);
    //通过对账标志 获取该标志对账流水数量
    String selectTraceNum(@Param("date") String date,@Param("checkFlag") String checkFlag);
    //获取往账对账总金额
    String selectChkSndTotalSum(@Param("date") String date);
}