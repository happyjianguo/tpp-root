package com.fxbank.tpp.simu.interceptor;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.simu.SimuApp;
import com.fxbank.tpp.simu.config.ESBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author ZhouYongwei
 * @ClassName: ESBPackConvertInterceptor
 * @Description: ESB通讯与报文处理
 * @date 2018年4月2日 下午2:14:22
 */
@Component
@ConfigurationProperties(prefix = "esb")
public class ESBPackConvertInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(ESBPackConvertInterceptor.class);

    @Resource
    private ESBConfig esbConfig;

    /**
     * @Title: afterCompletion
     * @Description: 组包发送
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ESB_BASE dto = (ESB_BASE) request.getAttribute("REPDTO");

        String repBody = null;
        try {
            repBody = JsonUtil.toJson(dto);
        } catch (RuntimeException e) {
            logger.error("生成应答报文失败", e);
            return;
        }

        request.setAttribute("RspBody", repBody);
        logger.info("发送HTTP报文体=[" + repBody + "]");
        response.getWriter().print(repBody);
    }


    /**
     * @Title: preHandle
     * @Description: 接收拆包
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        StringBuffer reqBody = new StringBuffer();
        String s = "";
        ServletInputStream sis = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(sis, "UTF-8"));
        while ((s = br.readLine()) != null) {
            reqBody.append(s);
        }
        logger.info("收到HTTP报文体=[" + reqBody.toString() + "]");
        String txCode = null;
        ESB_REQ_BASE reqBase = null;
        try {
            reqBase = JsonUtil.toBean(reqBody.toString(), ESB_REQ_BASE.class);
            txCode = "ESB_REQ_" + reqBase.getReqSysHead().getServiceId() + reqBase.getReqSysHead().getSceneId();
        } catch (Exception e) {
            logger.error("解析交易代码失败", e);
            return false;
        }
        request.setAttribute("REQBASE", reqBase);
        logger.info("交易代码=[" + txCode + "]");

        ESB_BASE dto = null;
        Class<?> esbClass = null;
        for (String systemId : SimuApp.systemIds) {
            String className = esbConfig.getDtoPath() + "." + systemId + "." + txCode;
            try {
                esbClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error("查找类文件[" + className + "]");
                continue;
            }
        }

        if (esbClass == null) {
            logger.error("交易码[" + txCode + "]对应的类文件未定义");
            return false;
        }

        try {
            dto = (ESB_BASE) JsonUtil.toBean(reqBody.toString(), esbClass);
        } catch (RuntimeException e) {
            logger.error("解析报文失败[" + reqBody + "]", e);
            return false;
        }

        request.setAttribute("TXCODE", txCode);
        request.setAttribute("REQDTO", dto);
        return true;
    }

}

class ESB_REQ_BASE extends ESB_BASE {

    private static final long serialVersionUID = 3654761523419826631L;

    public ESB_REQ_BASE() {
        super(null, 0, 0, 0);
    }

    @JSONField(name = "SYS_HEAD")
    private ESB_REQ_SYS_HEAD reqSysHead;

    public ESB_REQ_SYS_HEAD getReqSysHead() {
        return reqSysHead;
    }

    public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
        this.reqSysHead = reqSysHead;
    }

}

