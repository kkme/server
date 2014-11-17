package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应升级灵图
 *
 * @author SevenSoul
 */
@Component
public class GCUpgradeMagicPaper extends GCMessage{
	
	/** 灵图信息  */
	private com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo magicPaperInfo;
	/** 神魄buff信息  */
	private com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos;

	public GCUpgradeMagicPaper (){
	}
	
	public GCUpgradeMagicPaper (
			com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo magicPaperInfo,
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos ){
			this.magicPaperInfo = magicPaperInfo;
			this.godsoulBuffInfos = godsoulBuffInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		magicPaperInfo = new com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo();
						magicPaperInfo.setPaperId(readInteger());
						magicPaperInfo.setCurrentLevel(readInteger());
						magicPaperInfo.setPropertyId(readInteger());
						magicPaperInfo.setCurrentEffect(readInteger());
						magicPaperInfo.setCurrentEquipBitMaxLevel(readInteger());
						magicPaperInfo.setNextEffect(readInteger());
						magicPaperInfo.setNextEquipBitMaxLevel(readInteger());
							{
	com.hifun.soul.gameserver.item.assist.CommonItem objcostItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	magicPaperInfo.setCostItem(objcostItem);
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
						magicPaperInfo.setCostItemNum(readInteger());
						magicPaperInfo.setIsMaxLevel(readBoolean());
						magicPaperInfo.setHasItemNum(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		godsoulBuffInfos = new com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo objgodsoulBuffInfos = new com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo();
			godsoulBuffInfos[i] = objgodsoulBuffInfos;
					objgodsoulBuffInfos.setBuffId(readInteger());
							objgodsoulBuffInfos.setNeedUpgradeLevel(readInteger());
							objgodsoulBuffInfos.setPropertyId(readInteger());
							objgodsoulBuffInfos.setAmendEffect(readInteger());
							objgodsoulBuffInfos.setValid(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(magicPaperInfo.getPaperId());
		writeInteger(magicPaperInfo.getCurrentLevel());
		writeInteger(magicPaperInfo.getPropertyId());
		writeInteger(magicPaperInfo.getCurrentEffect());
		writeInteger(magicPaperInfo.getCurrentEquipBitMaxLevel());
		writeInteger(magicPaperInfo.getNextEffect());
		writeInteger(magicPaperInfo.getNextEquipBitMaxLevel());
			com.hifun.soul.gameserver.item.assist.CommonItem costItem_magicPaperInfo = magicPaperInfo.getCostItem();
					writeString(costItem_magicPaperInfo.getUUID());
					writeInteger(costItem_magicPaperInfo.getItemId());
					writeString(costItem_magicPaperInfo.getName());
					writeString(costItem_magicPaperInfo.getDesc());
					writeInteger(costItem_magicPaperInfo.getType());
					writeInteger(costItem_magicPaperInfo.getIcon());
					writeInteger(costItem_magicPaperInfo.getRarity());
					writeInteger(costItem_magicPaperInfo.getBind());
					writeInteger(costItem_magicPaperInfo.getOverlapNum());
					writeInteger(costItem_magicPaperInfo.getBagType());
					writeInteger(costItem_magicPaperInfo.getBagIndex());
					writeShort(costItem_magicPaperInfo.getSellCurrencyType());
					writeInteger(costItem_magicPaperInfo.getSellNum());
					writeInteger(costItem_magicPaperInfo.getPosition());
					writeInteger(costItem_magicPaperInfo.getEndure());
					writeShort(costItem_magicPaperInfo.getEquipHole());
					writeShort(costItem_magicPaperInfo.getEmbedCurrencyType());
					writeInteger(costItem_magicPaperInfo.getEmbedCurrencyNum());
					writeShort(costItem_magicPaperInfo.getExtractCurrencyType());
					writeInteger(costItem_magicPaperInfo.getExtractCurrencyNum());
					writeShort(costItem_magicPaperInfo.getShopCurrencyType());
					writeInteger(costItem_magicPaperInfo.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_costItem_magicPaperInfo=costItem_magicPaperInfo.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_costItem_magicPaperInfo.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_costItem_magicPaperInfo.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_costItem_magicPaperInfo_jequipBaseAttributes = equipBaseAttributes_costItem_magicPaperInfo[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_costItem_magicPaperInfo_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_costItem_magicPaperInfo_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_costItem_magicPaperInfo=costItem_magicPaperInfo.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_costItem_magicPaperInfo.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_costItem_magicPaperInfo.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_costItem_magicPaperInfo_jequipSpecialAttributes = equipSpecialAttributes_costItem_magicPaperInfo[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_costItem_magicPaperInfo_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_costItem_magicPaperInfo_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_costItem_magicPaperInfo=costItem_magicPaperInfo.getGemAttributes();
	writeShort(gemAttributes_costItem_magicPaperInfo.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_costItem_magicPaperInfo.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_costItem_magicPaperInfo_jgemAttributes = gemAttributes_costItem_magicPaperInfo[jgemAttributes];
													writeInteger(gemAttributes_costItem_magicPaperInfo_jgemAttributes.getKey());
													writeInteger(gemAttributes_costItem_magicPaperInfo_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_costItem_magicPaperInfo=costItem_magicPaperInfo.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_costItem_magicPaperInfo.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_costItem_magicPaperInfo.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos = equipGemItemInfos_costItem_magicPaperInfo[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos=equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_magicPaperInfo_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_costItem_magicPaperInfo=costItem_magicPaperInfo.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_costItem_magicPaperInfo.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_costItem_magicPaperInfo.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_costItem_magicPaperInfo_jequipUpgradeAttributes = equipUpgradeAttributes_costItem_magicPaperInfo[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_costItem_magicPaperInfo_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_costItem_magicPaperInfo_jequipUpgradeAttributes.getValue());
									}
					writeFloat(costItem_magicPaperInfo.getExtraSuccessRate());
					writeInteger(costItem_magicPaperInfo.getUpgradeLevel());
					writeInteger(costItem_magicPaperInfo.getLimitLevel());
					writeInteger(costItem_magicPaperInfo.getLimitOccupation());
					writeInteger(costItem_magicPaperInfo.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_costItem_magicPaperInfo=costItem_magicPaperInfo.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_costItem_magicPaperInfo.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_costItem_magicPaperInfo.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_costItem_magicPaperInfo_jmaterialsOfEquipPaper = materialsOfEquipPaper_costItem_magicPaperInfo[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_costItem_magicPaperInfo_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_costItem_magicPaperInfo_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(costItem_magicPaperInfo.getMaxOverlap());
					writeBoolean(costItem_magicPaperInfo.getIsEquiped());
					writeBoolean(costItem_magicPaperInfo.getCanSell());
				writeInteger(magicPaperInfo.getCostItemNum());
		writeBoolean(magicPaperInfo.getIsMaxLevel());
		writeInteger(magicPaperInfo.getHasItemNum());
	writeShort(godsoulBuffInfos.length);
	for(int i=0; i<godsoulBuffInfos.length; i++){
	com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo objgodsoulBuffInfos = godsoulBuffInfos[i];
				writeInteger(objgodsoulBuffInfos.getBuffId());
				writeInteger(objgodsoulBuffInfos.getNeedUpgradeLevel());
				writeInteger(objgodsoulBuffInfos.getPropertyId());
				writeInteger(objgodsoulBuffInfos.getAmendEffect());
				writeBoolean(objgodsoulBuffInfos.getValid());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPGRADE_MAGIC_PAPER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPGRADE_MAGIC_PAPER";
	}

	public com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo getMagicPaperInfo (){
		return magicPaperInfo;
	}
		
	public void setMagicPaperInfo (com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo magicPaperInfo){
		this.magicPaperInfo = magicPaperInfo;
	}

	public com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] getGodsoulBuffInfos (){
		return godsoulBuffInfos;
	}

	public void setGodsoulBuffInfos (com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos){
		this.godsoulBuffInfos = godsoulBuffInfos;
	}	
}