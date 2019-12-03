package com.fxbank.tpp.mivs.mapper.IdVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.IdVrfctn.MivsIdvrfctnFdbkEntity;
import java.util.List;

public interface MivsIdvrfctnFdbkEntityMapper extends MyMapper<MivsIdvrfctnFdbkEntity> {
    List<MivsIdvrfctnFdbkEntity> selectAll();
}