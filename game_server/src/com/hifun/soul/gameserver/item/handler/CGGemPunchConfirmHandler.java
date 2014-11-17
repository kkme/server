package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.CGGemPunchConfirm;
import com.hifun.soul.gameserver.item.msg.GCGemPunchConfirm;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGemPunchConfirmHandler implements
		IMessageHandlerWithType<CGGemPunchConfirm> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GEM_PUNCH_CONFIRM;
	}

	@Override
	public void execute(CGGemPunchConfirm message) {
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
		
		// 判断选中装备的合法性
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		
		if(!equipItem.canGemPunch()){
			human.sendErrorMessage(LangConstants.MAX_HOLE);
			return;
		}
		
		EquipItemFeature itemFeature = (EquipItemFeature) equipItem
				.getFeature();
		int itemId = itemFeature.getNextGemPunchItemId();
		SimpleCommonItem commonItem = CommonItemBuilder
				.genSimpleCommonItem(itemId);
		if (commonItem == null) {
			return;
		}
		int itemNum = itemFeature.getNextGemPunchItemNum();

		GCGemPunchConfirm gcMsg = new GCGemPunchConfirm();
		gcMsg.setItemName(commonItem.getName());
		gcMsg.setItemNum(itemNum);
		human.sendMessage(gcMsg);
		
	}

}
