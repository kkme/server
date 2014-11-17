package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionTechnology;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGContributeLegionTechnology;
import com.hifun.soul.gameserver.legion.msg.GCContributeLegionTechnology;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTemplate;

@Component
public class CGContributeLegionTechnologyHandler implements
		IMessageHandlerWithType<CGContributeLegionTechnology> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CONTRIBUTE_LEGION_TECHNOLOGY;
	}

	@Override
	public void execute(CGContributeLegionTechnology message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 校验军团成员是否存在
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.TECHNOLOGY, true)) {
			return;
		}
		int technologyType = message.getTechnologyType();
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		LegionTechnology technology = globalLegionManager.getTechnology(legion,
				technologyType);
		// 是否达到最高等级
		if (technology.getTechnologyLevel() >= templateManager
				.getTechnologyMaxLevel(technologyType)) {
			human.sendErrorMessage(LangConstants.LEGION_TECH_MAX_LEVEL);
			return;
		}
		// 如果满足升级条件，建筑等级是否允许
		LegionTechnologyTemplate currentTemplate = templateManager
				.getTechnologyTemplate(technologyType,
						technology.getTechnologyLevel());
		boolean canUpgrade = canUpgrade(technology);
		if (canUpgrade
				&& globalLegionManager.getBuilding(legion,
						LegionBuildingType.TECHNOLOGY).getBuildingLevel() < currentTemplate
						.getNeedBuildingLevel()) {
			human.sendErrorMessage(LangConstants.LEGION_BUILDING_LEVEL_NOT_ENOUGH);
			return;
		}
		// 消耗金币升级科技
		int costMoney = templateManager.getConstantsTemplate()
				.getContributeTechCostCoin();
		if (human.getWallet().costMoney(CurrencyType.COIN, costMoney,
				MoneyLogReason.LEGION_TECHNOLOGY_CONTRIBUTE, "")) {
			if (canUpgrade) {
				technology.setCurrentCoin(0);
				technology
						.setTechnologyLevel(technology.getTechnologyLevel() + 1);
			} else {
				technology.setCurrentCoin(technology.getCurrentCoin()
						+ costMoney);
			}
			// 更新科技
			globalLegionManager.updateTechnology(technology);

			// 捐献奖励
			// 个人贡献
			globalLegionManager.addSelfContribution(human, templateManager
					.getConstantsTemplate().getContributeTechContribution(),
					true);
			// 个人勋章
			globalLegionManager.addSelfMedal(human, templateManager
					.getConstantsTemplate().getContributeTechMedal(), true);
			// 军团资金
			globalLegionManager.addLegionCoin(human, templateManager
					.getConstantsTemplate().getContributeTechMedal(), true);
			// 返回消息
			GCContributeLegionTechnology msg = new GCContributeLegionTechnology();
			msg.setTechnologyInfo(globalLegionManager.generateTechnologyInfo(
					legion, technology));
			msg.setSelfMedal(legionMember.getMedal());
			human.sendMessage(msg);
		}

	}

	/**
	 * 科技是否可升级
	 */
	private boolean canUpgrade(LegionTechnology technology) {
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		int costMoney = templateManager.getConstantsTemplate()
				.getContributeTechCostCoin();
		if (technology.getCurrentCoin() + costMoney >= templateManager
				.getTechnologyTemplate(technology.getTechnologyType(),
						technology.getTechnologyLevel()).getUpNeedCoin()) {
			return true;
		}
		return false;
	}
}
