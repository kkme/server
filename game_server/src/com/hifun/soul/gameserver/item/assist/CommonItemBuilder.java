package com.hifun.soul.gameserver.item.assist;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;
import com.hifun.soul.gameserver.item.template.EquipUpgradeTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;


/**
 * 构建CommonItem
 * @author magicstone
 */
public class CommonItemBuilder {
	
	/**
	 * item转化为CommonItem
	 * @param item
	 * @return
	 */
	public static CommonItem converToCommonItem(Item item) {
		if(item == null){
			return null;
		}
		CommonItem commonItem = new CommonItem();
		commonItem.setUUID(item.getUUID());
		commonItem.setItemId(item.getItemId());
		commonItem.setName(item.getName());
		commonItem.setDesc(item.getDesc());
		commonItem.setType(item.getType());
		commonItem.setIcon(item.getIcon());
		commonItem.setRarity(item.getRarity());
		commonItem.setBind(item.getBind());
		commonItem.setOverlapNum(item.getOverlapNum());
		commonItem.setBagIndex(item.getBagIndex());
		commonItem.setBagType(item.getBagType().getIndex());
		commonItem.setSellCurrencyType(item.getSellCurrencyType());
		commonItem.setSellNum(item.getSellCurrencyNum());
		commonItem.setMaxOverlap(item.getMaxOverlap());
		commonItem.setCanSell(item.isCanSell());
		// 对于特殊属性，首先设置初始值
		commonItem.setPosition(0);
		commonItem.setEndure(0);
		commonItem.setEquipHole(0);
		commonItem.setMaxEquipHole(0);
		commonItem.setEmbedCurrencyType((short)0);
		commonItem.setEmbedCurrencyNum(0);
		commonItem.setExtractCurrencyType((short)0);
		commonItem.setExtractCurrencyNum(0);
		KeyValuePair<Integer, Integer>[] equipBaseAttributes = KeyValuePair
				.newKeyValuePairArray(0);
		commonItem.setEquipBaseAttributes(equipBaseAttributes);
		KeyValuePair<Integer, Integer>[] equipSpecialAttributes = KeyValuePair
				.newKeyValuePairArray(0);
		commonItem.setEquipSpecialAttributes(equipSpecialAttributes);
		KeyValuePair<Integer, Integer>[] equipUpgradeAttributes = KeyValuePair
				.newKeyValuePairArray(0);
		commonItem.setEquipUpgradeAttributes(equipUpgradeAttributes);
		KeyValuePair<Integer, Integer>[] gemAttributes = KeyValuePair
				.newKeyValuePairArray(0);
		commonItem.setGemAttributes(gemAttributes);
		GemItemInfo[] equipGemItemInfos = new GemItemInfo[0];
		commonItem.setEquipGemItemInfos(equipGemItemInfos);
		KeyValuePair<String, Integer>[] materialsOfEquipPaper = KeyValuePair.newKeyValuePairArray(0);
		commonItem.setMaterialsOfEquipPaper(materialsOfEquipPaper);
		if(item.isEquip()){
			EquipItemFeature equipItemFeature = (EquipItemFeature)item.getFeature();
			EquipUpgradeTemplate upgradeTemplate = GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeTemplate(item.getItemId(), equipItemFeature.getLevel());
			if(upgradeTemplate!=null){
				commonItem.setSellNum(upgradeTemplate.getSellPrice());
			}
			commonItem.setPosition(equipItemFeature.getPosition());
			commonItem.setEndure(equipItemFeature.getEndure());
			commonItem.setEquipHole(equipItemFeature.getEquipHole());
			commonItem.setMaxEquipHole(equipItemFeature.getMaxEquipHole());
			commonItem.setUpgradeLevel(equipItemFeature.getLevel());
			commonItem.setIsEquiped(equipItemFeature.isEquiped());
			// 装备基础属性
			List<KeyValuePair<Integer, Integer>> _equipBaseAttributes = 
					equipItemFeature.getEquipBaseAttributes();
			if(equipBaseAttributes == null){
				KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
						.newKeyValuePairArray(0);
				commonItem.setEquipBaseAttributes(emptyAttributes);
			}
			else{
				KeyValuePair<Integer, Integer>[] attributes = KeyValuePair
						.newKeyValuePairArray(_equipBaseAttributes.size());
				for(int i=0; i<attributes.length; i++) {
					attributes[i] = _equipBaseAttributes.get(i);
				}
				commonItem.setEquipBaseAttributes(attributes);
			}
			// 装备特殊属性
			List<KeyValuePair<Integer, Integer>> _equipSpecialAttributes = 
					equipItemFeature.getEquipSpecialAttributes();
			if(_equipSpecialAttributes == null){
				KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
						.newKeyValuePairArray(0);
				commonItem.setEquipSpecialAttributes(emptyAttributes);
			}
			else{
				KeyValuePair<Integer, Integer>[] attributes = KeyValuePair
						.newKeyValuePairArray(_equipSpecialAttributes.size());
				for(int i=0; i<attributes.length; i++) {
					attributes[i] = _equipSpecialAttributes.get(i);
				}
				commonItem.setEquipSpecialAttributes(attributes);
			}
			
			// 装备宝石
			GemItemInfo[] _gemItemInfos = equipItemFeature.getGemItemInfos();
			List<GemItemInfo> _gemItemInfoList = new ArrayList<GemItemInfo>();
			for(int i=0; i<_gemItemInfos.length; i++){
				if(_gemItemInfos[i] == null || _gemItemInfos[i].getEquipGemAttributes() == null){
					continue;
				}
				else{
					_gemItemInfoList.add(_gemItemInfos[i]);
				}
			}
			commonItem.setEquipGemItemInfos(_gemItemInfoList.toArray(new GemItemInfo[_gemItemInfoList.size()]));
			// 装备强化属性
			commonItem.setEquipUpgradeAttributes(GameServerAssist.getEquipUpgradeTemplateManager().getEquipUpgradeAttributes(item.getItemId(), 
					equipItemFeature.getLevel()));
			EquipItem equipItem = (EquipItem)item;
			commonItem.setLimitOccupation(equipItem.getLimitOccupation());
			commonItem.setLimitLevel(equipItem.getLimitLevel());
		}
		else if(item.getType() == ItemDetailType.GEM.getIndex()){
			MaterialItemFeature materialItemFeature = (MaterialItemFeature)item.getFeature();
			commonItem.setEmbedCurrencyType((short)materialItemFeature.getGemEmbedCurrencyType().getIndex());
			commonItem.setEmbedCurrencyNum(materialItemFeature.getGemEnbedCurrencyNum());
			commonItem.setExtractCurrencyType((short)materialItemFeature.getGemExtractCurrencyType().getIndex());
			commonItem.setExtractCurrencyNum(materialItemFeature.getGemExtractCurrencyNum());
			// 宝石属性
			List<KeyValuePair<Integer, Integer>> _gemAttributes = 
					materialItemFeature.getGemAttributes();
			if(gemAttributes == null){
				KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
						.newKeyValuePairArray(0);
				commonItem.setGemAttributes(emptyAttributes);
			}
			else{
				KeyValuePair<Integer, Integer>[] attributes = KeyValuePair
						.newKeyValuePairArray(_gemAttributes.size());
				for(int i=0; i<attributes.length; i++) {
					attributes[i] = _gemAttributes.get(i);
				}
				commonItem.setGemAttributes(attributes);
			}
		}
		else if(item.getType() == ItemDetailType.FORTUNESTONE.getIndex()){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			commonItem.setExtraSuccessRate(consumeItemFeature.getExtraSuccessRate());
		}
		else if(item.getType() == ItemDetailType.EQUIPPAPER.getIndex()){
			//装备图纸所需材料
			EquipMakeTemplate equipPaperTemplate = GameServerAssist.getEquipMakeTemplateManager().getEquipMakeTemplate(item.getItemId());
			if (equipPaperTemplate != null) {				
				String[] materialIds = equipPaperTemplate.getMaterialItemIds().split(",");
				String[] needNumStrs = equipPaperTemplate.getMaterialItemNum().split(",");
				materialsOfEquipPaper = KeyValuePair.newKeyValuePairArray(materialIds.length);
				for (int i = 0; i < materialsOfEquipPaper.length; i++) {
					ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(Integer.parseInt(materialIds[i]));
					materialsOfEquipPaper[i] = new KeyValuePair<String, Integer>();
					materialsOfEquipPaper[i].setKey(itemTemplate.getName());
					materialsOfEquipPaper[i].setValue(Integer.parseInt(needNumStrs[i]));
				}
			}
			commonItem.setMaterialsOfEquipPaper(materialsOfEquipPaper);
		}
		if(item.getItemId() == ItemConstantId.BOSS_WAR_DAMAGE_SPREE_ID){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			String desc = MessageFormat.format(commonItem.getDesc(), consumeItemFeature.getDynamicPropertyValue(DynamicPropertyType.HONOUR)
					,consumeItemFeature.getDynamicPropertyValue(DynamicPropertyType.COIN));
			commonItem.setDesc(desc);
		}
		if(item.getItemId() == ItemConstantId.MARS_KILL_REWARD_ID){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			String desc = MessageFormat.format(commonItem.getDesc(), consumeItemFeature.getDynamicPropertyValue(DynamicPropertyType.COIN));
			commonItem.setDesc(desc);
		}
		if(item.getItemId() == ItemConstantId.LEGION_BOSS_DAMAGE_SPREE_ID){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			String desc = MessageFormat.format(commonItem.getDesc(), consumeItemFeature.getDynamicPropertyValue(DynamicPropertyType.HONOUR)
					,consumeItemFeature.getDynamicPropertyValue(DynamicPropertyType.COIN));
			commonItem.setDesc(desc);
		}
		return commonItem;
	}
	
	/**
	 * 根据itemId生成CommonItem
	 * @param itemId
	 * @return
	 */
	public static CommonItem genCommonItem(Integer itemId) {
		Item item = ItemFactory.creatNewItem(null, itemId);
		if(item == null) {
			return null;
		}
		return CommonItemBuilder.converToCommonItem(item);
	}
	
	/**
	 * 根据itemIds生成CommonItem[]
	 * @param itemIds
	 * @return
	 */
	public static CommonItem[] genCommonItems(int[] itemIds) {
		if(itemIds == null
				|| itemIds.length == 0){
			return null;
		}
		List<CommonItem> items = new ArrayList<CommonItem>();
		for(int itemId : itemIds){
			CommonItem commonItem = genCommonItem(itemId);
			if(commonItem != null){
				items.add(commonItem);
			}
		}
		return items.toArray(new CommonItem[0]);
	}
	
	/**
	 * 根据itemId获取物品的简单信息
	 * @param itemId
	 * @return
	 */
	public static SimpleCommonItem genSimpleCommonItem(Integer itemId) {
		SimpleCommonItem simpleCommonItem = new SimpleCommonItem();
		ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(itemId);
		if(itemTemplate == null){
			return null;
		}
		simpleCommonItem.setItemId(itemId);
		simpleCommonItem.setName(itemTemplate.getName());
		simpleCommonItem.setDesc(itemTemplate.getDesc());
		simpleCommonItem.setIcon(itemTemplate.getIcon());
		return simpleCommonItem;
	}
	
	/**
	 * 根据itemIds生成SimpleCommonItem[]
	 * @param itemIds
	 * @return
	 */
	public static SimpleCommonItem[] genSimpleCommonItems(int[] itemIds) {
		if(itemIds == null
				|| itemIds.length == 0){
			return null;
		}
		List<SimpleCommonItem> items = new ArrayList<SimpleCommonItem>();
		for(Integer itemId : itemIds){
			ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(itemId);
			if(itemTemplate == null){
				continue;
			}
			SimpleCommonItem simpleCommonItem = new SimpleCommonItem();
			simpleCommonItem.setItemId(itemId);
			simpleCommonItem.setName(itemTemplate.getName());
			simpleCommonItem.setDesc(itemTemplate.getDesc());
			simpleCommonItem.setIcon(itemTemplate.getIcon());
			items.add(simpleCommonItem);
		}
		return items.toArray(new SimpleCommonItem[0]);
	}
	
	/**
	 * 转化itemList为CommonItem[]
	 * @param itemList
	 * @return
	 */
	public static CommonItem[] converToCommonItemArray(List<Item> itemList){
		if(itemList==null){
			return new CommonItem[0];
		}
		CommonItem[] array = new CommonItem[itemList.size()];
		for(int i =0;i<array.length;i++){
			array[i] = converToCommonItem(itemList.get(i));
		}
		return array;
	}
}
