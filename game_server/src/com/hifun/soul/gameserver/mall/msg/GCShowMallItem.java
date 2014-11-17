package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示选中道具
 *
 * @author SevenSoul
 */
@Component
public class GCShowMallItem extends GCMessage{
	
	/** 商城道具信息 */
	private com.hifun.soul.gameserver.mall.msg.MallItemInfo mallItem;

	public GCShowMallItem (){
	}
	
	public GCShowMallItem (
			com.hifun.soul.gameserver.mall.msg.MallItemInfo mallItem ){
			this.mallItem = mallItem;
	}

	@Override
	protected boolean readImpl() {
		mallItem = new com.hifun.soul.gameserver.mall.msg.MallItemInfo();
						mallItem.setItemId(readInteger());
						mallItem.setCurrencyType(readShort());
						mallItem.setNum(readInteger());
						mallItem.setDiscount(readBoolean());
						mallItem.setDiscountRate(readFloat());
						mallItem.setMallItemType(readInteger());
							{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	mallItem.setCommonItem(objcommonItem);
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
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mallItem.getItemId());
		writeShort(mallItem.getCurrencyType());
		writeInteger(mallItem.getNum());
		writeBoolean(mallItem.getDiscount());
		writeFloat(mallItem.getDiscountRate());
		writeInteger(mallItem.getMallItemType());
			com.hifun.soul.gameserver.item.assist.CommonItem commonItem_mallItem = mallItem.getCommonItem();
					writeString(commonItem_mallItem.getUUID());
					writeInteger(commonItem_mallItem.getItemId());
					writeString(commonItem_mallItem.getName());
					writeString(commonItem_mallItem.getDesc());
					writeInteger(commonItem_mallItem.getType());
					writeInteger(commonItem_mallItem.getIcon());
					writeInteger(commonItem_mallItem.getRarity());
					writeInteger(commonItem_mallItem.getBind());
					writeInteger(commonItem_mallItem.getOverlapNum());
					writeInteger(commonItem_mallItem.getBagType());
					writeInteger(commonItem_mallItem.getBagIndex());
					writeShort(commonItem_mallItem.getSellCurrencyType());
					writeInteger(commonItem_mallItem.getSellNum());
					writeInteger(commonItem_mallItem.getPosition());
					writeInteger(commonItem_mallItem.getEndure());
					writeShort(commonItem_mallItem.getEquipHole());
					writeShort(commonItem_mallItem.getEmbedCurrencyType());
					writeInteger(commonItem_mallItem.getEmbedCurrencyNum());
					writeShort(commonItem_mallItem.getExtractCurrencyType());
					writeInteger(commonItem_mallItem.getExtractCurrencyNum());
					writeShort(commonItem_mallItem.getShopCurrencyType());
					writeInteger(commonItem_mallItem.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_mallItem=commonItem_mallItem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_mallItem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_mallItem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_mallItem_jequipBaseAttributes = equipBaseAttributes_commonItem_mallItem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_mallItem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_mallItem_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_mallItem=commonItem_mallItem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_mallItem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_mallItem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_mallItem_jequipSpecialAttributes = equipSpecialAttributes_commonItem_mallItem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_mallItem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_mallItem_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_mallItem=commonItem_mallItem.getGemAttributes();
	writeShort(gemAttributes_commonItem_mallItem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_mallItem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_mallItem_jgemAttributes = gemAttributes_commonItem_mallItem[jgemAttributes];
													writeInteger(gemAttributes_commonItem_mallItem_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_mallItem_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_mallItem=commonItem_mallItem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_mallItem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_mallItem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos = equipGemItemInfos_commonItem_mallItem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos=equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_mallItem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_mallItem=commonItem_mallItem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_mallItem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_mallItem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_mallItem_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_mallItem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_mallItem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_mallItem_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_mallItem.getExtraSuccessRate());
					writeInteger(commonItem_mallItem.getUpgradeLevel());
					writeInteger(commonItem_mallItem.getLimitLevel());
					writeInteger(commonItem_mallItem.getLimitOccupation());
					writeInteger(commonItem_mallItem.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_mallItem=commonItem_mallItem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_mallItem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_mallItem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_mallItem_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_mallItem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_mallItem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_mallItem_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_mallItem.getMaxOverlap());
					writeBoolean(commonItem_mallItem.getIsEquiped());
					writeBoolean(commonItem_mallItem.getCanSell());
				return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MALL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MALL_ITEM";
	}

	public com.hifun.soul.gameserver.mall.msg.MallItemInfo getMallItem(){
		return mallItem;
	}
		
	public void setMallItem(com.hifun.soul.gameserver.mall.msg.MallItemInfo mallItem){
		this.mallItem = mallItem;
	}
}