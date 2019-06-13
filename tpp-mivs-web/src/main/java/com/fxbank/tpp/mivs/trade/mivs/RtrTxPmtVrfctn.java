package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_323_001_01;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsTxpmtVrfctnInfoModel;
import com.fxbank.tpp.mivs.model.response.MIVS_323_001_01_RtrTxPmtVrfctn;
import com.fxbank.tpp.mivs.service.IMivsTxPmtVrfctnInfoService;
import com.fxbank.tpp.mivs.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 纳税信息联网核查应答报文
 * @Author: 王鹏
 * @Date: 2019/4/30 7:50
 */
@Service("MIVS_323_001_01")
public class RtrTxPmtVrfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrTxPmtVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference(version = "1.0.0")
    private IMivsTxPmtVrfctnInfoService mivsTxPmtVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_323_001_01 mivs323 = (MIVS_323_001_01) dto;
        MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.OprlErr oprlErr = mivs323.getRtrTxPmtVrfctn().getRspsn().getOprlErr();
        MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf vrfctnInf = mivs323.getRtrTxPmtVrfctn().getRspsn().getVrfctnInf();
        MIVS_323_001_01_RtrTxPmtVrfctn.MsgHdr msgHdr = mivs323.getRtrTxPmtVrfctn().getMsgHdr();
        MIVS_323_001_01_RtrTxPmtVrfctn.OrgnlBizQry orgnlBizQry = mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry();
        myLog.info(logger, "收到人行纳税信息联网核查应答报文,进行同步处理");
        byte[] b323 = SerializeUtil.serialize(mivs323);
        String channel = "323_" + orgnlBizQry.getMsgId();   //TODO 拼接原报文三要素
        myLog.info(logger, "323报文同步通道编号=[" + channel + "]");

        if(oprlErr.getProcSts() != null){
            super.jedisPublish(myLog,channel.getBytes(), b323);
            myLog.info(logger, "发布至redis成功");
        }else{
            //查询主表数据
            MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoModelSelectMaster = mivsTxPmtVrfctnInfoService.selectMasterAndAttached(mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry().getMsgId(), mivs323.getRtrTxPmtVrfctn().getOrgnlBizQry().getInstgPty().getInstgPty(), "master");
            myLog.info(logger, "查询主表数据查询结果为：" + txpmtVrfctnInfoModelSelectMaster.toString());
            //收到人行通讯回执，准备更新数据库状态，插入附表数据，在同一个事务进行
            myLog.info(logger, "收到人行通讯回执，准备更新数据库状态，插入附表数据，在同一个事务进行");
            MivsTxpmtVrfctnInfoModel txpmtVrfctnInfoUmasAndIatt = new MivsTxpmtVrfctnInfoModel();
            //更新数据的主键赋值
            txpmtVrfctnInfoUmasAndIatt.setPlat_date(txpmtVrfctnInfoModelSelectMaster.getPlat_date());
            txpmtVrfctnInfoUmasAndIatt.setPlat_trace(txpmtVrfctnInfoModelSelectMaster.getPlat_trace());
            //主表应答信息赋值
            txpmtVrfctnInfoUmasAndIatt.setMivs_sts("05");
            txpmtVrfctnInfoUmasAndIatt.setRslt(vrfctnInf.getRslt());
            txpmtVrfctnInfoUmasAndIatt.setData_resrc_dt(vrfctnInf.getDataResrcDt());
            //取循环数据
            List<MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf> TxpmtInf = vrfctnInf.getTxpmtInf();
            //定义附表数据
            List<MivsTxpmtVrfctnInfoModel.TxpmtInf> txpmtInfList = new ArrayList<MivsTxpmtVrfctnInfoModel.TxpmtInf>();
            int txpmt_inf_nb = 0;
            if(TxpmtInf != null && !TxpmtInf.isEmpty()) {
                for (MIVS_323_001_01_RtrTxPmtVrfctn.Rspsn.VrfctnInf.TxpmtInf Info:TxpmtInf) {
                    MivsTxpmtVrfctnInfoModel.TxpmtInf txpmtInf = new MivsTxpmtVrfctnInfoModel.TxpmtInf();
                    //赋值纳税核查信息附表数据
                    txpmtInf.setPlat_date(mivs323.getSysDate());
                    txpmtInf.setPlat_trace(mivs323.getSysTraceno());
                    txpmtInf.setPlat_time(mivs323.getSysTime());
                    txpmtInf.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
                    txpmtInf.setMsg_id(msgHdr.getMsgId());
                    txpmtInf.setCre_dt_tm(msgHdr.getCreDtTm());
                    txpmtInf.setOrig_msg_id(orgnlBizQry.getMsgId());
                    txpmtInf.setOrig_instg_drct_pty(orgnlBizQry.getInstgPty().getInstgDrctPty());
                    txpmtInf.setOrig_instg_pty(orgnlBizQry.getInstgPty().getInstgPty());
                    txpmtInf.setTxpmt_inf_nb(txpmt_inf_nb+1);
                    txpmtInf.setTx_auth_cd(Info.getTxAuthCd());
                    txpmtInf.setTx_auth_nm(Info.getTxAuthNm());
                    txpmtInf.setTxpyr_sts(Info.getTxpySts());
                    //插入纳税核查信息附表
                    txpmtInfList.add(txpmtInf);
                    txpmt_inf_nb++;
                    myLog.info(logger, "条数：" + txpmt_inf_nb);
                }
            }
            txpmtVrfctnInfoUmasAndIatt.setTxpmtInfList(txpmtInfList);
            txpmtVrfctnInfoUmasAndIatt.setTxpmt_inf_cnt(txpmt_inf_nb);
            mivsTxPmtVrfctnInfoService.uMasterAndiAttached(txpmtVrfctnInfoUmasAndIatt, "all");
            super.jedisPublish(myLog,channel.getBytes(), b323);
            myLog.info(logger, "发布至redis成功");
        }
        return mivs323;
    }

}