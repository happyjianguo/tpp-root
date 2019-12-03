package com.fxbank.tpp.mivs.mapper.FreeFrmt;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.FreeFrmt.MivsFreefrmtConfEntity;
import java.util.List;

public interface MivsFreefrmtConfEntityMapper extends MyMapper<MivsFreefrmtConfEntity> {
    List<MivsFreefrmtConfEntity> selectAll();
}