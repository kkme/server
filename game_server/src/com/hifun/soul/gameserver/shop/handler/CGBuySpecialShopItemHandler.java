package com.hifun.soul.gameserver.shop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.entity.SpecialShopNotifyEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.SpecialShopItem;
import com.hifun.soul.gameserver.shop.SpecialShopNotify;
import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.shop.msg.CGBuySpecialShopItem;
import com.hifun.soul.gameserver.shop.msg.GCBuySpecialShopItem;
import com.hifun.soul.gameserver.shop.service.ShopTemplateManager;
import com.hifun.soul.gameserver.shop.service.SpecialShopNotifyService;

@Component
public class CGBuySpecialShopItemHandler implements
		IMessageHandlerWithType<CGBuySpecialShopItem> {

	@Autowired
	private IDataService dataService;
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private SpecialShopNotifyService specialShopService;
	@Autowired
	private ShopTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_SPECIAL_SHOP_ITEM;
	}

	@Override
	public void execute(CGBuySpecialShopItem message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(player.getHuman(), GameFuncType.SPECIAL_SHOP, true)){
			return;
		}
		HumanSpecialShopManager specialShopManager = human.getHumanSpecialShopManager();
		// 判断是否是出售物品
		SpecialShopItem specialShopItem = specialShopManager.getSpecialShopItem(message.getSpecialShopItemId());
		if(specialShopItem == null
				|| !templateManager.canSee(specialShopItem.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		// 判断是否已经购买过
		if(specialShopItem.getIsBuy()){
			return;
		}
		// 判断是否有足够的货币
		CurrencyType currencyType = CurrencyType.indexOf(specialShopItem.getCurrencyType());
		if(currencyType == null){
			return;
		}
		if(!human.getWallet().isEnough(currencyType, specialShopItem.getCurrencyNum())){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,currencyType.getDesc());
			return;
		}
		// 判断背包是否有格子
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 扣除货币
		if(human.getWallet().costMoney(currencyType, specialShopItem.getCurrencyNum(), MoneyLogReason.BUY_SPECIAL_SHOP_ITEM, "")){
			// 给玩家物品
			human.getBagManager().putItems(BagType.MAIN_BAG, specialShopItem.getItemId(), specialShopItem.getItemNum(), ItemLogReason.BUY_SPECIAL_SHOP, "");
			// 设置该物品状态
			specialShopItem.setIsBuy(true);
			specialShopManager.updateSpecialShopItem(message.getSpecialShopItemId(), specialShopItem);
			Item item = ItemFactory.creatNewItem(human, specialShopItem.getItemId());
			if(item != null){
				SpecialShopNotify simpleSpecialShopItem = new SpecialShopNotify();
				simpleSpecialShopItem.setItemId(specialShopItem.getItemId());
				simpleSpecialShopItem.setItemName(item.getName());
				simpleSpecialShopItem.setRoleName(human.getName());
				simpleSpecialShopItem.setSpecialShopItemId(message.getSpecialShopItemId());
				simpleSpecialShopItem.setItemNum(specialShopItem.getItemNum());
				specialShopService.addSimpleSpecialShopItem(simpleSpecialShopItem);
				
				SpecialShopNotifyEntity entity = new SpecialShopNotifyEntity();
				entity.setItemId(specialShopItem.getItemId());
				entity.setItemNum(specialShopItem.getItemNum());
				entity.setRewardName(item.getName());
				entity.setRoleName(human.getName());
				entity.setSpecialShopItemId(message.getSpecialShopItemId());
				dataService.insert(entity, null);
			}
			
			// 更新客户端状态
			GCBuySpecialShopItem gcMsg = new GCBuySpecialShopItem();
			gcMsg.setIsBuy(true);
			gcMsg.setSpecialShopItemId(message.getSpecialShopItemId());
			gcMsg.setSpecialShopNotifyList(specialShopService.getSpecialShopNotify());
			human.sendMessage(gcMsg);
		}
		
	}

}
