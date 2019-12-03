package com.fxbank.tpp.mivs.mapper.AbnmlVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.AbnmlVrfctn.MivsAbnmlvrfctnInfoEntity;
import java.util.List;

public interface MivsAbnmlvrfctnInfoEntityMapper extends MyMapper<MivsAbnmlvrfctnInfoEntity> {
    List<MivsAbnmlvrfctnInfoEntity> selectAll();
}