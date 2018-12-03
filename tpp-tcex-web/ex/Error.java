package com.fxbank.cap.paf.ex;

public class Error {
	private InputStream is ;
//	private OutputStream os;
	
	public TcpSocketIO(SocketChannel sc) throws IOException{
			is = sc.socket().getInputStream();
//			os = sc.socket().getOutputStream();
	}
	
	protected byte[] read(int offset ,int length) throws IOException{
		try {
			if (length == 0)
				return null;
			byte[] buf = new byte[length];
			int pos = 0;
			int n;
			while (pos < length) {
				n = is.read(buf, pos + offset, length - pos);
				if (n < 0)
					throw new IOException("读 ByteArray 错: 读到流末尾");
				pos += n;
			}
			return buf;
		}catch(SocketTimeoutException e){
			throw new IOException("读ByteArray超时." + e.getMessage(),e);
		}catch(IOException e) {
			throw new IOException("读 ByteArray 错", e);
		}
		
	}

}
