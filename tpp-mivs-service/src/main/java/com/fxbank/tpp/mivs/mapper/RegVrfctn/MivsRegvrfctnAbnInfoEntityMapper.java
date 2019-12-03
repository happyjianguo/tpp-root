package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnAbnInfoEntity;
import java.util.List;

public interface MivsRegvrfctnAbnInfoEntityMapper extends MyMapper<MivsRegvrfctnAbnInfoEntity> {
    List<MivsRegvrfctnAbnInfoEntity> selectAll();
}