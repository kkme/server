package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGDonateLegionCrystal;
import com.hifun.soul.gameserver.legion.msg.GCDonateLegionCrystal;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;

@Component
public class CGDonateLegionCrystalHandler implements
		IMessageHandlerWithType<CGDonateLegionCrystal> {

	@Override
	public short getMessageType() {
		return MessageType.CG_DONATE_LEGION_CRYSTAL;
	}

	@Override
	public void execute(CGDonateLegionCrystal message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		int costCrystal = message.getDonateCrystal();
		if (costCrystal <= 0) {
			human.sendWarningMessage(LangConstants.LEGION_DONATE_ONE_CRYSTAL);
			return;
		}
		LegionConstantsTemplate constantsTemplate = GameServerAssist
				.getLegionTemplateManager().getConstantsTemplate();
		// 判断VIP等级是否足够
		if (human.getVipLevel() < constantsTemplate.getDonateNeedVip()) {
			human.sendWarningMessage(LangConstants.DONATE_CRYSTAL_NEED_VIP,
					constantsTemplate.getDonateNeedVip());
			return;
		}
		// 消耗魔晶
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.LEGION_DONATE_CRYSTAL, "")) {
			// 军团经验
			int addExp = constantsTemplate.getDonateGetLegionExp()
					* costCrystal;
			globalLegionManager.addExperience(human, legion, addExp, true);
			// 个人贡献
			int addContri = constantsTemplate.getDonateGetSelfContri()
					* costCrystal;
			globalLegionManager.addSelfContribution(human, addContri, true);
			// 勋章
			int addMedal = constantsTemplate.getDonateGetMedal() * costCrystal;
			globalLegionManager.addSelfMedal(human, addMedal, true);

			// 返回消息
			GCDonateLegionCrystal msg = new GCDonateLegionCrystal();
			msg.setLegionExperience(legion.getExperience());
			LegionMember legionMember = globalLegionManager
					.getLegionMember(human.getHumanGuid());
			msg.setSelfContribution(legionMember.getTotalContribution());
			msg.setLegionMedal(legionMember.getTotalContribution());
			human.sendMessage(msg);
		}
	}
}
