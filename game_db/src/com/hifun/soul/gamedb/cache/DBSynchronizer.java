package com.hifun.soul.gamedb.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.DBCallbackFactory;
import com.hifun.soul.gamedb.service.IDataService;

/**
 * GameServer同步缓存数据到DB的类。
 * <p>
 * 
 * <b>注意：该类不是线程安全的。</b>
 * 
 * @author crazyjohn
 * 
 */

public class DBSynchronizer {

	private int tickTimes = 0;
	/** 心跳间隔 */
	private int tickInterval;

	private boolean cacheIsClean = true;

	private static Logger logger = Loggers.CACHE_LOGGER;

	public DBSynchronizer(int tickInterval) {
		this.tickInterval = tickInterval;
	}

	/**
	 * 同步Server中所有的缓存数据到DataServer
	 * 
	 * @param caches
	 * @param ds
	 */
	public void synchronize(Collection<ICachableComponent> caches,
			IDataService ds) {
		if (CacheCfg.isOff()) {
			if (cacheIsClean)
				return;
			flushCache(caches, ds, false);
			logger.warn("[DataServerSynchronizer]:The Cache of Server "
					+ "has been turned off factitiously!");
			return;
		}
		tickTimes++;
		cacheIsClean = false;
		if (tickTimes < tickInterval)
			return;
		flushCache(caches, ds, false);
		tickTimes = 0;
	}

	private void flushCache(Collection<ICachableComponent> caches,
			IDataService ds, boolean outputLog) {
		List<IEntity> updateEntityList = new ArrayList<IEntity>();
		List<IEntity> deleteEntityList = new ArrayList<IEntity>();
		for (ICachableComponent cache : caches) {
			List<IEntity> updateEntities = cache.getUpdateEntities();
			if (updateEntities != null) {
				updateEntityList.addAll(updateEntities);
			}
			List<IEntity> deleteEntities = cache.getDeleteEntities();
			if (deleteEntities != null) {
				deleteEntityList.addAll(deleteEntities);
			}
		}

		flushUpdateCache(updateEntityList, ds);
		flushDeleteCache(deleteEntityList, ds);
		if (outputLog) {
			logger.info("[DataServerSynchronizer]:Update "
					+ updateEntityList.size()
					+ " [UPDATE] cache entities to data server after "
					+ this.tickInterval + " tick times!");
			logger.info("[DataServerSynchronizer]:Update "
					+ deleteEntityList.size()
					+ " [DELETE] cache entities to data server after "
					+ this.tickInterval + " tick times!");
		}
		cacheIsClean = true;
	}

	private void flushUpdateCache(List<IEntity> updateEntityList,
			IDataService ds) {
		for (IEntity entity : updateEntityList) {
			ds.update(entity);
		}
	}

	private void flushDeleteCache(List<IEntity> deleteEntityList,
			IDataService ds) {
		for (IEntity entity : deleteEntityList) {
			ds.delete(entity, DBCallbackFactory.<Void> getNullDBCallback());
		}
	}
}
