package com.fxbank.tpp.mivs.mapper.TxPmtVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvfctnInfoAttEntity;

import java.util.List;

public interface MivsTxpmtvfctnInfoAttMapper extends MyMapper<MivsTxpmtvfctnInfoAttEntity> {
    List<MivsTxpmtvfctnInfoAttEntity> selectAll();
}