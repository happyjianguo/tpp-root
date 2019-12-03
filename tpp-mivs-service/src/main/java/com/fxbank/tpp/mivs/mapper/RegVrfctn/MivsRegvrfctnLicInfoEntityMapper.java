package com.fxbank.tpp.mivs.mapper.RegVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.RegVrfctn.MivsRegvrfctnLicInfoEntity;
import java.util.List;

public interface MivsRegvrfctnLicInfoEntityMapper extends MyMapper<MivsRegvrfctnLicInfoEntity> {
    List<MivsRegvrfctnLicInfoEntity> selectAll();
}