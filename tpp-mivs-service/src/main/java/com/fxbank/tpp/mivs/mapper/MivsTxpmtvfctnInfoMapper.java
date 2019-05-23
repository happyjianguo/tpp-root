package com.fxbank.tpp.mivs.mapper;

import com.fxbank.cip.base.common.MyMapper;
import com.fxbank.tpp.mivs.entity.MivsTxpmtvfctnInfo;
import java.util.List;

public interface MivsTxpmtvfctnInfoMapper extends MyMapper<MivsTxpmtvfctnInfo> {
    List<MivsTxpmtvfctnInfo> selectAll();
}