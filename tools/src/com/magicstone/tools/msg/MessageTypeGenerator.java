package com.magicstone.tools.msg;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.tools.msg.GeneratorHelper;

/**
 * MessageTypeGenerator;
 * 
 * @author crazyjohn
 * 
 */
public class MessageTypeGenerator {
	private Map<String, Short> fieldInfos = new LinkedHashMap<String, Short>();
	// field name min size
	private static final int FIELD_NAME_MIN_SIZE = 3;

	public Map<String, Short> generateFieldsInfo() {
		fieldInfos.clear();
		Class<?> typeClass = MessageType.class;
		Field[] fields = typeClass.getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field eachField : fields) {
			if (eachField.getName().length() < FIELD_NAME_MIN_SIZE) {
				continue;
			}
			String fieldName = eachField.getName();
			String prefix = fieldName.substring(0, 3);
			if ((prefix.equals("CG_") || prefix.equals("GC_"))
					&& ((eachField.getModifiers() & Modifier.STATIC) != 0)
					& ((eachField.getModifiers() & Modifier.FINAL) != 0)) {
				short fieldValue = 0;
				try {
					fieldValue = eachField.getShort(null);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (fieldValue <= 0) {
					throw new RuntimeException("消息号溢出！！");
				} else if (fieldInfos.keySet().contains(fieldValue)) {
					throw new RuntimeException(String.format("%s消息号与其他消息号冲突",
							fieldName));
				}
				fieldInfos.put(fieldName, fieldValue);
			}
		}
		return fieldInfos;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MessageTypeGenerator generator = new MessageTypeGenerator();
		Map<String, Short> fieldInfos = generator.generateFieldsInfo();
		VelocityContext context = new VelocityContext();
		context.put("fieldInfos", fieldInfos);
		VelocityEngine engine = new VelocityEngine();
		Properties props = new Properties();
		props.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, GeneratorHelper
				.getBuildPath("lua"));
		props.setProperty(Velocity.ENCODING_DEFAULT, "utf-8");
		props.setProperty(Velocity.INPUT_ENCODING, "utf-8");
		props.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
		try {
			engine.init(props);
			Template template = engine.getTemplate("LuaMessageType.vm");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("lua/MessageType.lua"), "utf-8"));
			template.merge(context, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
