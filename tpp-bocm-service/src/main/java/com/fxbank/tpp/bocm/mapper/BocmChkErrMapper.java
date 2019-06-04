package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkErr;

public interface BocmChkErrMapper extends MyMapper<BocmChkErr> {
    List<BocmChkErr> selectAll();    
    List<BocmChkErr> selectByDate(String date);
    void deleteByDate(@Param("date") String date);
}