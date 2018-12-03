package com.fxbank.cap.paf.utils;

public class StringUtils {
	
	public static String trimEspChar(String string,byte espChar) {
        if (string != null) {
            string = string.trim();
            byte[] esp = new byte[1];
            esp[0] = espChar;
            String s = new String(esp);
            string = string.replace(s, "");
        }
        return string;
    }
	
	public static String fillStringAppend(String string,Integer size,String appender){
		StringBuffer sb = new StringBuffer(string);
		 for(int i=sb.length();i<size;i++){
			 sb.append(appender);
		 }
		 return sb.toString();
	}
	
}
