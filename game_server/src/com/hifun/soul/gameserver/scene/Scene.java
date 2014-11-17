package com.hifun.soul.gameserver.scene;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.ITickable;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageQueue;

/**
 * 
 * 场景的抽象类
 * 
 * @author magicstone
 * 
 */
public abstract class Scene implements ITickable, IHeartBeatable {
	/** 场景消息队列 */
	private MessageQueue msgQueue = new MessageQueue();
	/** 场景玩家管理器 */
	private ScenePlayerManager playerManager;
	/** 场景角色管理器 */
	private SceneHumanManager humanManager;
	/** 场景可以容纳的最大玩家数量 */
	protected int maxPlayerCount;

	public Scene(int maxPlayerCount) {
		this.maxPlayerCount = maxPlayerCount;
		this.playerManager = new ScenePlayerManager(maxPlayerCount);
		this.humanManager = new SceneHumanManager();
	}

	@Override
	public void tick() {
		// 处理场景消息;
		processSceneMessage();
		// 处理玩家消息
		this.playerManager.tick();
	}

	/**
	 * 处理场景消息;
	 */
	private void processSceneMessage() {
		// 场景消息按帧处理;
		while (!msgQueue.isEmpty()) {
			IMessage msg = msgQueue.get();
			msg.execute();
		}
	}

	@Override
	public void heartBeat() {
		this.playerManager.heartBeat();
		this.humanManager.heartBeat();
	}

	public SceneHumanManager getHumanManager() {
		return humanManager;
	}

	public ScenePlayerManager getPlayerManager() {
		return this.playerManager;
	}

	/**
	 * 向场景中投递消息;
	 * 
	 * @param msg
	 */
	public void putMessage(IMessage msg) {
		this.msgQueue.put(msg);
	}

	/**
	 * 场景的负载是否已经满了;
	 * 
	 * @return true表示满了,无法负担更多的玩家;
	 */
	public boolean isFull() {
		return this.playerManager.getAllPlayers().size() >= this.maxPlayerCount;
	}

}
