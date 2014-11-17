package com.hifun.soul.gamedb.cache.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.ICacheObject;

/**
 * 缓存对象到数据库实体对象的转化;
 * 
 * @author crazyjohn
 * 
 * @param <FROM>
 * @param <TO>
 */
public interface ICacheConverter<FROM extends ICacheObject, TO extends IEntity>
		extends IConverter<FROM, TO> {

	/**
	 * 缓存数据转化为实体的接口;
	 * 
	 * @param src
	 * @param useDiffStrategy
	 *            是否使用差异更新的方式;如果使用的话转换的时候会判断实体的某个部分是否发生了更改;
	 * @return
	 */
	public TO converter(FROM src, boolean useDiffStrategy);

}
