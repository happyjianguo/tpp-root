/**   
* @Title: REP_10103.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月7日 下午5:51:44 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;


/** 
* @ClassName: REP_10103 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2019年5月7日 下午5:51:44 
*  
*/
public class REP_10103 extends REP_BASE {
	
	private static final long serialVersionUID = -3159939908759734651L;

    private String filTxt;
	
    @Deprecated
	public REP_10103() {
		super(null, 0, 0, 0);
	}

    public REP_10103(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

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
    
    
}
