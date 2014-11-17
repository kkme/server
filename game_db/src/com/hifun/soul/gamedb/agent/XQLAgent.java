package com.hifun.soul.gamedb.agent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.query.CommonLimitableQueryExecutor;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;

/**
 * XQL执行器的代理;
 * 
 * @author crazyjohn
 * 
 */
public class XQLAgent implements IXQLExecutor {
	private Map<String, IXQLExecutor> xqlExecutors = new ConcurrentHashMap<String, IXQLExecutor>();
	private Map<String, Integer> limitMap = new ConcurrentHashMap<String, Integer>();
	private CommonLimitableQueryExecutor limitExecutor;
	/** DB查询操作的次数 */
	private Map<String, Integer> dbQueryTimes = new ConcurrentHashMap<String, Integer>();

	/**
	 * 
	 * @param queryNames
	 *            查询名称数组;
	 * @param executor
	 *            对应的执行器;
	 */
	public XQLAgent(IDBService dbService) {		
		limitExecutor = new CommonLimitableQueryExecutor(dbService);		
	}
	
	/**
	 * 注册XQL执行器;
	 * 
	 * @param queryName
	 *            查询名称;
	 * @param executor
	 *            执行器;
	 */
	public void registerXQLExecutor(String queryName, IXQLExecutor executor) {
		if (this.xqlExecutors.containsKey(queryName)) {
			throw new IllegalArgumentException(String.format(
					"This query: %s has been bounded.", queryName));
		}
		this.xqlExecutors.put(queryName, executor);
		this.dbQueryTimes.put(queryName, 0);
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		IXQLExecutor executor = this.xqlExecutors.get(queryName);
		if (this.limitMap.get(queryName) != null) {
			this.limitExecutor.setLimitCount(this.limitMap.get(queryName));
			return limitExecutor.execute(queryName, params, values);
		}
		// 记录查询
		this.dbQueryTimes.put(queryName, this.dbQueryTimes.get(queryName) + 1);
		return executor.execute(queryName, params, values);
	}

	/**
	 * 注册有限制性条数的查询;
	 * <p>
	 * 解决HQL不支持mysql关键字limit的问题;
	 * 
	 * @param queryName
	 *            查询名称;
	 * @param limit
	 *            限制条数;
	 */
	public void registerLimitXQLExecutor(String queryName, int limit) {
		limitMap.put(queryName, limit);
	}

	/**
	 * 获取数据库查询计数器信息
	 * 
	 * @return
	 */
	public Map<String, Integer> getDbQueryTimesInfo() {
		return dbQueryTimes;
	}
}
