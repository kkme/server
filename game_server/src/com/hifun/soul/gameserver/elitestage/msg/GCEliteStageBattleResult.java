package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 精英副本战斗结果
 *
 * @author SevenSoul
 */
@Component
public class GCEliteStageBattleResult extends GCMessage{
	
	/** 副本id */
	private int stageId;
	/** 战斗结果:true表示成功，false表示失败 */
	private boolean battleResult;
	/** 金币 */
	private int coinNum;
	/** 经验 */
	private int experience;
	/** 科技点 */
	private int techPoint;
	/** 奖品列表 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] items;
	/** 评星 */
	private int star;
	/** 评星总数 */
	private int totalStar;

	public GCEliteStageBattleResult (){
	}
	
	public GCEliteStageBattleResult (
			int stageId,
			boolean battleResult,
			int coinNum,
			int experience,
			int techPoint,
			com.hifun.soul.gameserver.item.assist.CommonItem[] items,
			int star,
			int totalStar ){
			this.stageId = stageId;
			this.battleResult = battleResult;
			this.coinNum = coinNum;
			this.experience = experience;
			this.techPoint = techPoint;
			this.items = items;
			this.star = star;
			this.totalStar = totalStar;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		stageId = readInteger();
		battleResult = readBoolean();
		coinNum = readInteger();
		experience = readInteger();
		techPoint = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		items = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objitems = new com.hifun.soul.gameserver.item.assist.CommonItem();
			items[i] = objitems;
					objitems.setUUID(readString());
							objitems.setItemId(readInteger());
							objitems.setName(readString());
							objitems.setDesc(readString());
							objitems.setType(readInteger());
							objitems.setIcon(readInteger());
							objitems.setRarity(readInteger());
							objitems.setBind(readInteger());
							objitems.setOverlapNum(readInteger());
							objitems.setBagType(readInteger());
							objitems.setBagIndex(readInteger());
							objitems.setSellCurrencyType(readShort());
							objitems.setSellNum(readInteger());
							objitems.setPosition(readInteger());
							objitems.setEndure(readInteger());
							objitems.setEquipHole(readShort());
							objitems.setEmbedCurrencyType(readShort());
							objitems.setEmbedCurrencyNum(readInteger());
							objitems.setExtractCurrencyType(readShort());
							objitems.setExtractCurrencyNum(readInteger());
							objitems.setShopCurrencyType(readShort());
							objitems.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objitems.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objitems.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objitems.setGemAttributes(subListgemAttributes);
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
		objitems.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objitems.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objitems.setExtraSuccessRate(readFloat());
							objitems.setUpgradeLevel(readInteger());
							objitems.setLimitLevel(readInteger());
							objitems.setLimitOccupation(readInteger());
							objitems.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objitems.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objitems.setMaxOverlap(readInteger());
							objitems.setIsEquiped(readBoolean());
							objitems.setCanSell(readBoolean());
				}
		star = readInteger();
		totalStar = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageId);
		writeBoolean(battleResult);
		writeInteger(coinNum);
		writeInteger(experience);
		writeInteger(techPoint);
	writeShort(items.length);
	for(int i=0; i<items.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objitems = items[i];
				writeString(objitems.getUUID());
				writeInteger(objitems.getItemId());
				writeString(objitems.getName());
				writeString(objitems.getDesc());
				writeInteger(objitems.getType());
				writeInteger(objitems.getIcon());
				writeInteger(objitems.getRarity());
				writeInteger(objitems.getBind());
				writeInteger(objitems.getOverlapNum());
				writeInteger(objitems.getBagType());
				writeInteger(objitems.getBagIndex());
				writeShort(objitems.getSellCurrencyType());
				writeInteger(objitems.getSellNum());
				writeInteger(objitems.getPosition());
				writeInteger(objitems.getEndure());
				writeShort(objitems.getEquipHole());
				writeShort(objitems.getEmbedCurrencyType());
				writeInteger(objitems.getEmbedCurrencyNum());
				writeShort(objitems.getExtractCurrencyType());
				writeInteger(objitems.getExtractCurrencyNum());
				writeShort(objitems.getShopCurrencyType());
				writeInteger(objitems.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objitems=objitems.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objitems.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objitems.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objitems_jequipBaseAttributes = equipBaseAttributes_objitems[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objitems_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objitems_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objitems=objitems.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objitems.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objitems.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objitems_jequipSpecialAttributes = equipSpecialAttributes_objitems[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objitems_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objitems_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objitems=objitems.getGemAttributes();
	writeShort(gemAttributes_objitems.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objitems.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objitems_jgemAttributes = gemAttributes_objitems[jgemAttributes];
													writeInteger(gemAttributes_objitems_jgemAttributes.getKey());
													writeInteger(gemAttributes_objitems_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objitems=objitems.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objitems.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objitems.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objitems_jequipGemItemInfos = equipGemItemInfos_objitems[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objitems_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objitems_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objitems_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos=equipGemItemInfos_objitems_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objitems_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objitems=objitems.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objitems.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objitems.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objitems_jequipUpgradeAttributes = equipUpgradeAttributes_objitems[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objitems_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objitems_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objitems.getExtraSuccessRate());
				writeInteger(objitems.getUpgradeLevel());
				writeInteger(objitems.getLimitLevel());
				writeInteger(objitems.getLimitOccupation());
				writeInteger(objitems.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objitems=objitems.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objitems.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objitems.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objitems_jmaterialsOfEquipPaper = materialsOfEquipPaper_objitems[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objitems_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objitems_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objitems.getMaxOverlap());
				writeBoolean(objitems.getIsEquiped());
				writeBoolean(objitems.getCanSell());
	}
		writeInteger(star);
		writeInteger(totalStar);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ELITE_STAGE_BATTLE_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ELITE_STAGE_BATTLE_RESULT";
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public boolean getBattleResult(){
		return battleResult;
	}
		
	public void setBattleResult(boolean battleResult){
		this.battleResult = battleResult;
	}

	public int getCoinNum(){
		return coinNum;
	}
		
	public void setCoinNum(int coinNum){
		this.coinNum = coinNum;
	}

	public int getExperience(){
		return experience;
	}
		
	public void setExperience(int experience){
		this.experience = experience;
	}

	public int getTechPoint(){
		return techPoint;
	}
		
	public void setTechPoint(int techPoint){
		this.techPoint = techPoint;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getItems(){
		return items;
	}

	public void setItems(com.hifun.soul.gameserver.item.assist.CommonItem[] items){
		this.items = items;
	}	

	public int getStar(){
		return star;
	}
		
	public void setStar(int star){
		this.star = star;
	}

	public int getTotalStar(){
		return totalStar;
	}
		
	public void setTotalStar(int totalStar){
		this.totalStar = totalStar;
	}
}