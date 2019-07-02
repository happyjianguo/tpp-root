package com.fxbank.tpp.mivs.mapper.TxPmtVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnFdbkEntity;
import java.util.List;

public interface MivsTxpmtvrfctnFdbkEntityMapper extends MyMapper<MivsTxpmtvrfctnFdbkEntity> {
    List<MivsTxpmtvrfctnFdbkEntity> selectAll();
}