package com.hifun.soul.gameserver.gem.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新合成宝石的面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateGemSynthesisPanel extends GCMessage{
	
	/** 是否达到最大等级 */
	private boolean reachMaxGrade;
	/** 基础成功率 */
	private float baseSuccessRate;
	/** 消耗货币类型 */
	private int currencyType;
	/** 消耗的货币数量 */
	private int costMoney;
	/** 当前的属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentyProperties;
	/** 强化后的属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeProperties;
	/** 合成所需材料物品 */
	private com.hifun.soul.gameserver.item.assist.CommonItem materialItem;
	/** 合成所需材料数量 */
	private int needMaterialNum;
	/** 已有材料数量 */
	private int hasMaterialNum;
	/** 商城是否出售 */
	private boolean isMallBuy;
	/** 合成后物品 */
	private com.hifun.soul.gameserver.item.assist.CommonItem upgradeItem;

	public GCUpdateGemSynthesisPanel (){
	}
	
	public GCUpdateGemSynthesisPanel (
			boolean reachMaxGrade,
			float baseSuccessRate,
			int currencyType,
			int costMoney,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentyProperties,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeProperties,
			com.hifun.soul.gameserver.item.assist.CommonItem materialItem,
			int needMaterialNum,
			int hasMaterialNum,
			boolean isMallBuy,
			com.hifun.soul.gameserver.item.assist.CommonItem upgradeItem ){
			this.reachMaxGrade = reachMaxGrade;
			this.baseSuccessRate = baseSuccessRate;
			this.currencyType = currencyType;
			this.costMoney = costMoney;
			this.currentyProperties = currentyProperties;
			this.upgradeProperties = upgradeProperties;
			this.materialItem = materialItem;
			this.needMaterialNum = needMaterialNum;
			this.hasMaterialNum = hasMaterialNum;
			this.isMallBuy = isMallBuy;
			this.upgradeItem = upgradeItem;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		reachMaxGrade = readBoolean();
		baseSuccessRate = readFloat();
		currencyType = readInteger();
		costMoney = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		currentyProperties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objcurrentyProperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			currentyProperties[i] = objcurrentyProperties;
					objcurrentyProperties.setKey(readInteger());
							objcurrentyProperties.setValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		upgradeProperties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objupgradeProperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			upgradeProperties[i] = objupgradeProperties;
					objupgradeProperties.setKey(readInteger());
							objupgradeProperties.setValue(readInteger());
				}
		materialItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
						materialItem.setUUID(readString());
						materialItem.setItemId(readInteger());
						materialItem.setName(readString());
						materialItem.setDesc(readString());
						materialItem.setType(readInteger());
						materialItem.setIcon(readInteger());
						materialItem.setRarity(readInteger());
						materialItem.setBind(readInteger());
						materialItem.setOverlapNum(readInteger());
						materialItem.setBagType(readInteger());
						materialItem.setBagIndex(readInteger());
						materialItem.setSellCurrencyType(readShort());
						materialItem.setSellNum(readInteger());
						materialItem.setPosition(readInteger());
						materialItem.setEndure(readInteger());
						materialItem.setEquipHole(readShort());
						materialItem.setEmbedCurrencyType(readShort());
						materialItem.setEmbedCurrencyNum(readInteger());
						materialItem.setExtractCurrencyType(readShort());
						materialItem.setExtractCurrencyNum(readInteger());
						materialItem.setShopCurrencyType(readShort());
						materialItem.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		materialItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		materialItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		materialItem.setGemAttributes(subListgemAttributes);
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
		materialItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		materialItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						materialItem.setExtraSuccessRate(readFloat());
						materialItem.setUpgradeLevel(readInteger());
						materialItem.setLimitLevel(readInteger());
						materialItem.setLimitOccupation(readInteger());
						materialItem.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		materialItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						materialItem.setMaxOverlap(readInteger());
						materialItem.setIsEquiped(readBoolean());
						materialItem.setCanSell(readBoolean());
				needMaterialNum = readInteger();
		hasMaterialNum = readInteger();
		isMallBuy = readBoolean();
		upgradeItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
						upgradeItem.setUUID(readString());
						upgradeItem.setItemId(readInteger());
						upgradeItem.setName(readString());
						upgradeItem.setDesc(readString());
						upgradeItem.setType(readInteger());
						upgradeItem.setIcon(readInteger());
						upgradeItem.setRarity(readInteger());
						upgradeItem.setBind(readInteger());
						upgradeItem.setOverlapNum(readInteger());
						upgradeItem.setBagType(readInteger());
						upgradeItem.setBagIndex(readInteger());
						upgradeItem.setSellCurrencyType(readShort());
						upgradeItem.setSellNum(readInteger());
						upgradeItem.setPosition(readInteger());
						upgradeItem.setEndure(readInteger());
						upgradeItem.setEquipHole(readShort());
						upgradeItem.setEmbedCurrencyType(readShort());
						upgradeItem.setEmbedCurrencyNum(readInteger());
						upgradeItem.setExtractCurrencyType(readShort());
						upgradeItem.setExtractCurrencyNum(readInteger());
						upgradeItem.setShopCurrencyType(readShort());
						upgradeItem.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		upgradeItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		upgradeItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		upgradeItem.setGemAttributes(subListgemAttributes);
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
		upgradeItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		upgradeItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						upgradeItem.setExtraSuccessRate(readFloat());
						upgradeItem.setUpgradeLevel(readInteger());
						upgradeItem.setLimitLevel(readInteger());
						upgradeItem.setLimitOccupation(readInteger());
						upgradeItem.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		upgradeItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						upgradeItem.setMaxOverlap(readInteger());
						upgradeItem.setIsEquiped(readBoolean());
						upgradeItem.setCanSell(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(reachMaxGrade);
		writeFloat(baseSuccessRate);
		writeInteger(currencyType);
		writeInteger(costMoney);
	writeShort(currentyProperties.length);
	for(int i=0; i<currentyProperties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objcurrentyProperties = currentyProperties[i];
				writeInteger(objcurrentyProperties.getKey());
				writeInteger(objcurrentyProperties.getValue());
	}
	writeShort(upgradeProperties.length);
	for(int i=0; i<upgradeProperties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objupgradeProperties = upgradeProperties[i];
				writeInteger(objupgradeProperties.getKey());
				writeInteger(objupgradeProperties.getValue());
	}
		writeString(materialItem.getUUID());
		writeInteger(materialItem.getItemId());
		writeString(materialItem.getName());
		writeString(materialItem.getDesc());
		writeInteger(materialItem.getType());
		writeInteger(materialItem.getIcon());
		writeInteger(materialItem.getRarity());
		writeInteger(materialItem.getBind());
		writeInteger(materialItem.getOverlapNum());
		writeInteger(materialItem.getBagType());
		writeInteger(materialItem.getBagIndex());
		writeShort(materialItem.getSellCurrencyType());
		writeInteger(materialItem.getSellNum());
		writeInteger(materialItem.getPosition());
		writeInteger(materialItem.getEndure());
		writeShort(materialItem.getEquipHole());
		writeShort(materialItem.getEmbedCurrencyType());
		writeInteger(materialItem.getEmbedCurrencyNum());
		writeShort(materialItem.getExtractCurrencyType());
		writeInteger(materialItem.getExtractCurrencyNum());
		writeShort(materialItem.getShopCurrencyType());
		writeInteger(materialItem.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_materialItem=materialItem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_materialItem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_materialItem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_materialItem_jequipBaseAttributes = equipBaseAttributes_materialItem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_materialItem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_materialItem_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_materialItem=materialItem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_materialItem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_materialItem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_materialItem_jequipSpecialAttributes = equipSpecialAttributes_materialItem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_materialItem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_materialItem_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_materialItem=materialItem.getGemAttributes();
	writeShort(gemAttributes_materialItem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_materialItem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_materialItem_jgemAttributes = gemAttributes_materialItem[jgemAttributes];
													writeInteger(gemAttributes_materialItem_jgemAttributes.getKey());
													writeInteger(gemAttributes_materialItem_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_materialItem=materialItem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_materialItem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_materialItem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_materialItem_jequipGemItemInfos = equipGemItemInfos_materialItem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_materialItem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_materialItem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_materialItem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos=equipGemItemInfos_materialItem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_materialItem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_materialItem=materialItem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_materialItem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_materialItem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_materialItem_jequipUpgradeAttributes = equipUpgradeAttributes_materialItem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_materialItem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_materialItem_jequipUpgradeAttributes.getValue());
									}
		writeFloat(materialItem.getExtraSuccessRate());
		writeInteger(materialItem.getUpgradeLevel());
		writeInteger(materialItem.getLimitLevel());
		writeInteger(materialItem.getLimitOccupation());
		writeInteger(materialItem.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_materialItem=materialItem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_materialItem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_materialItem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_materialItem_jmaterialsOfEquipPaper = materialsOfEquipPaper_materialItem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_materialItem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_materialItem_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(materialItem.getMaxOverlap());
		writeBoolean(materialItem.getIsEquiped());
		writeBoolean(materialItem.getCanSell());
		writeInteger(needMaterialNum);
		writeInteger(hasMaterialNum);
		writeBoolean(isMallBuy);
		writeString(upgradeItem.getUUID());
		writeInteger(upgradeItem.getItemId());
		writeString(upgradeItem.getName());
		writeString(upgradeItem.getDesc());
		writeInteger(upgradeItem.getType());
		writeInteger(upgradeItem.getIcon());
		writeInteger(upgradeItem.getRarity());
		writeInteger(upgradeItem.getBind());
		writeInteger(upgradeItem.getOverlapNum());
		writeInteger(upgradeItem.getBagType());
		writeInteger(upgradeItem.getBagIndex());
		writeShort(upgradeItem.getSellCurrencyType());
		writeInteger(upgradeItem.getSellNum());
		writeInteger(upgradeItem.getPosition());
		writeInteger(upgradeItem.getEndure());
		writeShort(upgradeItem.getEquipHole());
		writeShort(upgradeItem.getEmbedCurrencyType());
		writeInteger(upgradeItem.getEmbedCurrencyNum());
		writeShort(upgradeItem.getExtractCurrencyType());
		writeInteger(upgradeItem.getExtractCurrencyNum());
		writeShort(upgradeItem.getShopCurrencyType());
		writeInteger(upgradeItem.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_upgradeItem=upgradeItem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_upgradeItem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_upgradeItem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_upgradeItem_jequipBaseAttributes = equipBaseAttributes_upgradeItem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_upgradeItem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_upgradeItem_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_upgradeItem=upgradeItem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_upgradeItem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_upgradeItem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_upgradeItem_jequipSpecialAttributes = equipSpecialAttributes_upgradeItem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_upgradeItem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_upgradeItem_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_upgradeItem=upgradeItem.getGemAttributes();
	writeShort(gemAttributes_upgradeItem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_upgradeItem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_upgradeItem_jgemAttributes = gemAttributes_upgradeItem[jgemAttributes];
													writeInteger(gemAttributes_upgradeItem_jgemAttributes.getKey());
													writeInteger(gemAttributes_upgradeItem_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_upgradeItem=upgradeItem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_upgradeItem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_upgradeItem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_upgradeItem_jequipGemItemInfos = equipGemItemInfos_upgradeItem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_upgradeItem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_upgradeItem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_upgradeItem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos=equipGemItemInfos_upgradeItem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_upgradeItem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_upgradeItem=upgradeItem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_upgradeItem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_upgradeItem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_upgradeItem_jequipUpgradeAttributes = equipUpgradeAttributes_upgradeItem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_upgradeItem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_upgradeItem_jequipUpgradeAttributes.getValue());
									}
		writeFloat(upgradeItem.getExtraSuccessRate());
		writeInteger(upgradeItem.getUpgradeLevel());
		writeInteger(upgradeItem.getLimitLevel());
		writeInteger(upgradeItem.getLimitOccupation());
		writeInteger(upgradeItem.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_upgradeItem=upgradeItem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_upgradeItem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_upgradeItem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_upgradeItem_jmaterialsOfEquipPaper = materialsOfEquipPaper_upgradeItem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_upgradeItem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_upgradeItem_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(upgradeItem.getMaxOverlap());
		writeBoolean(upgradeItem.getIsEquiped());
		writeBoolean(upgradeItem.getCanSell());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_GEM_SYNTHESIS_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_GEM_SYNTHESIS_PANEL";
	}

	public boolean getReachMaxGrade(){
		return reachMaxGrade;
	}
		
	public void setReachMaxGrade(boolean reachMaxGrade){
		this.reachMaxGrade = reachMaxGrade;
	}

	public float getBaseSuccessRate(){
		return baseSuccessRate;
	}
		
	public void setBaseSuccessRate(float baseSuccessRate){
		this.baseSuccessRate = baseSuccessRate;
	}

	public int getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(int currencyType){
		this.currencyType = currencyType;
	}

	public int getCostMoney(){
		return costMoney;
	}
		
	public void setCostMoney(int costMoney){
		this.costMoney = costMoney;
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getCurrentyProperties(){
		return currentyProperties;
	}

	public void setCurrentyProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentyProperties){
		this.currentyProperties = currentyProperties;
	}	

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getUpgradeProperties(){
		return upgradeProperties;
	}

	public void setUpgradeProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeProperties){
		this.upgradeProperties = upgradeProperties;
	}	

	public com.hifun.soul.gameserver.item.assist.CommonItem getMaterialItem(){
		return materialItem;
	}
		
	public void setMaterialItem(com.hifun.soul.gameserver.item.assist.CommonItem materialItem){
		this.materialItem = materialItem;
	}

	public int getNeedMaterialNum(){
		return needMaterialNum;
	}
		
	public void setNeedMaterialNum(int needMaterialNum){
		this.needMaterialNum = needMaterialNum;
	}

	public int getHasMaterialNum(){
		return hasMaterialNum;
	}
		
	public void setHasMaterialNum(int hasMaterialNum){
		this.hasMaterialNum = hasMaterialNum;
	}

	public boolean getIsMallBuy(){
		return isMallBuy;
	}
		
	public void setIsMallBuy(boolean isMallBuy){
		this.isMallBuy = isMallBuy;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getUpgradeItem(){
		return upgradeItem;
	}
		
	public void setUpgradeItem(com.hifun.soul.gameserver.item.assist.CommonItem upgradeItem){
		this.upgradeItem = upgradeItem;
	}
}