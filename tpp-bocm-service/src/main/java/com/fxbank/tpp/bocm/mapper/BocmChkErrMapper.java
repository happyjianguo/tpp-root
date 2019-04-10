package com.fxbank.tpp.bocm.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmChkErr;
import java.util.List;

public interface BocmChkErrMapper extends MyMapper {
    List<BocmChkErr> selectAll();
}