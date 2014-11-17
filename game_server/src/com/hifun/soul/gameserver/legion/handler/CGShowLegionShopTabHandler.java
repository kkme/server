package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.info.LegionShopItemInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionShopTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionShopTab;
import com.hifun.soul.gameserver.legion.template.LegionShopTemplate;

@Component
public class CGShowLegionShopTabHandler implements
		IMessageHandlerWithType<CGShowLegionShopTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_SHOP_TAB;
	}

	@Override
	public void execute(CGShowLegionShopTab message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.SHOP, true)) {
			return;
		}
		GCShowLegionShopTab msg = new GCShowLegionShopTab();
		// 建筑信息
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.SHOP);
		if (buildingInfo == null) {
			return;
		}
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		msg.setLegionBuildingInfo(buildingInfo);
		List<LegionShopTemplate> shopTemplateList = templateManager
				.getShopTemplateList(buildingInfo.getBuildingLevel(),
						human.getLevel(), human.getOccupation().getIndex());
		// 商店物品信息
		List<LegionShopItemInfo> itemInfoList = new ArrayList<LegionShopItemInfo>();
		for (LegionShopTemplate template : shopTemplateList) {
			LegionShopItemInfo info = new LegionShopItemInfo();
			int itemId = template.getItemId();
			CommonItem commonItem = CommonItemBuilder.genCommonItem(template
					.getItemId());
			info.setCommonItem(commonItem);
			info.setMaxNum(template.getDayNum());
			info.setNeedMedal(template.getCostMedal());
			info.setRemainNum(globalLegionManager.getShopItem(legion, itemId)
					.getBuyNum());
			info.setItemType(template.getItemType());
			itemInfoList.add(info);
		}
		msg.setItemInfos(itemInfoList.toArray(new LegionShopItemInfo[0]));
		msg.setSelfMedal(globalLegionManager.getLegionMember(
				human.getHumanGuid()).getMedal());
		human.sendMessage(msg);
	}
}
