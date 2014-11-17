package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionShop;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGBuyLegionShopItem;
import com.hifun.soul.gameserver.legion.msg.GCBuyLegionShopItem;
import com.hifun.soul.gameserver.legion.template.LegionShopTemplate;
import com.hifun.soul.gameserver.shop.service.ShopTemplateManager;

@Component
public class CGBuyLegionShopItemHandler implements
		IMessageHandlerWithType<CGBuyLegionShopItem> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_LEGION_SHOP_ITEM;
	}

	@Override
	public void execute(CGBuyLegionShopItem message) {
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
		int itemId = message.getItemId();
		// 判断是否是商城出售物品
		ShopTemplateManager shopTemplateManager = GameServerAssist
				.getShopTemplateManager();
		CommonItem commonItem = CommonItemBuilder.genCommonItem(itemId);
		if (commonItem == null
				|| !shopTemplateManager.canSee(commonItem, human.getLevel(),
						human.getOccupation().getIndex())) {
			return;
		}
		ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager()
				.getItemTemplate(itemId);
		if (itemTemplate == null) {
			return;
		}
		LegionTemplateManager legionTemplateManager = GameServerAssist
				.getLegionTemplateManager();
		LegionShopTemplate shopItemTempate = legionTemplateManager
				.getShopTemplate(itemId);
		if (shopItemTempate == null) {
			return;
		}

		// 判断商品是否足够
		LegionShop legionShop = globalLegionManager.getShopItem(legion, itemId);
		int num = message.getNum();
		if (legionShop.getBuyNum() + num > shopItemTempate.getDayNum()) {
			human.sendErrorMessage(LangConstants.LEGION_SHOP_ITEM_NOT_ENOUGH);
			return;
		}

		// 判断背包是否可以放得下
		if (num <= 0) {
			return;
		}
		if (!human.getBagManager().canContain(itemId, num)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}

		// 扣除个人勋章，将物品放到背包里
		if (legionMember.costMedal(human, num * shopItemTempate.getCostMedal(),
				false)) {
			if (human.getBagManager().putItems(BagType.MAIN_BAG, itemId, num,
					ItemLogReason.BUY_LEGION_SHOP, "")) {
				legionShop.setBuyNum(legionShop.getBuyNum() + num);
				globalLegionManager.updateShopItem(legionShop);

				// 返回消息
				GCBuyLegionShopItem msg = new GCBuyLegionShopItem();
				msg.setItemId(itemId);
				msg.setItemRemainNum(shopItemTempate.getDayNum()
						- legionShop.getBuyNum());
				msg.setSelfMedal(legionMember.getMedal());
				human.sendMessage(msg);
			}
		}
	}
}
