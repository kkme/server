package com.hifun.soul.gameserver.battle.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.TimerManager;
import com.hifun.soul.core.util.MessageTypeUtil;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleState;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.schedule.IBattleScheduleMessage;
import com.hifun.soul.gameserver.common.msg.CGMessage;
import com.hifun.soul.gameserver.human.Human;

/**
 * 战斗消息分发器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleMessageDispatcher implements IBattleDispatcher {
	private Logger logger = LoggerFactory
			.getLogger(BattleMessageDispatcher.class);
	/** 战斗处理器集合 */
	private List<IBattleProcessor> battleProcessors;
	/** 战斗相关调度处理器 */
	private TimerManager battleScheduleManager = new TimerManager(
			"BattleScheduler");

	public BattleMessageDispatcher(int battleProcessorCount) {
		battleProcessors = new ArrayList<IBattleProcessor>(battleProcessorCount);
		for (int i = 0; i < battleProcessorCount; i++) {
			battleProcessors.add(new BattleProcessor("BattleProcessor" + i));
		}
	}

	@Override
	public void dispatchBattleMessage(CGMessage message) {
		Human human = message.getPlayer().getHuman();

		// 角色没在战斗状态退出
		if (!human.isInBattleState()) {
			return;
		}
		// 战斗上下文检查
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 如果当前战斗未开启
		if (battle.getCurrentState() == BattleState.INIT
				&& message.getType() != MessageType.CG_BATTLE_RESOURCE_LOADED) {
			// FIXME: CRAZYJOHN 给客户端错误提示;
			human.sendErrorMessage(LangConstants.BATTLE_INIT_ING);
			logger.error(String.format(
					"Battle not on STARTING state, state: %s, messageType: %s",
					battle.getCurrentState(),
					MessageTypeUtil.getMessageTypeName(message.getType())));
			return;
		}
		// 取出处理器
		IBattleProcessor processor = getBattleProcessor(battle);

		if (processor == null) {
			logger.warn("No suit battleProcessor for this message, type: "
					+ MessageTypeUtil.getMessageTypeName(message.getType()));
			return;
		}
		// 附加战斗处理器
		if (battle.getBattleProcessor() == null) {
			battle.attachProcessor(this);
		}
		processor.put(message);

	}

	/**
	 * 获取处理器;
	 * 
	 * @param battle
	 * @return
	 */
	private IBattleProcessor getBattleProcessor(Battle battle) {
		IBattleProcessor result = this.battleProcessors.get(System
				.identityHashCode(battle) % battleProcessors.size());
		return result;
	}

	@Override
	public void start() {
		for (IBattleProcessor processor : this.battleProcessors) {
			processor.start();
		}
		// 启动调度器
		battleScheduleManager.start(new IMessageProcessor() {

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
				if (!(msg instanceof IBattleScheduleMessage)) {
					return;
				}
				IBattleScheduleMessage scheduleMsg = (IBattleScheduleMessage) msg;
				Battle battle = scheduleMsg.getBattle();
				if (battle == null) {
					return;
				}
				IBattleProcessor result = battleProcessors.get(System
						.identityHashCode(battle) % battleProcessors.size());
				result.put(msg);
			}

			@Override
			public boolean isFull() {
				// TODO Auto-generated method stub
				return false;
			}

		});
	}

	@Override
	public void stop() {
		for (IBattleProcessor processor : this.battleProcessors) {
			processor.stop();
		}
		// 关闭调度器
		this.battleScheduleManager.stop();
	}

	/** 进行一次调度动作 */
	@Override
	public void scheduleOnece(SysInternalMessage task, long delay) {
		this.battleScheduleManager.scheduleOnece(task, delay);
	}
}
