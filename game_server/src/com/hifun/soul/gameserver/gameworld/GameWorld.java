package com.hifun.soul.gameserver.gameworld;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.TimerManager;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.ExecutorUtil;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.scene.ManageScene;
import com.hifun.soul.gameserver.scene.SceneHumanManager;
import com.hifun.soul.gameserver.scene.SceneRunner;

/**
 * 游戏世界基础实现;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class GameWorld implements IGameWorld {
	private Logger logger = Loggers.SCENE_LOGGER;
	@Autowired
	private IDataService dataService;
	@Autowired
	protected TemplateService templateService;
	/** 负责管理玩家的场景抽象 */
	private ManageScene manageScene;
	/** 场景工作单元 */
	protected SceneRunner manageSceneRunner;
	/** 线程池 */
	private ExecutorService executorService;
	/** 场景调度管理器 */
	private TimerManager sceneTimerManager = new TimerManager("SceneScheduler");
	/** 主消息处理器 */
	private IMessageProcessor mainMessageProcessor;

	public GameWorld() {
		executorService = Executors
				.newSingleThreadExecutor(new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread namedThread = new Thread(r);
						namedThread.setName("SceneThread");
						return namedThread;
					}
				});
	}

	/**
	 * 游戏世界初始化;
	 * 
	 */
	@Override
	public void init(int maxPlayerCount, int tickIntervalForHuman,
			int tickIntervalForSystem) {
		// 构建管理场景
		manageScene = new ManageScene(maxPlayerCount);
		manageScene.setDataService(dataService);
		manageSceneRunner = new SceneRunner(manageScene, tickIntervalForHuman,
				tickIntervalForSystem);
		// 绑定依赖
		ApplicationContext
				.getApplicationContext()
				.getDefaultListableBeanFactory()
				.registerResolvableDependency(TimerManager.class,
						this.sceneTimerManager);
	}

	/**
	 * 场景启动
	 * 
	 */
	@Override
	public void start() {
		logger.info("begin start gameWorldThread");
		// 启动场景服务
		this.executorService.execute(this.manageSceneRunner);
		// 启动时间调度管理器
		sceneTimerManager.start(new IMessageProcessor() {
			@Override
			public void start() {
				// TODO Auto-generated method stub

			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub

			}

			@Override
			public void put(IMessage msg) {
				putMessage(msg);
			}

			@Override
			public boolean isFull() {
				return manageScene.isFull();
			}

		});
		logger.info("end start gameWorldThread");
	}

	/**
	 * 关闭游戏世界相关服务;
	 * 
	 */
	@Override
	public void stop() {
		logger.info("begin stop gameWorldThread");
		// 关闭调度服务
		this.sceneTimerManager.stop();
		// 关闭场景服务
		ExecutorUtil.shutdownAndAwaitTermination(this.executorService);
		logger.info("end stop gameWorldThread");
	}

	@Override
	public Player getPlayerByPassportId(long passportId) {
		return this.manageScene.getPlayerManager().getPlayer(passportId);
	}

	/**
	 * 添加在线玩家, 在认证成功以后调用;
	 * <p>
	 * 由主线程调用;
	 * 
	 * @param player
	 */
	public void addOnlineAccount(Player player) {
		this.manageScene.getPlayerManager().addOnlineAccount(player);
	}

	/**
	 * 玩家进入游戏;
	 * 
	 * @param human
	 */
	@Override
	public void humanEnter(final Human human) {
		this.putMessage(new SysInternalMessage() {
			@Override
			public void execute() {
				human.onLogin();
			}
		});
		this.manageScene.getHumanManager().addHuman(human);
	}

	/**
	 * 投递给世界消息;
	 * 
	 * @param msg
	 */
	@Override
	public void putMessage(IMessage msg) {
		this.manageScene.putMessage(msg);
	}

	/**
	 * 获取所有世界玩家;
	 * 
	 * @return
	 */
	public Collection<Player> getAllPlayers() {
		return manageScene.getPlayerManager().getAllPlayers();
	}

	public SceneHumanManager getSceneHumanManager() {
		return manageScene.getHumanManager();
	}

	/**
	 * 增加一个游戏世界的调度;
	 * 
	 * @param task
	 * @param delay
	 */
	public void scheduleOnece(SysInternalMessage task, long delay) {
		this.sceneTimerManager.scheduleOnece(task, delay);
	}

	/**
	 * 给游戏世界添加一个心跳调度;
	 * 
	 * @param object
	 * @param period
	 */
	public void addHeartBeatObject(final IHeartBeatable object, long period) {
		this.sceneTimerManager.scheduleAtFixedDelay(new SysInternalMessage() {

			@Override
			public void execute() {
				object.heartBeat();
			}
		}, 0, period);
	}

	public void registerMainMessageProcessor(IMessageProcessor mainProcessor) {
		this.mainMessageProcessor = mainProcessor;
	}

	public IMessageProcessor getMainMessageProcessor() {
		return mainMessageProcessor;
	}

	@Override
	public void playerLeve(Player player) {
		this.manageScene.playerLeveScene(player);
	}

	@Override
	public void boradcast(IMessage messge) {
		for (Player player : this.getAllPlayers()) {
			player.sendMessage(messge);
		}
	}

	/**
	 * 服务器是否爆满;
	 * 
	 * @return
	 */
	public boolean isPlayerFull() {
		return this.manageScene.isFull();
	}

}
