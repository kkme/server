package com.hifun.soul.gameserver.prison.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGShowArrestTab;
import com.hifun.soul.gameserver.prison.msg.GCShowArrestTab;

/**
 * 展示抓捕标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowArrestTabHandler implements
		IMessageHandlerWithType<CGShowArrestTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ARREST_TAB;
	}

	@Override
	public void execute(CGShowArrestTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner prisoner = manager.getPrisoner(human.getHumanGuid());
		if (prisoner == null) {
			return;
		}
		if (prisoner.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		List<Prisoner> looserList = prisoner.getLooserList();
		GCShowArrestTab msg = new GCShowArrestTab();
		msg.setRemainArrestNum(prisoner.getTotalArrestNum()
				- prisoner.getArrestedNum());
		msg.setRemainRescueNum(prisoner.getTotalRescueNum()
				- prisoner.getRescuedNum());
		msg.setRemainInteractNum(prisoner.getTotalInteractNum()
				- prisoner.getInteractedNum());
		msg.setCurrentExperience(prisoner.getExtractedExperience());
		// 经验限制 根据角色的等级来获取
		int expLimit = GameServerAssist.getPrisonTemplateManager()
				.getPrisonExperienceTemplate(human.getLevel())
				.getExperienceLimit();
		msg.setExperienceLimit(expLimit);
		msg.setLoosers(PrisonerToInfoConverter.convertToArray(looserList));
		int buyArrestNumCost = GameServerAssist.getPrisonTemplateManager()
				.getPrisonBuyArrestNumTemplate(prisoner.getBuyArrestNum() + 1)
				.getCostCrystal();
		msg.setBuyArrestNumCost(buyArrestNumCost);
		human.sendMessage(msg);
	}
}
