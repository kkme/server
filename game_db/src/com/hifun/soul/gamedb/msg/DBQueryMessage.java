package com.hifun.soul.gamedb.msg;

import java.util.List;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 负责数据库查询的消息
 * 
 * @author crazyjohn
 * 
 */
public class DBQueryMessage extends AbstractDBMessage<List<?>> {
	private String queryName;
	private String[] params;
	private Object[] values;

	public DBQueryMessage(String queryName, String[] params, Object[] values,
			IDBCallback<List<?>> callback) {
		super(callback);
		this.queryName = queryName;
		this.params = params;
		this.values = values;
	}

	public String getQueryName() {
		return this.queryName;
	}

	public String[] getParams() {
		return this.params;
	}

	public Object[] getValues() {
		return this.values;
	}

	@Override
	protected void doRealDBAction(IDBAgent dbAgent,
			DBCallbackMessage<List<?>> callbackMsg) {
		callbackMsg.setResult(dbAgent.query(this));
	}

	@Override
	public Class<? extends IEntity> getEntityClass() {
		throw new UnsupportedOperationException();
	}

}
