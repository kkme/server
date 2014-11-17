package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买选中道具信息
 *
 * @author SevenSoul
 */
@Component
public class GCAskMallItemInfo extends GCMessage{
	
	/** 物品的基础属性信息 */
	private com.hifun.soul.gameserver.item.assist.CommonItem commonItem;
	/** 货币类型 */
	private short currencyType;
	/** 货币数量 */
	private int currencyNum;

	public GCAskMallItemInfo (){
	}
	
	public GCAskMallItemInfo (
			com.hifun.soul.gameserver.item.assist.CommonItem commonItem,
			short currencyType,
			int currencyNum ){
			this.commonItem = commonItem;
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
	}

	@Override
	protected boolean readImpl() {
		commonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
						commonItem.setUUID(readString());
						commonItem.setItemId(readInteger());
						commonItem.setName(readString());
						commonItem.setDesc(readString());
						commonItem.setType(readInteger());
						commonItem.setIcon(readInteger());
						commonItem.setRarity(readInteger());
						commonItem.setBind(readInteger());
						commonItem.setOverlapNum(readInteger());
						commonItem.setBagType(readInteger());
						commonItem.setBagIndex(readInteger());
						commonItem.setSellCurrencyType(readShort());
						commonItem.setSellNum(readInteger());
						commonItem.setPosition(readInteger());
						commonItem.setEndure(readInteger());
						commonItem.setEquipHole(readShort());
						commonItem.setEmbedCurrencyType(readShort());
						commonItem.setEmbedCurrencyNum(readInteger());
						commonItem.setExtractCurrencyType(readShort());
						commonItem.setExtractCurrencyNum(readInteger());
						commonItem.setShopCurrencyType(readShort());
						commonItem.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		commonItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		commonItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		commonItem.setGemAttributes(subListgemAttributes);
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
		commonItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		commonItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						commonItem.setExtraSuccessRate(readFloat());
						commonItem.setUpgradeLevel(readInteger());
						commonItem.setLimitLevel(readInteger());
						commonItem.setLimitOccupation(readInteger());
						commonItem.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		commonItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						commonItem.setMaxOverlap(readInteger());
						commonItem.setIsEquiped(readBoolean());
						commonItem.setCanSell(readBoolean());
				currencyType = readShort();
		currencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(commonItem.getUUID());
		writeInteger(commonItem.getItemId());
		writeString(commonItem.getName());
		writeString(commonItem.getDesc());
		writeInteger(commonItem.getType());
		writeInteger(commonItem.getIcon());
		writeInteger(commonItem.getRarity());
		writeInteger(commonItem.getBind());
		writeInteger(commonItem.getOverlapNum());
		writeInteger(commonItem.getBagType());
		writeInteger(commonItem.getBagIndex());
		writeShort(commonItem.getSellCurrencyType());
		writeInteger(commonItem.getSellNum());
		writeInteger(commonItem.getPosition());
		writeInteger(commonItem.getEndure());
		writeShort(commonItem.getEquipHole());
		writeShort(commonItem.getEmbedCurrencyType());
		writeInteger(commonItem.getEmbedCurrencyNum());
		writeShort(commonItem.getExtractCurrencyType());
		writeInteger(commonItem.getExtractCurrencyNum());
		writeShort(commonItem.getShopCurrencyType());
		writeInteger(commonItem.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem=commonItem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_jequipBaseAttributes = equipBaseAttributes_commonItem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem=commonItem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_jequipSpecialAttributes = equipSpecialAttributes_commonItem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem=commonItem.getGemAttributes();
	writeShort(gemAttributes_commonItem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_jgemAttributes = gemAttributes_commonItem[jgemAttributes];
													writeInteger(gemAttributes_commonItem_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem=commonItem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_jequipGemItemInfos = equipGemItemInfos_commonItem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos=equipGemItemInfos_commonItem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem=commonItem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_jequipUpgradeAttributes.getValue());
									}
		writeFloat(commonItem.getExtraSuccessRate());
		writeInteger(commonItem.getUpgradeLevel());
		writeInteger(commonItem.getLimitLevel());
		writeInteger(commonItem.getLimitOccupation());
		writeInteger(commonItem.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem=commonItem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(commonItem.getMaxOverlap());
		writeBoolean(commonItem.getIsEquiped());
		writeBoolean(commonItem.getCanSell());
		writeShort(currencyType);
		writeInteger(currencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ASK_MALL_ITEM_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ASK_MALL_ITEM_INFO";
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getCommonItem(){
		return commonItem;
	}
		
	public void setCommonItem(com.hifun.soul.gameserver.item.assist.CommonItem commonItem){
		this.commonItem = commonItem;
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}
}