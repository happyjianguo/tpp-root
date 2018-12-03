package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafSingleCrdt;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface PafSingleCrdtMapper extends MyMapper<PafSingleCrdt> {
    List<PafSingleCrdt> selectAll();
}