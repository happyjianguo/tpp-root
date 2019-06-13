package com.fxbank.tpp.mivs.mapper.TxPmtVrfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.TxPmtVrfctn.MivsTxpmtvrfctnInfoEntity;
import java.util.List;

public interface MivsTxpmtvrfctnInfoMapper extends MyMapper<MivsTxpmtvrfctnInfoEntity> {
    List<MivsTxpmtvrfctnInfoEntity> selectAll();
}