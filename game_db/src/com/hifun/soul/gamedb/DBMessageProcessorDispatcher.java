package com.hifun.soul.gamedb;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.core.server.TimerManager;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gamedb.msg.CacheToDBSynchronizedSchedule;
import com.hifun.soul.gamedb.msg.DBQueryMessage;
import com.hifun.soul.gamedb.msg.IDBMessage;
import com.hifun.soul.gamedb.msg.handler.DBMessageHandler;

/**
 * 数据库消息处理器;
 * 
 * @author crazyjohn
 * 
 */
public class DBMessageProcessorDispatcher implements IDBDispatcher {
	private Logger logger = Loggers.DB_MAIN_LOGGER;
	/** 比较费时的查询消息处理器(目前只负责Account) */
	private QueueMessageProcessor specialQueryDbMessageProcessor;
	private Map<String, QueueMessageProcessor> querys = new HashMap<String, QueueMessageProcessor>();
	/** 角色以外实体的处理器 */
	private QueueMessageProcessor commonEntityProcessor;
	/** 角色的实体代理器 */
	private QueueMessageProcessor humanEntityProcessor;
	/** 实体消息处理器 */
	private Map<Class<? extends IEntity>, QueueMessageProcessor> entityProcessors = new HashMap<Class<? extends IEntity>, QueueMessageProcessor>();
	/** 查询消息处理器 */
	private Map<String, QueueMessageProcessor> queryProcessors = new HashMap<String, QueueMessageProcessor>();
	/** DB调度管理器 */
	private TimerManager dbTimerManager = new TimerManager("DBScheduler");

	public DBMessageProcessorDispatcher(DBMessageHandler handler) {
		specialQueryDbMessageProcessor = new QueueMessageProcessor(
				"SpecialDBQueryMessageProcessor", handler);
		commonEntityProcessor = new QueueMessageProcessor(
				"DBCommonEntityMessageProcessor", handler);
		humanEntityProcessor = new QueueMessageProcessor(
				"DBHumanEntityProcessor", handler);
	}

	@Override
	public void start() {
		logger.info("Begin start the dbService...");
		specialQueryDbMessageProcessor.start();
		commonEntityProcessor.start();
		humanEntityProcessor.start();
		dbTimerManager.start(this.humanEntityProcessor);
		logger.info("DBService started.");
	}

	@Override
	public void stop() {
		logger.info("Begin stop the dbService...");
		commonEntityProcessor.stop();
		humanEntityProcessor.stop();
		specialQueryDbMessageProcessor.stop();
		dbTimerManager.stop();
		logger.info("DBService stoped.");
	}

	@Override
	public void put(IDBMessage msg) {
		// 如果是调度消息
		if (msg instanceof CacheToDBSynchronizedSchedule) {
			this.humanEntityProcessor.put(msg);
			return;
		}
		if (msg instanceof DBQueryMessage) {
			DBQueryMessage queryMsg = (DBQueryMessage) msg;
			String queryName = queryMsg.getQueryName();
			if (getSpecialQueryProcessor(queryName) != null) {
				getSpecialQueryProcessor(queryName).put(queryMsg);
			} else {
				// 取出查询处理器
				this.getEntityQueryProcessor(queryName).put(msg);
			}
			return;
		}
		Class<? extends IEntity> entityClass = msg.getEntityClass();
		// 获取实体消息处理器;
		QueueMessageProcessor processor = getEntityMessageProcessor(entityClass);
		if (processor == null) {
			throw new IllegalArgumentException(String.format("Can't find %s enity processor! you have to reigster it first at DBServiceManager.", entityClass.getSimpleName()));
		}
		processor.put(msg);
		

	}

	/**
	 * 取出实体查询处理器;
	 * 
	 * @param queryName
	 * @return
	 */
	private QueueMessageProcessor getEntityQueryProcessor(String queryName) {
		return this.queryProcessors.get(queryName);
	}

	/**
	 * 获取实体消息处理器;
	 * 
	 * @param entityClass
	 * @return
	 */
	private QueueMessageProcessor getEntityMessageProcessor(
			Class<? extends IEntity> entityClass) {
		return entityProcessors.get(entityClass);
	}

	/**
	 * 注册需要角色实体处理器处理的实体;跟角色数据相关;
	 * 
	 * @param entityClass
	 */
	@Override
	public void registerHumanEntityProcessor(
			Class<? extends IEntity>[] entityClasses) {
		// 注册角色相关的实体处理
		for (Class<? extends IEntity> each : entityClasses) {
			this.entityProcessors.put(each, this.humanEntityProcessor);
		}

	}

	/**
	 * 注册角色无关的实体处理;
	 * 
	 * @param entityClass
	 */
	@Override
	public void registerCommonXXEntityProcessor(
			Class<? extends IEntity>[] entityClasses) {
		// 注册角色以外的实体处理
		for (Class<? extends IEntity> each : entityClasses) {
			this.entityProcessors.put(each, this.commonEntityProcessor);
		}

	}

	/**
	 * 去除特殊查詢处理器;
	 * 
	 * @param queryName
	 * @return
	 */
	private QueueMessageProcessor getSpecialQueryProcessor(String queryName) {
		return querys.get(queryName);
	}

	@Override
	public void registerSpecialQuery(String specialQuery) {
		this.querys.put(specialQuery, this.specialQueryDbMessageProcessor);
	}

	@Override
	public void registerHumanEntityQuery(String queryName) {
		queryProcessors.put(queryName, this.humanEntityProcessor);
	}

	@Override
	public void registerCommonXXEntityQuery(String queryName) {
		queryProcessors.put(queryName, this.commonEntityProcessor);
	}

	@Override
	public void addHeartBeatObject(final IHeartBeatable object, long period) {
		this.dbTimerManager.scheduleAtFixedDelay(
				new CacheToDBSynchronizedSchedule(object), 0, period);
	}

}
