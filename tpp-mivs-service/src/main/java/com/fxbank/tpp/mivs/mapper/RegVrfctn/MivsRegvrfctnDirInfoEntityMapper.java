package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnDirInfoEntity;
import java.util.List;

public interface MivsRegvrfctnDirInfoEntityMapper extends MyMapper<MivsRegvrfctnDirInfoEntity> {
    List<MivsRegvrfctnDirInfoEntity> selectAll();
}