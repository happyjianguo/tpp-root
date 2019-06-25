package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkErr;

public interface BocmChkErrMapper extends MyMapper<BocmChkErr> {
    List<BocmChkErr> selectAll();    
    //通过日期获取对账错误列表
    List<BocmChkErr> selectByDate(String date);
    //通过日期删除对账错误列表
    void deleteByDate(@Param("date") String date);
}