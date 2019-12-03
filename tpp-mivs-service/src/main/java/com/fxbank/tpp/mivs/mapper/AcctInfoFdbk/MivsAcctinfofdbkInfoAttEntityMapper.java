package com.fxbank.tpp.mivs.mapper.AcctInfoFdbk;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.AcctInfoFdbk.MivsAcctinfofdbkInfoAttEntity;
import java.util.List;

public interface MivsAcctinfofdbkInfoAttEntityMapper extends MyMapper<MivsAcctinfofdbkInfoAttEntity> {
    List<MivsAcctinfofdbkInfoAttEntity> selectAll();
}