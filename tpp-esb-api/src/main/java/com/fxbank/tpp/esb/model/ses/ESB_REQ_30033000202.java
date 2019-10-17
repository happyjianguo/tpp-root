package com.fxbank.tpp.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
/**
* @ClassName: ESB_REQ_30033000202 
* @Description:IC卡验证应答报文
* @author YePuLiang
* @date 2019年6月25日 下午2:08:53 
*
 */
public class ESB_REQ_30033000202 extends ESB_BASE {

	private static final long serialVersionUID = -1360950203845217850L;

	public ESB_REQ_30033000202 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300330002", "02");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -8505431430712414605L;
		@JSONField(name = "CARD_NO")
		private String cardNo;//卡号
		@JSONField(name = "F55")
		private String f55;//55域内容
		@JSONField(name = "IC_CARD_SEQ_NO")
		private String icCardSeqNo;//IC卡序列号
		
	public String  getCardNo(){
			return cardNo;
		}
		public void setCardNo(String cardNo){
			this.cardNo = cardNo;
		}
		public String  getF55(){
			return f55;
		}
		public void setF55(String f55){
			this.f55 = f55;
		}
		public String  getIcCardSeqNo(){
			return icCardSeqNo;
		}
		public void setIcCardSeqNo(String icCardSeqNo){
			this.icCardSeqNo = icCardSeqNo;
		}
		
	}
	
	

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public ESB_REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public ESB_REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

}