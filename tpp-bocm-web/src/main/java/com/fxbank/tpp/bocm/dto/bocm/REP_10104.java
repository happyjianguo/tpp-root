/**   
* @Title: REP_10104.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月23日 下午3:22:44 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_10104 
* @Description: 工作密钥申请
* @author YePuLiang
* @date 2019年5月23日 下午3:22:44 
*  
*/
public class REP_10104 extends REP_BASE{
	
	@FixedField(order = 5, len = 2, scale = 0, desc = "应用代码")
	private Integer appCod = 40;

	@FixedField(order = 6, len = 1, scale = 0, desc = "响应标示")
    private String rspFlg = "0";

	@FixedField(order = 7, len = 2, scale = 0, desc = "密文长度")
    private Integer blkLen;
	
	@FixedField(order = 8, len = 32, scale = 0, desc = "密文值")
    private String blkVal;
	
	@FixedField(order = 9, len = 2, scale = 0, desc = "密钥校验值长度")
    private Integer chkLen;
	
	@FixedField(order = 10, len = 16, scale = 0, desc = "密钥校验值")
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
	
	
	public static void main(String[] args) {
		
		REP_10104 repDto = new REP_10104();
		repDto.setChkVal("ABCDEFG");
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(repDto,"UTF-8"));
		System.out.println(fixPack.toString());
		REP_10104 reqBase = (REP_10104)new FixedUtil(fixPack.toString(),"UTF-8").toBean(REP_10104.class);	
		
		System.out.println("长度【"+reqBase.getChkVal().length()+"】【"+reqBase.getChkVal()+"】");
	}
	
	
}
