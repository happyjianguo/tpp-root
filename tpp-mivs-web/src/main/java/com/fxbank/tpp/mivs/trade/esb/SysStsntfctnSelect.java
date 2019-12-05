package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000216;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000216;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;
import com.fxbank.tpp.mivs.service.IMivsSysStsNtfctnService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 王鹏
 * @Date: 2019/9/10 13:40
 */
@Service("REQ_50023000216")
public class SysStsntfctnSelect extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsSysStsNtfctnService mivsSysStsNtfctnService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000216 req = (REQ_50023000216) dto;//接收ESB请求报文
        REQ_50023000216.REQ_BODY reqBody = req.getReqBody();

        //查询数据
        MivsSysStsNtfctnModel mivsSysStsNtfctnModel =  new MivsSysStsNtfctnModel();
        if(reqBody.getQueDt() != null && !reqBody.getQueDt().equals("")) {
            mivsSysStsNtfctnModel.setPlat_date(Integer.parseInt(reqBody.getQueDt()));
        }
        mivsSysStsNtfctnModel.setMsg_id(isOrNotNull(reqBody.getMsgId(),"801来账MSGID", "N"));

        List<MivsSysStsNtfctnModel> mivsSysStsNtfctnModels = mivsSysStsNtfctnService.selectMsg(mivsSysStsNtfctnModel); //查询数据库业务数据
        if(mivsSysStsNtfctnModels != null && !mivsSysStsNtfctnModels.isEmpty()) {
            myLog.info(logger, "查询结果为：" + mivsSysStsNtfctnModels.toString());
        }else{
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000216 rep = new REP_50023000216();
        if(mivsSysStsNtfctnModels != null && !mivsSysStsNtfctnModels.isEmpty()) {
            List<REP_50023000216.resultList> resultArrayList = new ArrayList<REP_50023000216.resultList>();
            int i = 0;
            for (MivsSysStsNtfctnModel Info : mivsSysStsNtfctnModels) {
                REP_50023000216.resultList resultList = new REP_50023000216.resultList();
                resultList.setMsgId(Info.getMsg_id());
                resultList.setCreDtTm(Info.getCre_dt_tm());
                resultList.setInstgDrctPty(Info.getInstg_drct_pty());
                resultList.setInstgPty(Info.getInstg_pty());
                resultList.setInstdDrctPty(Info.getInstd_drct_pty());
                resultList.setInstdPty(Info.getInstd_pty());
                resultList.setCurSysDt(Info.getCur_sys_dt());
                resultList.setNxtSysDt(Info.getNxt_sys_dt());
                resultList.setSysInd(Info.getSys_ind());
                resultList.setSvcInd(Info.getSvc_ind());
                resultList.setSysOpTm(Info.getNxt_sys_op_tm());
                resultList.setSysClTm(Info.getNxt_sys_cl_tm());
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}