package com.fxbank.cap.manager.service;


import com.fxbank.cap.manager.entity.PafAccMstReport;
import com.fxbank.cap.manager.mapper.PafAccMstReportMapper;
import com.fxbank.cip.base.log.MyLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PafAccMstService {

    @Resource
    private PafAccMstReportMapper mapper;


    public PafAccMstReport getListPage(PafAccMstReport data){
        //分页查询，传入页码，每页行数
        Page<PafAccMstReport> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<PafAccMstReport> list=mapper.selectListPage(data);
        //分页信息
        PageInfo<PafAccMstReport> pageInfo = new PageInfo<PafAccMstReport>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }


    public List<String> getChildDeparts(int userId){
        String departId=mapper.getDeptIdByUserId(userId);
        departId="%/"+departId+"/%";
        List<String> list=mapper.getDepartCodeByParentId(departId);
        return list;
    }


    public PafAccMstReport getFileList(PafAccMstReport data){
        //分页查询，传入页码，每页行数
        Page<PafAccMstReport> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<PafAccMstReport> list=mapper.getFileListByCenterNo(data);
        //分页信息
        PageInfo<PafAccMstReport> pageInfo = new PageInfo<PafAccMstReport>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }


    public PafAccMstReport getDataById(String id){
        PafAccMstReport data=new PafAccMstReport();
        data.setId(Integer.parseInt(id));
        data=mapper.selectOne(data);
        return data;
    }


    public List<PafAccMstReport> getListByFileName(String fileName){
        PafAccMstReport data=new PafAccMstReport();
        data.setFileName(fileName);
        List<PafAccMstReport> list=mapper.select(data);
        return list;
    }


    public void save(PafAccMstReport pafAccMstReport) {
        String id=mapper.selectMaxId();
        if(id==null) id="0";
        pafAccMstReport.setId(Integer.parseInt(id)+1);
        mapper.insertSelective(pafAccMstReport);
    }
}
