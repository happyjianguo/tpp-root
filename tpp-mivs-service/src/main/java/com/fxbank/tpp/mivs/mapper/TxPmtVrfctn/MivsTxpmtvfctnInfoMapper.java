package com.fxbank.tpp.mivs.mapper.TxPmtVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvfctnInfoEntity;
import java.util.List;

public interface MivsTxpmtvfctnInfoMapper extends MyMapper<MivsTxpmtvfctnInfoEntity> {
    List<MivsTxpmtvfctnInfoEntity> selectAll();
}