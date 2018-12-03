package com.fxbank.cap.paf.mapper;

import com.fxbank.cap.paf.entity.PafBatchCrdtMaster;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PafBatchCrdtMasterMapper extends MyMapper<PafBatchCrdtMaster> {
    List<PafBatchCrdtMaster> selectAll();
    List<PafBatchCrdtMaster> selectBatchByTxStatus(@Param("txStatus") String txStatus);
    PafBatchCrdtMaster selectDetailsAmtSum(@Param("batchNo") String batchNo);
    int updateMaster(@Param("master") PafBatchCrdtMaster master,@Param("oldStatus") String oldStatus);
}