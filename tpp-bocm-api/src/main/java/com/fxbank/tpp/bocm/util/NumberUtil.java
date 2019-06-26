package com.fxbank.tpp.bocm.util;

import java.math.BigDecimal;

/** 
* @ClassName: NumberUtil 
* @Description: 交易金额补零转换通用类
* @author YePuLiang
* @date 2019年6月26日 上午10:46:17 
*  
*/
public class NumberUtil {
	/**
	* @Title: toPack 
	* @Description: 转交行报文需要在当前金额补零
	* @param @param d
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws
	 */
	public static double addPoint(double d){	
		BigDecimal b = new BigDecimal(Double.toString(d));
	    BigDecimal c = new BigDecimal(Double.toString(100d));    
	    return b.multiply(c).intValue();
	}
	
	/**
	* @Title: toPack 
	* @Description: 交行报文金额转换本行需要去00
	* @param @param d
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws
	 */
	public static double removePoint(double d){	
	    return d/100;
	}
}
