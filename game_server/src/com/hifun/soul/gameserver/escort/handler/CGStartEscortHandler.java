package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.enums.EscortState;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGStartEscort;
import com.hifun.soul.gameserver.escort.msg.GCStartEscort;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 开始押运
 * 
 * @author yandajun
 * 
 */
@Component
public class CGStartEscortHandler implements
		IMessageHandlerWithType<CGStartEscort> {

	@Override
	public short getMessageType() {
		return MessageType.CG_START_ESCORT;
	}

	@Override
	public void execute(CGStartEscort message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		// 如果押运奖励没领取不能押运
		if (globalEscortManager.hasEscortReward(human)) {
			human.sendErrorMessage(LangConstants.ESCORT_HAS_REWARD);
			return;
		}
		// 是否还有押运次数
		if (human.getEscortRemainNum() <= 0) {
			human.sendErrorMessage(LangConstants.HAS_NO_ESCORT_NUM);
			return;
		}
		EscortInfo escortInfo = globalEscortManager.getEscortInfo(human
				.getHumanGuid());
		// 是否正在押运中
		if (escortInfo != null
				&& escortInfo.getEscortState() == EscortState.UNDERWAY
						.getIndex()) {
			human.sendErrorMessage(LangConstants.IS_ESCORTING);
			return;
		}
		// 开始押运
		globalEscortManager.startEscort(human, message.getIsEncouraged());
		GCStartEscort msg = new GCStartEscort();
		human.sendMessage(msg);
	}
}
