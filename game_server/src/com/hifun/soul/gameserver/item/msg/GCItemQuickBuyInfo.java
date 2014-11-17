package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 可快捷购买的物品消息
 *
 * @author SevenSoul
 */
@Component
public class GCItemQuickBuyInfo extends GCMessage{
	
	/** 铁匠铺用于显示的物品 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] quickBuyItems;
	/** 可快捷购买的物品id */
	private int[] quickBuyItemIds;

	public GCItemQuickBuyInfo (){
	}
	
	public GCItemQuickBuyInfo (
			com.hifun.soul.gameserver.item.assist.CommonItem[] quickBuyItems,
			int[] quickBuyItemIds ){
			this.quickBuyItems = quickBuyItems;
			this.quickBuyItemIds = quickBuyItemIds;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		quickBuyItems = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objquickBuyItems = new com.hifun.soul.gameserver.item.assist.CommonItem();
			quickBuyItems[i] = objquickBuyItems;
					objquickBuyItems.setUUID(readString());
							objquickBuyItems.setItemId(readInteger());
							objquickBuyItems.setName(readString());
							objquickBuyItems.setDesc(readString());
							objquickBuyItems.setType(readInteger());
							objquickBuyItems.setIcon(readInteger());
							objquickBuyItems.setRarity(readInteger());
							objquickBuyItems.setBind(readInteger());
							objquickBuyItems.setOverlapNum(readInteger());
							objquickBuyItems.setBagType(readInteger());
							objquickBuyItems.setBagIndex(readInteger());
							objquickBuyItems.setSellCurrencyType(readShort());
							objquickBuyItems.setSellNum(readInteger());
							objquickBuyItems.setPosition(readInteger());
							objquickBuyItems.setEndure(readInteger());
							objquickBuyItems.setEquipHole(readShort());
							objquickBuyItems.setEmbedCurrencyType(readShort());
							objquickBuyItems.setEmbedCurrencyNum(readInteger());
							objquickBuyItems.setExtractCurrencyType(readShort());
							objquickBuyItems.setExtractCurrencyNum(readInteger());
							objquickBuyItems.setShopCurrencyType(readShort());
							objquickBuyItems.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objquickBuyItems.setEquipBaseAttributes(subListequipBaseAttributes);
	for(int jequipBaseAttributes = 0; jequipBaseAttributes < subCountequipBaseAttributes; jequipBaseAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipBaseAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipBaseAttributes[jequipBaseAttributes] = objequipBaseAttributes;
						objequipBaseAttributes.setKey(readInteger());
								objequipBaseAttributes.setValue(readInteger());
							}
	}
								{
	int subCountequipSpecialAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipSpecialAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipSpecialAttributes);
		objquickBuyItems.setEquipSpecialAttributes(subListequipSpecialAttributes);
	for(int jequipSpecialAttributes = 0; jequipSpecialAttributes < subCountequipSpecialAttributes; jequipSpecialAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipSpecialAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipSpecialAttributes[jequipSpecialAttributes] = objequipSpecialAttributes;
						objequipSpecialAttributes.setKey(readInteger());
								objequipSpecialAttributes.setValue(readInteger());
							}
	}
								{
	int subCountgemAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListgemAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountgemAttributes);
		objquickBuyItems.setGemAttributes(subListgemAttributes);
	for(int jgemAttributes = 0; jgemAttributes < subCountgemAttributes; jgemAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objgemAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListgemAttributes[jgemAttributes] = objgemAttributes;
						objgemAttributes.setKey(readInteger());
								objgemAttributes.setValue(readInteger());
							}
	}
								{
	int subCountequipGemItemInfos = readShort();
		com.hifun.soul.gameserver.item.assist.GemItemInfo[] subListequipGemItemInfos = new com.hifun.soul.gameserver.item.assist.GemItemInfo[subCountequipGemItemInfos];
		objquickBuyItems.setEquipGemItemInfos(subListequipGemItemInfos);
	for(int jequipGemItemInfos = 0; jequipGemItemInfos < subCountequipGemItemInfos; jequipGemItemInfos++){
						com.hifun.soul.gameserver.item.assist.GemItemInfo objequipGemItemInfos = new com.hifun.soul.gameserver.item.assist.GemItemInfo();
		subListequipGemItemInfos[jequipGemItemInfos] = objequipGemItemInfos;
						objequipGemItemInfos.setItemId(readInteger());
								objequipGemItemInfos.setIndex(readInteger());
								objequipGemItemInfos.setRarity(readInteger());
									{
	int subCountequipGemAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipGemAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipGemAttributes);
		objequipGemItemInfos.setEquipGemAttributes(subListequipGemAttributes);
	for(int jequipGemAttributes = 0; jequipGemAttributes < subCountequipGemAttributes; jequipGemAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipGemAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipGemAttributes[jequipGemAttributes] = objequipGemAttributes;
						objequipGemAttributes.setKey(readInteger());
								objequipGemAttributes.setValue(readInteger());
							}
	}
							}
	}
								{
	int subCountequipUpgradeAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipUpgradeAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipUpgradeAttributes);
		objquickBuyItems.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objquickBuyItems.setExtraSuccessRate(readFloat());
							objquickBuyItems.setUpgradeLevel(readInteger());
							objquickBuyItems.setLimitLevel(readInteger());
							objquickBuyItems.setLimitOccupation(readInteger());
							objquickBuyItems.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objquickBuyItems.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objquickBuyItems.setMaxOverlap(readInteger());
							objquickBuyItems.setIsEquiped(readBoolean());
							objquickBuyItems.setCanSell(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		quickBuyItemIds = new int[count];
		for(int i=0; i<count; i++){
			quickBuyItemIds[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(quickBuyItems.length);
	for(int i=0; i<quickBuyItems.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objquickBuyItems = quickBuyItems[i];
				writeString(objquickBuyItems.getUUID());
				writeInteger(objquickBuyItems.getItemId());
				writeString(objquickBuyItems.getName());
				writeString(objquickBuyItems.getDesc());
				writeInteger(objquickBuyItems.getType());
				writeInteger(objquickBuyItems.getIcon());
				writeInteger(objquickBuyItems.getRarity());
				writeInteger(objquickBuyItems.getBind());
				writeInteger(objquickBuyItems.getOverlapNum());
				writeInteger(objquickBuyItems.getBagType());
				writeInteger(objquickBuyItems.getBagIndex());
				writeShort(objquickBuyItems.getSellCurrencyType());
				writeInteger(objquickBuyItems.getSellNum());
				writeInteger(objquickBuyItems.getPosition());
				writeInteger(objquickBuyItems.getEndure());
				writeShort(objquickBuyItems.getEquipHole());
				writeShort(objquickBuyItems.getEmbedCurrencyType());
				writeInteger(objquickBuyItems.getEmbedCurrencyNum());
				writeShort(objquickBuyItems.getExtractCurrencyType());
				writeInteger(objquickBuyItems.getExtractCurrencyNum());
				writeShort(objquickBuyItems.getShopCurrencyType());
				writeInteger(objquickBuyItems.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objquickBuyItems=objquickBuyItems.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objquickBuyItems.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objquickBuyItems.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objquickBuyItems_jequipBaseAttributes = equipBaseAttributes_objquickBuyItems[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objquickBuyItems_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objquickBuyItems_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objquickBuyItems=objquickBuyItems.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objquickBuyItems.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objquickBuyItems.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objquickBuyItems_jequipSpecialAttributes = equipSpecialAttributes_objquickBuyItems[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objquickBuyItems_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objquickBuyItems_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objquickBuyItems=objquickBuyItems.getGemAttributes();
	writeShort(gemAttributes_objquickBuyItems.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objquickBuyItems.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objquickBuyItems_jgemAttributes = gemAttributes_objquickBuyItems[jgemAttributes];
													writeInteger(gemAttributes_objquickBuyItems_jgemAttributes.getKey());
													writeInteger(gemAttributes_objquickBuyItems_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objquickBuyItems=objquickBuyItems.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objquickBuyItems.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objquickBuyItems.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objquickBuyItems_jequipGemItemInfos = equipGemItemInfos_objquickBuyItems[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos=equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objquickBuyItems_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objquickBuyItems=objquickBuyItems.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objquickBuyItems.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objquickBuyItems.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objquickBuyItems_jequipUpgradeAttributes = equipUpgradeAttributes_objquickBuyItems[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objquickBuyItems_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objquickBuyItems_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objquickBuyItems.getExtraSuccessRate());
				writeInteger(objquickBuyItems.getUpgradeLevel());
				writeInteger(objquickBuyItems.getLimitLevel());
				writeInteger(objquickBuyItems.getLimitOccupation());
				writeInteger(objquickBuyItems.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objquickBuyItems=objquickBuyItems.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objquickBuyItems.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objquickBuyItems.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objquickBuyItems_jmaterialsOfEquipPaper = materialsOfEquipPaper_objquickBuyItems[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objquickBuyItems_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objquickBuyItems_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objquickBuyItems.getMaxOverlap());
				writeBoolean(objquickBuyItems.getIsEquiped());
				writeBoolean(objquickBuyItems.getCanSell());
	}
	writeShort(quickBuyItemIds.length);
	for(int i=0; i<quickBuyItemIds.length; i++){
	Integer objquickBuyItemIds = quickBuyItemIds[i];
			writeInteger(objquickBuyItemIds);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ITEM_QUICK_BUY_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ITEM_QUICK_BUY_INFO";
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getQuickBuyItems(){
		return quickBuyItems;
	}

	public void setQuickBuyItems(com.hifun.soul.gameserver.item.assist.CommonItem[] quickBuyItems){
		this.quickBuyItems = quickBuyItems;
	}	

	public int[] getQuickBuyItemIds(){
		return quickBuyItemIds;
	}

	public void setQuickBuyItemIds(int[] quickBuyItemIds){
		this.quickBuyItemIds = quickBuyItemIds;
	}	
}