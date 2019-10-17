package com.fxbank.tpp.bocm.dto.esb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.nettty.ServerInitializer;

/** 
* @ClassName: REP_30063800601 
* @Description: 代理交行业务流水信息查询响应
* @author YePuLiang
* @date 2019年7月9日 上午11:15:57 
*  
*/
public class REP_30063800601 {

	

	
	
	@FixedField(order = 1, len = 7, cyc = "filTxt" ,desc = "总笔数")
    private Integer tolCnt;
	
	@FixedField(order = 3, desc = "循环内容")
    private List<Detail> filTxt = new ArrayList<Detail>();

	@FixedField(order = 4, len = 15, desc = "总金额")
    private String tolAmt;
	
	 public static class Detail implements Serializable{

		private static final long serialVersionUID = -3924198328133222501L;
		
			@FixedField(order = 31, len = 4, scale = 0, desc = "交易代码")
			private String txCode="281A";
			@FixedField(order = 32, len = 4, scale = 0, desc = "交易来源")
		    private String source;
			@FixedField(order = 33, len = 4, scale = 0, desc = "平台日期")
		    private String platDate;

			
			public String getTxCode() {
				return txCode;
			}
			public void setTxCode(String txCode) {
				this.txCode = txCode;
			}
			public String getSource() {
				return source;
			}
			public void setSource(String source) {
				this.source = source;
			}
			public String getPlatDate() {
				return platDate;
			}
			public void setPlatDate(String platDate) {
				this.platDate = platDate;
			}
		
	
			
	 }







	public Integer getTolCnt() {
		return tolCnt;
	}







	public void setTolCnt(Integer tolCnt) {
		this.tolCnt = tolCnt;
	}







	public List<Detail> getFilTxt() {
		return filTxt;
	}







	public void setFilTxt(List<Detail> filTxt) {
		this.filTxt = filTxt;
	}







	public String getTolAmt() {
		return tolAmt;
	}







	public void setTolAmt(String tolAmt) {
		this.tolAmt = tolAmt;
	}







	public static void main(String[] args) {
		REP_30063800601 rep = new REP_30063800601();
		
		REP_30063800601.Detail trad = new REP_30063800601.Detail();
		trad.setSource("MT1");
		REP_30063800601.Detail trad1 = new REP_30063800601.Detail();
		trad1.setSource("MT2");
		REP_30063800601.Detail trad2 = new REP_30063800601.Detail();
		trad2.setSource("MT3");
		
		List<REP_30063800601.Detail> list = new ArrayList<REP_30063800601.Detail>();
		list.add(trad);
		list.add(trad1);
		list.add(trad2);
		
		
		rep.setFilTxt(list);
		rep.setTolCnt(list.size());
		rep.setTolAmt("100");
		
		StringBuffer fixPack = new StringBuffer(FixedUtil.toFixed(rep,"|",ServerInitializer.CODING));
		System.out.println("====================");
		System.out.println(fixPack);
	}

}
