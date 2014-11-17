package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionBuilding;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGUpgradeLegionBuilding;
import com.hifun.soul.gameserver.legion.msg.GCUpgradeLegionBuilding;

@Component
public class CGUpgradeLegionBuildingHandler implements
		IMessageHandlerWithType<CGUpgradeLegionBuilding> {

	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_LEGION_BUILDING;
	}

	@Override
	public void execute(CGUpgradeLegionBuilding message) {
		Human human = message.getPlayer().getHuman();
		// 校验是否是团长
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		if (legion.getCommanderId() != human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.LEGION_UPGRADE_BUILDING_MUST_COMMANDER);
			return;
		}
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();

		LegionBuildingType buildingType = LegionBuildingType.indexOf(message
				.getBuildingType());
		// 是否已达最高等级
		int highestLevel = templateManager.getMaxBuildingLevel(buildingType);
		LegionBuilding legionBuilding = globalLegionManager.getBuilding(legion,
				buildingType);
		if (legionBuilding == null) {
			return;
		}
		int currentLevel = legionBuilding.getBuildingLevel();
		if (currentLevel >= highestLevel) {
			human.sendErrorMessage(LangConstants.LEGION_BUILDING_IS_MAX_LEVEL);
			return;
		}
		// 军团等级是否足够
		int needLegionLevel = templateManager
				.getUpgradeBuildingNeedLegionLevel(buildingType, currentLevel);
		if (legion.getLegionLevel() < needLegionLevel) {
			human.sendErrorMessage(LangConstants.LEGION_LEVEL_NOT_ENOUGH);
			return;
		}
		// 消耗军团资金
		int costLegionCoin = templateManager.getUpgradeBuildingCost(
				buildingType, currentLevel);
		if (legion.costCoin(human, costLegionCoin, false)) {
			// 升级建筑
			legionBuilding
					.setBuildingLevel(legionBuilding.getBuildingLevel() + 1);
			globalLegionManager.updateBuilding(legionBuilding);
			// 发送消息
			GCUpgradeLegionBuilding msg = new GCUpgradeLegionBuilding();
			msg.setBuildingType(buildingType.getIndex());
			human.sendMessage(msg);
		}
	}
}
