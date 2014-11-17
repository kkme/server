package com.hifun.soul.gameserver.building.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.building.msg.CGUpgradeBuilding;

@Component
public class CGUpgradeBuildingHandler implements
		IMessageHandlerWithType<CGUpgradeBuilding> {

	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_BUILDING;
	}

	@Override
	public void execute(CGUpgradeBuilding message) {
		// 没有建筑升级功能
	}

}
