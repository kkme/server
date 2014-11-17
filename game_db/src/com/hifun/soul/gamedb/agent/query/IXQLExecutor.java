package com.hifun.soul.gamedb.agent.query;

import java.util.List;

/**
 * XQL执行器接口;
 * <p>
 * 根据底层数据服务的实现不同,可能是SQL或者是HQL;
 * 
 * @author crazyjohn
 * 
 */
public interface IXQLExecutor {

	public List<?> execute(String queryName, String[] params, Object[] values);

}
