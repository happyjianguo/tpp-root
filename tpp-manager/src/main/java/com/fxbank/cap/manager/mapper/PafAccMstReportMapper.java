package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.PafAccMstReport;
import com.fxbank.cip.base.common.MyMapper;

import java.util.List;

public interface PafAccMstReportMapper extends MyMapper<PafAccMstReport> {

    List<PafAccMstReport> selectAll();
    List<PafAccMstReport> selectListPage(PafAccMstReport data);
    String getDeptIdByUserId(int userId);
    List getDepartCodeByParentId(String departId);
    List<PafAccMstReport> getFileListByCenterNo(PafAccMstReport data);
    String selectMaxId();
}
