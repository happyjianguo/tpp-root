package com.fxbank.tpp.bocm.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.bocm.entity.BocmRcvLog;
import java.util.List;

public interface BocmRcvLogMapper extends MyMapper<BocmRcvLog> {
    List<BocmRcvLog> selectAll();
}