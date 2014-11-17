package com.hifun.soul.tools.msg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.item.ItemDetailType;

/**
 * 客户端枚举生成器;<br>
 * 
 * <pre>
 * 1. 目标枚举类有有{@code AutoCreateClientEnumType } 标记;
 * 2. 目标枚举类要实现 {@code IndexedEnum};
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public class ClientEnumGenerator {

	/**
	 * 把指定的服务端枚举生成客户端的枚举文件;
	 * 
	 * @param enumClass
	 *            枚举类
	 * @throws ClassNotFoundException
	 *             找不到指定的枚举类的时候;
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 *             发生IO异常的时候;
	 */
	public static void generateOneEnumFile(Class<?> enumClass)
			throws ClassNotFoundException, IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, IOException {
		if (!enumClass.isEnum()) {
			return;
		}
		if (!IndexedEnum.class.isAssignableFrom(enumClass)) {
			return;
		}
		// 要有自动创建客户端枚举标记
		if (!enumClass.isAnnotationPresent(AutoCreateClientEnumType.class)) {
			return;
		}
		// 所有字段
		Field[] fields = enumClass.getFields();
		Enum<?>[] values = (Enum<?>[]) enumClass.getMethod("values",
				new Class[] {}).invoke(null, new Object[] {});
		if (values == null || values.length == 0) {
			return;
		}
		String clientFileName = enumClass.getSimpleName() + ".as";
		File clientFile = new File(clientFileName);
		FileWriter writer = new FileWriter(clientFile);
		// 写头
		writer.write("package com.hifun.soul.game.constants{\n\t/**\n\t * genarated by ClientEnumGenerator, do not modified!\n\t */\n\tpublic class "
				+ enumClass.getSimpleName() + " {\n ");
		int fieldIndex = 0;
		for (Enum<?> each : values) {
			int index = (Integer) enumClass.getMethod("getIndex",
					new Class[] {}).invoke(each, new Object[] {});
			ClientEnumComment annotation = fields[fieldIndex].getAnnotation(ClientEnumComment.class);
			if (annotation != null) {
				writer.write("\t\t/** " + annotation.comment() + " */\n");
			}
			fieldIndex++;
			writer.write("\t\tpublic static const " + each.name() + ":int = "
					+ index + ";\n");
		}
		// 写尾
		writer.write("\t}\n}");
		writer.close();
		System.out.println("Successfully generated the clienEnumFile: "
				+ clientFileName + ", please refresh to check.");
	}

	public static void main(String[] args) throws ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IOException {
		generateOneEnumFile(ClientGameSceneType.class);
		
	}

}
