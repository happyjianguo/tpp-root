package com.fxbank.tpp.mivs.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.MivsIdvrfctnInfo;
import java.util.List;

public interface MivsIdvrfctnInfoMapper extends MyMapper<MivsIdvrfctnInfo> {
    List<MivsIdvrfctnInfo> selectAll();
}