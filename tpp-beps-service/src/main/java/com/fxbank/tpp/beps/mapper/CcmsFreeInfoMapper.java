package com.fxbank.tpp.beps.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.beps.entity.CcmsFreeInfo;
import java.util.List;

public interface CcmsFreeInfoMapper extends MyMapper<CcmsFreeInfo> {
    List<CcmsFreeInfo> selectAll();
}