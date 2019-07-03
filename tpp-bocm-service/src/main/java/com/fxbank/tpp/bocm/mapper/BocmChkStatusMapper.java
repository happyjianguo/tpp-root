package com.fxbank.tpp.bocm.mapper;

import java.util.List;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkStatus;

public interface BocmChkStatusMapper extends MyMapper<BocmChkStatus> {
    List<BocmChkStatus> selectAll();    
    //通过日期获取对账状态
    List<BocmChkStatus> selectByDate(String date);

}