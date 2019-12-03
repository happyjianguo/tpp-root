package com.fxbank.tpp.mivs.mapper.CmonConf;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.CmonConf.MivsCmonconfInfoEntity;
import java.util.List;

public interface MivsCmonconfInfoEntityMapper extends MyMapper<MivsCmonconfInfoEntity> {
    List<MivsCmonconfInfoEntity> selectAll();
}