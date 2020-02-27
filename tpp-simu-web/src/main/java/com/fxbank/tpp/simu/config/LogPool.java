package com.fxbank.tpp.simu.config;

import com.fxbank.cip.base.log.MyLog;
import org.springframework.stereotype.Component;

/** 
* @ClassName: LogPool 
* @Description: 交易日志池
* @author ZhouYongwei
* @date 2018年5月9日 下午3:12:40 
*  
*/
@Component
public class LogPool {
	private ThreadLocal<MyLog> threadLocal = new ThreadLocal<MyLog>();

	public void init(MyLog logUtil){
		threadLocal.set(logUtil);
	}
	
	public MyLog get(){
		return threadLocal.get();
	}
	
	public void remove(){
		threadLocal.remove();
	}
}
