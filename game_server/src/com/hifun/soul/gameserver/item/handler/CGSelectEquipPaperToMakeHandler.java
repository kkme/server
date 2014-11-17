package com.hifun.soul.gameserver.item.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
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
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.msg.CGSelectEquipPaperToMake;
import com.hifun.soul.gameserver.item.msg.GCUpdateEquipMakePanel;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipMakeGuideTemplate;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

@Component
public class CGSelectEquipPaperToMakeHandler implements IMessageHandlerWithType<CGSelectEquipPaperToMake> {
	private static Logger logger = Loggers.ITEM_LOGGER;
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private EquipMakeTemplateManager templateManager;
	@Autowired
	private StageTemplateManager stageTemplateManager;
	@Autowired
	private ItemTemplateManager itemTemplateManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_SELECT_EQUIP_PAPER_TO_MAKE;
	}

	@Override
	public void execute(CGSelectEquipPaperToMake message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_MAKE, true)){
			return;
		}
		int bagIndex = message.getBagIndex();
		HumanBagManager bagManager = human.getBagManager();
		Item equipPaper = bagManager.getItem(BagType.MAIN_BAG, bagIndex);
		if(equipPaper==null){
			logger.info("选择的装备图纸为null,不能用于装备的打造。[humanId:"+human.getHumanGuid());
			return;
		}
		if(equipPaper.getType() != ItemDetailType.EQUIPPAPER.getIndex()){
			logger.info("选择的物品不是装备图纸,不能用于装备的打造。[humanId:"+human.getHumanGuid());
			return;
		}
		EquipMakeTemplate template = templateManager.getEquipMakeTemplate(equipPaper.getItemId());
		if(template == null){
			logger.info("装备打造模板中没有该图纸的打造配置。[paperItemId:"+equipPaper.getItemId()+"][humanId:"+human.getHumanGuid());
			return;
		}
		// 判断该图纸打造完成的装备是否是自己可以使用的装备
		ItemTemplate itemTemplate = itemTemplateManager.getItemTemplate(template.getEquipItemId());
		if(itemTemplate == null){
			return;
		}
		if(itemTemplate.getOccupationLimit() > 0
				&& itemTemplate.getOccupationLimit() != human.getOccupation().getIndex()){
			human.sendErrorMessage(LangConstants.EQUIPMAKE_LIMITOCCUPATION);
			return;
		}
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
		// 打造新装备需要的装备
		Item equip = ItemFactory.creatNewItem(human, template.getNeedEquipId());
		CommonItem equipInfo = CommonItemBuilder.converToCommonItem(equip);
		
		GCUpdateEquipMakePanel gcMsg = new GCUpdateEquipMakePanel();
		gcMsg.setCostMoney(template.getCostMomey());
		gcMsg.setCurrencyType(template.getCurrencyType());
		gcMsg.setIsMoneyEnough(isMoneyEnough);
		gcMsg.setHasMaterialNums(hasNums);
		gcMsg.setMaterialItem(items);
		gcMsg.setNeedMaterialNums(needNums);
		gcMsg.setEquip(equipInfo);
		gcMsg.setEquipNum(bagManager.getItemCount(template.getNeedEquipId()));
		gcMsg.setGuides(guides);
		gcMsg.setNeedPaperNum(template.getNeedPaperNum());
		gcMsg.setHasPaperNum(bagManager.getItemCount(equipPaper.getItemId()));
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
