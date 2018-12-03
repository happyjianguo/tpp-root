package com.fxbank.cap.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.esb.model.ses.ESB_REP_30015700901;
import com.fxbank.cap.manager.entity.PafAccMstReport;
import com.fxbank.cap.manager.entity.SysUser;
import com.fxbank.cap.manager.model.PafCenterInfo;
import com.fxbank.cap.manager.model.PafCenterList;
import com.fxbank.cap.manager.service.PafAccMstService;
import com.fxbank.cap.manager.utils.FileUtil;
import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.exception.PafTradeExecuteException;
import com.fxbank.cap.paf.model.*;
import com.fxbank.cap.paf.service.IAccountService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import com.tienon.util.FileFieldConv;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PafAccMstController {

    private static Logger logger = LoggerFactory.getLogger(PafAccMstController.class);

    @Resource
    private PafAccMstService service;

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Reference(version = "1.0.0")
    private IAccountService accountService;

    @Resource
    private MyJedis myJedis;

    private final static String BRTEL_PREFIX = "paf_branch.";

    @RequestMapping("/paf/accMstList")
    public String list(Model model,PafAccMstReport data){

        model.addAttribute("data",data);
        String jsonStrCenterList = null;
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        try (Jedis jedis = myJedis.connect()) {
            jsonStrCenterList = jedis.get(BRTEL_PREFIX + "PAFCENTER_LIST");
        }
        PafCenterList pafCenterList = JsonUtil.toBean(jsonStrCenterList, PafCenterList.class);
        List<PafCenterInfo> pafCenterInfoList =new ArrayList<PafCenterInfo>();
        List<String> departCodes=service.getChildDeparts(sysUser.getId());
        for(int i=0;i<pafCenterList.getData().size();i++){
            PafCenterInfo d=pafCenterList.getData().get(i);
            for(int j=0;j<departCodes.size();j++){
                if(d.getDepartCode().equals(departCodes.get(j))){
                    pafCenterInfoList.add(d);
                    break;
                }
            }
        }
        model.addAttribute("pafCenterInfoList",pafCenterInfoList);
        return "paf/acc_mst_list";
    }


    @ResponseBody
    @RequestMapping("/paf/accMstListData")
    public String listData(PafAccMstReport data){
        data=service.getListPage(data);
        List<PafAccMstReport> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }


    @RequestMapping("/paf/accMstFile")
    public String accMstFile(@ModelAttribute(value="data") PafAccMstReport data){

        return "paf/acc_mst_file";
    }


    @ResponseBody
    @RequestMapping("/paf/accMstFileData")
    public String accMstFileData(PafAccMstReport data){
        data=service.getFileList(data);
        List<PafAccMstReport> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }


    @ResponseBody
    @RequestMapping("/paf/pushFile")
    public String pushFile(String ids){
        MyLog myLog = new MyLog();
        JSONObject json=new JSONObject();
        try {
            String []arr=ids.split(",");
            Integer sysDate = publicService.getSysDate("CIP");
            Integer sysTime = publicService.getSysTime();
            Integer sysTraceno = publicService.getSysTraceno();
            //生成发送的文件名
            String fileName = "BDC_BAL_NTF" + "SBDC201" + String.format("%08d", sysDate)
                    + String.format("%06d", sysTime) + String.format("%08d", sysTraceno) + ".act";
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<arr.length;i++){
                PafAccMstReport data=service.getDataById(arr[i]);
                sb.append(i+1).append("|");
                sb.append(null==data.getAcctNo()?"":data.getAcctNo()).append("|");
                sb.append(null==data.getReference()?"":data.getReference()).append("|");
                sb.append(null==data.getTranCode()?"":data.getTranCode()).append("|");
                sb.append(null==data.getOthAcctNo()?"":data.getOthAcctNo()).append("|");
                sb.append(null==data.getOthAcctName()?"":data.getOthAcctName()).append("|");
                sb.append(null==data.getTranAmt()?"":data.getTranAmt()).append("|");
                sb.append(null==data.getTranDate()?"":data.getTranDate()).append("|");
                sb.append(null==data.getTranTime()?"":data.getTranTime()).append("|");
                sb.append(null==data.getAvailableAmt()?"":data.getAvailableAmt()).append("|");
                sb.append(null==data.getBranch()?"":data.getBranch()).append("|");
                sb.append(null==data.getRemark()?"":data.getRemark()).append("|");
                sb.append(null==data.getCcy()?"":data.getCcy()).append("|");
                sb.append(null==data.getAmtType()?"":data.getAmtType()).append("|");
                sb.append(null==data.getBalance()?"":data.getBalance()).append("|");
                sb.append(null==data.getOdtBalance()?"":data.getOdtBalance()).append("|");
                sb.append(null==data.getDocType()?"":data.getDocType()).append("|");
                sb.append(null==data.getVoucherNo()?"":data.getVoucherNo()).append("|");
                sb.append(null==data.getOthBranch()?"":data.getOthBranch()).append("|");
                sb.append(null==data.getNarrative()?"":data.getNarrative()).append("|");
                sb.append(null==data.getReversak()?"":data.getReversak()).append("|");
                sb.append(null==data.getSerialNum()?"":data.getSerialNum()).append("|");
                sb.append(null==data.getVolumeNum()?"":data.getVolumeNum()).append("|");
                sb.append("\n");
            }
            myLog.info(logger, "账户签约通知发送报文：[" + sb.toString()+ "]");
            System.out.println("账户签约通知发送报文：[" + sb.toString()+ "]");
            CLI_REP_DATA pack=pushTraceLogFile(myLog,sb.toString(),fileName,sysDate,sysTime,sysTraceno);
            if (pack.getHead().get("TxStatus").equals("0") && pack.getHead().get("RtnCode").equals("00000")) {
                json.put("success",true);
                json.put("message","发送账户签约通知成功");
            } else {
                json.put("success",false);
                json.put("message","发送账户签约通知失败");
            }
        }catch (Exception e){
            json.put("success",false);
            json.put("message","发送账户签约通知报错:"+e);
        }
        return  json.toString();
    }


    @ResponseBody
    @RequestMapping("/paf/pushFileList")
    public String pushFileList(String fileNames){
        MyLog myLog = new MyLog();
        JSONObject json=new JSONObject();
        try {
            String []arr=fileNames.split(",");
            Integer sysDate = publicService.getSysDate("CIP");
            Integer sysTime = publicService.getSysTime();
            Integer sysTraceno = publicService.getSysTraceno();
            //生成发送的文件名
            String fileName = "BDC_BAL_NTF" + "SBDC201" + String.format("%08d", sysDate)
                    + String.format("%06d", sysTime) + String.format("%08d", sysTraceno) + ".act";
            StringBuffer sb=new StringBuffer();
            int num=0;
            for(int i=0;i<arr.length;i++){
                List<PafAccMstReport> dataList=service.getListByFileName(arr[i]);
                for(int j=0;j<dataList.size();j++){
                    PafAccMstReport data=dataList.get(j);
                    sb.append(num+1).append("|");
                    sb.append(null==data.getAcctNo()?"":data.getAcctNo()).append("|");
                    sb.append(null==data.getReference()?"":data.getReference()).append("|");
                    sb.append(null==data.getTranCode()?"":data.getTranCode()).append("|");
                    sb.append(null==data.getOthAcctNo()?"":data.getOthAcctNo()).append("|");
                    sb.append(null==data.getOthAcctName()?"":data.getOthAcctName()).append("|");
                    sb.append(null==data.getTranAmt()?"":data.getTranAmt()).append("|");
                    sb.append(null==data.getTranDate()?"":data.getTranDate()).append("|");
                    sb.append(null==data.getTranTime()?"":data.getTranTime()).append("|");
                    sb.append(null==data.getAvailableAmt()?"":data.getAvailableAmt()).append("|");
                    sb.append(null==data.getBranch()?"":data.getBranch()).append("|");
                    sb.append(null==data.getRemark()?"":data.getRemark()).append("|");
                    sb.append(null==data.getCcy()?"":data.getCcy()).append("|");
                    sb.append(null==data.getAmtType()?"":data.getAmtType()).append("|");
                    sb.append(null==data.getBalance()?"":data.getBalance()).append("|");
                    sb.append(null==data.getOdtBalance()?"":data.getOdtBalance()).append("|");
                    sb.append(null==data.getDocType()?"":data.getDocType()).append("|");
                    sb.append(null==data.getVoucherNo()?"":data.getVoucherNo()).append("|");
                    sb.append(null==data.getOthBranch()?"":data.getOthBranch()).append("|");
                    sb.append(null==data.getNarrative()?"":data.getNarrative()).append("|");
                    sb.append(null==data.getReversak()?"":data.getReversak()).append("|");
                    sb.append(null==data.getSerialNum()?"":data.getSerialNum()).append("|");
                    sb.append(null==data.getVolumeNum()?"":data.getVolumeNum()).append("|");
                    sb.append("\n");
                }
            }
            myLog.info(logger, "账户签约通知发送报文：[" + sb.toString()+ "]");
            System.out.println("账户签约通知发送报文：[" + sb.toString()+ "]");
            CLI_REP_DATA pack=pushTraceLogFile(myLog,sb.toString(),fileName,sysDate,sysTime,sysTraceno);
            if (pack.getHead().get("TxStatus").equals("0") && pack.getHead().get("RtnCode").equals("00000")) {
                json.put("success",true);
                json.put("message","发送账户签约通知成功");
            } else {
                json.put("success",false);
                json.put("message","发送账户签约通知失败");
            }
        }catch (Exception e){
            json.put("success",false);
            json.put("message","发送账户签约通知报错:"+e);
        }
        return json.toString();
    }


    private CLI_REP_DATA pushTraceLogFile(MyLog myLog, String str, String fileName, Integer sysDate,
                                          Integer sysTime, Integer sysTraceno) throws PafTradeExecuteException {
        CLI_REQ_BDC reqData = new CLI_REQ_BDC(myLog, sysDate, sysTime, sysTraceno);
        FIELDS_LIST_OUTER outer = new FIELDS_LIST_OUTER("FILE_LIST");
        List<FIELDS_LIST_INNER> innerList = new ArrayList<FIELDS_LIST_INNER>();
        FIELDS_LIST_INNER inner0 = new FIELDS_LIST_INNER("0");
        innerList.add(inner0);
        inner0.getField().add(new FIELD("NAME", fileName));
        String bcdString = null;
        try {
//            String fileBuf = FileUtil.readToString(localFile, "UTF-8");
            myLog.info(logger, "发送的账户信息[" + str + "]");
            bcdString = FileFieldConv.fieldASCtoBCD(str, PAF.ENCODING);
        } catch (IOException e) {
            myLog.error(logger, "转换文件异常", e);
            throw new RuntimeException("转换文件异常", e);
        }
        inner0.getField().add(new FIELD("DATA", bcdString));
        outer.setField_list(innerList);
        reqData.getBody().setField_list(outer);
        CLI_REP_DATA pack = null;
        try {
            pack = accountService.notify(reqData, "SBDC201");
        } catch (JAXBException e) {
            myLog.error(logger, "发送账户通知异常", e);
            throw new RuntimeException("发送账户通知异常", e);
        }

        return pack;
    }
}
