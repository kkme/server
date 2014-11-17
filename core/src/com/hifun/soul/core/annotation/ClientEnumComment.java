package com.hifun.soul.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动创建客户端枚举的注释;
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ClientEnumComment {
	/**
	 * 注释内容
	 * @return	返回注释内容;
	 */
	String comment();
}
