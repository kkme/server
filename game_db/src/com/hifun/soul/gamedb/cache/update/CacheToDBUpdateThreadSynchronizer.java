package com.hifun.soul.gamedb.cache.update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.ICacheableAgent;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.cache.ICacheObject;
import com.hifun.soul.gamedb.cache.converter.ICacheConverter;
import com.hifun.soul.gamedb.util.ModifiedSet;

/**
 * 玩家数据的数据库同步操作
 * <p>
 * FIXME:此处的转换器写死了,只是HumanConverter,也就是只支持角色的转换.需要扩展成转化器集合,
 * 然后每次转换的时候从集合中根据类型取出转换器
 * </p>
 * 
 * @author crazyjohn
 * 
 */
public class CacheToDBUpdateThreadSynchronizer implements IHeartBeatable {
	/** 数据库更新线程 */
	private AbstractDBUpdateThread updateDBThread;

	/** 数据库代理 */
	private IDBAgent dbAgent;

	/** 实体转换 */
	private Map<Class<? extends ICacheObject>, ICacheConverter<ICacheObject, IEntity>> converterMap = new HashMap<Class<? extends ICacheObject>, ICacheConverter<ICacheObject, IEntity>>();

	/**
	 * 实体轮询的索引
	 * <p>
	 * 具体作用是:因为每次在向updateThread的queue中添加数据时候不能超过batchSyncSize
	 * 所以要利用索引记录'当前游标'(虚拟的一个东西)停留的位置
	 * </p>
	 * */
	private int agentIndex;

	/** 每次定时更新的数量 */
	private int batchSyncSize = 200;

	/**
	 * 
	 * @param dbAgent
	 *            对应的数据库代理
	 * @param updateDBThread
	 *            对应的更新线程
	 * @param batchSyncSize
	 *            更新器每次添加到更新线程中的最大数据
	 */
	public CacheToDBUpdateThreadSynchronizer(IDBAgent dbAgent,
			AbstractDBUpdateThread updateDBThread, int batchSyncSize) {
		this.dbAgent = dbAgent;
		this.updateDBThread = updateDBThread;
		this.batchSyncSize = batchSyncSize;
	}

	/**
	 * 停机时候的同步动作
	 */
	public void synchronizedOnShutdown() {

		List<ICacheableAgent<?, ?>> agentList = dbAgent
				.getModifiedEntityAgent();
		// 淘汰实体代理中需要淘汰的数据
		evcitAgentList(agentList);
		for (ICacheableAgent<?, ?> eachAgent : agentList) {
			ModifiedSet<?> modifiedSet = eachAgent.getModifiedSet();

			if (modifiedSet == null || modifiedSet.size() == 0)
				continue;

			while (true) { // 此循环一直到所有脏数据都更新完毕之后退出
				List<? extends IEntity> modifiedList = modifiedSet
						.getModified(50);
				if (modifiedList == null || modifiedList.isEmpty())
					break;
				updateDBThread.updateAtOnce(fillUpdateList(modifiedList));
			}
		}
	}

	/**
	 * 添加所有更新数据到更新线程的更新队列
	 * 
	 * @param agentList
	 *            实体代理列表
	 */
	private void addToUpdateQueue(List<ICacheableAgent<?, ?>> agentList) {
		for (int i = agentIndex; i < agentList.size(); i++) {
			ICacheableAgent<?, ?> agent = agentList.get(i);
			// 取出队列中剩余脏数据的条数和每次允许的最大更新量做对比;
			int minSize = Math.min(updateDBThread.getLeftQueueSize(),
					batchSyncSize);
			if (minSize <= 0) {
				break;
			} else {
				agentIndex++;
				List<? extends IEntity> modifiedList = agent.getModifiedSet()
						.getModified(minSize);
				if (modifiedList == null || modifiedList.isEmpty()) {
					continue;
				}
				updateDBThread.addToUpdateQueue(fillUpdateList(modifiedList));
			}
		}
	}

	/**
	 * 填充更新列表
	 * 
	 * @param modifiedList
	 *            更改列表
	 * @return 返回填充好的更新列表
	 */
	private List<IEntity> fillUpdateList(List<? extends IEntity> modifiedList) {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (IEntity entity : modifiedList) {

			if (entity == null)
				return updateList;

			if (entity instanceof ICacheObject) {
				ICacheObject modifiedEntity = (ICacheObject) entity;

				if (!modifiedEntity.isModified())
					return updateList;

				ICacheConverter<ICacheObject, IEntity> converter = converterMap
						.get(modifiedEntity.getClass());
				if (converter == null) {
					throw new RuntimeException("Entity Coverter not regist.");
				}
				// 这里如果转化出Null的话, 说明实体没有更改数据;
				IEntity updateEntity = converter.converter(
						modifiedEntity, false);
				if (updateEntity != null) {
					// 添加到更新列表
					updateList.add(updateEntity);
				}
				// 重置实体的状态
				modifiedEntity.resetModified();
			} else {
				// 添加到更新列表
				updateList.add(entity);
			}
		}
		return updateList;
	}

	@SuppressWarnings("unchecked")
	public void registEntityConverter(
			Class<? extends ICacheObject> entityClass,
			ICacheConverter<? extends ICacheObject, ? extends IEntity> converter) {
		if (entityClass == null || converter == null)
			return;
		converterMap.put(entityClass,
				(ICacheConverter<ICacheObject, IEntity>) converter);
	}

	/**
	 * 淘汰实体代理列表中需要淘汰的数据
	 * 
	 * @param agentList
	 */
	private void evcitAgentList(Collection<ICacheableAgent<?, ?>> agentList) {
		for (ICacheableAgent<?, ?> agent : agentList) {
			agent.evict();
		}
	}

	/**
	 * 利用心跳定期更新脏数据。
	 */
	@Override
	public void heartBeat() {
		// 首先取得更改实体代理列表
		List<ICacheableAgent<?, ?>> agentList = dbAgent
				.getModifiedEntityAgent();
		// 淘汰实体代理中需要淘汰的数据
		evcitAgentList(agentList);
		if (agentIndex >= agentList.size()) {
			agentIndex = 0;
		}
		// 添加数据到更新线程的更新队列
		addToUpdateQueue(agentList);
	}

	/**
	 * 启动工作线程;
	 */
	public void start() {
		this.updateDBThread.start();
	}

}
