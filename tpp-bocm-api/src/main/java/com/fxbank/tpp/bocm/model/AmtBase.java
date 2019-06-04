/**   
* @Title: AmtBase.java 
* @Package com.fxbank.tpp.bocm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月28日 上午11:33:06 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.model;

import java.math.BigDecimal;

/** 
* @ClassName: AmtBase 
* @Description: 处理Double金额换算
* @author YePuLiang
* @date 2019年5月28日 上午11:33:06 
*  
*/
public class AmtBase {
	
	public static double toPack(double d){	
		BigDecimal b = new BigDecimal(Double.toString(d));
	    BigDecimal c = new BigDecimal(Double.toString(100d));    
	    return b.multiply(c).intValue();
	}

}
