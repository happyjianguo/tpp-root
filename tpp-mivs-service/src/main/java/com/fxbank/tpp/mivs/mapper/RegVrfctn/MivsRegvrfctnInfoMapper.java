package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnInfoEntity;

import java.util.List;

public interface MivsRegvrfctnInfoMapper extends MyMapper<MivsRegvrfctnInfoEntity> {
    List<MivsRegvrfctnInfoEntity> selectAll();
}