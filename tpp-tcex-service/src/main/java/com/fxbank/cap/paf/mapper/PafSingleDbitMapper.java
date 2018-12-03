package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafSingleDbit;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface PafSingleDbitMapper extends MyMapper<PafSingleDbit> {
    List<PafSingleDbit> selectAll();
}