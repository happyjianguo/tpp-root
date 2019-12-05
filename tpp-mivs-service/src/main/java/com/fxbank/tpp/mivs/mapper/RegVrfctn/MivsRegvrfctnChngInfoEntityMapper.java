package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnChngInfoEntity;
import java.util.List;

public interface MivsRegvrfctnChngInfoEntityMapper extends MyMapper<MivsRegvrfctnChngInfoEntity> {
    List<MivsRegvrfctnChngInfoEntity> selectAll();
}