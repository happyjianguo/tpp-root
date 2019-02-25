package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TcexChkErr;
import java.util.List;

public interface TcexChkErrMapper extends MyMapper<TcexChkErr> {
    List<TcexChkErr> selectAll();
    List<TcexChkErr> selectByDate(String date);
    void deleteByDate(String date);
}