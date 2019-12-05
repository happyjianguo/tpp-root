package com.fxbank.tpp.mivs.mapper.TxPmtVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoAttEntity;
import java.util.List;

public interface MivsTxpmtvrfctnInfoAttEntityMapper extends MyMapper<MivsTxpmtvrfctnInfoAttEntity> {
    List<MivsTxpmtvrfctnInfoAttEntity> selectAll();
}