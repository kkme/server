package com.hifun.soul.gameserver.item.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.GemItemInfo;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.msg.CGGemExtract;
import com.hifun.soul.gameserver.item.msg.GCGemExtract;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGemExtractHandler implements IMessageHandlerWithType<CGGemExtract>{
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GEM_EXTRACT;
	}

	@Override
	public void execute(CGGemExtract message) {
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
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		int gemIndex = message.getGemIndex();
		if(gemIndex < 0){
			return;
		}
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		GemItemInfo gemItemInfo = equipItemFeature.getGemItemInfo(gemIndex);
		if(gemItemInfo == null){
			return;
		}
		Item gemItem = ItemFactory.creatNewItem(human, gemItemInfo.getItemId());
		MaterialItemFeature materialItemFeature = (MaterialItemFeature)gemItem.getFeature();
		// 判断卸下宝石的金钱是否足够
		CurrencyType costCurrencyType = materialItemFeature.getGemExtractCurrencyType();
		int costCurrencyNum = materialItemFeature.getGemExtractCurrencyNum();
		if(!human.getWallet().isEnough(costCurrencyType, costCurrencyNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, costCurrencyType.getDesc());
			return;
		}
		// 判断是否有足够的位置
		if(!human.getBagManager().canContain(gemItem)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 消耗金钱
		if(human.getWallet().costMoney(costCurrencyType, costCurrencyNum, MoneyLogReason.GEM_PUNCH, "")){
			// 生成属性与原属性一样的宝石
			List<KeyValuePair<Integer,Integer>> equipGemAttributeList = new ArrayList<KeyValuePair<Integer,Integer>>();
			KeyValuePair<Integer,Integer>[] equipGemAttributes = gemItemInfo.getEquipGemAttributes();
			if(equipGemAttributes != null){
				for(KeyValuePair<Integer,Integer> equipGemAttribute : equipGemAttributes){
					equipGemAttributeList.add(equipGemAttribute);
				}
			}
			materialItemFeature.setGemAttributes(equipGemAttributeList);
			// 卸下原装备上的宝石
			equipItemFeature.removeGameItemInfo(gemIndex);
			// 玩家添加宝石
			bagManager.putItem(BagType.MAIN_BAG, gemItem, ItemLogReason.GEM_EXTRACT, "");
			// 下发背包更新消息
			bagManager.updateItem(equipItem.getBagType(), equipItem.getBagIndex());
			// 通知客户端
			GCGemExtract gcMsg = new GCGemExtract();
			gcMsg.setGemIndex((short) gemIndex);
			human.sendMessage(gcMsg);
		}
	}

}
