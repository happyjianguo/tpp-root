package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnFdbkEntity;
import java.util.List;

public interface MivsRegvrfctnFdbkEntityMapper extends MyMapper<MivsRegvrfctnFdbkEntity> {
    List<MivsRegvrfctnFdbkEntity> selectAll();
}