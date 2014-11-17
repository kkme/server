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
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.CGSelectEquip;
import com.hifun.soul.gameserver.item.msg.GCSelectEquip;
import com.hifun.soul.gameserver.item.service.EquipUpgradeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.EquipUpgradeTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGSelectEquipHandler implements 
	IMessageHandlerWithType<CGSelectEquip>{

	@Autowired
	private EquipUpgradeTemplateManager equipUpgradeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SELECT_EQUIP;
	}

	@Override
	public void execute(CGSelectEquip message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_UPGRADE, true)){
			return;
		}
		
		HumanBagManager bagManager = human.getBagManager();
		BagType bagType = BagType.indexOf(message.getEquipBagType());
		int bagIndex = message.getEquipBagIndex();
		Item equip = bagManager.getItem(bagType, bagIndex);
		if(equip == null
				|| !equip.isEquip()){
			return;
		}
		
		// 判断该装备是否是该职业的装备
		EquipItemTemplate equipItemTemplate = (EquipItemTemplate)equip.getTemplate();
		if(equipItemTemplate.getLimitOccupation() > 0
				&& equipItemTemplate.getLimitOccupation() != human.getOccupation().getIndex()){
			human.sendErrorMessage(LangConstants.UPGRADE_LIMITOCCUPATION);
			return;
		}
		
		EquipItemFeature equipItemFeature = (EquipItemFeature)equip.getFeature();
		EquipUpgradeTemplate equipUpgradeTemplate 
			= equipUpgradeTemplateManager.getEquipUpgradeTemplate(equip.getItemId(),equipItemFeature.getLevel());
		if(equipUpgradeTemplate == null){
			human.sendErrorMessage(LangConstants.EQUIP_UPGRADE_FULL);
			return;
		}
		
		GCSelectEquip gcMsg = new GCSelectEquip();
		gcMsg.setRate(equipUpgradeTemplate.getUpgradeRate());
		gcMsg.setCurrencyType(equipUpgradeTemplate.getCostCurrencyType());
		gcMsg.setCurrencyNum(equipUpgradeTemplate.getCostCurrencyNum());
		gcMsg.setUpgradeAttributes(
				equipUpgradeTemplateManager.getEquipUpgradeAttributes(equip.getItemId(), equipItemFeature.getLevel()+1));
		gcMsg.setUpgradeStone(CommonItemBuilder.genCommonItem(equipUpgradeTemplate.getMaterialId()));
		gcMsg.setNeedUpgradeStoneNum(equipUpgradeTemplate.getMaterialNum());
		human.sendMessage(gcMsg);
		
//		human.getHumanGuideManager().showGuide(GuideType.EQUIP_UPGRADE.getIndex());
	}

}
