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
	
	/**
	* @Title: toPack 
	* @Description: 转交行报文需要在当前金额补零
	* @param @param d
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws
	 */
	public static double toPack(double d){	
		BigDecimal b = new BigDecimal(Double.toString(d));
	    BigDecimal c = new BigDecimal(Double.toString(100d));    
	    return b.multiply(c).intValue();
	}

}
