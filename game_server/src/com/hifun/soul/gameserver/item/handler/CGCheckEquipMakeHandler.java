package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.msg.CGCheckEquipMake;
import com.hifun.soul.gameserver.item.msg.GCCheckEquipMake;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGCheckEquipMakeHandler implements
		IMessageHandlerWithType<CGCheckEquipMake> {

	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private EquipMakeTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CHECK_EQUIP_MAKE;
	}

	@Override
	public void execute(CGCheckEquipMake message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_MAKE, false)){
			return;
		}
		
		HumanBagManager bagManager = human.getBagManager();
		boolean clothMake = checkEquipMake(bagManager, ItemDetailType.CLOTH.getIndex());
		boolean hatMake = checkEquipMake(bagManager, ItemDetailType.HAT.getIndex());
		boolean necklaceMake = checkEquipMake(bagManager, ItemDetailType.NECKLACE.getIndex());
		boolean shoesMake = checkEquipMake(bagManager, ItemDetailType.SHOES.getIndex());
		boolean weaponMake = checkEquipMake(bagManager, ItemDetailType.WEAPON.getIndex());
		
		GCCheckEquipMake gcMsg = new GCCheckEquipMake();
		gcMsg.setCloth(clothMake);
		gcMsg.setHat(hatMake);
		gcMsg.setNecklace(necklaceMake);
		gcMsg.setShoes(shoesMake);
		gcMsg.setWeapon(weaponMake);
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 校验对应位置的装备是否可以升级
	 * 
	 * @param bagManager
	 * @param index
	 * @return
	 */
	private boolean checkEquipMake(HumanBagManager bagManager, int index) {
		boolean state = false;
		Item cloth = bagManager.getEquipItem(index);
		if(cloth != null){
			EquipMakeTemplate template = templateManager.getEquipMakeTemplateByEquipId(cloth.getItemId());
			if(template != null){
				int count = bagManager.getItemCount(template.getPaperId());
				if(count > 0){
					state = true;
				}
			}
		}
		
		return state;
	}

}
