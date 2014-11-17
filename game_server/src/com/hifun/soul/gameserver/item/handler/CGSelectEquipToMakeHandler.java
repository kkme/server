package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.msg.CGSelectEquipToMake;
import com.hifun.soul.gameserver.item.msg.GCSelectEquipToMake;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipMakeGuideTemplate;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

@Component
public class CGSelectEquipToMakeHandler implements
		IMessageHandlerWithType<CGSelectEquipToMake> {

	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private EquipMakeTemplateManager templateManager;
	@Autowired
	private StageTemplateManager stageTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SELECT_EQUIP_TO_MAKE;
	}

	@Override
	public void execute(CGSelectEquipToMake message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_MAKE, true)){
			return;
		}
		
		BagType bagType = BagType.indexOf(message.getEquipBagType());
		if(bagType == null){
			return;
		}
		int bagIndex = message.getEquipBagIndex();
		HumanBagManager bagManager = human.getBagManager();
		Item oldEquip = bagManager.getItem(bagType, bagIndex);
		if(oldEquip==null){
			return;
		}
		EquipMakeTemplate template = templateManager.getEquipMakeTemplateByEquipId(oldEquip.getItemId());
		if(template == null){
			return;
		}
		// 判断背包中是否有图纸
		if(bagManager.getItemCount(template.getPaperId()) <= 0){
			return;
		}
		Item paper = bagManager.getItemsFromMainBag(template.getPaperId(), 1).get(0);
		int[] materialIds = template.getMaterialIdArray();
		int[] needNums = template.getMaterialNumArray();	
		int[] hasNums = new int[materialIds.length];
		CommonItem[] items = new CommonItem[materialIds.length];
		String[] guides = new String[materialIds.length];
		int itemCount=0;
		for(int i=0;i<items.length;i++){
			int itemId = materialIds[i];
			itemCount = bagManager.getItemCount(itemId);
			hasNums[i] = itemCount;
			Item material = ItemFactory.creatNewItem(human, itemId);
			items[i] = CommonItemBuilder.converToCommonItem(material);
			// 反复跟策划确认过不会出现模版不存在问题，为防止配置错误断开，做一点保险措施，再有问题就要调整消息结构
			guides[i] = getGuideStageName(itemId);
		}
		boolean isMoneyEnough = human.getWallet().isEnough(CurrencyType.indexOf(template.getCurrencyType()), template.getCostMomey());
		
		GCSelectEquipToMake gcMsg = new GCSelectEquipToMake();
		gcMsg.setCostMoney(template.getCostMomey());
		gcMsg.setCurrencyType(template.getCurrencyType());
		gcMsg.setIsMoneyEnough(isMoneyEnough);
		gcMsg.setHasMaterialNums(hasNums);
		gcMsg.setMaterialItem(items);
		gcMsg.setNeedMaterialNums(needNums);
		gcMsg.setEquip(CommonItemBuilder.converToCommonItem(oldEquip));
		gcMsg.setEquipNum(bagManager.getItemCount(template.getNeedEquipId()));
		gcMsg.setGuides(guides);
		gcMsg.setPaper(CommonItemBuilder.converToCommonItem(paper));
		human.sendMessage(gcMsg);
		
	}
	
	private String getGuideStageName(int itemId) {
		EquipMakeGuideTemplate guideTemplate = templateManager.getEquipMakeGuideTemplate(itemId);
		if(guideTemplate != null){
			String[] mapAndStageId = guideTemplate.getGuide().split(",");
			if(mapAndStageId.length >= 2){
				StageTemplate stageTemplate = stageTemplateManager.getStageTemplate(Integer.parseInt(mapAndStageId[1]));
				if(stageTemplate != null){
					return stageTemplate.getStageName();
				}
			}
		}
		return "";
	}

}
