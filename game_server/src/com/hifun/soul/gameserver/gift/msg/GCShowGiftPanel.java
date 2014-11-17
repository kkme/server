package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求打开天赋面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowGiftPanel extends GCMessage{
	
	/** 可用的天赋点 */
	private int giftPointRemain;
	/** 天赋点总数 */
	private int giftPointTotal;
	/** 天赋属性 */
	private com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes;
	/** 天赋信息 */
	private com.hifun.soul.gameserver.gift.model.GiftInfo[] gifts;

	public GCShowGiftPanel (){
	}
	
	public GCShowGiftPanel (
			int giftPointRemain,
			int giftPointTotal,
			com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes,
			com.hifun.soul.gameserver.gift.model.GiftInfo[] gifts ){
			this.giftPointRemain = giftPointRemain;
			this.giftPointTotal = giftPointTotal;
			this.giftAttributes = giftAttributes;
			this.gifts = gifts;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		giftPointRemain = readInteger();
		giftPointTotal = readInteger();
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
		count = readShort();
		count = count < 0 ? 0 : count;
		gifts = new com.hifun.soul.gameserver.gift.model.GiftInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.gift.model.GiftInfo objgifts = new com.hifun.soul.gameserver.gift.model.GiftInfo();
			gifts[i] = objgifts;
					objgifts.setId(readInteger());
							objgifts.setType(readInteger());
							objgifts.setName(readString());
							objgifts.setIcon(readInteger());
							objgifts.setCurrentPropertyId(readInteger());
							objgifts.setCurrentPropertyValue(readInteger());
							objgifts.setPropertyValueType(readInteger());
							objgifts.setCostGiftPoint(readInteger());
							objgifts.setActiveState(readInteger());
							objgifts.setDesc(readString());
							objgifts.setCurrentLevel(readInteger());
							objgifts.setMaxLevel(readInteger());
							objgifts.setOpenLevel(readInteger());
							objgifts.setNextPropertyId(readInteger());
							objgifts.setNextPropertyValue(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcostItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objgifts.setCostItem(objcostItem);
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
							objgifts.setCostItemNum(readInteger());
							objgifts.setBagItemNum(readInteger());
							objgifts.setUpgradeNeedLevel(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(giftPointRemain);
		writeInteger(giftPointTotal);
	writeShort(giftAttributes.length);
	for(int i=0; i<giftAttributes.length; i++){
	com.hifun.soul.gameserver.gift.model.GiftProperty objgiftAttributes = giftAttributes[i];
				writeInteger(objgiftAttributes.getProperty());
				writeInteger(objgiftAttributes.getPropertyValue());
				writeInteger(objgiftAttributes.getPropertyValueType());
	}
	writeShort(gifts.length);
	for(int i=0; i<gifts.length; i++){
	com.hifun.soul.gameserver.gift.model.GiftInfo objgifts = gifts[i];
				writeInteger(objgifts.getId());
				writeInteger(objgifts.getType());
				writeString(objgifts.getName());
				writeInteger(objgifts.getIcon());
				writeInteger(objgifts.getCurrentPropertyId());
				writeInteger(objgifts.getCurrentPropertyValue());
				writeInteger(objgifts.getPropertyValueType());
				writeInteger(objgifts.getCostGiftPoint());
				writeInteger(objgifts.getActiveState());
				writeString(objgifts.getDesc());
				writeInteger(objgifts.getCurrentLevel());
				writeInteger(objgifts.getMaxLevel());
				writeInteger(objgifts.getOpenLevel());
				writeInteger(objgifts.getNextPropertyId());
				writeInteger(objgifts.getNextPropertyValue());
					com.hifun.soul.gameserver.item.assist.CommonItem costItem_objgifts = objgifts.getCostItem();
					writeString(costItem_objgifts.getUUID());
					writeInteger(costItem_objgifts.getItemId());
					writeString(costItem_objgifts.getName());
					writeString(costItem_objgifts.getDesc());
					writeInteger(costItem_objgifts.getType());
					writeInteger(costItem_objgifts.getIcon());
					writeInteger(costItem_objgifts.getRarity());
					writeInteger(costItem_objgifts.getBind());
					writeInteger(costItem_objgifts.getOverlapNum());
					writeInteger(costItem_objgifts.getBagType());
					writeInteger(costItem_objgifts.getBagIndex());
					writeShort(costItem_objgifts.getSellCurrencyType());
					writeInteger(costItem_objgifts.getSellNum());
					writeInteger(costItem_objgifts.getPosition());
					writeInteger(costItem_objgifts.getEndure());
					writeShort(costItem_objgifts.getEquipHole());
					writeShort(costItem_objgifts.getEmbedCurrencyType());
					writeInteger(costItem_objgifts.getEmbedCurrencyNum());
					writeShort(costItem_objgifts.getExtractCurrencyType());
					writeInteger(costItem_objgifts.getExtractCurrencyNum());
					writeShort(costItem_objgifts.getShopCurrencyType());
					writeInteger(costItem_objgifts.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_costItem_objgifts=costItem_objgifts.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_costItem_objgifts.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_costItem_objgifts.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_costItem_objgifts_jequipBaseAttributes = equipBaseAttributes_costItem_objgifts[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_costItem_objgifts_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_costItem_objgifts_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_costItem_objgifts=costItem_objgifts.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_costItem_objgifts.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_costItem_objgifts.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_costItem_objgifts_jequipSpecialAttributes = equipSpecialAttributes_costItem_objgifts[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_costItem_objgifts_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_costItem_objgifts_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_costItem_objgifts=costItem_objgifts.getGemAttributes();
	writeShort(gemAttributes_costItem_objgifts.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_costItem_objgifts.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_costItem_objgifts_jgemAttributes = gemAttributes_costItem_objgifts[jgemAttributes];
													writeInteger(gemAttributes_costItem_objgifts_jgemAttributes.getKey());
													writeInteger(gemAttributes_costItem_objgifts_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_costItem_objgifts=costItem_objgifts.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_costItem_objgifts.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_costItem_objgifts.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_costItem_objgifts_jequipGemItemInfos = equipGemItemInfos_costItem_objgifts[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos=equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objgifts_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_costItem_objgifts=costItem_objgifts.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_costItem_objgifts.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_costItem_objgifts.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_costItem_objgifts_jequipUpgradeAttributes = equipUpgradeAttributes_costItem_objgifts[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_costItem_objgifts_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_costItem_objgifts_jequipUpgradeAttributes.getValue());
									}
					writeFloat(costItem_objgifts.getExtraSuccessRate());
					writeInteger(costItem_objgifts.getUpgradeLevel());
					writeInteger(costItem_objgifts.getLimitLevel());
					writeInteger(costItem_objgifts.getLimitOccupation());
					writeInteger(costItem_objgifts.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_costItem_objgifts=costItem_objgifts.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_costItem_objgifts.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_costItem_objgifts.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_costItem_objgifts_jmaterialsOfEquipPaper = materialsOfEquipPaper_costItem_objgifts[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_costItem_objgifts_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_costItem_objgifts_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(costItem_objgifts.getMaxOverlap());
					writeBoolean(costItem_objgifts.getIsEquiped());
					writeBoolean(costItem_objgifts.getCanSell());
						writeInteger(objgifts.getCostItemNum());
				writeInteger(objgifts.getBagItemNum());
				writeInteger(objgifts.getUpgradeNeedLevel());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_GIFT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_GIFT_PANEL";
	}

	public int getGiftPointRemain(){
		return giftPointRemain;
	}
		
	public void setGiftPointRemain(int giftPointRemain){
		this.giftPointRemain = giftPointRemain;
	}

	public int getGiftPointTotal(){
		return giftPointTotal;
	}
		
	public void setGiftPointTotal(int giftPointTotal){
		this.giftPointTotal = giftPointTotal;
	}

	public com.hifun.soul.gameserver.gift.model.GiftProperty[] getGiftAttributes(){
		return giftAttributes;
	}

	public void setGiftAttributes(com.hifun.soul.gameserver.gift.model.GiftProperty[] giftAttributes){
		this.giftAttributes = giftAttributes;
	}	

	public com.hifun.soul.gameserver.gift.model.GiftInfo[] getGifts(){
		return gifts;
	}

	public void setGifts(com.hifun.soul.gameserver.gift.model.GiftInfo[] gifts){
		this.gifts = gifts;
	}	
}