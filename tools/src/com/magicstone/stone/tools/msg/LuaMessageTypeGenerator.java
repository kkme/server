package com.magicstone.stone.tools.msg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.msg.MessageType;

/**
 * 消息类型生成器;
 * 
 * @author crazyjohn
 * 
 */
public class LuaMessageTypeGenerator {
	private static Logger logger = LoggerFactory.getLogger(LuaMessageTypeGenerator.class);
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
				} catch (Exception e) {
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
		LuaMessageTypeGenerator generator = new LuaMessageTypeGenerator();
		Map<String, Short> fieldInfos = generator.generateFieldsInfo();
		VelocityContext context = new VelocityContext();
		context.put("fieldInfos", fieldInfos);
		try {
			LuaGeneratorHelper.generate(context, "LuaMessageType.vm", "target/type/MessageType.lua");
		} catch (Exception e) {
			logger.error("LuaMessageTypeGenerator execute error ", e);
		}
	}

}
