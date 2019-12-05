package com.fxbank.tpp.mivs.mapper.FreeFrmt;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.FreeFrmt.MivsFreefrmtInfoEntity;
import java.util.List;

public interface MivsFreefrmtInfoEntityMapper extends MyMapper<MivsFreefrmtInfoEntity> {
    List<MivsFreefrmtInfoEntity> selectAll();
}