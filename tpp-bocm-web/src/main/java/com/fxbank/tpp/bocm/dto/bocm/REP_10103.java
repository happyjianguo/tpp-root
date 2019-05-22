/**   
* @Title: REP_10103.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 下午3:47:32 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;

/** 
* @ClassName: REP_10103 
* @Description: 对账文件获取
* @author YePuLiang
* @date 2019年5月6日 下午3:47:32 
*  
*/
public class REP_10103 extends REP_BASE {
	
	@FixedField(order = 5, len = 8, desc = "文件长度")
    private String filLen;

	@FixedField(order = 6, len = 7, desc = "总笔数")
    private String tolCnt;

	@FixedField(order = 7, len = 15, desc = "总金额")
    private Double tolAmt;
	
	@FixedField(order = 8, desc = "循环内容")
    private List<Inner> content = new ArrayList<Inner>();
	
    public static class Inner {

        @FixedField(order = 81, len = 2, desc = "业务类型")
        private String svalue;

        @FixedField(order = 82, len = 2, desc = "交易日期1")
        private Integer ivalue;

		public String getSvalue() {
			return svalue;
		}

		public void setSvalue(String svalue) {
			this.svalue = svalue;
		}

		public Integer getIvalue() {
			return ivalue;
		}

		public void setIvalue(Integer ivalue) {
			this.ivalue = ivalue;
		}


    }

	public String getFilLen() {
		return filLen;
	}

	public void setFilLen(String filLen) {
		this.filLen = filLen;
	}

	public String getTolCnt() {
		return tolCnt;
	}

	public void setTolCnt(String tolCnt) {
		this.tolCnt = tolCnt;
	}

	public Double getTolAmt() {
		return tolAmt;
	}

	public void setTolAmt(Double tolAmt) {
		this.tolAmt = tolAmt;
	}

	public List<Inner> getContent() {
		return content;
	}

	public void setContent(List<Inner> content) {
		this.content = content;
	}

//	public String getFilTxt() {
//		return filTxt;
//	}
//
//	public void setFilTxt(String filTxt) {
//		this.filTxt = filTxt;
//	}
	
	
	public static void main(String[] args) {
		String tx = "NFX0000交易成功                          1906200001279100000008      2              601020304";
		
		REP_10103 modelRep = new FixedUtil(tx).toBean(REP_10103.class); // 定长
		
		

	}
	
   
    


}
