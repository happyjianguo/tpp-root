package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafBatchLoanMaster;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PafBatchLoanMasterMapper extends MyMapper<PafBatchLoanMaster> {
    List<PafBatchLoanMaster> selectAll();
    List<PafBatchLoanMaster> selectBatchByTxStatus(@Param("txStatus") String txStatus);
    PafBatchLoanMaster selectDetailsAmtSum(@Param("batchNo") String batchNo);
    int updateMaster(@Param("master") PafBatchLoanMaster master,@Param("oldStatus") String oldStatus);
}