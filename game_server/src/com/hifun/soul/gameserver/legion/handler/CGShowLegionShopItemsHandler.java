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
import com.hifun.soul.gameserver.legion.LegionBuilding;
import com.hifun.soul.gameserver.legion.LegionShop;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionShopItemInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionShopItems;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionShopItems;
import com.hifun.soul.gameserver.legion.template.LegionShopTemplate;

@Component
public class CGShowLegionShopItemsHandler implements
		IMessageHandlerWithType<CGShowLegionShopItems> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_SHOP_ITEMS;
	}

	@Override
	public void execute(CGShowLegionShopItems message) {
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
		GCShowLegionShopItems msg = new GCShowLegionShopItems();
		// 建筑信息
		LegionBuilding building = globalLegionManager.getBuilding(legion,
				LegionBuildingType.SHOP);
		if (building == null) {
			return;
		}
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		// 物品信息
		List<LegionShopTemplate> shopTemplateList = templateManager
				.getShopTemplateListByItemType(building.getBuildingLevel(),
						message.getItemType(), human.getLevel(), human
								.getOccupation().getIndex());
		List<LegionShopItemInfo> itemInfoList = new ArrayList<LegionShopItemInfo>();
		for (LegionShopTemplate template : shopTemplateList) {
			LegionShopItemInfo itemInfo = new LegionShopItemInfo();
			int itemId = template.getItemId();
			CommonItem commonItem = CommonItemBuilder.genCommonItem(template
					.getItemId());
			itemInfo.setCommonItem(commonItem);
			itemInfo.setMaxNum(template.getDayNum());
			itemInfo.setNeedMedal(template.getCostMedal());
			// 剩余数量 = 日限量 - 已购买数量
			int buyNum = 0;
			LegionShop legionShop = globalLegionManager.getShopItem(legion,
					itemId);
			if (legionShop != null) {
				buyNum = legionShop.getBuyNum();
			}
			itemInfo.setRemainNum(template.getDayNum() - buyNum);
			itemInfoList.add(itemInfo);
		}
		msg.setItemInfos(itemInfoList.toArray(new LegionShopItemInfo[0]));
		human.sendMessage(msg);
	}
}
