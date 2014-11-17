package com.hifun.soul.gamedb.cache.update;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.logger.Loggers;

/**
 * 缓存数据更新到数据库的线程
 * 
 * @author crazyjohn
 * 
 */
public abstract class AbstractDBUpdateThread implements Runnable {
	/** 数据库更新日志 */
	private static final Logger logger = Loggers.UPDATE_THREAD_LOGGER;

	/** 消息阻塞队列 */
	private BlockingQueue<IEntity> queue = new LinkedBlockingQueue<IEntity>();

	/** 实体更新器列表 */
	private Map<Class<? extends IEntity>, IEntityUpdater<? extends IEntity>> entityUpdators = new HashMap<Class<? extends IEntity>, IEntityUpdater<? extends IEntity>>();

	/** 队列的最大长度 */
	private int maxQueueSize;

	/** 更新服务启动标识 */
	private boolean start;

	/** 更新线程对象 */
	private Thread thread;

	private CountDownLatch stopLatch;

	/**
	 * 
	 * @param batchSize
	 *            每次更新的条数
	 * @param interval
	 *            当队列中没有数据或者更新条数大于batchSize时候,线程的休眠时间
	 * @param maxQueueSize
	 *            最大的队列长度
	 */
	public AbstractDBUpdateThread(int maxQueueSize) {
		if (maxQueueSize <= 0) {
			throw new IllegalArgumentException(
					"The updateDBThread maxQueueSize muse > 0");
		}
		this.maxQueueSize = maxQueueSize;
	}


	/**
	 * 注册实体更新器
	 * 
	 * @param updater
	 *            需要注册的更新器
	 */
	public void regisgerUpdator(Class<? extends IEntity> entityClass,
			IEntityUpdater<? extends IEntity> updater) {
		if (entityUpdators.containsKey(entityClass)) {
			throw new IllegalArgumentException("The class ["
					+ updater.getClass() + "] has been bind");
		}
		entityUpdators.put(entityClass, updater);
	}

	/**
	 * 启动更新服务
	 */
	public void start() {
		start = true;
		thread = new Thread(this, "DBHumanCacheUpdateThread");
		thread.start();
	}

	public void stop() {
		// 这里是为了等待更新线程结束以后在进行stop操作
		if (thread == null) {
			return;
		}
		this.start = false;
		// 中断当前线程
		this.thread.interrupt();
		this.thread = null;
		// 更新所有队列中的数据
		if (stopLatch != null) {
			try {
				stopLatch.await();
			} catch (InterruptedException e) {
			}
			if (logger.isInfoEnabled()) {
				logger.info("Begin [sync the left queue data],size:"
						+ this.queue.size());
			}
			updateAllQueueDatas();
			if (logger.isInfoEnabled()) {
				logger.info("Finished [sync the left queue data],size:"
						+ this.queue.size());
			}
		}

	}

	/**
	 * 更新单个实体,此方法由类内部调用
	 * 
	 * @param entity
	 *            需要更新的实体
	 * @return true 更新成功; false 表示更新异常
	 */
	@SuppressWarnings("unchecked")
	protected <T extends IEntity> boolean updateEntity(T entity) {
		IEntityUpdater<T> updater = (IEntityUpdater<T>) entityUpdators
				.get(entity.getClass());
		if (updater == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("The entity class [" + entity.getClass()
						+ "] do not bound a updator");
			}
			return false;
		} else {
			try {
				updater.update(entity);
				return true;
			} catch (Exception e) {
				logger.error("Update Error", e);
			}
		}
		return true;
	}

	/**
	 * 更新队列中的所有数据
	 */
	private void updateAllQueueDatas() {
		IEntity entity = null;
		while ((entity = queue.poll()) != null) {
			updateEntity(entity);
		}
	}

	/**
	 * 立即更新容器中的所有实体
	 * 
	 * @param collection
	 *            待更新的实体容器
	 */
	public void updateAtOnce(Collection<IEntity> collection) {
		for (IEntity entity : collection) {
			updateEntity(entity);
		}
	}

	@Override
	public void run() {
		// 在这里构造同步计数器
		CountDownLatch countDownLatch = new CountDownLatch(1);
		stopLatch = countDownLatch;
		try {
			while (start) {
				try {
					doUpdate();
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("Update error", e);
					}
				}

			}
		} finally {
			// 最后同步计数器归零
			countDownLatch.countDown();
		}

	}

	/**
	 * 得到剩余的队列的队列长度
	 * 
	 * @return 剩余的队列长度
	 */
	public int getLeftQueueSize() {
		return maxQueueSize - queue.size();
	}

	/**
	 * 将更新列表中的数据添加到当前的实体队列中
	 * 
	 * @param updateList
	 */
	public void addToUpdateQueue(Collection<IEntity> updateList) {
		queue.addAll(updateList);
	}

	protected IEntity getEntityFromQueueAsync() {
		return this.queue.poll();
	}

	protected IEntity getEntityFromQueueSync() throws InterruptedException {
		return this.queue.take();
	}

	protected Logger getLogger() {
		return logger;
	}

	protected abstract void doUpdate();

}
