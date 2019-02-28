package com.fxbank.tpp.tcex.trade;

import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.model.ModelPage;

public class TradeBase {
	
	protected ModelPage initModelpage(REQ_APP_HEAD reqAppHead){
		return new ModelPage(reqAppHead.getPgupOrPgnd(), Integer.valueOf(reqAppHead.getTotalNum()), Integer.valueOf(reqAppHead.getCurrentNum()), reqAppHead.getTotalFlag());
	}
	
	protected void setAppHead(REP_APP_HEAD repAppHead,ModelPage page,REQ_APP_HEAD reqAppHead){
		repAppHead.setPgupOrPgnd(reqAppHead.getPgupOrPgnd());
		repAppHead.setTotalNum(reqAppHead.getTotalNum());
		repAppHead.setCurrentNum(String.valueOf(page.getCurrentNum()));
		repAppHead.setTotalNum(String.valueOf(page.getTotalRows()));
		repAppHead.setTotalFlag(reqAppHead.getTotalFlag());
	}
}
