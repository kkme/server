package com.hifun.soul.gamedb.cache.update;

import com.hifun.soul.core.orm.IEntity;

/**
 * 同步数据库更新类。 
 *
 * @author crazyjohn
 *
 */
public class DBUpdateBlockingThread extends AbstractDBUpdateThread {


	/**
	 * 
	 * @param batchSize
	 *            每次更新的条数
	 * @param interval
	 *            当队列中没有数据或者更新条数大于batchSize时候,线程的休眠时间
	 * @param maxQueueSize
	 *            最大的队列长度
	 */
	public DBUpdateBlockingThread(int maxQueueSize) {
		super(maxQueueSize);
		this.getLogger().info("Blocking Update Thread Started!");
	}

	@Override
	protected void doUpdate() {
		try {
			IEntity entity = this.getEntityFromQueueSync();
			if(entity==null) return;
			updateEntity(entity);
		} catch (InterruptedException e) {
			this.getLogger().error("DBUpdateThread Interrupted!", e);
		}
	}
}
