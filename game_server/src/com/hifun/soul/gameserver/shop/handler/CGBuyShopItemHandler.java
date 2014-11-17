package com.hifun.soul.gameserver.shop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.msg.CGBuyShopItem;
import com.hifun.soul.gameserver.shop.service.ShopTemplateManager;

@Component
public class CGBuyShopItemHandler implements
		IMessageHandlerWithType<CGBuyShopItem> {
	@Autowired
	private ShopTemplateManager shopService;
	@Autowired
	private ItemTemplateManager itemTemplateService;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_SHOP_ITEM;
	}

	@Override
	public void execute(CGBuyShopItem message) {
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
		
		int itemId = message.getItemId();
		int num = message.getNum();
		if(itemId <= 0
				|| num <= 0){
			return;
		}
		
		// 判断是否是商城出售物品
		CommonItem commonItem = shopService.getShopItem(itemId);
		if(commonItem == null
				|| !shopService.canSee(commonItem, human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		ItemTemplate itemTemplate = itemTemplateService.getItemTemplate(itemId);
		if(itemTemplate == null){
			return;
		}
		
		CurrencyType currencyType = CurrencyType.indexOf(commonItem.getShopCurrencyType());
		int totalNum = commonItem.getShopCurrencyNum() * num;
		// 判断货币是否足够
		if(!human.getWallet().isEnough(currencyType, totalNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, currencyType.getDesc());
			return;
		}
		
		// 判断背包是否可以放得下
		if(!human.getBagManager().canContain(itemId,num)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		
		// 扣除金钱
		if(human.getWallet().costMoney(currencyType, totalNum, MoneyLogReason.SHOP_BUY, "")) {
			// 给物品
			human.getBagManager().putItems(BagType.MAIN_BAG,itemId,num,ItemLogReason.SHOP_BUY,"");
		}
	}

}
