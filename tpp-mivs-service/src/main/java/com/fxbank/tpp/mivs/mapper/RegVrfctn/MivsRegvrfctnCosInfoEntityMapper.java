package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnCosInfoEntity;
import java.util.List;

public interface MivsRegvrfctnCosInfoEntityMapper extends MyMapper<MivsRegvrfctnCosInfoEntity> {
    List<MivsRegvrfctnCosInfoEntity> selectAll();
}