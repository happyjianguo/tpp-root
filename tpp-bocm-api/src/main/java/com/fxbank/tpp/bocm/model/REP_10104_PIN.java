/**   
* @Title: REP_10104_PIN.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年6月21日 上午11:27:33 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_10104_PIN 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年6月21日 上午11:27:33 
*  
*/
public class REP_10104_PIN extends REP_BASE {
	
	private static final long serialVersionUID = -6800285963364978524L;

	@Deprecated
	 public REP_10104_PIN() {
		super(null, 0, 0, 0);
	 }

	 public REP_10104_PIN(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
	    super(mylog, sysDate, sysTime, sysTraceno);
	 }
	 
		@FixedField(order = 5, len = 2, scale = 0, desc = "应用代码")
		private Integer appCod;

		@FixedField(order = 6, len = 1, scale = 0, desc = "响应标示")
	    private String rspFlg;

		@FixedField(order = 7, len = 2, scale = 0, desc = "密文长度")
	    private Integer blkLen;
		
		@FixedField(order = 8, len = 32, scale = 0, desc = "密文值")
	    private String blkVal;
		
		@FixedField(order = 9, len = 2, scale = 0, desc = "密钥校验值长度")
	    private Integer chkLen;
		
		@FixedField(order = 10, len = 6, scale = 0, desc = "密钥校验值")
	    private String chkVal;
		

		public Integer getAppCod() {
			return appCod;
		}

		public void setAppCod(Integer appCod) {
			this.appCod = appCod;
		}

		public String getRspFlg() {
			return rspFlg;
		}

		public void setRspFlg(String rspFlg) {
			this.rspFlg = rspFlg;
		}

		public Integer getBlkLen() {
			return blkLen;
		}

		public void setBlkLen(Integer blkLen) {
			this.blkLen = blkLen;
		}

		public String getBlkVal() {
			return blkVal;
		}

		public void setBlkVal(String blkVal) {
			this.blkVal = blkVal;
		}

		public Integer getChkLen() {
			return chkLen;
		}

		public void setChkLen(Integer chkLen) {
			this.chkLen = chkLen;
		}

		public String getChkVal() {
			return chkVal;
		}

		public void setChkVal(String chkVal) {
			this.chkVal = chkVal;
		}

}
