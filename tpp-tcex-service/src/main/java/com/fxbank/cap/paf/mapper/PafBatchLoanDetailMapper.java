package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafBatchLoanDetail;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PafBatchLoanDetailMapper extends MyMapper<PafBatchLoanDetail> {
    List<PafBatchLoanDetail> selectAll();
    List<String> selectSeqNosByBatchNo(@Param("batchNo") String batchNo);
    int addList(List<PafBatchLoanDetail> list);
    int addList2(List<PafBatchLoanDetail> list);
    List<String> selectSeqNosByParams(@Param("batchNo") String batchNo,@Param("txStatus") String txStatus);
    int updateDetail(@Param("detail") PafBatchLoanDetail detail,@Param("oldStatus") String oldStatus);
}