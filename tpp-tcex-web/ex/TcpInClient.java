package com.fxbank.cap.paf.ex;

//1.1. 签到请求代码示例
public class TcpInClient {	
	
	private static XmlCenterFront exchange = new XmlCenterFront();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		// 签到请求
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<message>");
		sb.append("<head>");
		sb.append("<field name=\"SendDate\">2012/05/06</field>");
		sb.append("<field name=\"SendTime\">11:39</field>");
		sb.append("<field name=\"SendSeqNo\">12341234</field>");
		sb.append("<field name=\"TxUnitNo\">01</field>");
		sb.append("<field name=\"SendNode\">C51140</field>");
		sb.append("<field name=\"TxCode\">BDC001</field>");
		sb.append("<field name=\"ReceiveNode\">105000</field>");
		sb.append("<field name=\"CustNo\">001</field>");
		sb.append("<field name=\"OperNo\">001</field>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("</body>");
		sb.append("</message>");
		
		
		//初始化请求参数
		RefbdcTcpClient cc =  new RefbdcTcpClient("127.0.0.1",9999,"C51140", 60000);
		
		//签到
		byte[] ret = cc.login(sb.toString().getBytes());
		String response = new String(ret);
		System.out.println("tcp client login return msg:" + new String(response));
		
		String key = getSessionKey(response);
//		String key = "44d41b233bc3bd6d4f848a3df45c303eb371676a94692412841888f1f7c6e947bb42d724e2d0ec42df38c3a5579dc5410594b91eae71e799877cd327374da87f26d29b9cc01b721dd1f02a00fd3eb95679a9eaeb88cd13420445b6a997249b0bd22f8fe22381d93b3c022fa193a5a321ceedd73f08f408fcd080af55fd424659";
				
		byte[] key2 = RefbdcUtil.decryptSessionKey("Z:\\home\\key\\C51140", key);
		
		// 其他交易
		String sendMsg = LoadTest.loadRequest("BDC103");
		
		//	动态修改流水号
//		String SendSeqNo = TimeUtil.currentMillitm() + new Random().
//		sendMsg.replaceAll("$SendSeqNo", );
		String[] files = new String[1];
		files[0] = "Z:\\BDC_BAL_NTFICBC20140804155545001.act";
		sendMsg = addFile(sendMsg,files);
		byte[] request = RefbdcUtil.encryptMsg(sendMsg.getBytes(), key2);
		byte[] ret2 = cc.invokeFront(request);
		byte[] response2 = RefbdcUtil.decryptMsg(ret2, key2);
		
		System.out.println("tcp client receive msg:" + new String(response2));
	}
	
	private static String getSessionKey(String xmlMsg) throws DocumentException{
		SAXReader reader = new SAXReader();
		ByteArrayInputStream bais = new ByteArrayInputStream(xmlMsg.getBytes());
		
		Document doc = reader.read(bais);
		
		Element root = doc.getRootElement();
		Element body = root.element("body");
		Element field = body.element("field");
//		System.out.println(field.getName());
//		System.out.println(field.getText());
		String key = field.getText();
		return key;
	}
	
	private static String addFile(String msg,String[] files) throws IOException{
		if(msg == null || msg.equals("")){
			return "";
		}
		
		JSONObject son = exchange.toJson(msg);
		
		
		if(files != null && files.length > 0){
			for(int i=0;i<files.length;i++){
				File file = new File(files[i]);
				if(file.exists()){
					String fileName = file.getName();
					FileInputStream fis = new FileInputStream(file);
//					byte[] fileContentBytes = new byte[(int)file.length()];
//					fis.read(fileContentBytes);
//					String fileContent = new String(fileContentBytes);
					JSONObject o = new JSONObject();
					o.put("NAME", fileName);
					String tmp = FileFieldConv.file2Field(fis);
					o.put("DATA", tmp);
					ByteArrayOutputStream bo = new  ByteArrayOutputStream();
					FileFieldConv.field2File(tmp, bo);
					String tmp2 = new String(bo.toByteArray());
					son.getJSONObject("message").getJSONObject("body").append("FILE_LIST", o);
					fis.close();
				}
			}
			
		}
//		System.out.println("son:\n" + son);
		StringBuffer request = new StringBuffer();
		request.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
		request.append(XML.Format(exchange.fromJson(son)));
		
		
		System.out.println("添加文件内容之后的请求报文:" + request.toString());
		return request.toString();
	}

}
