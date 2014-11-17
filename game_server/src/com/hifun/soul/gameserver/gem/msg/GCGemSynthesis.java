package com.hifun.soul.gameserver.gem.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 宝石合成
 *
 * @author SevenSoul
 */
@Component
public class GCGemSynthesis extends GCMessage{
	
	/** 物品列表 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] item;

	public GCGemSynthesis (){
	}
	
	public GCGemSynthesis (
			com.hifun.soul.gameserver.item.assist.CommonItem[] item ){
			this.item = item;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		item = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objitem = new com.hifun.soul.gameserver.item.assist.CommonItem();
			item[i] = objitem;
					objitem.setUUID(readString());
							objitem.setItemId(readInteger());
							objitem.setName(readString());
							objitem.setDesc(readString());
							objitem.setType(readInteger());
							objitem.setIcon(readInteger());
							objitem.setRarity(readInteger());
							objitem.setBind(readInteger());
							objitem.setOverlapNum(readInteger());
							objitem.setBagType(readInteger());
							objitem.setBagIndex(readInteger());
							objitem.setSellCurrencyType(readShort());
							objitem.setSellNum(readInteger());
							objitem.setPosition(readInteger());
							objitem.setEndure(readInteger());
							objitem.setEquipHole(readShort());
							objitem.setEmbedCurrencyType(readShort());
							objitem.setEmbedCurrencyNum(readInteger());
							objitem.setExtractCurrencyType(readShort());
							objitem.setExtractCurrencyNum(readInteger());
							objitem.setShopCurrencyType(readShort());
							objitem.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objitem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objitem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objitem.setGemAttributes(subListgemAttributes);
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
		objitem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objitem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objitem.setExtraSuccessRate(readFloat());
							objitem.setUpgradeLevel(readInteger());
							objitem.setLimitLevel(readInteger());
							objitem.setLimitOccupation(readInteger());
							objitem.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objitem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objitem.setMaxOverlap(readInteger());
							objitem.setIsEquiped(readBoolean());
							objitem.setCanSell(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(item.length);
	for(int i=0; i<item.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objitem = item[i];
				writeString(objitem.getUUID());
				writeInteger(objitem.getItemId());
				writeString(objitem.getName());
				writeString(objitem.getDesc());
				writeInteger(objitem.getType());
				writeInteger(objitem.getIcon());
				writeInteger(objitem.getRarity());
				writeInteger(objitem.getBind());
				writeInteger(objitem.getOverlapNum());
				writeInteger(objitem.getBagType());
				writeInteger(objitem.getBagIndex());
				writeShort(objitem.getSellCurrencyType());
				writeInteger(objitem.getSellNum());
				writeInteger(objitem.getPosition());
				writeInteger(objitem.getEndure());
				writeShort(objitem.getEquipHole());
				writeShort(objitem.getEmbedCurrencyType());
				writeInteger(objitem.getEmbedCurrencyNum());
				writeShort(objitem.getExtractCurrencyType());
				writeInteger(objitem.getExtractCurrencyNum());
				writeShort(objitem.getShopCurrencyType());
				writeInteger(objitem.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objitem=objitem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objitem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objitem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objitem_jequipBaseAttributes = equipBaseAttributes_objitem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objitem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objitem_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objitem=objitem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objitem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objitem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objitem_jequipSpecialAttributes = equipSpecialAttributes_objitem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objitem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objitem_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objitem=objitem.getGemAttributes();
	writeShort(gemAttributes_objitem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objitem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objitem_jgemAttributes = gemAttributes_objitem[jgemAttributes];
													writeInteger(gemAttributes_objitem_jgemAttributes.getKey());
													writeInteger(gemAttributes_objitem_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objitem=objitem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objitem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objitem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objitem_jequipGemItemInfos = equipGemItemInfos_objitem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objitem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objitem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objitem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos=equipGemItemInfos_objitem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objitem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objitem=objitem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objitem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objitem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objitem_jequipUpgradeAttributes = equipUpgradeAttributes_objitem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objitem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objitem_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objitem.getExtraSuccessRate());
				writeInteger(objitem.getUpgradeLevel());
				writeInteger(objitem.getLimitLevel());
				writeInteger(objitem.getLimitOccupation());
				writeInteger(objitem.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objitem=objitem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objitem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objitem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objitem_jmaterialsOfEquipPaper = materialsOfEquipPaper_objitem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objitem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objitem_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objitem.getMaxOverlap());
				writeBoolean(objitem.getIsEquiped());
				writeBoolean(objitem.getCanSell());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GEM_SYNTHESIS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GEM_SYNTHESIS";
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getItem(){
		return item;
	}

	public void setItem(com.hifun.soul.gameserver.item.assist.CommonItem[] item){
		this.item = item;
	}	
}