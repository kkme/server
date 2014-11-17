package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团某一类型商品 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionShopItems extends GCMessage{
	
	/** 军团商品信息 */
	private com.hifun.soul.gameserver.legion.info.LegionShopItemInfo[] itemInfos;

	public GCShowLegionShopItems (){
	}
	
	public GCShowLegionShopItems (
			com.hifun.soul.gameserver.legion.info.LegionShopItemInfo[] itemInfos ){
			this.itemInfos = itemInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		itemInfos = new com.hifun.soul.gameserver.legion.info.LegionShopItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionShopItemInfo objitemInfos = new com.hifun.soul.gameserver.legion.info.LegionShopItemInfo();
			itemInfos[i] = objitemInfos;
						{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objitemInfos.setCommonItem(objcommonItem);
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
							objitemInfos.setItemType(readInteger());
							objitemInfos.setNeedMedal(readInteger());
							objitemInfos.setMaxNum(readInteger());
							objitemInfos.setRemainNum(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(itemInfos.length);
	for(int i=0; i<itemInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionShopItemInfo objitemInfos = itemInfos[i];
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objitemInfos = objitemInfos.getCommonItem();
					writeString(commonItem_objitemInfos.getUUID());
					writeInteger(commonItem_objitemInfos.getItemId());
					writeString(commonItem_objitemInfos.getName());
					writeString(commonItem_objitemInfos.getDesc());
					writeInteger(commonItem_objitemInfos.getType());
					writeInteger(commonItem_objitemInfos.getIcon());
					writeInteger(commonItem_objitemInfos.getRarity());
					writeInteger(commonItem_objitemInfos.getBind());
					writeInteger(commonItem_objitemInfos.getOverlapNum());
					writeInteger(commonItem_objitemInfos.getBagType());
					writeInteger(commonItem_objitemInfos.getBagIndex());
					writeShort(commonItem_objitemInfos.getSellCurrencyType());
					writeInteger(commonItem_objitemInfos.getSellNum());
					writeInteger(commonItem_objitemInfos.getPosition());
					writeInteger(commonItem_objitemInfos.getEndure());
					writeShort(commonItem_objitemInfos.getEquipHole());
					writeShort(commonItem_objitemInfos.getEmbedCurrencyType());
					writeInteger(commonItem_objitemInfos.getEmbedCurrencyNum());
					writeShort(commonItem_objitemInfos.getExtractCurrencyType());
					writeInteger(commonItem_objitemInfos.getExtractCurrencyNum());
					writeShort(commonItem_objitemInfos.getShopCurrencyType());
					writeInteger(commonItem_objitemInfos.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objitemInfos=commonItem_objitemInfos.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objitemInfos.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objitemInfos.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objitemInfos_jequipBaseAttributes = equipBaseAttributes_commonItem_objitemInfos[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objitemInfos_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objitemInfos_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objitemInfos=commonItem_objitemInfos.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objitemInfos.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objitemInfos.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objitemInfos_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objitemInfos[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objitemInfos_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objitemInfos_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objitemInfos=commonItem_objitemInfos.getGemAttributes();
	writeShort(gemAttributes_commonItem_objitemInfos.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objitemInfos.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objitemInfos_jgemAttributes = gemAttributes_commonItem_objitemInfos[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objitemInfos_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objitemInfos_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objitemInfos=commonItem_objitemInfos.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objitemInfos.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objitemInfos.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos = equipGemItemInfos_commonItem_objitemInfos[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos=equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objitemInfos_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objitemInfos=commonItem_objitemInfos.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objitemInfos.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objitemInfos.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objitemInfos_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objitemInfos[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objitemInfos_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objitemInfos_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objitemInfos.getExtraSuccessRate());
					writeInteger(commonItem_objitemInfos.getUpgradeLevel());
					writeInteger(commonItem_objitemInfos.getLimitLevel());
					writeInteger(commonItem_objitemInfos.getLimitOccupation());
					writeInteger(commonItem_objitemInfos.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objitemInfos=commonItem_objitemInfos.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objitemInfos.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objitemInfos.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objitemInfos_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objitemInfos[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objitemInfos_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objitemInfos_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objitemInfos.getMaxOverlap());
					writeBoolean(commonItem_objitemInfos.getIsEquiped());
					writeBoolean(commonItem_objitemInfos.getCanSell());
						writeInteger(objitemInfos.getItemType());
				writeInteger(objitemInfos.getNeedMedal());
				writeInteger(objitemInfos.getMaxNum());
				writeInteger(objitemInfos.getRemainNum());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_SHOP_ITEMS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_SHOP_ITEMS";
	}

	public com.hifun.soul.gameserver.legion.info.LegionShopItemInfo[] getItemInfos(){
		return itemInfos;
	}

	public void setItemInfos(com.hifun.soul.gameserver.legion.info.LegionShopItemInfo[] itemInfos){
		this.itemInfos = itemInfos;
	}	
}