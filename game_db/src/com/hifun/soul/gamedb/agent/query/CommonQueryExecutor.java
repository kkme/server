package com.hifun.soul.gamedb.agent.query;

import java.util.List;

import com.hifun.soul.core.orm.IDBService;

/**
 * 通用的查询执行器;
 * 
 * @author crazyjohn
 * 
 */
public class CommonQueryExecutor implements IXQLExecutor {
	protected IDBService dbService;
	
	public CommonQueryExecutor(IDBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		return dbService.findByNamedQueryAndNamedParam(queryName, params,
				values);
	}

}
