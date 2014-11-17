package com.hifun.soul.gamedb.cache.update;

import com.hifun.soul.core.orm.IEntity;

/**
 * 异步数据库更新类。
 * 
 *
 * @author crazyjohn
 *
 */
public class DBUpdateNonBlockingThread extends AbstractDBUpdateThread {

	/** 每次批量更新的数量 */
	private int batchSize;

	/** 更新时候线程的休眠时间间隔 */
	private long interval;


	/**
	 * 
	 * @param batchSize
	 *            每次更新的条数
	 * @param interval
	 *            当队列中没有数据或者更新条数大于batchSize时候,线程的休眠时间
	 * @param maxQueueSize
	 *            最大的队列长度
	 */
	public DBUpdateNonBlockingThread(int batchSize, long interval, int maxQueueSize) {
		super(maxQueueSize);
		if (batchSize <= 0) {
			throw new IllegalArgumentException(
					"The updateDBThread batchSize must  > 0");
		}
		if (interval <= 0) {
			throw new IllegalArgumentException(
					"The updateDBThread interval must  > 0");
		}
		this.batchSize = batchSize;
		this.interval = interval;
		this.getLogger().info("NonBlocking Update Thread Started!");
	}

	@Override
	protected void doUpdate() {
		long startTime = System.currentTimeMillis();
		int size = batchUpdateFromQueue(batchSize);
		long elapsedeTime = System.currentTimeMillis() - startTime;
		// 当更新条数为0或者条数大于指定更新量的时候,当前线程休眠指定的时间
		if (elapsedeTime < interval) {
			try {
				Thread.sleep(interval - elapsedeTime);
			} catch (InterruptedException e) {
				this.getLogger().error("DBUpdateThread Interrupted!", e);
			}
		}else{
			this.getLogger().warn("CacheToDBUpdateThread was busy, it took "+elapsedeTime+"ms to update "+size+" records!");
		}		
	}
	
	/**
	 * 从消息队列中批量更新
	 * 
	 * @param batchSize
	 *            更新的消息条数
	 * @return 实际更新的消息数量
	 */
	private int batchUpdateFromQueue(int batchSize) {
		int size = 0;
		while (size <= batchSize) {
			IEntity entity = this.getEntityFromQueueAsync();
			if (entity == null) {
				break;
			}
			if (updateEntity(entity)) {
				size++;
			}
		}
		return size;
	}

}
