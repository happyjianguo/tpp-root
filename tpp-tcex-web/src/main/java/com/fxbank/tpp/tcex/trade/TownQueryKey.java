package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.PasswordModel;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_KEY001;
import com.fxbank.tpp.tcex.dto.esb.REQ_KEY001;

/**
 * 工作密钥更新申请
 * @author liye
 *
 */
@Service("REQ_KEY001")
public class TownQueryKey extends TradeBase implements TradeExecutionStrategy{
	private static Logger logger = LoggerFactory.getLogger(CityQueryAcctInfo.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IPasswordService passwordService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_KEY001 reqDto = (REQ_KEY001)dto;
		String keyModel = reqDto.getReqBody().getKeyModel();
		//请求核心获取账户信息
		PasswordModel passwordModel = new PasswordModel(logPool.get(), reqDto.getSysDate(), 
				reqDto.getSysTime(),reqDto.getSysTraceno());
		passwordModel.setKeyModel(keyModel);
		passwordModel = passwordService.genKey(passwordModel);
		REP_KEY001 repDto = new REP_KEY001();
		repDto.getRepBody().setKeyValue(passwordModel.getKeyValue());
		repDto.getRepBody().setChkKeyValue(passwordModel.getCheckValue());
		myLog.info(logger, "获取工作密钥更新申请成功，申请密钥类型"+keyModel);
		return repDto;
	}
	
	
}
