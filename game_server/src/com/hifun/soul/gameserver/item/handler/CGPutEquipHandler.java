package com.hifun.soul.gameserver.item.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.EmbedGemInfo;
import com.hifun.soul.gameserver.item.assist.GemItemInfo;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.msg.CGPutEquip;
import com.hifun.soul.gameserver.item.msg.GCPutEquip;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGPutEquipHandler implements IMessageHandlerWithType<CGPutEquip> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_PUT_EQUIP;
	}

	@Override
	public void execute(CGPutEquip message) {
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
		
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		GemItemInfo[] gemItemInfos = equipItemFeature.getGemItemInfos();
		if(gemItemInfos == null){
			gemItemInfos = new GemItemInfo[0];
		}
		
		List<EmbedGemInfo> gemInfoList = new ArrayList<EmbedGemInfo>();
		for(int i=0; i<gemItemInfos.length; i++){
			if(gemItemInfos[i] == null){
				continue;
			}
			
			Item item = ItemFactory.creatNewItem(human, gemItemInfos[i].getItemId());
			((MaterialItemFeature)item.getFeature()).setGemAttributes(Arrays.asList(gemItemInfos[i].getEquipGemAttributes()));
			CommonItem commonItem = CommonItemBuilder.converToCommonItem(item);
			EmbedGemInfo embedGemInfo = new EmbedGemInfo();
			embedGemInfo.setIndex(i);
			embedGemInfo.setCommonItem(commonItem);
			gemInfoList.add(embedGemInfo);
		}
		
		GCPutEquip gcMsg = new GCPutEquip();
		gcMsg.setItem(CommonItemBuilder.converToCommonItem(equipItem));
		gcMsg.setGems(gemInfoList.toArray(new EmbedGemInfo[0]));
		human.sendMessage(gcMsg);
		
//		human.getHumanGuideManager().showGuide(GuideType.EQUIP_EMBED.getIndex());
	}

}
