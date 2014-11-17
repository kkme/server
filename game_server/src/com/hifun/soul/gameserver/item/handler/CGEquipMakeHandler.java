package com.hifun.soul.gameserver.item.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.CGEquipMake;
import com.hifun.soul.gameserver.item.msg.GCEquipMake;
import com.hifun.soul.gameserver.item.service.EquipMakeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;

/**
 * 装备打造消息处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGEquipMakeHandler implements IMessageHandlerWithType<CGEquipMake> {

	@Autowired
	private EquipMakeTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_EQUIP_MAKE;
	}

	@Override
	public void execute(CGEquipMake message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_MAKE, true)){
			return;
		}
		
		// 判断是否有图纸
		HumanBagManager bagManager = human.getBagManager();
		BagType paperBagType = BagType.indexOf(message.getPaperBagType());
		int paperBagIndex = message.getPaperBagIndex();
		Item paperItem = bagManager.getItem(paperBagType, paperBagIndex);
		if (paperItem == null || paperItem.getType() != ItemDetailType.EQUIPPAPER.getIndex()) {
			return;
		}
		
		EquipMakeTemplate template = templateManager.getEquipMakeTemplate(paperItem.getItemId());
		// 判断图纸数量是否足够
		if (bagManager.getItemCount(paperItem.getItemId()) < template.getNeedPaperNum()) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, paperItem.getName());
			return;
		}
		
		// 判断是否是所需装备并且是否有所需装备
		BagType equipBagType = BagType.indexOf(message.getEquipBagType());
		int equipBagIndex = message.getEquipBagIndex();
		Item equipItem = bagManager.getItem(equipBagType, equipBagIndex);
		if (equipItem == null || equipItem.getItemId() != template.getNeedEquipId()) {
			return;
		}
		
		// 判断是否有足够的金钱
		CurrencyType currencyType = CurrencyType.indexOf(template.getCurrencyType());
		boolean isMoneyEnough = human.getWallet().costMoney(currencyType, template.getCostMomey(),MoneyLogReason.EQUIP_MAKE,"");
		if (isMoneyEnough == false) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, currencyType.getDesc());
			return;
		}
		
		// 判断所需要的材料是否足够
		Map<Integer, Integer> idNumMap = new HashMap<Integer, Integer>();
		String[] materialIds = template.getMaterialItemIds().split(",");
		String[] needNumStrs = template.getMaterialItemNum().split(",");
		int[] needNums = new int[materialIds.length];
		for(int i=0; i<materialIds.length; i++){
			int itemId = Integer.parseInt(materialIds[i]);
			needNums[i] = Integer.parseInt(needNumStrs[i]);
			if(idNumMap.get(itemId) == null){
				idNumMap.put(itemId, needNums[i]);
			}
			else{
				idNumMap.put(itemId, idNumMap.get(itemId)+needNums[i]);
			}
		}
		int itemCount = 0;
		for (int itemId : idNumMap.keySet()) {
			itemCount = bagManager.getItemCount(itemId);
			if (itemCount < idNumMap.get(itemId)) {
				return;
			}
		}
		
		// 判断合成之后的装备的等级
		EquipItemFeature oldFeature = (EquipItemFeature)equipItem.getFeature();
		int oldEquipLevel = oldFeature.getLevel();
		int newEquipLevel = oldEquipLevel-GameServerAssist.getGameConstants().getEquipMakeDownLevel()>0?oldEquipLevel-GameServerAssist.getGameConstants().getEquipMakeDownLevel():0;
		boolean isEquiped = oldFeature.isEquiped();
		// 生成新装备
		Item equip = ItemFactory.creatNewItem(human, template.getEquipItemId());
		if (equip == null) {
			return;
		}
		
		((EquipItemFeature)equip.getFeature()).setLevel(newEquipLevel,null,"");
		
		// 删除材料
		for (int i = 0; i < materialIds.length; i++) {
			Item material = ItemFactory.creatNewItem(human, Integer.parseInt(materialIds[i]));
			material.setOverlapNum(needNums[i]);
			bagManager.removeItemByItemId(Integer.parseInt(materialIds[i]),needNums[i],ItemLogReason.EQUIP_MAKE,"");
		}		
		// 删除原装备
		bagManager.removeItem(equipItem.getBagType(),equipItem.getBagIndex(),1,ItemLogReason.EQUIP_MAKE,"");
		// 删除图纸
		bagManager.removeItemByItemId(paperItem.getItemId(),template.getNeedPaperNum(),ItemLogReason.EQUIP_MAKE,"");
		
		// 新装备加入背包
		if (bagManager.putItem(BagType.MAIN_BAG, equip,ItemLogReason.EQUIP_MAKE,"")) {
			if(isEquiped && bagManager.canEquip((EquipItem)equip)){
				bagManager.equipItem(equip.getBagIndex());
			}
			GCEquipMake gcMsg = new GCEquipMake();
			CommonItem[] items = new CommonItem[1];
			items[0] = CommonItemBuilder.converToCommonItem(equip);
			gcMsg.setItem(items);
			gcMsg.setMakeResult(true);
			human.sendMessage(gcMsg);
			human.getHumanGuideManager().showGuide(GuideType.EQUIP_MAKE_SUCCESS.getIndex());
		}
	}

}
