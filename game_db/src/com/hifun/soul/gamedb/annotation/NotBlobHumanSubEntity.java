package com.hifun.soul.gamedb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色子实体, 没有使用blob持久化到t_human表里, 而是分表按字段存储, 使用此标记;<br>
 * 标记以后此实体的数据库写操作会使用 @ IDBService} 直接存储;
 * 
 * @author crazyjohn
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NotBlobHumanSubEntity {

}
