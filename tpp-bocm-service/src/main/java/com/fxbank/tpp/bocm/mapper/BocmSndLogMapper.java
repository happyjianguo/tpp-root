package com.fxbank.tpp.bocm.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmSndLog;
import java.util.List;

public interface BocmSndLogMapper extends MyMapper<BocmSndLog> {
    List<BocmSndLog> selectAll();
}