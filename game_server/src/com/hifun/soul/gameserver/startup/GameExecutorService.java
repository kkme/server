package com.hifun.soul.gameserver.startup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.util.ExecutorUtil;

/**
 * 系统线程池服务
 * 
 */
@Scope("singleton")
@Component
public class GameExecutorService {
	/** 用于系统维护的线程池,如检测到WorldServer的重连操作等 */
	private final ExecutorService maintainsExecutor;

	/** 用于执行定时任务的线程池 */
	private final ScheduledExecutorService scheduleExecutorService;

	public GameExecutorService() {
		this.maintainsExecutor = Executors.newSingleThreadExecutor();
		this.scheduleExecutorService = Executors.newScheduledThreadPool(1);
	}

	/**
	 * 取得用于系统维护的线程池
	 * 
	 * @return
	 */
	public ExecutorService getMaintainsExcecutor() {
		return maintainsExecutor;
	}

	/**
	 * 增加按指定的周期执行的任务
	 * 
	 * @param task
	 *            任务
	 * @param period
	 *            执行周期,单位为毫秒
	 */
	public void scheduleTask(Runnable task, long period) {
		this.scheduleExecutorService.scheduleAtFixedRate(task, 0, period,
				TimeUnit.MILLISECONDS);
	}

	public void stop() {
		ExecutorUtil.shutdownAndAwaitTermination(this.maintainsExecutor, 0,
				TimeUnit.MILLISECONDS);
		ExecutorUtil.shutdownAndAwaitTermination(this.scheduleExecutorService,
				0, TimeUnit.MILLISECONDS);
	}
}
