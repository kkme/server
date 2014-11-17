package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示灵图标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowMagicPaperTab extends GCMessage{
	
	/** 灵图列表  */
	private com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo[] magicPaperInfos;
	/** 神魄buff信息  */
	private com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos;

	public GCShowMagicPaperTab (){
	}
	
	public GCShowMagicPaperTab (
			com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo[] magicPaperInfos,
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos ){
			this.magicPaperInfos = magicPaperInfos;
			this.godsoulBuffInfos = godsoulBuffInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		magicPaperInfos = new com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo objmagicPaperInfos = new com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo();
			magicPaperInfos[i] = objmagicPaperInfos;
					objmagicPaperInfos.setPaperId(readInteger());
							objmagicPaperInfos.setCurrentLevel(readInteger());
							objmagicPaperInfos.setPropertyId(readInteger());
							objmagicPaperInfos.setCurrentEffect(readInteger());
							objmagicPaperInfos.setCurrentEquipBitMaxLevel(readInteger());
							objmagicPaperInfos.setNextEffect(readInteger());
							objmagicPaperInfos.setNextEquipBitMaxLevel(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcostItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objmagicPaperInfos.setCostItem(objcostItem);
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
							objmagicPaperInfos.setCostItemNum(readInteger());
							objmagicPaperInfos.setIsMaxLevel(readBoolean());
							objmagicPaperInfos.setHasItemNum(readInteger());
				}
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
	writeShort(magicPaperInfos.length);
	for(int i=0; i<magicPaperInfos.length; i++){
	com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo objmagicPaperInfos = magicPaperInfos[i];
				writeInteger(objmagicPaperInfos.getPaperId());
				writeInteger(objmagicPaperInfos.getCurrentLevel());
				writeInteger(objmagicPaperInfos.getPropertyId());
				writeInteger(objmagicPaperInfos.getCurrentEffect());
				writeInteger(objmagicPaperInfos.getCurrentEquipBitMaxLevel());
				writeInteger(objmagicPaperInfos.getNextEffect());
				writeInteger(objmagicPaperInfos.getNextEquipBitMaxLevel());
					com.hifun.soul.gameserver.item.assist.CommonItem costItem_objmagicPaperInfos = objmagicPaperInfos.getCostItem();
					writeString(costItem_objmagicPaperInfos.getUUID());
					writeInteger(costItem_objmagicPaperInfos.getItemId());
					writeString(costItem_objmagicPaperInfos.getName());
					writeString(costItem_objmagicPaperInfos.getDesc());
					writeInteger(costItem_objmagicPaperInfos.getType());
					writeInteger(costItem_objmagicPaperInfos.getIcon());
					writeInteger(costItem_objmagicPaperInfos.getRarity());
					writeInteger(costItem_objmagicPaperInfos.getBind());
					writeInteger(costItem_objmagicPaperInfos.getOverlapNum());
					writeInteger(costItem_objmagicPaperInfos.getBagType());
					writeInteger(costItem_objmagicPaperInfos.getBagIndex());
					writeShort(costItem_objmagicPaperInfos.getSellCurrencyType());
					writeInteger(costItem_objmagicPaperInfos.getSellNum());
					writeInteger(costItem_objmagicPaperInfos.getPosition());
					writeInteger(costItem_objmagicPaperInfos.getEndure());
					writeShort(costItem_objmagicPaperInfos.getEquipHole());
					writeShort(costItem_objmagicPaperInfos.getEmbedCurrencyType());
					writeInteger(costItem_objmagicPaperInfos.getEmbedCurrencyNum());
					writeShort(costItem_objmagicPaperInfos.getExtractCurrencyType());
					writeInteger(costItem_objmagicPaperInfos.getExtractCurrencyNum());
					writeShort(costItem_objmagicPaperInfos.getShopCurrencyType());
					writeInteger(costItem_objmagicPaperInfos.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_costItem_objmagicPaperInfos.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_costItem_objmagicPaperInfos.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_costItem_objmagicPaperInfos_jequipBaseAttributes = equipBaseAttributes_costItem_objmagicPaperInfos[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_costItem_objmagicPaperInfos_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_costItem_objmagicPaperInfos_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_costItem_objmagicPaperInfos.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_costItem_objmagicPaperInfos.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_costItem_objmagicPaperInfos_jequipSpecialAttributes = equipSpecialAttributes_costItem_objmagicPaperInfos[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_costItem_objmagicPaperInfos_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_costItem_objmagicPaperInfos_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getGemAttributes();
	writeShort(gemAttributes_costItem_objmagicPaperInfos.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_costItem_objmagicPaperInfos.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_costItem_objmagicPaperInfos_jgemAttributes = gemAttributes_costItem_objmagicPaperInfos[jgemAttributes];
													writeInteger(gemAttributes_costItem_objmagicPaperInfos_jgemAttributes.getKey());
													writeInteger(gemAttributes_costItem_objmagicPaperInfos_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_costItem_objmagicPaperInfos.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_costItem_objmagicPaperInfos.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos = equipGemItemInfos_costItem_objmagicPaperInfos[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos=equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_costItem_objmagicPaperInfos_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_costItem_objmagicPaperInfos.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_costItem_objmagicPaperInfos.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_costItem_objmagicPaperInfos_jequipUpgradeAttributes = equipUpgradeAttributes_costItem_objmagicPaperInfos[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_costItem_objmagicPaperInfos_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_costItem_objmagicPaperInfos_jequipUpgradeAttributes.getValue());
									}
					writeFloat(costItem_objmagicPaperInfos.getExtraSuccessRate());
					writeInteger(costItem_objmagicPaperInfos.getUpgradeLevel());
					writeInteger(costItem_objmagicPaperInfos.getLimitLevel());
					writeInteger(costItem_objmagicPaperInfos.getLimitOccupation());
					writeInteger(costItem_objmagicPaperInfos.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_costItem_objmagicPaperInfos=costItem_objmagicPaperInfos.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_costItem_objmagicPaperInfos.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_costItem_objmagicPaperInfos.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_costItem_objmagicPaperInfos_jmaterialsOfEquipPaper = materialsOfEquipPaper_costItem_objmagicPaperInfos[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_costItem_objmagicPaperInfos_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_costItem_objmagicPaperInfos_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(costItem_objmagicPaperInfos.getMaxOverlap());
					writeBoolean(costItem_objmagicPaperInfos.getIsEquiped());
					writeBoolean(costItem_objmagicPaperInfos.getCanSell());
						writeInteger(objmagicPaperInfos.getCostItemNum());
				writeBoolean(objmagicPaperInfos.getIsMaxLevel());
				writeInteger(objmagicPaperInfos.getHasItemNum());
	}
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
		return MessageType.GC_SHOW_MAGIC_PAPER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MAGIC_PAPER_TAB";
	}

	public com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo[] getMagicPaperInfos (){
		return magicPaperInfos;
	}

	public void setMagicPaperInfos (com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo[] magicPaperInfos){
		this.magicPaperInfos = magicPaperInfos;
	}	

	public com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] getGodsoulBuffInfos (){
		return godsoulBuffInfos;
	}

	public void setGodsoulBuffInfos (com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos){
		this.godsoulBuffInfos = godsoulBuffInfos;
	}	
}