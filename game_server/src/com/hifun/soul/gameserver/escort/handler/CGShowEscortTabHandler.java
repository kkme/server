package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.enums.EscortState;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGShowEscortTab;
import com.hifun.soul.gameserver.escort.msg.GCShowEscortTab;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 展示押运详细页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowEscortTabHandler implements
		IMessageHandlerWithType<CGShowEscortTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ESCORT_TAB;
	}

	@Override
	public void execute(CGShowEscortTab message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortInfo escortInfo = globalEscortManager.getEscortInfo(message
				.getEscortId());
		if (escortInfo == null) {
			return;
		}
		generateEscortInfo(human, escortInfo);
		GCShowEscortTab msg = new GCShowEscortTab();
		msg.setEscortInfo(escortInfo);
		human.sendMessage(msg);
	}

	/**
	 * 生成押运信息
	 */
	private void generateEscortInfo(Human human, EscortInfo escortInfo) {
		// 拦截奖励，由等级差决定
		int robLevelDiff = human.getLevel() - escortInfo.getOwnerLevel();
		float robRewardCoinRate = GameServerAssist.getEscortTemplateManager()
				.getRobRewardCoinRate(robLevelDiff);
		escortInfo
				.setRobRewardCoin((int) (escortInfo.getEscortRewardCoin() * robRewardCoinRate));
		// 押运剩余时间
		if (escortInfo.getEscortState() == EscortState.UNDERWAY.getIndex()) {
			escortInfo
					.setEscortRemainTime((int) (escortInfo.getEndTime() - GameServerAssist
							.getSystemTimeService().now()));
		}
	}
}
