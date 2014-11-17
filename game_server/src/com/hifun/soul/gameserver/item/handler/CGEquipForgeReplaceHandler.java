package com.hifun.soul.gameserver.item.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.msg.CGEquipForgeReplace;

@Component
public class CGEquipForgeReplaceHandler implements
		IMessageHandlerWithType<CGEquipForgeReplace> {

	@Override
	public short getMessageType() {
		return MessageType.CG_EQUIP_FORGE_REPLACE;
	}

	@Override
	public void execute(CGEquipForgeReplace message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(human, GameFuncType.STONE_FORGE, true)){
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		BagType equipBagType = BagType.indexOf(message.getEquipBagType());
		if(equipBagType == null){
			return;
		}
		int equipBagIndex = message.getEquipBagIndex();
		// 选中的不是装备直接返回
		Item equipItem = bagManager.getItem(equipBagType, equipBagIndex);
		if(equipItem == null || !equipItem.isEquip()){
			return;
		}
		// 更新洗练的消耗信息
		human.getHumanForgeManager().replaceForge(equipItem);
	}

}
