package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGShowPressTab;
import com.hifun.soul.gameserver.prison.msg.GCShowPressTab;

/**
 * 打开压榨页面
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowPressTabHandler implements
		IMessageHandlerWithType<CGShowPressTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_PRESS_TAB;
	}

	@Override
	public void execute(CGShowPressTab message) {
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
		GCShowPressTab msg = new GCShowPressTab();
		// 剩余劳作时间
		msg.setRemainWorkTime((int) (slave.getBeSlaveTime()
				+ GameServerAssist.getGameConstants().getHoldSlaveTimeLimit()
				* TimeUtils.HOUR - GameServerAssist.getSystemTimeService()
				.now()));
		msg.setCurrentExperience(slave.getCurrentExperience());
		// 每分钟收益
		int expPerMinute = GameServerAssist.getPrisonTemplateManager()
				.getPrisonExperienceTemplate(slave.getBeSlaveSelfLevel())
				.getExpPerMinute();
		msg.setExpPerMinute(expPerMinute);
		// 提取1小时花费(不足1小时按1小时算)
		msg.setOneHourCost(GameServerAssist.getGameConstants()
				.getCostCrystalPerHourExp());
		human.sendMessage(msg);
	}
}
