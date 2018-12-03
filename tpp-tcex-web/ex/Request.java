package com.fxbank.cap.paf.ex;

public class Request {
	public byte[] login(byte[] msg) throws Exception{
		if(msg == null)
			throw new Exception("签到交易报文为空");
		
		// 发送签到交易
		InetSocketAddress addr = new InetSocketAddress(serverIp, serverPort);
		SocketChannel sc = null;
		TcpSocketIO tcpIO;
		try {
			sc = SocketChannel.open();
			sc.connect(addr);
			sc.socket().setSoTimeout(timeout);
			
			tcpIO = new TcpSocketIO(sc);
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("网络连接出错." + e.getMessage());
		}
		
		//请求
		
		// header
		byte[] header = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		byte[] nodeNoByte = (nodeNo + "1").getBytes();
		System.arraycopy(nodeNoByte, 0, header, 0, nodeNoByte.length);
		ByteBuffer headerBuf = ByteBuffer.wrap(header);
		// length
		byte[] buf = new byte[]{0,0,0,0};
		ByteArrayUtil.DATA2NET(msg.length, buf);
		ByteBuffer lenbuf = ByteBuffer.wrap(buf);
		// msg
		ByteBuffer requestBuffer = ByteBuffer.wrap(msg);
		try {
			sc.write(headerBuf);
			sc.write(lenbuf);
			sc.write(requestBuffer);
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("发送请求出错." + e.getMessage());
		}
		
		
		//响应
		byte[] responseMsg = null;
		headerBuf.clear();
		lenbuf.clear();
		try {
			// header
//			sc.read(headerBuf);
			headerBuf = ByteBuffer.wrap(tcpIO.read(0,16));
			// length
//			sc.read(lenbuf);
			lenbuf = ByteBuffer.wrap(tcpIO.read(0,4));
			int contentLength = (int) ByteArrayUtil.NET2DATA(lenbuf.array());
			// msg
//			ByteBuffer responseBuffer = ByteBuffer.allocate(contentLength);
//			sc.read(responseBuffer);
			ByteBuffer responseBuffer = ByteBuffer.wrap(tcpIO.read(0, contentLength));
			responseMsg = responseBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("接收响应出错." + e.getLocalizedMessage());
		}
		
		close(sc);
		
		return responseMsg;//明文
	}

public byte[] invokeFront(byte[] msg) throws Exception{
		if(msg == null)
			throw new Exception("发送报文为空.");
		
		InetSocketAddress addr = new InetSocketAddress(serverIp, serverPort);
		SocketChannel sc = null;
		TcpSocketIO tcpIO;
		try {
			sc = SocketChannel.open();
			sc.connect(addr);
			sc.socket().setSoTimeout(timeout);
			
			tcpIO = new TcpSocketIO(sc);
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("网络连接出错." + e.getMessage());
		}
		
		//请求
		// header
		byte[] header = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		byte[] nodeNoByte = (nodeNo + "0").getBytes();
		System.arraycopy(nodeNoByte, 0, header, 0, nodeNoByte.length);
		ByteBuffer headerBuf = ByteBuffer.wrap(header);
		// length
		byte[] buf = new byte[]{0,0,0,0};
		ByteArrayUtil.DATA2NET(msg.length, buf);
		ByteBuffer lenbuf = ByteBuffer.wrap(buf);
		// msg
		ByteBuffer requestBuffer = ByteBuffer.wrap(msg);
		try {
			sc.write(headerBuf);
			sc.write(lenbuf);
			sc.write(requestBuffer);
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("发送请求出错." + e.getMessage());
		}
		
		
		//响应
		byte[] responseMsg = null;
		headerBuf.clear();
		lenbuf.clear();
		try {
//			sc.read(headerBuf);
			headerBuf = ByteBuffer.wrap(tcpIO.read(0,16));
//			sc.read(lenbuf);
			lenbuf = ByteBuffer.wrap(tcpIO.read(0,4));
			int contentLength = (int) ByteArrayUtil.NET2DATA(lenbuf.array());
			
//			ByteBuffer responseBuffer = ByteBuffer.allocate(contentLength);
			ByteBuffer responseBuffer = ByteBuffer.wrap(tcpIO.read(0, contentLength));
			sc.read(responseBuffer);
			responseMsg = responseBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			close(sc);
			throw new Exception("接收响应出错." + e.getLocalizedMessage());
		}
		
		close(sc);
		
		return responseMsg;//密文
	}

private void close(SocketChannel sc) throws Exception{
		if(sc != null)
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("关闭连接出错." + e.getMessage());
			}
	}

}
