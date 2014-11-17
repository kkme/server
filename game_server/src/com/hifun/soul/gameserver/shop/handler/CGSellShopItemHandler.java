package com.hifun.soul.gameserver.shop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipUpgradeTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.msg.CGSellShopItem;

@Component
public class CGSellShopItemHandler implements IMessageHandlerWithType<CGSellShopItem> {

	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private ItemTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SELL_SHOP_ITEM;
	}

	@Override
	public void execute(CGSellShopItem message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SHOP, true)){
			return;
		}
		
		BagType bagType = BagType.indexOf(message.getBagType());
		int bagIndex = message.getBagIndex();
		Item item = human.getBagManager().getItem(bagType, bagIndex);
		if(item == null){
			return;
		}
		
		// 判断物品是否可以出售
		ItemTemplate itemTemplate = templateManager.getItemTemplate(item.getItemId());
		if(itemTemplate == null
				|| !itemTemplate.canSell()){
			human.sendErrorMessage(LangConstants.CAN_NOT_SELL_ITEM);
			return;
		}
		
		int num = message.getNum();
		if(num < 1){
			return; 
		}
		
		if(item.getOverlapNum() < num){
			return;
		}
		int sellPrice = item.getSellCurrencyNum();
		if(item.isEquip()){
			EquipItemFeature feature = (EquipItemFeature)item.getFeature();
			if(feature.isEquiped()){
				human.sendErrorMessage(LangConstants.CAN_NOT_SELL);
				return;
			}
			EquipUpgradeTemplate upgradeTemplate = GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeTemplate(item.getItemId(), feature.getLevel());
			if(upgradeTemplate !=null){
				sellPrice = upgradeTemplate.getSellPrice();
			}
		}
		
		human.getWallet().addMoney(CurrencyType.indexOf(item.getSellCurrencyType()), 
				sellPrice * num,
				true, MoneyLogReason.SHOP_SELL, "");
		
		human.getBagManager().removeItem(item.getBagType(),item.getBagIndex(),num,ItemLogReason.SHOP_SELL,"");
	}

}
