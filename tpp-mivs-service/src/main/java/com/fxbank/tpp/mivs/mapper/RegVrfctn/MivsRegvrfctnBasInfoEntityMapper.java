package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnBasInfoEntity;

import java.util.List;

public interface MivsRegvrfctnBasInfoEntityMapper extends MyMapper<MivsRegvrfctnBasInfoEntity> {
    List<MivsRegvrfctnBasInfoEntity> selectAll();
}