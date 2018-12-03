package com.fxbank.cap.paf.TEST;

import java.io.IOException;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cap.paf.constant.PAF;
import com.fxbank.cap.paf.dto.SER_REP_BDC;
import com.fxbank.cap.paf.dto.SER_REQ_DATA;
import com.fxbank.cap.paf.model.FIELD;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.tienon.util.FileFieldConv;

@Service("SBDC201")
public class TEST_accNotify implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(TEST_accNotify.class);

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		SER_REQ_DATA reqDto = (SER_REQ_DATA) dto;
		for(Entry<String,Object> e : reqDto.getHead().entrySet()){
			logger.info("HEAD:"+e.getKey()+" || "+e.getValue());
		}
		for(Entry<String,Object> e : reqDto.getBody().entrySet()){
			logger.info("BODY:"+e.getKey()+" || "+e.getValue());
		}
		for (Entry<String, Object> e : reqDto.getBodyList().entrySet()) {
			logger.info("BODYLIST:" + e.getKey() + " || " + e.getValue());
			if (e.getKey().equals("DATA0")) {
				String bcdString = (String) e.getValue();
				try {
					String ascString = FileFieldConv.fieldBCDtoASC(bcdString,PAF.ENCODING);
					logger.info("收到的文件内容:"+ascString);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		SER_REP_BDC repDto = new SER_REP_BDC();
		repDto.getBody().getField().add(new FIELD("Rbk1","Rbk1"));
		return repDto;
	}

}
