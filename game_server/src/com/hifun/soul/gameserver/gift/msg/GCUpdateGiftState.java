package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新天赋状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateGiftState extends GCMessage{
	
	/** 状态改变的天赋 */
	private com.hifun.soul.gameserver.gift.model.GiftInfo[] changedGift;
	/** 天赋属性 */
	private com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes;
	/** 可用的天赋点 */
	private int giftPointRemain;

	public GCUpdateGiftState (){
	}
	
	public GCUpdateGiftState (
			com.hifun.soul.gameserver.gift.model.GiftInfo[] changedGift,
			com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes,
			int giftPointRemain ){
			this.changedGift = changedGift;
			this.giftAttributes = giftAttributes;
			this.giftPointRemain = giftPointRemain;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		changedGift = new com.hifun.soul.gameserver.gift.model.GiftInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.gift.model.GiftInfo objchangedGift = new com.hifun.soul.gameserver.gift.model.GiftInfo();
			changedGift[i] = objchangedGift;
					objchangedGift.setId(readInteger());
							objchangedGift.setType(readInteger());
							objchangedGift.setName(readString());
							objchangedGift.setIcon(readInteger());
							objchangedGift.setCurrentPropertyId(readInteger());
							objchangedGift.setCurrentPropertyValue(readInteger());
							objchangedGift.setPropertyValueType(readInteger());
							objchangedGift.setCostGiftPoint(readInteger());
							objchangedGift.setActiveState(readInteger());
							objchangedGift.setDesc(readString());
							objchangedGift.setCurrentLevel(readInteger());
							objchangedGift.setMaxLevel(readInteger());
							objchangedGift.setOpenLevel(readInteger());
							objchangedGift.setNextPropertyId(readInteger());
							objchangedGift.setNextPropertyValue(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcostItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objchangedGift.setCostItem(objcostItem);
				objcostItem.setUUID(readString());
				objcostItem.setItemId(readInteger());
				objcostItem.setName(readString());
				objcostItem.setDesc(readString());
				objcostItem.setType(readInteger());
				objcostItem.setIcon(readInteger());
				objcostItem.setRarity(readInteger());
				objcostItem.setBind(readInteger());
				objcostItem.setOverlapNum(readInteger());
				objcostItem.setBagType(readInteger());
				objcostItem.setBagIndex(readInteger());
				objcostItem.setSellCurrencyType(readShort());
				objcostItem.setSellNum(readInteger());
				objcostItem.setPosition(readInteger());
				objcostItem.setEndure(readInteger());
				objcostItem.setEquipHole(readShort());
				objcostItem.setEmbedCurrencyType(readShort());
				objcostItem.setEmbedCurrencyNum(readInteger());
				objcostItem.setExtractCurrencyType(readShort());
				objcostItem.setExtractCurrencyNum(readInteger());
				objcostItem.setShopCurrencyType(readShort());
				objcostItem.setShopCurrencyNum(readInteger());
					{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objcostItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objcostItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objcostItem.setGemAttributes(subListgemAttributes);
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
		objcostItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objcostItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
				objcostItem.setExtraSuccessRate(readFloat());
				objcostItem.setUpgradeLevel(readInteger());
				objcostItem.setLimitLevel(readInteger());
				objcostItem.setLimitOccupation(readInteger());
				objcostItem.setMaxEquipHole(readInteger());
					{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objcostItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
				objcostItem.setMaxOverlap(readInteger());
				objcostItem.setIsEquiped(readBoolean());
				objcostItem.setCanSell(readBoolean());
			}
							objchangedGift.setCostItemNum(readInteger());
							objchangedGift.setBagItemNum(readInteger());
							objchangedGift.setUpgradeNeedLevel(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		giftAttributes = new com.hifun.soul.gameserver.gift.model.GiftProperty[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.gift.model.GiftProperty objgiftAttributes = new com.hifun.soul.gameserver.gift.model.GiftProperty();
			giftAttributes[i] = objgiftAttributes;
					objgiftAttributes.setProperty(readInteger());
							objgiftAttributes.setPropertyValue(readInteger());
							objgiftAttributes.setPropertyValueType(readInteger());
				}
		giftPointRemain = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(changedGift.length);
	for(int i=0; i<changedGift.length; i++){
	com.hifun.soul.gameserver.gift.model.GiftInfo objchangedGift = changedGift[i];
				writeInteger(objchangedGift.getId());
				writeInteger(objchangedGift.getType());
				writeString(objchangedGift.getName());
				writeInteger(objchangedGift.getIcon());
				writeInteger(objchangedGift.getCurrentPropertyId());
				writeInteger(objchangedGift.getCurrentPropertyValue());
				writeInteger(objchangedGift.getPropertyValueType());
				writeInteger(objchangedGift.getCostGiftPoint());
				writeInteger(objchangedGift.getActiveState());
				writeString(objchangedGift.getDesc());
				writeInteger(objchangedGift.getCurrentLevel());
				writeInteger(objchangedGift.getMaxLevel());
				writeInteger(objchangedGift.getOpenLevel());
				writeInteger(objchangedGift.getNextPropertyId());
				writeInteger(objchangedGift.getNextPropertyValue());
					com.hifun.soul.gameserver.item.assist.CommonItem costItem_objchangedGift = objchangedGift.getCostItem();
					writeString(costItem_objchangedGift.getUUID());
					writeInteger(costItem_objchangedGift.getItemId());
					writeString(costItem_objchangedGift.getName());
					writeString(costItem_objchangedGift.getDesc());
					writeInteger(costItem_objchangedGift.getType());
					writeInteger(costItem_objchangedGift.getIcon());
					writeInteger(costItem_objchangedGift.getRarity());
					writeInteger(costItem_objchangedGift.getBind());
					writeInteger(costItem_objchangedGift.getOverlapNum());
					writeInteger(costItem_objchangedGift.getBagType());
					writeInteger(costItem_objchangedGift.getBagIndex());
					writeShort(costItem_objchangedGift.getSellCurrencyType());
					writeInteger(costItem_objchangedGift.getSellNum());
					writeInteger(costItem_objchangedGift.getPosition());
					writeInteger(costItem_objchangedGift.getEndure());
					writeShort(costItem_objchangedGift.getEquipHole());
					writeShort(costItem_objchangedGift.getEmbedCurrencyType());
					writeInteger(costItem_objchangedGift.getEmbedCurrencyNum());
					writeShort(costItem_objchangedGift.getExtractCurrencyType());
					writeInteger(costItem_objchangedGift.getExtractCurrencyNum());
					writeShort(costItem_objchangedGift.getShopCurrencyType());
					writeInteger(costItem_objchangedGift.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_costItem_objchangedGift=costItem_objchangedGift.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_costItem_objchangedGift.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_costItem_objchangedGift.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_costItem_objchangedGift_jequipBaseAttributes = equipBaseAttributes_costItem_objchangedGift[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_costItem_objchangedGift_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_costItem_objchangedGift_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_costItem_objchangedGift=costItem_objchangedGift.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_costItem_objchangedGift.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_costItem_objchangedGift.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_costItem_objchangedGift_jequipSpecialAttributes = equipSpecialAttributes_costItem_objchangedGift[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_costItem_objchangedGift_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_costItem_objchangedGift_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_costItem_objchangedGift=costItem_objchangedGift.getGemAttributes();
	writeShort(gemAttributes_costItem_objchangedGift.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_costItem_objchangedGift.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_costItem_objchangedGift_jgemAttributes = gemAttributes_costItem_objchangedGift[jgemAttributes];
													writeInteger(gemAttributes_costItem_objchangedGift_jgemAttributes.getKey());
													writeInteger(gemAttributes_costItem_objchangedGift_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_costItem_objchangedGift=costItem_objchangedGift.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_costItem_objchangedGift.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_costItem_objchangedGift.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos = equipGemItemInfos_costItem_objchangedGift[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos=equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objchangedGift_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_costItem_objchangedGift=costItem_objchangedGift.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_costItem_objchangedGift.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_costItem_objchangedGift.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_costItem_objchangedGift_jequipUpgradeAttributes = equipUpgradeAttributes_costItem_objchangedGift[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_costItem_objchangedGift_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_costItem_objchangedGift_jequipUpgradeAttributes.getValue());
									}
					writeFloat(costItem_objchangedGift.getExtraSuccessRate());
					writeInteger(costItem_objchangedGift.getUpgradeLevel());
					writeInteger(costItem_objchangedGift.getLimitLevel());
					writeInteger(costItem_objchangedGift.getLimitOccupation());
					writeInteger(costItem_objchangedGift.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_costItem_objchangedGift=costItem_objchangedGift.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_costItem_objchangedGift.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_costItem_objchangedGift.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_costItem_objchangedGift_jmaterialsOfEquipPaper = materialsOfEquipPaper_costItem_objchangedGift[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_costItem_objchangedGift_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_costItem_objchangedGift_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(costItem_objchangedGift.getMaxOverlap());
					writeBoolean(costItem_objchangedGift.getIsEquiped());
					writeBoolean(costItem_objchangedGift.getCanSell());
						writeInteger(objchangedGift.getCostItemNum());
				writeInteger(objchangedGift.getBagItemNum());
				writeInteger(objchangedGift.getUpgradeNeedLevel());
	}
	writeShort(giftAttributes.length);
	for(int i=0; i<giftAttributes.length; i++){
	com.hifun.soul.gameserver.gift.model.GiftProperty objgiftAttributes = giftAttributes[i];
				writeInteger(objgiftAttributes.getProperty());
				writeInteger(objgiftAttributes.getPropertyValue());
				writeInteger(objgiftAttributes.getPropertyValueType());
	}
		writeInteger(giftPointRemain);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_GIFT_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_GIFT_STATE";
	}

	public com.hifun.soul.gameserver.gift.model.GiftInfo[] getChangedGift(){
		return changedGift;
	}

	public void setChangedGift(com.hifun.soul.gameserver.gift.model.GiftInfo[] changedGift){
		this.changedGift = changedGift;
	}	

	public com.hifun.soul.gameserver.gift.model.GiftProperty[] getGiftAttributes(){
		return giftAttributes;
	}

	public void setGiftAttributes(com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes){
		this.giftAttributes = giftAttributes;
	}	

	public int getGiftPointRemain(){
		return giftPointRemain;
	}
		
	public void setGiftPointRemain(int giftPointRemain){
		this.giftPointRemain = giftPointRemain;
	}
}