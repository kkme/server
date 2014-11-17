package com.hifun.soul.gameserver.mall.handler;

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
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.CGBuyMallItem;
import com.hifun.soul.gameserver.mall.msg.GCBuyMallItem;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;
import com.hifun.soul.gameserver.mall.service.MallTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGBuyMallItemHandler implements
		IMessageHandlerWithType<CGBuyMallItem> {

	@Autowired
	private MallTemplateManager mallService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_MALL_ITEM;
	}

	@Override
	public void execute(CGBuyMallItem message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MALL, true)) {
			return;
		}
		
		int itemId = message.getItemId();
		if(itemId < 1){
			return;
		}
		int num = message.getNum();
		if(num < 1){
			return;
		}
		
		// 判断是否是商城出售物品
		MallItemInfo mallItemInfo = mallService.getMallItemInfo(itemId);
		if(mallItemInfo == null
				|| !mallService.canSee(mallItemInfo.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		
		// 判断货币是否足够
		CurrencyType currencyType = CurrencyType.indexOf(mallItemInfo.getCurrencyType());
		int totalNum = mallItemInfo.getNum();
		if(mallItemInfo.getDiscount()){
			float discountRate = mallItemInfo.getDiscountRate();
			if(discountRate <= 0){
				return;
			}
			
			totalNum = (int) (totalNum * discountRate);
		}
		totalNum = totalNum * num;
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
		if(human.getWallet().costMoney(currencyType, totalNum, MoneyLogReason.MALL_BUY, "")) {
			// 给物品
			human.getBagManager().putItems(BagType.MAIN_BAG,itemId,num,ItemLogReason.MALL,"");
			
			GCBuyMallItem gcMsg = new GCBuyMallItem();
			gcMsg.setSuccess(true);
			human.sendMessage(gcMsg);			
		}
		else{
			GCBuyMallItem gcMsg = new GCBuyMallItem();
			gcMsg.setSuccess(false);
			human.sendMessage(gcMsg);
		}

	}

}
