package com.hifun.soul.gameserver.startup;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.msg.sys.ScheduledMessage;
import com.hifun.soul.core.server.ExecutableMessageHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.gamedb.msg.DBCallbackMessage;
import com.hifun.soul.gameserver.battle.processor.BattleMessageDispatcher;
import com.hifun.soul.gameserver.battle.processor.IBattleDispatcher;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.msg.CGMessage;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.CGPlayerCoolieLogin;
import com.hifun.soul.gameserver.player.msg.CGPlayerLogin;

/**
 * 游戏服务器消息处理器
 * 
 */
public class GameMessageProcessor implements IMessageProcessor {
	protected static final Logger log = Loggers.MSG_LOGGER;

	/** 主消息处理器，处理服务器内部消息、玩家不属于任何场景时发送的消息 */
	private QueueMessageProcessor mainMessageProcessor;
	private IBattleDispatcher battleMessageProcessor;

	public GameMessageProcessor(int battleProcessorCount) {
		mainMessageProcessor = new QueueMessageProcessor(
				"MainMessageProcessor", new ExecutableMessageHandler());
		// 战斗处理器
		battleMessageProcessor = new BattleMessageDispatcher(
				battleProcessorCount);
	}

	@Override
	public boolean isFull() {
		return mainMessageProcessor.isFull();
	}

	/**
	 * <pre>
	 * 1、服务器内部消息、玩家不属于任何场景时发送的消息，单独一个消息队列进行处理
	 * 2、玩家在场景中发送过来的消息，添加到玩家的消息队列中，在场景的线程中进行处理
	 * </pre>
	 */
	@Override
	public void put(IMessage msg) {
		if (!GameServerRuntime.isOpen() && !(msg instanceof SysInternalMessage)
				&& !(msg instanceof ScheduledMessage)) {
			log.info("【Receive but will not process because server not open】"
					+ msg);
			return;
		}
		// 处理数据库回调消息
		if (msg instanceof DBCallbackMessage) {
			DBCallbackMessage<?> callbackMsg = (DBCallbackMessage<?>) msg;
			if (callbackMsg.isSendByMainThread()) {
				mainMessageProcessor.put(msg);
				return;
			} else {
				// 把数据库消息投递到场景线程;
				GameServerAssist.getGameWorld().putMessage(callbackMsg);
				return;
			}
		}

		// 如果是CG消息, 投递给场景线程(游戏线程)处理
		if (msg instanceof CGMessage) {
			// 登录消息直接投递到主线程处理
			if (msg instanceof CGPlayerLogin
					|| msg instanceof CGPlayerCoolieLogin) {
				this.mainMessageProcessor.put(msg);
				return;
			}
			GameClientSession session = ((CGMessage) msg).getSession();
			if (session == null) {
				return;
			}
			// 此处的读操作会发生多线程调用, 所以要保证player的可视性;
			Player player = session.getPlayer();
			if (player == null) {
				log.error("Player not found, maybe occur some concurrent error! msg:"
						+ msg);
				return;
			}
			log.debug("【Receive】" + msg + player);
			if (log.isDebugEnabled()) {
				if (player.getHuman() != null) {
					log.debug("【Receive】" + msg + " from player : "
							+ player.getHuman().getName() + ",roleUUID : "
							+ player.getHuman().getId());
				} else {
					log.debug("【Receive】" + msg);
				}
			}
			// 如果玩家已经在游戏中, 而且当前状态可以处理此类消息;
			if (player.getHuman() != null
					&& player.getHuman().isInBattleState()) {
				// 战斗中是否可以处理此类消息?
				if (!player.getState().canProcessMessage(msg.getType())) {
					log.warn(String
							.format("This human: %s, guid: %d is in battling, can't process this kind of message: %s",
									player.getHuman().getName(), player
											.getHuman().getHumanGuid(), msg
											.getTypeName()));
					return;
				}
				// 如果在战斗中, 则消息转发给战斗处理分发器
				battleMessageProcessor.dispatchBattleMessage((CGMessage) msg);
			} else if (player.isInGaming()) {
				if (!player.getState().canProcessMessage(msg.getType())) {
					log.warn(String
							.format("This human: %s, guid: %d is in gaming, can't process this kind of message: %s",
									player.getHuman().getName(), player
											.getHuman().getHumanGuid(), msg
											.getTypeName()));
					return;
				}
				player.putMessage(msg);
			} else {
				mainMessageProcessor.put(msg);
			}
		} else {
			// 不是CG消息直接投递给主线程处理
			if (log.isDebugEnabled() && !(msg instanceof ScheduledMessage)) {
				log.debug("【Receive】" + msg);
			}
			mainMessageProcessor.put(msg);
			return;
		}
	}

	@Override
	public void start() {
		mainMessageProcessor.start();
		this.battleMessageProcessor.start();
	}

	@Override
	public void stop() {
		this.battleMessageProcessor.stop();
		mainMessageProcessor.stop();
	}

	/**
	 * 获得主消息处理线程Id
	 * 
	 * @return
	 */
	public long getThreadId() {
		return mainMessageProcessor.getThreadId();
	}

	/**
	 * @return
	 */
	public boolean isStop() {
		return mainMessageProcessor.isStop();
	}
}
