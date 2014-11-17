package com.hifun.soul.gameserver.scene;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.DBSynchronizer;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GlobalHeartbeatObjectManager;

public class SceneRunner implements Runnable {
	/** 开始处理的计时器 */
	private long startTime;
	/** 绑定的场景 */
	private final Scene scene;
	private SystemTimeService timeService;
	private GlobalHeartbeatObjectManager heartbeatObjectManager;
	/** 玩家数据更新器 */
	private DBSynchronizer huamnDbCacheSynchronizer;
	/** 其它数据的更新器 */
	private DBSynchronizer otherDbCacheSynchronizer;
	/** 角色以外的缓存管理器 */
	private CacheManager cacheManager;
	@Autowired
	private IDataService dataService;

	public SceneRunner(Scene scene, int tickIntervalForHuman, int tickIntervalForSystem) {
		this.scene = scene;
		timeService = ApplicationContext.getApplicationContext().getBean(
				SystemTimeService.class);
		heartbeatObjectManager = ApplicationContext.getApplicationContext()
				.getBean(GlobalHeartbeatObjectManager.class);
		huamnDbCacheSynchronizer = new DBSynchronizer(tickIntervalForHuman);
		otherDbCacheSynchronizer = new DBSynchronizer(tickIntervalForSystem);
		cacheManager = ApplicationContext.getApplicationContext().getBean(
				CacheManager.class);
		dataService = ApplicationContext.getApplicationContext().getBean(
				IDataService.class);
	}

	@Override
	public void run() {
		while (true) {
			startTime = System.currentTimeMillis();
			try {
				// 更新缓存的时间为当前系统时间
				timeService.update();
				heartbeatObjectManager.heartBeat();
				// 场景滴答,用来处理场景消息;
				scene.tick();
				// 场景心跳, 用来处理相关业务;
				scene.heartBeat();

			} catch (Throwable e) {
				Loggers.GAME_LOGGER.error("Scene tick error", e);
			}
			try {
				// 进行数据同步操作
				List<ICachableComponent> cacheComponents = new ArrayList<ICachableComponent>(
						scene.getHumanManager().getAllHumans());
				huamnDbCacheSynchronizer.synchronize(cacheComponents,
						dataService);
				List<ICachableComponent> otherCacheComponent = cacheManager
						.getOtherCaches();
				otherDbCacheSynchronizer.synchronize(otherCacheComponent,
						dataService);

			} catch (Exception e) {
				Loggers.GAME_LOGGER.error("Scene data synchronizer error", e);
			}
			long interval = System.currentTimeMillis() - startTime;
			if (interval < SharedConstants.GS_HEART_BEAT_INTERVAL) {
				try {
					Thread.sleep(SharedConstants.GS_HEART_BEAT_INTERVAL
							- interval);
				} catch (InterruptedException e) {
					Loggers.GAME_LOGGER.error(e.toString(), e);
				}
			} else {
				Loggers.GAME_LOGGER.warn(String.format(
						"SceneThread busy, process time: %d", interval));
			}
		}

	}

}
