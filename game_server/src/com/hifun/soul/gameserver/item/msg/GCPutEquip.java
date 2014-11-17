package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备放到宝石镶嵌面板
 *
 * @author SevenSoul
 */
@Component
public class GCPutEquip extends GCMessage{
	
	/** 装备的信息 */
	private com.hifun.soul.gameserver.item.assist.CommonItem item;
	/** 装备上的宝石信息 */
	private com.hifun.soul.gameserver.item.assist.EmbedGemInfo[] gems;

	public GCPutEquip (){
	}
	
	public GCPutEquip (
			com.hifun.soul.gameserver.item.assist.CommonItem item,
			com.hifun.soul.gameserver.item.assist.EmbedGemInfo[] gems ){
			this.item = item;
			this.gems = gems;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		item = new com.hifun.soul.gameserver.item.assist.CommonItem();
						item.setUUID(readString());
						item.setItemId(readInteger());
						item.setName(readString());
						item.setDesc(readString());
						item.setType(readInteger());
						item.setIcon(readInteger());
						item.setRarity(readInteger());
						item.setBind(readInteger());
						item.setOverlapNum(readInteger());
						item.setBagType(readInteger());
						item.setBagIndex(readInteger());
						item.setSellCurrencyType(readShort());
						item.setSellNum(readInteger());
						item.setPosition(readInteger());
						item.setEndure(readInteger());
						item.setEquipHole(readShort());
						item.setEmbedCurrencyType(readShort());
						item.setEmbedCurrencyNum(readInteger());
						item.setExtractCurrencyType(readShort());
						item.setExtractCurrencyNum(readInteger());
						item.setShopCurrencyType(readShort());
						item.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		item.setEquipBaseAttributes(subListequipBaseAttributes);
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
		item.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		item.setGemAttributes(subListgemAttributes);
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
		item.setEquipGemItemInfos(subListequipGemItemInfos);
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
		item.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						item.setExtraSuccessRate(readFloat());
						item.setUpgradeLevel(readInteger());
						item.setLimitLevel(readInteger());
						item.setLimitOccupation(readInteger());
						item.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		item.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						item.setMaxOverlap(readInteger());
						item.setIsEquiped(readBoolean());
						item.setCanSell(readBoolean());
				count = readShort();
		count = count < 0 ? 0 : count;
		gems = new com.hifun.soul.gameserver.item.assist.EmbedGemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.EmbedGemInfo objgems = new com.hifun.soul.gameserver.item.assist.EmbedGemInfo();
			gems[i] = objgems;
					objgems.setIndex(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objgems.setCommonItem(objcommonItem);
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
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(item.getUUID());
		writeInteger(item.getItemId());
		writeString(item.getName());
		writeString(item.getDesc());
		writeInteger(item.getType());
		writeInteger(item.getIcon());
		writeInteger(item.getRarity());
		writeInteger(item.getBind());
		writeInteger(item.getOverlapNum());
		writeInteger(item.getBagType());
		writeInteger(item.getBagIndex());
		writeShort(item.getSellCurrencyType());
		writeInteger(item.getSellNum());
		writeInteger(item.getPosition());
		writeInteger(item.getEndure());
		writeShort(item.getEquipHole());
		writeShort(item.getEmbedCurrencyType());
		writeInteger(item.getEmbedCurrencyNum());
		writeShort(item.getExtractCurrencyType());
		writeInteger(item.getExtractCurrencyNum());
		writeShort(item.getShopCurrencyType());
		writeInteger(item.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_item=item.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_item.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_item.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_item_jequipBaseAttributes = equipBaseAttributes_item[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_item_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_item_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_item=item.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_item.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_item.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_item_jequipSpecialAttributes = equipSpecialAttributes_item[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_item_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_item_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_item=item.getGemAttributes();
	writeShort(gemAttributes_item.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_item.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_item_jgemAttributes = gemAttributes_item[jgemAttributes];
													writeInteger(gemAttributes_item_jgemAttributes.getKey());
													writeInteger(gemAttributes_item_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_item=item.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_item.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_item.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_item_jequipGemItemInfos = equipGemItemInfos_item[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_item_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_item_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_item_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos=equipGemItemInfos_item_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_item_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_item=item.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_item.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_item.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_item_jequipUpgradeAttributes = equipUpgradeAttributes_item[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_item_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_item_jequipUpgradeAttributes.getValue());
									}
		writeFloat(item.getExtraSuccessRate());
		writeInteger(item.getUpgradeLevel());
		writeInteger(item.getLimitLevel());
		writeInteger(item.getLimitOccupation());
		writeInteger(item.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_item=item.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_item.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_item.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_item_jmaterialsOfEquipPaper = materialsOfEquipPaper_item[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_item_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_item_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(item.getMaxOverlap());
		writeBoolean(item.getIsEquiped());
		writeBoolean(item.getCanSell());
	writeShort(gems.length);
	for(int i=0; i<gems.length; i++){
	com.hifun.soul.gameserver.item.assist.EmbedGemInfo objgems = gems[i];
				writeInteger(objgems.getIndex());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objgems = objgems.getCommonItem();
					writeString(commonItem_objgems.getUUID());
					writeInteger(commonItem_objgems.getItemId());
					writeString(commonItem_objgems.getName());
					writeString(commonItem_objgems.getDesc());
					writeInteger(commonItem_objgems.getType());
					writeInteger(commonItem_objgems.getIcon());
					writeInteger(commonItem_objgems.getRarity());
					writeInteger(commonItem_objgems.getBind());
					writeInteger(commonItem_objgems.getOverlapNum());
					writeInteger(commonItem_objgems.getBagType());
					writeInteger(commonItem_objgems.getBagIndex());
					writeShort(commonItem_objgems.getSellCurrencyType());
					writeInteger(commonItem_objgems.getSellNum());
					writeInteger(commonItem_objgems.getPosition());
					writeInteger(commonItem_objgems.getEndure());
					writeShort(commonItem_objgems.getEquipHole());
					writeShort(commonItem_objgems.getEmbedCurrencyType());
					writeInteger(commonItem_objgems.getEmbedCurrencyNum());
					writeShort(commonItem_objgems.getExtractCurrencyType());
					writeInteger(commonItem_objgems.getExtractCurrencyNum());
					writeShort(commonItem_objgems.getShopCurrencyType());
					writeInteger(commonItem_objgems.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objgems=commonItem_objgems.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objgems.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objgems.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objgems_jequipBaseAttributes = equipBaseAttributes_commonItem_objgems[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objgems_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objgems_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objgems=commonItem_objgems.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objgems.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objgems.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objgems_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objgems[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objgems_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objgems_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objgems=commonItem_objgems.getGemAttributes();
	writeShort(gemAttributes_commonItem_objgems.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objgems.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objgems_jgemAttributes = gemAttributes_commonItem_objgems[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objgems_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objgems_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objgems=commonItem_objgems.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objgems.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objgems.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objgems_jequipGemItemInfos = equipGemItemInfos_commonItem_objgems[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos=equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objgems_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objgems=commonItem_objgems.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objgems.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objgems.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objgems_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objgems[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objgems_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objgems_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objgems.getExtraSuccessRate());
					writeInteger(commonItem_objgems.getUpgradeLevel());
					writeInteger(commonItem_objgems.getLimitLevel());
					writeInteger(commonItem_objgems.getLimitOccupation());
					writeInteger(commonItem_objgems.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objgems=commonItem_objgems.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objgems.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objgems.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objgems_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objgems[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objgems_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objgems_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objgems.getMaxOverlap());
					writeBoolean(commonItem_objgems.getIsEquiped());
					writeBoolean(commonItem_objgems.getCanSell());
			}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PUT_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PUT_EQUIP";
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getItem(){
		return item;
	}
		
	public void setItem(com.hifun.soul.gameserver.item.assist.CommonItem item){
		this.item = item;
	}

	public com.hifun.soul.gameserver.item.assist.EmbedGemInfo[] getGems(){
		return gems;
	}

	public void setGems(com.hifun.soul.gameserver.item.assist.EmbedGemInfo[] gems){
		this.gems = gems;
	}	
}