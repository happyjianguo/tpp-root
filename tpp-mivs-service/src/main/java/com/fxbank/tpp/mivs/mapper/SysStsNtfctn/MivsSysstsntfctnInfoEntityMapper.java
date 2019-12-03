package com.fxbank.tpp.mivs.mapper.SysStsNtfctn;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.SysStsNtfctn.MivsSysstsntfctnInfoEntity;
import java.util.List;

public interface MivsSysstsntfctnInfoEntityMapper extends MyMapper<MivsSysstsntfctnInfoEntity> {
    List<MivsSysstsntfctnInfoEntity> selectAll();
}