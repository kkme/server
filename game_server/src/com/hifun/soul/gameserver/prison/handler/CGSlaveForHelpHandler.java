package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGSlaveForHelp;

/**
 * 求救
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSlaveForHelpHandler implements
		IMessageHandlerWithType<CGSlaveForHelp> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SLAVE_FOR_HELP;
	}

	@Override
	public void execute(CGSlaveForHelp message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner slave = manager.getPrisoner(human.getHumanGuid());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		Prisoner master = manager.getPrisoner(slave.getMasterId());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		Prisoner helper = manager.getPrisoner(message.getHelperHumanId());
		if (helper == null) {
			return;
		}
		if (helper.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 判断是否是自己的主人
		if (slave.getMasterId() == message.getHelperHumanId()) {
			human.sendErrorMessage(LangConstants.SOS_SELF_MASTER);
			return;
		}
		// 是否达到求救次数限制
		if (slave.getForHelpedNum() >= slave.getTotalForHelpNum()) {
			human.sendErrorMessage(LangConstants.NO_MORE_SOS_TIMES);
			return;
		}
		// 等级差大于10级，不能求救
		if (helper.getHumanLevel() - master.getHumanLevel() > GameServerAssist
				.getGameConstants().getArrestLevelDiff()) {
			human.sendErrorMessage(LangConstants.OVER_LEVEL_DIFF_LIMIT);
			return;
		}
		manager.forHelp(human, message.getHelperHumanId());
	}

}
