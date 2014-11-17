package com.hifun.soul.gameserver.honourshop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示荣誉商店道具列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowHonourShopItemList extends GCMessage{
	
	/** 荣誉商店道具列表 */
	private com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo[] honourShopItemList;

	public GCShowHonourShopItemList (){
	}
	
	public GCShowHonourShopItemList (
			com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo[] honourShopItemList ){
			this.honourShopItemList = honourShopItemList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		honourShopItemList = new com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo objhonourShopItemList = new com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo();
			honourShopItemList[i] = objhonourShopItemList;
					objhonourShopItemList.setItemId(readInteger());
							objhonourShopItemList.setNeedHonour(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objhonourShopItemList.setCommonItem(objcommonItem);
				objcommonItem.setUUID(readString());
				objcommonItem.setItemId(readInteger());
				objcommonItem.setName(readString());
				objcommonItem.setDesc(readString());
				objcommonItem.setType(readInteger());
				objcommonItem.setIcon(readInteger());
				objcommonItem.setRarity(readInteger());
				objcommonItem.setBind(readInteger());
				objcommonItem.setOverlapNum(readInteger());
				objcommonItem.setBagType(readInteger());
				objcommonItem.setBagIndex(readInteger());
				objcommonItem.setSellCurrencyType(readShort());
				objcommonItem.setSellNum(readInteger());
				objcommonItem.setPosition(readInteger());
				objcommonItem.setEndure(readInteger());
				objcommonItem.setEquipHole(readShort());
				objcommonItem.setEmbedCurrencyType(readShort());
				objcommonItem.setEmbedCurrencyNum(readInteger());
				objcommonItem.setExtractCurrencyType(readShort());
				objcommonItem.setExtractCurrencyNum(readInteger());
				objcommonItem.setShopCurrencyType(readShort());
				objcommonItem.setShopCurrencyNum(readInteger());
					{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objcommonItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objcommonItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objcommonItem.setGemAttributes(subListgemAttributes);
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
		objcommonItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objcommonItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
				objcommonItem.setExtraSuccessRate(readFloat());
				objcommonItem.setUpgradeLevel(readInteger());
				objcommonItem.setLimitLevel(readInteger());
				objcommonItem.setLimitOccupation(readInteger());
				objcommonItem.setMaxEquipHole(readInteger());
					{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objcommonItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
				objcommonItem.setMaxOverlap(readInteger());
				objcommonItem.setIsEquiped(readBoolean());
				objcommonItem.setCanSell(readBoolean());
			}
							objhonourShopItemList.setItemType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(honourShopItemList.length);
	for(int i=0; i<honourShopItemList.length; i++){
	com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo objhonourShopItemList = honourShopItemList[i];
				writeInteger(objhonourShopItemList.getItemId());
				writeInteger(objhonourShopItemList.getNeedHonour());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objhonourShopItemList = objhonourShopItemList.getCommonItem();
					writeString(commonItem_objhonourShopItemList.getUUID());
					writeInteger(commonItem_objhonourShopItemList.getItemId());
					writeString(commonItem_objhonourShopItemList.getName());
					writeString(commonItem_objhonourShopItemList.getDesc());
					writeInteger(commonItem_objhonourShopItemList.getType());
					writeInteger(commonItem_objhonourShopItemList.getIcon());
					writeInteger(commonItem_objhonourShopItemList.getRarity());
					writeInteger(commonItem_objhonourShopItemList.getBind());
					writeInteger(commonItem_objhonourShopItemList.getOverlapNum());
					writeInteger(commonItem_objhonourShopItemList.getBagType());
					writeInteger(commonItem_objhonourShopItemList.getBagIndex());
					writeShort(commonItem_objhonourShopItemList.getSellCurrencyType());
					writeInteger(commonItem_objhonourShopItemList.getSellNum());
					writeInteger(commonItem_objhonourShopItemList.getPosition());
					writeInteger(commonItem_objhonourShopItemList.getEndure());
					writeShort(commonItem_objhonourShopItemList.getEquipHole());
					writeShort(commonItem_objhonourShopItemList.getEmbedCurrencyType());
					writeInteger(commonItem_objhonourShopItemList.getEmbedCurrencyNum());
					writeShort(commonItem_objhonourShopItemList.getExtractCurrencyType());
					writeInteger(commonItem_objhonourShopItemList.getExtractCurrencyNum());
					writeShort(commonItem_objhonourShopItemList.getShopCurrencyType());
					writeInteger(commonItem_objhonourShopItemList.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objhonourShopItemList.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objhonourShopItemList.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objhonourShopItemList_jequipBaseAttributes = equipBaseAttributes_commonItem_objhonourShopItemList[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objhonourShopItemList_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objhonourShopItemList_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objhonourShopItemList.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objhonourShopItemList.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objhonourShopItemList_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objhonourShopItemList[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objhonourShopItemList_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objhonourShopItemList_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getGemAttributes();
	writeShort(gemAttributes_commonItem_objhonourShopItemList.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objhonourShopItemList.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objhonourShopItemList_jgemAttributes = gemAttributes_commonItem_objhonourShopItemList[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objhonourShopItemList_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objhonourShopItemList_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objhonourShopItemList.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objhonourShopItemList.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos = equipGemItemInfos_commonItem_objhonourShopItemList[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos=equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objhonourShopItemList_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objhonourShopItemList.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objhonourShopItemList.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objhonourShopItemList_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objhonourShopItemList[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objhonourShopItemList_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objhonourShopItemList_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objhonourShopItemList.getExtraSuccessRate());
					writeInteger(commonItem_objhonourShopItemList.getUpgradeLevel());
					writeInteger(commonItem_objhonourShopItemList.getLimitLevel());
					writeInteger(commonItem_objhonourShopItemList.getLimitOccupation());
					writeInteger(commonItem_objhonourShopItemList.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objhonourShopItemList=commonItem_objhonourShopItemList.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objhonourShopItemList.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objhonourShopItemList.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objhonourShopItemList_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objhonourShopItemList[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objhonourShopItemList_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objhonourShopItemList_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objhonourShopItemList.getMaxOverlap());
					writeBoolean(commonItem_objhonourShopItemList.getIsEquiped());
					writeBoolean(commonItem_objhonourShopItemList.getCanSell());
						writeInteger(objhonourShopItemList.getItemType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HONOUR_SHOP_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HONOUR_SHOP_ITEM_LIST";
	}

	public com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo[] getHonourShopItemList(){
		return honourShopItemList;
	}

	public void setHonourShopItemList(com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo[] honourShopItemList){
		this.honourShopItemList = honourShopItemList;
	}	
}