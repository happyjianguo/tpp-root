package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkStatus;

public interface BocmChkStatusMapper extends MyMapper<BocmChkStatus> {
    List<BocmChkStatus> selectAll();    
    //通过日期获取对账结果表
    List<BocmChkStatus> selectByDateError(@Param("begDate") String begDate,@Param("endDate") String endDate,
    		@Param("state") String state);
    
    List<BocmChkStatus> selectByDateSuccess(@Param("begDate") String begDate,@Param("endDate") String endDate,
    		@Param("state") String state);
}