package com.hifun.soul.gamedb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hifun.soul.core.orm.IEntity;

/**
 * 角色子实体;在t_human表里使用大字段存储;
 * 
 * @author crazyjohn
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BlobSubHumanEntity {

	public Class<? extends IEntity> getSubEntityClass();

	public String getSubFieldDesc();
}
