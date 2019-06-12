package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnIllInfoEntity;
import java.util.List;

public interface MivsRegvrfctnIllInfoEntityMapper extends MyMapper<MivsRegvrfctnIllInfoEntity> {
    List<MivsRegvrfctnIllInfoEntity> selectAll();
}