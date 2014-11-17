package com.hifun.soul.tools.properties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.hifun.soul.core.annotation.Comment;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.tools.msg.GeneratorHelper;

/**
 * 角色属性模板生成
 * 
 * 
 */
public class RolePropertiesGenerator {

	/** 日志 */
	private static final Logger logger = Logger
			.getLogger(RolePropertiesGenerator.class);

	/** 类型 */
	private static final String TYPE_INT = "int";
	/** 模板列表 */
	private static final String trPropertiesTemplates = "tr_properties.vm";
	/** 生成客户端代码文件 */
	private static final String outputTrFile = "RoleProperties.as";
	/** 当前目录 */
	private static final String clientRootPath = "";
	/** 属性类名后缀 */
	private static final String[] propertyClassSuffixArray = { 
		"IntProperties", 
		"StrProperties", 
	};

	/**
	 * 初始化数据
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	private static <T> List<RolePropertiesObject> getPropertyObjectList(
			Class<T> clazz) {
		// 获取前缀
		String prefix = getPrefix(clazz);
		// 获取类注释
		Comment clazzComment = clazz.getAnnotation(Comment.class);
		
		// 创建属性列表
		List<RolePropertiesObject> properties = new ArrayList<RolePropertiesObject>();
		try {
			Field typeField = clazz.getField("TYPE");
			if (typeField != null
					&& TYPE_INT.equals(typeField.getType().getSimpleName())) {
				int type = typeField.getInt(null);
				Field[] fields = clazz.getFields();
				for (Field field : fields) {
					Comment fieldComment = field.getAnnotation(Comment.class);
					if (fieldComment != null) {// 判断是否存在包含注释的注解
						if (TYPE_INT.equals(field.getType().getSimpleName())) {// 判断整形变量
							RolePropertiesObject obj = new RolePropertiesObject();
							obj.setPrefix(prefix);
							obj.setKey(field.getName());
							obj.setValue(PropertyType.genPropertyKey(field
									.getInt(null), type));

							if (clazzComment == null) {
								obj.setComment(fieldComment.content());
							} else {
								obj.setComment(clazzComment.content() + " - " + fieldComment.content());
							}

							properties.add(obj);
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException", e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e);
		} catch (SecurityException e) {
			logger.error("SecurityException", e);
		} catch (NoSuchFieldException e) {
			logger.error("NoSuchFieldException", e);
		}
		return properties;
	}

	/**
	 * 通过给定类对象获取前缀
	 * 
	 * @param clazz
	 * @return
	 */
	private static String getPrefix(Class<?> clazz) {
		if (clazz == null) {
			return "";
		}

		String className = new String(clazz.getSimpleName());

		for (int i = 0; i < propertyClassSuffixArray.length; i++) {
			// 获取类名后缀
			String suffix = propertyClassSuffixArray[i];
			// 查找后缀索引位置
			int suffixIndex = className.indexOf(suffix);

			if (suffixIndex == -1) {
				continue;
			}

			// 删除后缀字符串
			className = className.substring(0, suffixIndex);
		}

		return className.toUpperCase();
	}

	/**
	 * 生成客户端文件
	 * 
	 * @param intList
	 * @param strList
	 * @param templateFileName
	 * @param outputFileName
	 */
	private static void createClientFile(List<RolePropertiesObject> intList,
			List<RolePropertiesObject> strList, String templateFileName,
			String outputFileName) {
		try {
			VelocityContext context = new VelocityContext();
			context.put("intAttrs", intList);
			context.put("strAttrs", strList);
			String outputFilePath = clientRootPath + outputFileName;
			GeneratorHelper.generate(context, templateFileName, outputFilePath);
			printToConsole(intList);
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		}
	}

	private static void printToConsole(List<RolePropertiesObject> intList) {
		System.out.println("Print int props: ");
		for (RolePropertiesObject each : intList) {
			System.out.println(each.getValue() + " " + each.getKey() + " " + each.getComment() );
		}
		// 分别打印
		for (RolePropertiesObject each : intList) {
			System.out.println(each.getValue());
		}
		for (RolePropertiesObject each : intList) {
			System.out.println(each.getComment());
		}
	}

	/**
	 * 生成属性文件
	 */
	private static void createPropertyFiles() {
		List<RolePropertiesObject> trIntList = new ArrayList<RolePropertiesObject>();
		List<RolePropertiesObject> trStrList = new ArrayList<RolePropertiesObject>();
		
		List<RolePropertiesObject> level1List = getPropertyObjectList(Level1Property.class);
		List<RolePropertiesObject> level2List = getPropertyObjectList(Level2Property.class);
		List<RolePropertiesObject> intList = getPropertyObjectList(HumanIntProperty.class);
		List<RolePropertiesObject> longList = getPropertyObjectList(HumanLongProperty.class);

		trIntList.addAll(level1List);
		trIntList.addAll(level2List);		
		trIntList.addAll(intList);
		trStrList.addAll(longList);

		// 生成客户端文件
		createClientFile(trIntList, trStrList, trPropertiesTemplates,
				outputTrFile);
	}

	/**
	 * 执行
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		createPropertyFiles();
		System.out.println("属性模板生成完毕。");
	}
}
