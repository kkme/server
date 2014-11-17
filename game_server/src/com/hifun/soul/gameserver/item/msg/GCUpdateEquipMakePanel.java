package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新打造装备的面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateEquipMakePanel extends GCMessage{
	
	/** 所需材料物品列表 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] materialItem;
	/** 所需材料对应的所需数量 */
	private int[] needMaterialNums;
	/** 已有材料数量 */
	private int[] hasMaterialNums;
	/** 消耗货币类型 */
	private int currencyType;
	/** 消耗的货币数量 */
	private int costMoney;
	/** 当前拥有的货币数量是否足够 */
	private boolean isMoneyEnough;
	/** 所需装备 */
	private com.hifun.soul.gameserver.item.assist.CommonItem equip;
	/** 拥有所需装备的数量 */
	private int equipNum;
	/** 引导关卡名称 */
	private String[] guides;
	/** 需要图纸数量 */
	private int needPaperNum;
	/** 已有图纸数量 */
	private int hasPaperNum;

	public GCUpdateEquipMakePanel (){
	}
	
	public GCUpdateEquipMakePanel (
			com.hifun.soul.gameserver.item.assist.CommonItem[] materialItem,
			int[] needMaterialNums,
			int[] hasMaterialNums,
			int currencyType,
			int costMoney,
			boolean isMoneyEnough,
			com.hifun.soul.gameserver.item.assist.CommonItem equip,
			int equipNum,
			String[] guides,
			int needPaperNum,
			int hasPaperNum ){
			this.materialItem = materialItem;
			this.needMaterialNums = needMaterialNums;
			this.hasMaterialNums = hasMaterialNums;
			this.currencyType = currencyType;
			this.costMoney = costMoney;
			this.isMoneyEnough = isMoneyEnough;
			this.equip = equip;
			this.equipNum = equipNum;
			this.guides = guides;
			this.needPaperNum = needPaperNum;
			this.hasPaperNum = hasPaperNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		materialItem = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objmaterialItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
			materialItem[i] = objmaterialItem;
					objmaterialItem.setUUID(readString());
							objmaterialItem.setItemId(readInteger());
							objmaterialItem.setName(readString());
							objmaterialItem.setDesc(readString());
							objmaterialItem.setType(readInteger());
							objmaterialItem.setIcon(readInteger());
							objmaterialItem.setRarity(readInteger());
							objmaterialItem.setBind(readInteger());
							objmaterialItem.setOverlapNum(readInteger());
							objmaterialItem.setBagType(readInteger());
							objmaterialItem.setBagIndex(readInteger());
							objmaterialItem.setSellCurrencyType(readShort());
							objmaterialItem.setSellNum(readInteger());
							objmaterialItem.setPosition(readInteger());
							objmaterialItem.setEndure(readInteger());
							objmaterialItem.setEquipHole(readShort());
							objmaterialItem.setEmbedCurrencyType(readShort());
							objmaterialItem.setEmbedCurrencyNum(readInteger());
							objmaterialItem.setExtractCurrencyType(readShort());
							objmaterialItem.setExtractCurrencyNum(readInteger());
							objmaterialItem.setShopCurrencyType(readShort());
							objmaterialItem.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objmaterialItem.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objmaterialItem.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objmaterialItem.setGemAttributes(subListgemAttributes);
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
		objmaterialItem.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objmaterialItem.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objmaterialItem.setExtraSuccessRate(readFloat());
							objmaterialItem.setUpgradeLevel(readInteger());
							objmaterialItem.setLimitLevel(readInteger());
							objmaterialItem.setLimitOccupation(readInteger());
							objmaterialItem.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objmaterialItem.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objmaterialItem.setMaxOverlap(readInteger());
							objmaterialItem.setIsEquiped(readBoolean());
							objmaterialItem.setCanSell(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		needMaterialNums = new int[count];
		for(int i=0; i<count; i++){
			needMaterialNums[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		hasMaterialNums = new int[count];
		for(int i=0; i<count; i++){
			hasMaterialNums[i] = readInteger();
		}
		currencyType = readInteger();
		costMoney = readInteger();
		isMoneyEnough = readBoolean();
		equip = new com.hifun.soul.gameserver.item.assist.CommonItem();
						equip.setUUID(readString());
						equip.setItemId(readInteger());
						equip.setName(readString());
						equip.setDesc(readString());
						equip.setType(readInteger());
						equip.setIcon(readInteger());
						equip.setRarity(readInteger());
						equip.setBind(readInteger());
						equip.setOverlapNum(readInteger());
						equip.setBagType(readInteger());
						equip.setBagIndex(readInteger());
						equip.setSellCurrencyType(readShort());
						equip.setSellNum(readInteger());
						equip.setPosition(readInteger());
						equip.setEndure(readInteger());
						equip.setEquipHole(readShort());
						equip.setEmbedCurrencyType(readShort());
						equip.setEmbedCurrencyNum(readInteger());
						equip.setExtractCurrencyType(readShort());
						equip.setExtractCurrencyNum(readInteger());
						equip.setShopCurrencyType(readShort());
						equip.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		equip.setEquipBaseAttributes(subListequipBaseAttributes);
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
		equip.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		equip.setGemAttributes(subListgemAttributes);
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
		equip.setEquipGemItemInfos(subListequipGemItemInfos);
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
		equip.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						equip.setExtraSuccessRate(readFloat());
						equip.setUpgradeLevel(readInteger());
						equip.setLimitLevel(readInteger());
						equip.setLimitOccupation(readInteger());
						equip.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		equip.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						equip.setMaxOverlap(readInteger());
						equip.setIsEquiped(readBoolean());
						equip.setCanSell(readBoolean());
				equipNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		guides = new String[count];
		for(int i=0; i<count; i++){
			guides[i] = readString();
		}
		needPaperNum = readInteger();
		hasPaperNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(materialItem.length);
	for(int i=0; i<materialItem.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objmaterialItem = materialItem[i];
				writeString(objmaterialItem.getUUID());
				writeInteger(objmaterialItem.getItemId());
				writeString(objmaterialItem.getName());
				writeString(objmaterialItem.getDesc());
				writeInteger(objmaterialItem.getType());
				writeInteger(objmaterialItem.getIcon());
				writeInteger(objmaterialItem.getRarity());
				writeInteger(objmaterialItem.getBind());
				writeInteger(objmaterialItem.getOverlapNum());
				writeInteger(objmaterialItem.getBagType());
				writeInteger(objmaterialItem.getBagIndex());
				writeShort(objmaterialItem.getSellCurrencyType());
				writeInteger(objmaterialItem.getSellNum());
				writeInteger(objmaterialItem.getPosition());
				writeInteger(objmaterialItem.getEndure());
				writeShort(objmaterialItem.getEquipHole());
				writeShort(objmaterialItem.getEmbedCurrencyType());
				writeInteger(objmaterialItem.getEmbedCurrencyNum());
				writeShort(objmaterialItem.getExtractCurrencyType());
				writeInteger(objmaterialItem.getExtractCurrencyNum());
				writeShort(objmaterialItem.getShopCurrencyType());
				writeInteger(objmaterialItem.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objmaterialItem=objmaterialItem.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objmaterialItem.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objmaterialItem.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objmaterialItem_jequipBaseAttributes = equipBaseAttributes_objmaterialItem[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objmaterialItem_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objmaterialItem_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objmaterialItem=objmaterialItem.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objmaterialItem.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objmaterialItem.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objmaterialItem_jequipSpecialAttributes = equipSpecialAttributes_objmaterialItem[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objmaterialItem_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objmaterialItem_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objmaterialItem=objmaterialItem.getGemAttributes();
	writeShort(gemAttributes_objmaterialItem.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objmaterialItem.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objmaterialItem_jgemAttributes = gemAttributes_objmaterialItem[jgemAttributes];
													writeInteger(gemAttributes_objmaterialItem_jgemAttributes.getKey());
													writeInteger(gemAttributes_objmaterialItem_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objmaterialItem=objmaterialItem.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objmaterialItem.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objmaterialItem.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objmaterialItem_jequipGemItemInfos = equipGemItemInfos_objmaterialItem[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objmaterialItem_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objmaterialItem_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objmaterialItem_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos=equipGemItemInfos_objmaterialItem_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objmaterialItem_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objmaterialItem=objmaterialItem.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objmaterialItem.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objmaterialItem.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objmaterialItem_jequipUpgradeAttributes = equipUpgradeAttributes_objmaterialItem[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objmaterialItem_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objmaterialItem_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objmaterialItem.getExtraSuccessRate());
				writeInteger(objmaterialItem.getUpgradeLevel());
				writeInteger(objmaterialItem.getLimitLevel());
				writeInteger(objmaterialItem.getLimitOccupation());
				writeInteger(objmaterialItem.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objmaterialItem=objmaterialItem.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objmaterialItem.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objmaterialItem.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objmaterialItem_jmaterialsOfEquipPaper = materialsOfEquipPaper_objmaterialItem[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objmaterialItem_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objmaterialItem_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objmaterialItem.getMaxOverlap());
				writeBoolean(objmaterialItem.getIsEquiped());
				writeBoolean(objmaterialItem.getCanSell());
	}
	writeShort(needMaterialNums.length);
	for(int i=0; i<needMaterialNums.length; i++){
	Integer objneedMaterialNums = needMaterialNums[i];
			writeInteger(objneedMaterialNums);
}
	writeShort(hasMaterialNums.length);
	for(int i=0; i<hasMaterialNums.length; i++){
	Integer objhasMaterialNums = hasMaterialNums[i];
			writeInteger(objhasMaterialNums);
}
		writeInteger(currencyType);
		writeInteger(costMoney);
		writeBoolean(isMoneyEnough);
		writeString(equip.getUUID());
		writeInteger(equip.getItemId());
		writeString(equip.getName());
		writeString(equip.getDesc());
		writeInteger(equip.getType());
		writeInteger(equip.getIcon());
		writeInteger(equip.getRarity());
		writeInteger(equip.getBind());
		writeInteger(equip.getOverlapNum());
		writeInteger(equip.getBagType());
		writeInteger(equip.getBagIndex());
		writeShort(equip.getSellCurrencyType());
		writeInteger(equip.getSellNum());
		writeInteger(equip.getPosition());
		writeInteger(equip.getEndure());
		writeShort(equip.getEquipHole());
		writeShort(equip.getEmbedCurrencyType());
		writeInteger(equip.getEmbedCurrencyNum());
		writeShort(equip.getExtractCurrencyType());
		writeInteger(equip.getExtractCurrencyNum());
		writeShort(equip.getShopCurrencyType());
		writeInteger(equip.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_equip=equip.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_equip.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_equip.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_equip_jequipBaseAttributes = equipBaseAttributes_equip[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_equip_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_equip_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_equip=equip.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_equip.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_equip.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_equip_jequipSpecialAttributes = equipSpecialAttributes_equip[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_equip_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_equip_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_equip=equip.getGemAttributes();
	writeShort(gemAttributes_equip.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_equip.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_equip_jgemAttributes = gemAttributes_equip[jgemAttributes];
													writeInteger(gemAttributes_equip_jgemAttributes.getKey());
													writeInteger(gemAttributes_equip_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_equip=equip.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_equip.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_equip.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_equip_jequipGemItemInfos = equipGemItemInfos_equip[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_equip_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_equip_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_equip_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos=equipGemItemInfos_equip_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_equip_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_equip=equip.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_equip.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_equip.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_equip_jequipUpgradeAttributes = equipUpgradeAttributes_equip[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_equip_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_equip_jequipUpgradeAttributes.getValue());
									}
		writeFloat(equip.getExtraSuccessRate());
		writeInteger(equip.getUpgradeLevel());
		writeInteger(equip.getLimitLevel());
		writeInteger(equip.getLimitOccupation());
		writeInteger(equip.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_equip=equip.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_equip.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_equip.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_equip_jmaterialsOfEquipPaper = materialsOfEquipPaper_equip[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_equip_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_equip_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(equip.getMaxOverlap());
		writeBoolean(equip.getIsEquiped());
		writeBoolean(equip.getCanSell());
		writeInteger(equipNum);
	writeShort(guides.length);
	for(int i=0; i<guides.length; i++){
	String objguides = guides[i];
			writeString(objguides);
}
		writeInteger(needPaperNum);
		writeInteger(hasPaperNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_EQUIP_MAKE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_EQUIP_MAKE_PANEL";
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getMaterialItem(){
		return materialItem;
	}

	public void setMaterialItem(com.hifun.soul.gameserver.item.assist.CommonItem[] materialItem){
		this.materialItem = materialItem;
	}	

	public int[] getNeedMaterialNums(){
		return needMaterialNums;
	}

	public void setNeedMaterialNums(int[] needMaterialNums){
		this.needMaterialNums = needMaterialNums;
	}	

	public int[] getHasMaterialNums(){
		return hasMaterialNums;
	}

	public void setHasMaterialNums(int[] hasMaterialNums){
		this.hasMaterialNums = hasMaterialNums;
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

	public boolean getIsMoneyEnough(){
		return isMoneyEnough;
	}
		
	public void setIsMoneyEnough(boolean isMoneyEnough){
		this.isMoneyEnough = isMoneyEnough;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getEquip(){
		return equip;
	}
		
	public void setEquip(com.hifun.soul.gameserver.item.assist.CommonItem equip){
		this.equip = equip;
	}

	public int getEquipNum(){
		return equipNum;
	}
		
	public void setEquipNum(int equipNum){
		this.equipNum = equipNum;
	}

	public String[] getGuides(){
		return guides;
	}

	public void setGuides(String[] guides){
		this.guides = guides;
	}	

	public int getNeedPaperNum(){
		return needPaperNum;
	}
		
	public void setNeedPaperNum(int needPaperNum){
		this.needPaperNum = needPaperNum;
	}

	public int getHasPaperNum(){
		return hasPaperNum;
	}
		
	public void setHasPaperNum(int hasPaperNum){
		this.hasPaperNum = hasPaperNum;
	}
}