package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000211;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000211;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsFreeFrmtModel;
import com.fxbank.tpp.mivs.service.IMivsFreeFrmtService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 公告信息查询
 * @Author: 王鹏
 * @Date: 2019/7/3 15:56
 */
@Service("REQ_50023000211")
public class FreeFrmtSelect extends TradeBase implements TradeExecutionStrategy{

        private static Logger logger = LoggerFactory.getLogger(ComConf.class);

        @Resource
        private LogPool logPool;

        @Resource
        private SyncCom syncCom;

        @Reference(version = "1.0.0")
        private IForwardToESBService forwardToESBService;

        @Reference(version = "1.0.0")
        private IMivsFreeFrmtService freeFrmtService;

        @Override
        public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
            MyLog myLog = logPool.get();

            REQ_50023000211 req = (REQ_50023000211) dto;//接收ESB请求报文
            REQ_50023000211.REQ_BODY reqBody = req.getReqBody();

            //查询数据落库
            MivsFreeFrmtModel freeFrmtModel =  new MivsFreeFrmtModel();
            freeFrmtModel.setStart_dt(reqBody.getStartDt());
            freeFrmtModel.setEnd_dt(reqBody.getEndDt());
            freeFrmtModel.setRply_flag(reqBody.getRplyFlag());

            List<MivsFreeFrmtModel> freeFrmtModels = freeFrmtService.selectResult(freeFrmtModel); //查询数据库业务数据
            myLog.info(logger,"查询结果为：" + freeFrmtModel.toString());

            REP_50023000211 rep = new REP_50023000211();
            if(freeFrmtModels != null && !freeFrmtModels.isEmpty()) {
                List<REP_50023000211.resultList> resultArrayList = new ArrayList<REP_50023000211.resultList>();
                int i = 0;
                for (MivsFreeFrmtModel Info : freeFrmtModels) {
                    REP_50023000211.resultList resultList = new REP_50023000211.resultList();
                    resultList.setPlatDate(Info.getPlat_date());
                    resultList.setPlatTrace(Info.getPlat_trace());
                    resultList.setPlatTime(Info.getPlat_time());
                    resultList.setMsgId(Info.getMsg_id());
                    resultList.setInstdDrctPty(Info.getInstd_drct_pty());
                    resultList.setInstdPty(Info.getInstd_pty());
                    resultList.setInstgDrctPty(Info.getInstg_drct_pty());
                    resultList.setInstgPty(Info.getInstg_pty());
                    resultList.setRplyFlag(Info.getRply_flag());
                    resultList.setMsgCntt(Info.getMsg_cntt());
                    resultList.setIsornotRsp(Info.getIsornot_rsp());
                    resultArrayList.add(resultList);
                    myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
                }
                rep.getRepBody().setResultList(resultArrayList);
            }
            return rep;
        }
}