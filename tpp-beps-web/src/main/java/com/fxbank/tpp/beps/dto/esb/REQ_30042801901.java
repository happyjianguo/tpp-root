package com.fxbank.tpp.beps.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * 
 * @类描述： 大额自由格式报文客户端请求接口
 * 
 * @项目名称：tpp-beps-web @包名： com.fxbank.tpp.beps.dto.esb
 * @类名称：REP_30042801901
 * @创建人：lit
 * @创建时间：2020年1月16日下午2:50:20
 * @修改人：lit
 * @修改时间：2020年1月16日下午2:50:20 @修改备注：
 * @version v1.0
 * @see
 * @bug
 * @Copyright
 * @mail *@qq.com
 */
public class REQ_30042801901 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30042801901() {
		super.txDesc = "大额自由格式报文";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {
		@JSONField(name = "INFO_CNT_T1")
		private String infoCntT1;// 查询书内容

		@JSONField(name = "RCVNG_BNK_NO_T6")
		private String rcvngBnkNoT6;// 接收行行号

		@JSONField(name = "RCVNG_TP_T")
		private String rcvngTpt;// 接收方类型

		@JSONField(name = "BNK_NM_T")
		private String bnkNmT;// 行名

		@JSONField(name = "RCVNG_CNTR_T3")
		private String rcvngCntrT3;// 收报中心

		public String getInfoCntT1() {
			return infoCntT1;
		}

		public void setInfoCntT1(String infoCntT1) {
			this.infoCntT1 = infoCntT1;
		}

		public String getRcvngBnkNoT6() {
			return rcvngBnkNoT6;
		}

		public void setRcvngBnkNoT6(String rcvngBnkNoT6) {
			this.rcvngBnkNoT6 = rcvngBnkNoT6;
		}

		public String getRcvngTpt() {
			return rcvngTpt;
		}

		public void setRcvngTpt(String rcvngTpt) {
			this.rcvngTpt = rcvngTpt;
		}

		public String getBnkNmT() {
			return bnkNmT;
		}

		public void setBnkNmT(String bnkNmT) {
			this.bnkNmT = bnkNmT;
		}

		public String getRcvngCntrT3() {
			return rcvngCntrT3;
		}

		public void setRcvngCntrT3(String rcvngCntrT3) {
			this.rcvngCntrT3 = rcvngCntrT3;
		}

	}
}
