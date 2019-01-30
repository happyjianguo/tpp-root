package com.fxbank.cip.manager.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cip.base.log.MyLog;

public class DivideUtil {
	private static Logger logger = LoggerFactory.getLogger(DivideUtil.class);

	private int cnt;			//待分配总数量
	private int slot;		//总线程数量
	private List<Element> elements = new ArrayList<Element>();
	private MyLog myLog;

	public DivideUtil(MyLog myLog,int cnt, int slot) {
		this.myLog = myLog;
		this.cnt = cnt;
		this.slot = slot;
	}
	
	public List<Element> divide(){
		int step = (cnt%slot==0)?cnt/slot:cnt/slot+1 ;
		myLog.info(logger,"待分配总数量["+cnt+"]");
		myLog.info(logger,"总线程数量["+slot+"]");
		int n=1;
		for(int beg=0,end=0;end<cnt;beg=end){
			end = (beg+step)>cnt?cnt:(beg+step);
			myLog.info(logger,"第["+n+"]组范围["+beg+","+end+"]");
			Element e = new Element(beg,end);
			elements.add(e);
			n++;
		}
		return elements;
	}
	
	public List<Element> getElements() {
		return elements;
	}

	public class Element {
		private int beg;
		private int end;
		
		public Element(int beg,int end){
			this.beg = beg;
			this.end = end;
		}

		public int getBeg() {
			return beg;
		}

		public void setBeg(int beg) {
			this.beg = beg;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

	}
}
