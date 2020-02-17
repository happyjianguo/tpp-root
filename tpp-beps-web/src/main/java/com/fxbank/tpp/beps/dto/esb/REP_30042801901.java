package com.fxbank.tpp.beps.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * 
 * @类描述：大额自由格式报文客户端应答接口
 * @项目名称：tpp-beps-web 
 * @包名： com.fxbank.tpp.beps.dto.esb
 * @类名称：REQ_30042801901
 * @创建人：lit
 * @创建时间：2020年1月16日下午3:54:57
 * @修改人：lit
 * @修改时间：2020年1月16日下午3:54:57 @修改备注：
 * @version v1.0
 * @see
 * @bug
 * @Copyright
 * @mail *@qq.com
 */
public class REP_30042801901 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();

	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public class REP_BODY {
		@JSONField(name = "CHNL_DT_T2")
		private String chnlDtT2; // 渠道日期

		@JSONField(name = "CHNL_SRL_NO_T2")
		private String chnlSrlNoT2; // 渠道流水

		public String getChnlDtT2() {
			return chnlDtT2;
		}

		public void setChnlDtT2(String chnlDtT2) {
			this.chnlDtT2 = chnlDtT2;
		}

		public String getChnlSrlNoT2() {
			return chnlSrlNoT2;
		}

		public void setChnlSrlNoT2(String chnlSrlNoT2) {
			this.chnlSrlNoT2 = chnlSrlNoT2;
		}
	}
}
