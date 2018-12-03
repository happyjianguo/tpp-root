package com.fxbank.cap.paf.ex;

public class FileFieldConv {

	/**
	 * 将文件内容转换为前置通讯报文域
	 * @param inputStream 输入流，例如待处理的文件流。inputStream 在函数外部负责打开和关闭，本函数只负责向流里读
	 * @return 字符串
	 * @throws IOException
	 */
	public static String file2Field(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BCDOutputStream bcdOutputStream = new BCDOutputStream(
				byteArrayOutputStream);
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(
				bcdOutputStream);
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = inputStream.read(buf);
			if (n == -1) {
				deflaterOutputStream.close();
				break;
			}
			deflaterOutputStream.write(buf, 0, n);
		}
		return byteArrayOutputStream.toString();
	}

	/**
	 * 将前置通讯报文域转换为文件内容
	 * @param fieldData 通讯报文域
	 * @param outputStream 输出流，例如输出文件流。 outputStream 在函数外部负责打开和关闭，本函数只负责向流里写
	 * @throws IOException
	 */
	public static void field2File(String fieldData, OutputStream outputStream) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes());
		BCDInputStream bcdInputStream = new BCDInputStream(byteArrayInputStream);
		InflaterInputStream inflaterInputStream = new InflaterInputStream(bcdInputStream);
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = inflaterInputStream.read(buf);
			if (n == -1) {
				inflaterInputStream.close();
				break;
			}
			outputStream.write(buf, 0, n);
		}
	}
	
	/**
	 * 文件内容从ASC转换为BCD
	 * @param fieldData
	 * @return
	 * @throws IOException
	 */
	public static String fieldASCtoBCD(String fieldData) throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BCDOutputStream bcdOutputStream = new BCDOutputStream(
				byteArrayOutputStream);
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(
				bcdOutputStream);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes());
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = byteArrayInputStream.read(buf);
			if (n == -1) {
				deflaterOutputStream.close();
				break;
			}
			deflaterOutputStream.write(buf, 0, n);
		}
		
		byteArrayInputStream.close();
		
		return byteArrayOutputStream.toString();
		
	}
	
	/**
	 * 文件内容从ASC转换为BCD后压缩,读取ASC时采用srcEncode编码
	 * @param fieldData
	 * @param srcEncode
	 * @return
	 * @throws IOException
	 */
	public static String fieldASCtoBCD(String fieldData,String srcEncode) throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BCDOutputStream bcdOutputStream = new BCDOutputStream(
				byteArrayOutputStream);
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(
				bcdOutputStream);
		
		ByteArrayInputStream byteArrayInputStream = null;
		if(srcEncode == null || srcEncode.equals("")){
			 byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes());
		}else{
			 byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes(srcEncode));
		}
		
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = byteArrayInputStream.read(buf);
			if (n == -1) {
				deflaterOutputStream.close();
				break;
			}
			deflaterOutputStream.write(buf, 0, n);
		}
		
		byteArrayInputStream.close();
		
		return byteArrayOutputStream.toString();
		
	}
	
	/**
	 * 文件内容从BCD转换为ASC
	 * @param fieldData
	 * @return
	 * @throws IOException
	 */
	public static String fieldBCDtoASC(String fieldData) throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(fieldData.getBytes().length);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes());
		BCDInputStream bcdInputStream = new BCDInputStream(byteArrayInputStream);
		InflaterInputStream inflaterInputStream = new InflaterInputStream(bcdInputStream);
		
		
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = inflaterInputStream.read(buf);
			if (n == -1) {
				inflaterInputStream.close();
				break;
			}
			byteArrayOutputStream.write(buf, 0, n);
		}
		
		return byteArrayOutputStream.toString();
		
	}
	
	/**
	 * 文件内容解压缩后从BCD转换为ASC,解码为ASC时采用srcEncode编码
	 * @param fieldData
	 * @param srcEncode
	 * @return
	 * @throws IOException
	 */
	public static String fieldBCDtoASC(String fieldData,String srcEncode) throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(fieldData.getBytes().length);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fieldData.getBytes());
		BCDInputStream bcdInputStream = new BCDInputStream(byteArrayInputStream);
		InflaterInputStream inflaterInputStream = new InflaterInputStream(bcdInputStream);
		
		
		byte buf[] = new byte[1024];
		int n;
		while (true) {
			n = inflaterInputStream.read(buf);
			if (n == -1) {
				inflaterInputStream.close();
				break;
			}
			byteArrayOutputStream.write(buf, 0, n);
		}
		
		if(srcEncode == null || srcEncode.equals("")){
			return byteArrayOutputStream.toString();
		}else{
			return byteArrayOutputStream.toString(srcEncode);
		}
	}
	
	private static byte[] addCapacity(byte rece[]) {
		byte temp[] = new byte[rece.length + 1024];
		System.arraycopy(rece, 0, temp, 0, rece.length);
		return temp;
	}

	public static void main(String args[]) throws IOException{

//		FileInputStream inputStream =  new FileInputStream("e:\\filetest\\a.txt");
//		String s = null;
//		try {
//			s = file2Field(inputStream);
//		} finally {
//			inputStream.close();
//		}
//
//		System.out.println(s);
//		
//		FileOutputStream outputStream = new FileOutputStream("e:\\filetest\\b.txt");
//		try{
//			field2File(s, outputStream);
//		} finally{
//			outputStream.close();
//		}
		
		String a  = "bank|center|1111|2222|3333|\nbank|center|aaaa|bbbb|cccc|\n";
//		System.out.println(file2Field(new FileInputStream(new java.io.File("F:\\bdcfiletest\\a.txt"))));
		System.out.println(fieldASCtoBCD(a));
//		System.out.println(fieldBCDtoASC(a));
	}

	// final static int MAX_LEN = 64*1024; //在此处定义文件转报文域的限制大小
	//        
	// /**
	// * 将文件内容转换为前置通讯报文域
	// *
	// * @param fileData 文件内容
	// * @return 通讯报文域
	// * @throws Exception
	// */
	// public static String file2Field(byte[] fileData) throws Exception{
	// Deflater compresser = new Deflater();
	// compresser.setInput(fileData);
	// compresser.finish();
	// byte[] outBuf = new byte[1024];
	// ByteArrayOutputStream bs = new ByteArrayOutputStream();
	// int totalLen = 0;
	// int compressLen = 0;
	// while(!compresser.finished()) {
	// compressLen = compresser.deflate(outBuf);
	// bs.write(outBuf, 0, compressLen);
	// totalLen += compressLen;
	// }
	// String fieldData = ASC2BCD(totalLen,bs.toByteArray());
	//            
	// if(fieldData.length() > MAX_LEN)
	// throw new Exception("编码压缩后的文件内容大于限制的长度，请检查文件的大小");
	// return fieldData;
	// }
	//        
	// /**
	// * 将前置通讯报文域转换为文件内容
	// *
	// * @param fieldData 通讯报文域
	// * @return 文件内容
	// * @throws Exception
	// */
	// public static byte[] field2File(String fieldData) throws Exception{
	// byte[] inBuf = BCD2ASC(fieldData);
	//            
	// Inflater decompresser = new Inflater();
	// decompresser.setInput(inBuf);
	//            
	// byte[] outBuf = new byte[1024];
	// ByteArrayOutputStream bs = new ByteArrayOutputStream();
	// int decompressLen = 0;
	// int totalLen = 0;
	// while(!decompresser.finished()) {
	// decompressLen = decompresser.inflate(outBuf);
	// bs.write(outBuf, 0, decompressLen);
	// totalLen += decompressLen;
	// }
	// return bs.toByteArray();
	// }
	//        
	// /**
	// * 将ASCII字符串转换成为BCD码格式
	// *
	// * @param in_lLen
	// * 字符串长度
	// * @param in_pusSrc
	// * 待转换的字符串
	// * @return 返回转换处理后的BCD码结果串
	// */
	// private static String ASC2BCD(long in_lLen, byte[] in_pusSrc) {
	// if (in_pusSrc == null)
	// return "";
	//
	// ByteArrayOutputStream bs = new ByteArrayOutputStream((int)in_lLen * 2);
	//
	// byte ch;
	//
	// for (int i = 0; i < in_lLen; i++) {
	// ch = (byte) (in_pusSrc[i] & 240);
	// ch = (byte) (ch >> 4);
	// ch = (byte)(ch & 0x0F);
	// if (9 >= ch)
	// bs.write( (byte) (48 | ch) );
	// else
	// bs.write( (byte) (64 | (ch - 9)) );
	//
	// ch = (byte) (in_pusSrc[i] & 15);
	// if (9 >= ch)
	// bs.write( (byte) (48 | ch) );
	// else
	// bs.write( (byte) (64 | (ch - 9)) );
	// }
	// return new String(bs.toByteArray());
	// }
	//        
	// /**
	// * 将BCD码转换成为ASC码
	// *
	// * @param bcd
	// * 输入BCD码的字符串
	// * @return 返回转换处理后的ASC码结果串 NOT NULL
	// * @throws Exception
	// */
	// private static byte[] BCD2ASC(String pusSrc) throws Exception {
	// char[] bcd = pusSrc.toCharArray();
	// ByteArrayOutputStream bs = new ByteArrayOutputStream();
	//
	// int lLen = 0;
	// byte ch;
	// char in;
	//
	// lLen = bcd.length;
	// if (lLen % 2 != 0) {
	// throw new Exception("非法的BCD输入字符串，长度不是2的整数倍");
	// }
	//
	// for (int i = 0; i < lLen; i = i + 2) {
	// in = bcd[i];
	// if ('9' >= in && '0' <= in)
	// ch = (byte) (in & 15);
	// else if (('f' >= in && 'a' <= in) || ('F' >= in && 'A' <= in))
	// ch = (byte) ((in & 15) + 9);
	// else {
	// ch = 0;
	// }
	//
	// ch = (byte) (ch << 4);
	// if (i + 1 < lLen)
	// in = bcd[i + 1];
	// else
	// in = '0';
	//
	// if ('0' <= in && '9' >= in)
	// ch |= in & 15;
	// else if (('f' >= in && 'a' <= in) || ('F' >= in && 'A' <= in))
	// ch |= (in & 15) + 9;
	// else {
	// ch |= 0;
	// }
	// bs.write(ch);
	// }
	// return bs.toByteArray();
	// }
}

class BCDOutputStream extends FilterOutputStream {
	private static final char __hexCharsUC[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private final static int MAX_CHARS_PER_LINE = 32; // 分行，显示友好一点

	private int characters = MAX_CHARS_PER_LINE;

	public BCDOutputStream(OutputStream out) {
		super(out);
	}

	public void write(int c) throws IOException {
		if (characters == 0) {
			characters = MAX_CHARS_PER_LINE;
			super.write('\n');
		}
		characters--;

		super.write(__hexCharsUC[(c & 0xf0) >> 4]);
		super.write(__hexCharsUC[c & 0x0f]);
	}
}

class BCDInputStream extends InputStream {

	private InputStream in;

	public BCDInputStream(InputStream input) {
		super();
		in = input;
	}

	public int read() throws IOException {
		int b0 = readPart();
		if (b0 == -1)
			return -1;

		int b1 = readPart();
		if (b1 == -1)
			b1 = 0;

		int d = (b0 << 4 | b1) & 0x00FF;
		return d;
	}

	private int readPart() throws IOException {
		while (true) {
			int c = in.read();

			if (c == -1)
				return -1;

			// skip whitespace?
			if (c == ' ' || c == '\t' || c == '\f' || c == '\r' || c == '\n')
				continue;

			if ((c >= '0') && (c <= '9'))
				return c - '0';
			else if ((c >= 'A') && (c <= 'F'))
				return c - 'A' + 0x0A;
			else if ((c >= 'a') && (c <= 'f'))
				return c - 'a' + 0x0A;
			else
				throw new NumberFormatException("Invalid hex digit '" + c
						+ "' in BCDInputStream .");
		}
	}
}

