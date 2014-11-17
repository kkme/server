package com.hifun.soul.gamedb.agent.query;

import java.util.List;

import com.hifun.soul.core.orm.IDBService;

/**
 * 支持限制性条数的通用查询;
 * 
 * @author crazyjohn
 * 
 */
public class CommonLimitableQueryExecutor implements IXQLExecutor {
	protected IDBService dbService;
	/** 限制条数 */
	private int limitCount;

	public CommonLimitableQueryExecutor(IDBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		return dbService.findByNamedQueryAndNamedParam(queryName, params,
				values, limitCount, 0);
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

}
