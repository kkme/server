package com.hifun.soul.gamedb.agent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gamedb.msg.DBDeleteMessage;
import com.hifun.soul.gamedb.msg.DBGetMessage;
import com.hifun.soul.gamedb.msg.DBInsertMessage;
import com.hifun.soul.gamedb.msg.DBQueryMessage;
import com.hifun.soul.gamedb.msg.DBUpdateMessage;
import com.hifun.soul.gamedb.msg.IDBMessage;

/**
 * 数据库代理入口;<br>
 * 
 * <pre>
 * 1. 提供了对insert, delete, update, get, query操作的封装;
 * 2. 接受的参数都是{@link IDBMessage} 类型的;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IDBAgent {

	public <E extends IEntity> Serializable insert(DBInsertMessage<E> msg);

	public <E extends IEntity> void delete(DBDeleteMessage<E> msg);

	public <E extends IEntity> void update(DBUpdateMessage<E> msg);

	public <E extends IEntity> E get(DBGetMessage<E> msg);

	public List<?> query(DBQueryMessage msg);

	public void registerXQLAgent(XQLAgent queryAgent);
	
	public void registerEntityAgent(IEntityAgent<? extends IEntity> entityAgent);
	
	public void registerCacheableAgent(
			ICacheableAgent<?, ?> agent);

	public List<ICacheableAgent<?, ?>> getModifiedEntityAgent();
	
	public Map<Class<?>, Map<DBOperationType, Integer>> getEntityDBOperationInfos();
	
	public Map<String, Integer> getDbQueryTimesInfo();
}
