package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.MasterExtractType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGMasterApplyExtractExperience;
import com.hifun.soul.gameserver.prison.msg.GCMasterApplyExtractExperience;

/**
 * 申请提取当前经验
 * 
 * @author yandajun
 * 
 */
@Component
public class CGMasterApplyExtractExperienceHandler implements
		IMessageHandlerWithType<CGMasterApplyExtractExperience> {

	@Override
	public short getMessageType() {
		return MessageType.CG_MASTER_APPLY_EXTRACT_EXPERIENCE;
	}

	@Override
	public void execute(CGMasterApplyExtractExperience message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		GameConstants gameConstants = GameServerAssist.getGameConstants();
		Prisoner master = manager.getPrisoner(human.getHumanGuid());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		Prisoner slave = manager.getPrisoner(message.getSlaveHumanId());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 判断是否已超出经验上限
		int expLimit = GameServerAssist.getPrisonTemplateManager()
				.getPrisonExperienceTemplate(human.getLevel())
				.getExperienceLimit();
		if (master.getExtractedExperience() >= expLimit) {
			human.sendErrorMessage(LangConstants.PRISON_OVER_MAX_EXPERIENCE);
			return;
		}
		// 根据提取类型，返回相应消息
		GCMasterApplyExtractExperience msg = new GCMasterApplyExtractExperience();
		MasterExtractType extractType = MasterExtractType.indexOf(message
				.getExtractType());
		int currentExperience = manager.getPrisoner(message.getSlaveHumanId())
				.getCurrentExperience();
		int costCrystalPerHourExp = GameServerAssist.getGameConstants()
				.getCostCrystalPerHourExp();
		switch (extractType) {
		case EXTRACT_CURRENT:
			msg.setExtractExperience(currentExperience);
			msg.setExtracExperienceCost(0);
			break;
		case EXTRACT_ONE_HOUR:
			msg.setExtractExperience(slave.getOneHourExperience());
			msg.setExtracExperienceCost(costCrystalPerHourExp);
			break;
		case EXTRACT_TOTAL:
			msg.setExtractExperience(slave.getTotalExperience());
			int hour = (int) ((slave.getSlaveDueTime() - slave
					.getLastBeExtractedTime()) / TimeUtils.HOUR + 1);
			int costCrystal = hour * gameConstants.getCostCrystalPerHourExp();
			msg.setExtracExperienceCost(costCrystal);
			break;
		}
		human.sendMessage(msg);
	}
}
