package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGMasterExtractExperience;

/**
 * 提取当前经验
 * 
 * @author yandajun
 * 
 */
@Component
public class CGMasterExtractExperienceHandler implements
		IMessageHandlerWithType<CGMasterExtractExperience> {

	@Override
	public short getMessageType() {
		return MessageType.CG_MASTER_EXTRACT_EXPERIENCE;
	}

	@Override
	public void execute(CGMasterExtractExperience message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner master = manager.getPrisoner(human.getHumanGuid());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			return;
		}
		Prisoner slave = manager.getPrisoner(message.getSlaveHumanId());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			return;
		}
		manager.extractExperience(human, message.getSlaveHumanId(),
				message.getExtractType());
	}

}
