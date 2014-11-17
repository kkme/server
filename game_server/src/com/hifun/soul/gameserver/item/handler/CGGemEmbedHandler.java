package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.msg.CGGemEmbed;
import com.hifun.soul.gameserver.item.msg.GCGemEmbed;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGemEmbedHandler implements IMessageHandlerWithType<CGGemEmbed>{
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GEM_EMBED;
	}

	@Override
	public void execute(CGGemEmbed message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.GEM_EMBED, true)){
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		BagType equipBagType = BagType.indexOf(message.getEquipBagType());
		int equipBagIndex = message.getEquipBagIndex();
		Item equipItem = bagManager.getItem(equipBagType, equipBagIndex);
		BagType gemBagType = BagType.indexOf(message.getGemBagType());
		int gemBagIndex = message.getGemBagIndex();
		Item gemItem = bagManager.getItem(gemBagType, gemBagIndex);
		// 判断选中装备的合法性
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		// 判断选中宝石的合法性
		if(gemItem == null
				|| gemItem.getType() != ItemDetailType.GEM.getIndex()){
			return;
		}
		// 判断该装备是否有合适的孔用来镶嵌
		if(!equipItem.canEmbedGem(gemItem,message.getIndex())){
			return;
		}
		// 判断是否有足够的金钱去镶嵌宝石
		MaterialItemFeature itemFeatrue = (MaterialItemFeature)gemItem.getFeature();
		CurrencyType costCurrencyType = itemFeatrue.getGemEmbedCurrencyType();
		int costCurrencyNum = itemFeatrue.getGemEnbedCurrencyNum();
		if(!human.getWallet().isEnough(costCurrencyType, costCurrencyNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, costCurrencyType.getDesc());
			return;
		}
		// 扣除金钱并删除宝石
		if(human.getWallet().costMoney(costCurrencyType, costCurrencyNum,MoneyLogReason.GEM_EMBED,"")
				&& bagManager.removeItem(gemBagType, gemBagIndex, 1, ItemLogReason.GEM_EMBED, "")){
			// 生成新的装备
			if(equipItem.embedGem(gemItem,message.getIndex())){
				bagManager.updateItem(equipItem.getBagType(), equipItem.getBagIndex());
				// 镶嵌成功消息
				GCGemEmbed gcMsg = new GCGemEmbed();
				gcMsg.setIndex(message.getIndex());
				gcMsg.setItem(CommonItemBuilder.converToCommonItem(gemItem));
				human.sendMessage(gcMsg);
			}
		}
	}

}
