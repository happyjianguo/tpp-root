package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafBatchCrdtDetail;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PafBatchCrdtDetailMapper extends MyMapper<PafBatchCrdtDetail> {
    List<PafBatchCrdtDetail> selectAll();
    List<String> selectSeqNosByBatchNo(@Param("batchNo") String batchNo);
    int addList(List<PafBatchCrdtDetail> list);
    int addList2(List<PafBatchCrdtDetail> list);
    List<String> selectSeqNosByParams(@Param("batchNo") String batchNo,@Param("txStatus") String txStatus);
    int updateDetail(@Param("detail") PafBatchCrdtDetail detail,@Param("oldStatus") String oldStatus);
}