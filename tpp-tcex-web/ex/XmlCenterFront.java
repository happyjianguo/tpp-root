public class XmlCenterFront {
	
	static AtomicInteger fileNameSerialNo = new AtomicInteger(1);
	
	/**
	 * 用于报文日志输出之前置空文件内容
	 * @param xml
	 * @return
	 */
	public String deleteFileContent(String xml){
		JSONObject input = XML.toJSONObject(xml);
		JSONObject jo = parseInput(input);
		JSONObject body = jo.getJSONObject("message").optJSONObject("body");
		if (body != null) {
			JSONArray a = body.optJSONArray("FILE_LIST");
			if (a != null) {
				for (int i = 0; i < a.length(); i++) {
					JSONObject x = a.getJSONObject(i);
					x.put("DATA", "");//文件内容置空
				}
			}
		}
		
		return XML.Format(fromJson(jo));
	}

	public JSONObject toJson(Reader xml) {
		JSONObject input = XML.toJSONObject(xml);
		return parseInput(input);
	}
	public JSONObject toJson(String xml) {
		JSONObject input = XML.toJSONObject(xml);
		return parseInput(input);
	}
	
	
	public EXchange toExchange(String xml, String dir) throws IOException {
		JSONObject input = XML.toJSONObject(xml);
		JSONObject jo = parseInput(input);
		EXchange exg = EXFactory.newEXchange();
		

		JSONObject body = jo.getJSONObject("message").optJSONObject("body");
		if (body != null) {
			JSONArray a = body.optJSONArray("FILE_LIST");
			if (a != null) {
				for (int i = 0; i < a.length(); i++) {
					JSONObject x = a.getJSONObject(i);
					String fileName = x.getString("NAME");
					String fieldData = x.getString("DATA");
					fileName = TimeUtil.getTime() + "_" + fileNameSerialNo.getAndIncrement() + "_" + fileName;
					
					File file = new File(dir,fileName);
					if(file.exists())
						file.delete();
					else
						file.createNewFile();
					FileOutputStream outputStream = new FileOutputStream(file);
					try {
						FileFieldConv.field2File(fieldData, outputStream);
					} finally {
						outputStream.close();
					}
					//修改文件名，置空文件内容
					x.put("NAME", fileName);
					x.put("DATA", "");
//					exg.addFile(fileName);
				}
			}
		}
		exg.add(jo);
		return exg;
	}
	private JSONObject parseInput(JSONObject input) {
		JSONObject output = new JSONObject();
		JSONObject message = new JSONObject();
		JSONObject head = new JSONObject();
		JSONObject body = new JSONObject();
		output.put("message", message);
		message.put("head", head);
		message.put("body", body);
		JSONObject tag = null;
		Object obj = null;
		
		// head
		tag = null;
		try{
			tag = input.getJSONObject("message").getJSONObject("head");
		}catch(JSONException e){
			// not do
		}
		if (tag != null){
			obj = tag.opt("field");
			if (obj != null){
				parseFieldObject(obj, head);
			}
		}
		
		// body field
		tag = null;
		try{
			tag = input.getJSONObject("message").getJSONObject("body");
		}catch(JSONException e){
			// not do
		}
		if (tag != null){
			obj = tag.opt("field");
			if (obj != null){
				parseFieldObject(obj, body);
			}
		}
		
		// body field-list
		tag = null;
		try{
			tag = input.getJSONObject("message").getJSONObject("body");
		}catch(JSONException e){
			// not do
		}
		if (tag != null){
			obj = tag.opt("field-list");
			if (obj != null){
				parseFieldListObject(obj, body);
			}
		}
		
		return output;
	}

	// <field name="FIELD_1">00</field>
	private void parseField(JSONObject o, JSONObject parent) {
		String name = o.getAttributes().getString("name");
		String value = o.getContent();
		parent.put(name, value);
	}

	// 多行
	// <field name="FIELD_1">00</field>
	// <field name="FIELD_1">00</field>
	private void parseFieldObject(Object obj, JSONObject parent) {
		if (obj == null)
			return;
		else if (obj instanceof JSONArray) {
			JSONArray a = (JSONArray) obj;
			for (int i = 0; i < a.length(); i++) {
				parseField(a.getJSONObject(i), parent);
			}
		} else if (obj instanceof JSONObject) {
			parseField((JSONObject) obj, parent);
		}
	}

	// <field-list name="SUMMARY1">
	private void parseFieldList(JSONObject o, JSONObject parent) {
		// 记录
		JSONArray array = new JSONArray();
		parent.put(o.getAttributes().getString("name"), array);

		Object obj = o.get("field-list");
		// <field-list name="0">
		if (obj == null)
			return;
		else if (obj instanceof JSONArray) {
			JSONArray a = (JSONArray) obj;
			for (int i = 0; i < a.length(); i++) {
				JSONObject x = new JSONObject();
				array.put(x);
				parseFieldObject(a.getJSONObject(i).get("field"), x);
			}
		} else if (obj instanceof JSONObject) {
			JSONObject x = new JSONObject();
			array.put(x);
			parseFieldObject(((JSONObject) obj).get("field"), x);
		}
	}

	// 多行
	// <field-list name="SUMMARY1">
	// <field-list name="SUMMARY2">
	private void parseFieldListObject(Object obj, JSONObject parent) {
		if (obj == null)
			return;
		else if (obj instanceof JSONArray) {
			JSONArray a = (JSONArray) obj;
			for (int i = 0; i < a.length(); i++) {
				parseFieldList(a.getJSONObject(i), parent);
			}
		} else if (obj instanceof JSONObject) {
			parseFieldList((JSONObject) obj, parent);
		}	
	}

	// PUT //////////////////////////////
	public JSONObject fromJson(JSONObject o) {
		JSONObject parent = new JSONObject();
		putObject(o, parent);
		return parent;
	}
	public JSONObject fromExchange(EXchange exg, String dir) throws IOException {
		JSONObject json = exg.getObject();
		JSONObject body = json.getJSONObject("message").optJSONObject("body");
		if (body != null) {
			JSONArray a = body.optJSONArray("FILE_LIST");
			if (a != null) {
				for (int i = 0; i < a.length(); i++) {
					JSONObject x = a.getJSONObject(i);
					String fileName = x.getString("NAME");
//					String fieldData = x.getString("DATA");
					
					File file = new File(dir,fileName);
					if(file.exists()){
						FileInputStream inputStream = new FileInputStream(file);
						try {
							String fileData = FileFieldConv.file2Field(inputStream);
							x.put("DATA",fileData);
						} finally {
							inputStream.close();
						}
					}
					//文件名需要改回原来的值
					fileName = fileName.replaceFirst("_", "");
					fileName = fileName.substring(fileName.indexOf("_")+1, fileName.length());
					x.put("NAME", fileName);
//					exg.addFile(fileName);
				}
			}
		}
		
		
//		// 加入文件内容
//		JSONArray a = new JSONArray();
//		for (int i=0; exg.getFileList() != null && i<exg.getFileList().size(); i++) {
//			String fileName = exg.getFileList().get(i);
//			JSONObject x = new JSONObject();
//			x.put("NAME",fileName);
//			
//			File file = new File(dir, fileName);
//			if(file.exists()){
//				FileInputStream inputStream = new FileInputStream(file);
//				try {
//					String fileData = FileFieldConv.file2Field(inputStream);
//					x.put("DATA",fileData);
//				} finally {
//					inputStream.close();
//				}
//			}else{
//				x.put("DATA", "");
//			}
//			a.put(x);
//		}
		
//		JSONObject jo = exg.getObject();
//		JSONObject body = jo.getJSONObject("message").optJSONObject("body");
//		if (a.length() > 0) {
//			if (body == null) {
//				body = new JSONObject();
//				jo.getJSONObject("message").put("body", body);
//			}
//			body.put("FILE_LIST", a);
//		}
		
		return fromJson(json);
	}


	private void putValue(String key, Object value, JSONObject parent) {
		if (value instanceof JSONArray) {
			putArray(key, (JSONArray) value, parent);
		} else if (value instanceof JSONObject) {
			JSONObject x = new JSONObject();
			parent.put(key, x);
			putObject((JSONObject) value, x);
		} else {
			putField(key, value.toString(), parent);
		}

	}

	private void putArray(String key, JSONArray a, JSONObject parent) {
		JSONObject entry = putFieldListEntry(key, parent);
		for (int i = 0; i < a.length(); i++) {
			JSONObject record = putFieldListEntry(Integer.toString(i), entry);
			JSONObject value = a.getJSONObject(i);
			putObject(value, record);
		}
	}

	private JSONObject putFieldListEntry(String name, JSONObject parent) {
		JSONObject x = new JSONObject();
		Attributes attr = new Attributes();
		attr.put("name", name);
		x.setAttributes(attr);
		parent.accumulate("field-list", x);
		return x;
	}

	private JSONObject putField(String name, String value, JSONObject parent) {
		JSONObject x = new JSONObject();
		Attributes attr = new Attributes();
		attr.put("name", name);
		x.setAttributes(attr);
		x.accContent(value);
		parent.accumulate("field", x);
		return x;
	}

	@SuppressWarnings("unchecked")
	private void putObject(JSONObject jo, JSONObject parent) {
		Iterator keys = jo.keys();

		while (keys.hasNext()) {
			String key = keys.next().toString();
			Object value = jo.get(key);
			putValue(key, value, parent);
		}
	}

	/*public static void main(String args[]) throws FileNotFoundException {

		XmlCenterFront conv = new XmlCenterFront();
		JSONObject x = conv.toJson(new FileReader("E:/dev/Workspace/home/loadtest/test.xml"));
		System.out.println(x);
		JSONObject y = conv.fromJson(x);
		String xml = XML.Format(y);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" + xml;
		System.out.println(xml);		
	}*/
}
