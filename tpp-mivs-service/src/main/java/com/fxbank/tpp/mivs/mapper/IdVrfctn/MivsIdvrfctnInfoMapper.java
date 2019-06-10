package com.fxbank.tpp.mivs.mapper.IdVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.IdVrfctn.MivsIdvrfctnInfoEntity;
import java.util.List;

public interface MivsIdvrfctnInfoMapper extends MyMapper<MivsIdvrfctnInfoEntity> {
    List<MivsIdvrfctnInfoEntity> selectAll();
}