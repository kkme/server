package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 选中要强化的装备
 *
 * @author SevenSoul
 */
@Component
public class GCSelectEquip extends GCMessage{
	
	/** 成功的概率 */
	private int rate;
	/** 花费货币类型 */
	private short currencyType;
	/** 花费货币数量 */
	private int currencyNum;
	/** 强化属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeAttributes;
	/** 需要强化石的数量 */
	private int needUpgradeStoneNum;
	/** 强化石 */
	private com.hifun.soul.gameserver.item.assist.CommonItem upgradeStone;

	public GCSelectEquip (){
	}
	
	public GCSelectEquip (
			int rate,
			short currencyType,
			int currencyNum,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeAttributes,
			int needUpgradeStoneNum,
			com.hifun.soul.gameserver.item.assist.CommonItem upgradeStone ){
			this.rate = rate;
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
			this.upgradeAttributes = upgradeAttributes;
			this.needUpgradeStoneNum = needUpgradeStoneNum;
			this.upgradeStone = upgradeStone;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		rate = readInteger();
		currencyType = readShort();
		currencyNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		upgradeAttributes = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objupgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			upgradeAttributes[i] = objupgradeAttributes;
					objupgradeAttributes.setKey(readInteger());
							objupgradeAttributes.setValue(readInteger());
				}
		needUpgradeStoneNum = readInteger();
		upgradeStone = new com.hifun.soul.gameserver.item.assist.CommonItem();
						upgradeStone.setUUID(readString());
						upgradeStone.setItemId(readInteger());
						upgradeStone.setName(readString());
						upgradeStone.setDesc(readString());
						upgradeStone.setType(readInteger());
						upgradeStone.setIcon(readInteger());
						upgradeStone.setRarity(readInteger());
						upgradeStone.setBind(readInteger());
						upgradeStone.setOverlapNum(readInteger());
						upgradeStone.setBagType(readInteger());
						upgradeStone.setBagIndex(readInteger());
						upgradeStone.setSellCurrencyType(readShort());
						upgradeStone.setSellNum(readInteger());
						upgradeStone.setPosition(readInteger());
						upgradeStone.setEndure(readInteger());
						upgradeStone.setEquipHole(readShort());
						upgradeStone.setEmbedCurrencyType(readShort());
						upgradeStone.setEmbedCurrencyNum(readInteger());
						upgradeStone.setExtractCurrencyType(readShort());
						upgradeStone.setExtractCurrencyNum(readInteger());
						upgradeStone.setShopCurrencyType(readShort());
						upgradeStone.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		upgradeStone.setEquipBaseAttributes(subListequipBaseAttributes);
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
		upgradeStone.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		upgradeStone.setGemAttributes(subListgemAttributes);
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
		upgradeStone.setEquipGemItemInfos(subListequipGemItemInfos);
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
		upgradeStone.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						upgradeStone.setExtraSuccessRate(readFloat());
						upgradeStone.setUpgradeLevel(readInteger());
						upgradeStone.setLimitLevel(readInteger());
						upgradeStone.setLimitOccupation(readInteger());
						upgradeStone.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		upgradeStone.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						upgradeStone.setMaxOverlap(readInteger());
						upgradeStone.setIsEquiped(readBoolean());
						upgradeStone.setCanSell(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rate);
		writeShort(currencyType);
		writeInteger(currencyNum);
	writeShort(upgradeAttributes.length);
	for(int i=0; i<upgradeAttributes.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objupgradeAttributes = upgradeAttributes[i];
				writeInteger(objupgradeAttributes.getKey());
				writeInteger(objupgradeAttributes.getValue());
	}
		writeInteger(needUpgradeStoneNum);
		writeString(upgradeStone.getUUID());
		writeInteger(upgradeStone.getItemId());
		writeString(upgradeStone.getName());
		writeString(upgradeStone.getDesc());
		writeInteger(upgradeStone.getType());
		writeInteger(upgradeStone.getIcon());
		writeInteger(upgradeStone.getRarity());
		writeInteger(upgradeStone.getBind());
		writeInteger(upgradeStone.getOverlapNum());
		writeInteger(upgradeStone.getBagType());
		writeInteger(upgradeStone.getBagIndex());
		writeShort(upgradeStone.getSellCurrencyType());
		writeInteger(upgradeStone.getSellNum());
		writeInteger(upgradeStone.getPosition());
		writeInteger(upgradeStone.getEndure());
		writeShort(upgradeStone.getEquipHole());
		writeShort(upgradeStone.getEmbedCurrencyType());
		writeInteger(upgradeStone.getEmbedCurrencyNum());
		writeShort(upgradeStone.getExtractCurrencyType());
		writeInteger(upgradeStone.getExtractCurrencyNum());
		writeShort(upgradeStone.getShopCurrencyType());
		writeInteger(upgradeStone.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_upgradeStone=upgradeStone.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_upgradeStone.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_upgradeStone.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_upgradeStone_jequipBaseAttributes = equipBaseAttributes_upgradeStone[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_upgradeStone_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_upgradeStone_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_upgradeStone=upgradeStone.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_upgradeStone.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_upgradeStone.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_upgradeStone_jequipSpecialAttributes = equipSpecialAttributes_upgradeStone[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_upgradeStone_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_upgradeStone_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_upgradeStone=upgradeStone.getGemAttributes();
	writeShort(gemAttributes_upgradeStone.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_upgradeStone.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_upgradeStone_jgemAttributes = gemAttributes_upgradeStone[jgemAttributes];
													writeInteger(gemAttributes_upgradeStone_jgemAttributes.getKey());
													writeInteger(gemAttributes_upgradeStone_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_upgradeStone=upgradeStone.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_upgradeStone.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_upgradeStone.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_upgradeStone_jequipGemItemInfos = equipGemItemInfos_upgradeStone[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_upgradeStone_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_upgradeStone_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_upgradeStone_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos=equipGemItemInfos_upgradeStone_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_upgradeStone_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_upgradeStone=upgradeStone.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_upgradeStone.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_upgradeStone.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_upgradeStone_jequipUpgradeAttributes = equipUpgradeAttributes_upgradeStone[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_upgradeStone_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_upgradeStone_jequipUpgradeAttributes.getValue());
									}
		writeFloat(upgradeStone.getExtraSuccessRate());
		writeInteger(upgradeStone.getUpgradeLevel());
		writeInteger(upgradeStone.getLimitLevel());
		writeInteger(upgradeStone.getLimitOccupation());
		writeInteger(upgradeStone.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_upgradeStone=upgradeStone.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_upgradeStone.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_upgradeStone.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_upgradeStone_jmaterialsOfEquipPaper = materialsOfEquipPaper_upgradeStone[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_upgradeStone_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_upgradeStone_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(upgradeStone.getMaxOverlap());
		writeBoolean(upgradeStone.getIsEquiped());
		writeBoolean(upgradeStone.getCanSell());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SELECT_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SELECT_EQUIP";
	}

	public int getRate(){
		return rate;
	}
		
	public void setRate(int rate){
		this.rate = rate;
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

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getUpgradeAttributes(){
		return upgradeAttributes;
	}

	public void setUpgradeAttributes(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] upgradeAttributes){
		this.upgradeAttributes = upgradeAttributes;
	}	

	public int getNeedUpgradeStoneNum(){
		return needUpgradeStoneNum;
	}
		
	public void setNeedUpgradeStoneNum(int needUpgradeStoneNum){
		this.needUpgradeStoneNum = needUpgradeStoneNum;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getUpgradeStone(){
		return upgradeStone;
	}
		
	public void setUpgradeStone(com.hifun.soul.gameserver.item.assist.CommonItem upgradeStone){
		this.upgradeStone = upgradeStone;
	}
}