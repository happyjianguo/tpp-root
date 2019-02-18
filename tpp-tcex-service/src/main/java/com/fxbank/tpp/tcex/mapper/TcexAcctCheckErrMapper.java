package com.fxbank.tpp.tcex.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.tcex.entity.TcexAcctCheckErr;
import java.util.List;

public interface TcexAcctCheckErrMapper extends MyMapper<TcexAcctCheckErr> {
    List<TcexAcctCheckErr> selectAll();
    List<TcexAcctCheckErr> selectByDate(String date);
    void deleteByDate(String date);
}