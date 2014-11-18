package com.magicstone.stone.tools.msg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.util.ArrayListWrapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.util.FileUtil;
import com.hifun.soul.core.util.JsScriptHelper;
import com.hifun.soul.core.util.StringUtils;
import com.hifun.soul.tools.msg.ConstantObject;
import com.hifun.soul.tools.msg.FieldObject;
import com.hifun.soul.tools.msg.MessageObject;

/**
 * 消息代码生成器
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class LuaMessageGenerator {
	private static final Logger logger = Logger
			.getLogger(LuaMessageGenerator.class);

	public static final String TYPE_BYTE = "Byte";
	public static final String TYPE_SHORT = "Short";
	public static final String TYPE_INT = "Integer";
	public static final String TYPE_LONG = "Long";
	public static final String TYPE_FLOAT = "Float";
	public static final String TYPE_DOUBLE = "Double";
	public static final String TYPE_STRING = "String";
	public static final String TYPE_BOOLEAN = "Boolean";

	private static final String[] sysTypes = { TYPE_BYTE, TYPE_SHORT, TYPE_INT,
			TYPE_LONG, TYPE_FLOAT, TYPE_DOUBLE, TYPE_STRING, TYPE_BOOLEAN };

	/** 服务器端模板列表 */
	private static final String[] serverMsgTemplates = { "cg_message_g.vm", "gc_message_g.vm"};

	/** 客户端模板列表 */
	private static final String[] clientMsgTemplates = { "lua_cg_message_c.vm",	"lua_gc_message_c.vm" };


	/** 消息号与消息类映射模板 */
	private static final String clientModelTemplate = "lua_client_model.vm";

	/** 生成文件存放的路径列表 */
	private static final String clientRootPath = "..\\tools\\target\\client\\";// 暂时生成在当前目录
	private static final String gameRootPath = "..\\tools\\target\\server\\";
	
	
	private static final boolean replaceDirectly = false;
	private static final String dataPath = "..\\..\\client_ios\\soul_client_ipad\\src\\com\\hifun\\soul\\game\\net\\datas\\";
	private static final String messagePath = "..\\..\\client_ios\\soul_client_ipad\\src\\com\\hifun\\soul\\game\\net\\messages\\";
	private static final String messageTypePath = "..\\..\\client_ios\\soul_client_ipad\\src\\com\\hifun\\soul\\game\\net\\";

	private static Map<String, Element> macros;
	private static Map<String, MessageObject> msgs;
	private static Map<String, List<FieldObject>> fields;

	/** */
	public static final String MODEL_DIC = "msg/model/";
	/** */
	public static final String TEMPLATE_DIC = "msg/template/lua/";

	public static final Namespace NAME_SPACE = Namespace
			.getNamespace("http://com.hifun.soul.message");
	
	public static final String SERVER_GAME = "game";// gameserver

	public LuaMessageGenerator() {
		macros = new HashMap<String, Element>();
	}

	public void createMessageFiles(String modelFileName) {
		try {
			System.out.println(modelFileName);
			msgs = new HashMap<String, MessageObject>();
			fields = new HashMap<String, List<FieldObject>>();
			String configFilePath = LuaGeneratorHelper.getBuildPath(MODEL_DIC
					+ modelFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			String module = root.getAttributeValue("module");// 所属模块
			List<Element> messages = root.getChildren("message", NAME_SPACE);// 消息体定义
			List<Element> constants = null;
			Element constantsElement = root.getChild("constants", NAME_SPACE);
			if (constantsElement != null) {
				constants = root.getChild("constants", NAME_SPACE)
						.getChildren();// 常量定义
			} else {
				constants = new ArrayList<Element>();
			}
			this.replaceMacros(messages);
			createServerFiles(messages, module);
			createClientFile(messages, module, constants);
		} catch (Exception e) {
			logger.error("", e);
		}
	}


	/**
	 * 生成服务器端文件，每个消息一个文件
	 * 
	 * @param messages
	 * @throws Exception
	 */
	private void createServerFiles(List<Element> messages, String module)
			throws Exception {
		for (Iterator<Element> i = messages.iterator(); i.hasNext();) {
			Element msgElement = (Element) i.next();
			MessageObject msgObj = new MessageObject();
			String msgType = msgElement.getAttributeValue("type");
			msgObj.setType(msgType);
			msgObj.setClassName(LuaGeneratorHelper
					.generateServerClassName(msgType));
			msgObj.setModule(module);
			msgObj.setComment(msgElement.getAttributeValue("comment"));
			msgObj.setHandleMethodName(LuaGeneratorHelper
					.generateHandleMethodName(msgType));
			if (msgElement.getAttributeValue("playerQueue") != null) {
				msgObj.setPlayerQueue(msgElement.getAttributeValue(
						"playerQueue").equals("true") ? true : false);
			}
			if (msgElement.getAttributeValue("friendQueue") != null) {
				msgObj.setFriendQueue(msgElement.getAttribute("friendQueue")
						.getValue().equals("true"));
			}
			if (msgElement.getAttributeValue("guildQueue") != null) {
				msgObj.setGuildQueue(msgElement.getAttribute("guildQueue")
						.getValue().equals("true"));
			}
			List<Element> fElements = msgElement.getChildren("field", NAME_SPACE);
			setMsgObjFields(msgObj, fElements, false, false);
			VelocityContext context = new VelocityContext();
			context.put("message", msgObj);
			context.put("list", msgObj.getFields());
			String templateFileName = "";
			String outputFile = "";
			for (int j = 0; j < serverMsgTemplates.length; j++) {
				String templateName = serverMsgTemplates[j];
				if (templateName.substring(0, 2).equalsIgnoreCase(
						msgType.substring(0, 2))) {
					templateFileName = templateName;
					char lastCharOfTempate = templateName.charAt(templateName
							.length() - 4);
					switch (lastCharOfTempate) {
					case 'g':// 放在GameServer
						String relativeDir = msgObj.getModule().replace(".", "\\");
						outputFile = gameRootPath + relativeDir + "\\"
								+ "msg\\" + msgObj.getClassName() + ".java";
						break;
					default:
						throw new RuntimeException("模板名称非法，" + templateName);
					}
					LuaGeneratorHelper.generate(context, templateFileName,
							outputFile);
				}
			}
			msgs.put(msgObj.getClassName(), msgObj);
		}
	}

	/**
	 * 生成客户端文件，每个模块一个文件
	 * 
	 * @param msgElements
	 * @throws Exception
	 */
	private void createClientFile(List<Element> msgElements, String module,
			List<Element> contantElements) throws Exception {
		module = module.substring(module.lastIndexOf(".")>0?module.lastIndexOf(".")+1:0);

		List<MessageObject> cgMsgs = new ArrayList<MessageObject>();
		List<MessageObject> gcMsgs = new ArrayList<MessageObject>();
		List<MessageObject> allClientMsgs = new ArrayList<MessageObject>();
		for (Iterator<Element> i = msgElements.iterator(); i.hasNext();) {
			Element mElement = (Element) i.next();
			MessageObject msgObj = new MessageObject();
			String msgType = mElement.getAttributeValue("type");
			fields.put(msgType, msgObj.getFields());
			msgObj.setType(msgType);
			msgObj.setClassName(LuaGeneratorHelper
					.generateServerClassName(msgType));
			msgObj.setModule(mElement.getAttributeValue("module"));
			msgObj.setComment(mElement.getAttributeValue("comment"));
			msgObj.setHandleMethodName(LuaGeneratorHelper
					.generateHandleMethodName(msgType));
			List<Element> fElements = mElement.getChildren("field", NAME_SPACE);
			this.setMsgObjFields(msgObj, fElements, true, false);
			if (msgType.substring(0, 2).equalsIgnoreCase("cg")) {
				cgMsgs.add(msgObj);
			} else if (msgType.substring(0, 2).equalsIgnoreCase("gc")) {
				gcMsgs.add(msgObj);
			} 
			if (msgType.contains("c") || msgType.contains("C")) {
				allClientMsgs.add(msgObj);
			}
		}

		this.createClientModel(allClientMsgs, module);

		List<ConstantObject> contants = new ArrayList<ConstantObject>();
		for (Iterator<Element> i = contantElements.iterator(); i.hasNext();) {
			Element constantElement = (Element) i.next();
			String constantName = constantElement.getAttributeValue("name");
			String constantDesc = constantElement.getValue();
			ConstantObject constantObj = new ConstantObject(constantName,
					constantDesc.replaceAll("\\n", "").replaceAll("\\r", "")
							.trim());
			contants.add(constantObj);
		}

		VelocityContext context = new VelocityContext();
		context.put("module", StringUtils.upperCaseFirstCharOnly(module));
		context.put("modulename", module);
		for (String templateFileName : clientMsgTemplates) {
			String outputFileNameSuffix = null;
			String templatePrefix = templateFileName.substring(0, 2);
			if (templatePrefix.equals("gc")) {
				if (gcMsgs.size() == 0) {
					continue;
				}
				context.put("msgs", gcMsgs);
				outputFileNameSuffix = "GCMessage.lua";
			} else if (templatePrefix.equals("cg")) {
				if (cgMsgs.size() == 0) {
					continue;
				}
				context.put("msgs", cgMsgs);
				outputFileNameSuffix = "CGMessage.lua";
			} 
			context.put("constants", contants);
			String outputFilePath; 
			if(replaceDirectly)
			{
				outputFilePath = messagePath
					+ StringUtils.upperCaseFirstCharOnly(module)
					+ outputFileNameSuffix;
			}
			else
			{
				outputFilePath = clientRootPath + "\\" + module + "\\message\\"
					+ StringUtils.upperCaseFirstCharOnly(module)
					+ outputFileNameSuffix;
			}
			LuaGeneratorHelper.generate(context, templateFileName, outputFilePath);
		}
	}

	/**
	 * 生成客户端数据模型
	 * 
	 * @param messages
	 */
	private void createClientModel(List<MessageObject> messages, String module) {
		Map<String, FieldObject> newTypeFields = new HashMap<String, FieldObject>();
		for (MessageObject msgObj : messages) {
			List<FieldObject> fields = msgObj.getFields();
			initNewTypeFields(newTypeFields,fields);
		}
		for (FieldObject fieldObject : newTypeFields.values()) {
			VelocityContext context = new VelocityContext();
			context.put("modulename", module);
			context.put("model", fieldObject);
			String outputFilePath; 
			if(replaceDirectly)
			{
				outputFilePath= dataPath
				+ fieldObject.getType() + ".lua";
			}
			else
			{
				outputFilePath= clientRootPath + "\\" + module + "\\data\\"
				+ fieldObject.getType() + ".lua";
			}
			try {
				LuaGeneratorHelper.generate(context, clientModelTemplate,
						outputFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化新字段类型
	 * 
	 * @param newTypeFields
	 * @param fields
	 */
	private void initNewTypeFields(Map<String, FieldObject> newTypeFields, List<FieldObject> fields) {
		if(fields == null
				|| fields.size() <= 0){
			return;
		}
		for (FieldObject fieldObject : fields) {
			if (fieldObject.getIsNewType()) {
				fieldObject.setType(fieldObject.getClientType());
				if (fieldObject.getClientType().contains(".")) {
					fieldObject.setType(LuaGeneratorHelper
							.getClientClassName(fieldObject.getType()));
					continue;
				}
				newTypeFields.put(fieldObject.getClientType(), fieldObject);
				
				// 嵌套查找新的字段类型
				initNewTypeFields(newTypeFields, fieldObject.getSubFields());
			}
		}
	}
	
	/**
	 * 设置消息对象的字段
	 * 
	 * @param msgObj
	 * @param msgElement
	 */
	private void setMsgObjFields(MessageObject msgObj, List<Element> fElements,
			boolean isClient, boolean isCppClient) {
		for (Iterator<Element> j = fElements.iterator(); j.hasNext();) {
			Element fElement = (Element) j.next();
			FieldObject field = new FieldObject();
			field.setType(fElement.getAttributeValue("type"));
			field.setClientType(fElement.getAttributeValue("clientType"));
			if (StringUtils.isEmpty(field.getClientType())) {
				field.setClientType(LuaGeneratorHelper.getClientClassName(field
						.getType())
						+ "Data");
			}
			field.setSmallName(fElement.getAttributeValue("name"));
			field.setComment(fElement.getAttributeValue("comment"));
			List<Element> subFieldElements = fElement.getChildren("field",
					NAME_SPACE);
			if (fElement.getAttributeValue("bytes") != null) {
				field.setBytes(fElement.getAttributeValue("bytes").equals(
						"true") ? true : false);
			}
			if (fElement.getAttributeValue("list") != null) {
				field
						.setList(fElement.getAttributeValue("list").equals(
								"true") ? true : false);
				msgObj.setHasListField(true);
			}

			boolean isSubMsg = "true".equalsIgnoreCase(fElement
					.getAttributeValue("subMsg"));
			field.setSubMsg(isSubMsg);
			if (isSubMsg) {
				field.setSubMsgType(LuaGeneratorHelper
						.generateServerClassName(field.getType()));
				msgObj.setListMsg(true);
				if (isCppClient) {
					field.setList(true);
					msgObj.setHasListField(true);
					field.setType("_LZOBEX_" + field.getType());
				}
			}

			// 如果不是系统定义的类型则说明是子消息
			if (!new ArrayListWrapper(sysTypes).contains(field.getType())) {
				if (!isCppClient || !field.isSubMsg()) {
					field.setIsNewType(true);
					if (!isClient && field.getType().indexOf("_") > 0) {
						field.setType(LuaGeneratorHelper
								.generateServerClassName(field.getType()));
					}
				}
			}

			if (subFieldElements.size() > 0) {
				this.setSubFields(field, subFieldElements);
			} else {
				if (isClient && field.getIsNewType()) {// 客户端生成消息的时候要为其补全
					field.setSubFields(fields.get(field.getType()));
				}
				if (!isClient && field.getList() && field.getIsNewType()) {
					String type = field.getType();
					field.setSubFields(msgs.get(type).getFields());
				}
			}
			if (field.isSubMsg() && !isCppClient) {
				msgObj.addSubMsg(field);
			} else {
				msgObj.addField(field);
			}
		}
	}

	/**
	 * @param field
	 * @param subFieldElements
	 */
	private void setSubFields(FieldObject field, List<Element> subFieldElements) {
		List<FieldObject> subFields = new ArrayList<FieldObject>();
		fields.put(field.getType(), subFields);
		for (Element subElement : subFieldElements) {
			FieldObject subField = new FieldObject();
			subField.setType(subElement.getAttributeValue("type"));
			subField.setClientType(subElement.getAttributeValue("clientType"));
			if (StringUtils.isEmpty(subField.getClientType())) {
				subField.setClientType(LuaGeneratorHelper
						.getClientClassName(subField.getType())
						+ "Data");
			}
			subField.setSmallName(subElement.getAttributeValue("name"));
			subField.setComment(subElement.getAttributeValue("comment"));
			if (subElement.getAttributeValue("list") != null) {
				subField.setList(subElement.getAttributeValue("list").equals(
						"true") ? true : false);
				field.setHasListField(true);
			}
			subFields.add(subField);
			List<Element> subsubFieldElements = subElement.getChildren("field",
					NAME_SPACE);
			if (subsubFieldElements.size() > 0) {
				this.setSubFields(subField, subsubFieldElements);
			}

			// 如果不是系统定义的类型则说明是子消息
			if (!new ArrayListWrapper(sysTypes).contains(subField.getType())) {
				subField.setIsNewType(true);
				if (subField.getType().indexOf("_") > 0) {
					subField.setType(LuaGeneratorHelper
							.generateServerClassName(subField.getType()));
				}
			}
		}
		field.setSubFields(subFields);
		// 如果配置了子节点，而且类型中不存在包名，则说明是新定义的类型
		if (field.getType().indexOf(".") == -1) {
			field.setNeedCreateType(true);
		}
	}


	/**
	 * 生成消息类型头文件
	 */
	private void createClientMessageType() {
		StringBuilder builder = new StringBuilder();
		builder.append("package com.hifun.soul.game.net{\r\n");
		builder.append("\tpublic class MessageType{\r\n");
		Class<?> mtClazz = MessageType.class;
		Field[] fields = mtClazz.getDeclaredFields();
		Field.setAccessible(fields, true);
		OutputStream out = null;
		try {
			Set<Short> msgNumSet = new HashSet<Short>();
			for (int i = 0; i < fields.length; i++) {
				String fName = fields[i].getName();
				if (fName.length() <= 3)
					continue;
				String prefix = fName.substring(0, 3);
				if ((prefix.equals("CG_") || prefix.equals("GC_"))
						&& ((fields[i].getModifiers() & Modifier.STATIC) != 0)
						& ((fields[i].getModifiers() & Modifier.FINAL) != 0)) {
					short messageNumber = fields[i].getShort(null);
					if (messageNumber <= 0) {
						throw new RuntimeException("消息号溢出！！");
					} else if (msgNumSet.contains(messageNumber)) {
						throw new RuntimeException(String.format("%s消息号与其他消息号冲突", fName));
					}
					builder.append("\t\tpublic static const ").append(fName)
							.append(":int=").append(messageNumber)
							.append(";\r\n");
					msgNumSet.add(messageNumber);

				}

			}
			builder.append("\t}\r\n");
			builder.append("}\r\n");
			String outPath;
			if(replaceDirectly)
			{
				outPath = messageTypePath+ "MessageType.lua";
			}
			else
			{
				outPath = clientRootPath+ "MessageType.lua";
			}
			out = new FileOutputStream(outPath);
			byte[] buffers = builder.toString().getBytes("UTF-8");
			out.write(buffers);
			out.flush();
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 */
	private void loadModelMacros(String marcoFileName) {
		try {
			String configFilePath = LuaGeneratorHelper.getBuildPath(MODEL_DIC
					+ marcoFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			List<Element> macroElements = root.getChildren("macro", NAME_SPACE);// 消息体定义
			for (Element macroElement : macroElements) {
				macros.put(macroElement.getAttributeValue("id"), macroElement);
			}
			// 处理宏定义中引用其它宏的情况
			for (Element macro : macros.values()) {
				List<Element> fieldList = macro.getChildren();
				for (Element macroField : fieldList) {
					String otherMacroId = macroField.getAttributeValue("macro");
					this.doReplaceMacros(macroField, otherMacroId);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 替换消息定义文件中的宏
	 * 
	 * @param messages
	 */
	private void replaceMacros(List<Element> messages) {
		for (Element msg : messages) {
			String macroId = null;
			// 首先替换message节点中的宏
			macroId = msg.getAttributeValue("macro");
			// 仅一次宏替换(这里假设此时的模板宏中没有还未被处理的嵌套宏)
			if (!this.doReplaceMacros(msg, macroId)) {
				List<Element> fieldList = msg.getChildren();
				// 循环嵌套调用
				replaceMacros(fieldList);
			}
		}
	}

	/**
	 * 替换宏
	 * 
	 * @param element
	 * @param macroId
	 * @return 仅当成功的进行宏替换后返回真
	 */
	private boolean doReplaceMacros(Element element, String macroId) {
		if (!StringUtils.isEmpty(macroId)) {
			if (!macros.containsKey(macroId)) {
				logger.error("消息配置错误，不存在这样的宏定义：" + macroId);
				return false;
			}
			Element macro = macros.get(macroId);
			Element macroClone = (Element) macro.clone();
			element.addContent(macroClone.removeContent());
			return true;
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(replaceDirectly)
		{
			FileUtil.cleanFolder(new File(dataPath),".svn");
			FileUtil.cleanFolder(new File(messagePath),".svn");
			FileUtil.delete(new File(messageTypePath+"MessageType.lua"));
		}
		else
		{
			FileUtil.delete(new File(clientRootPath));
		}
		Map<String, Object> context = new HashMap<String, Object>();
		LuaMessageGenerator generator = new LuaMessageGenerator();
		generator.loadModelMacros("macros.xml");
		context.put("engine", generator);
		JsScriptHelper.executeScriptFile(LuaGeneratorHelper
				.getBuildPath("msg/message_generator.js"), context);
		generator.createClientMessageType();
	}

}
