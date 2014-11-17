package com.hifun.soul.gameserver.refine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 攻打关卡奖励信息
 *
 * @author SevenSoul
 */
@Component
public class GCAttackRefineStage extends GCMessage{
	
	/** 试炼副本id */
	private int refineType;
	/** 攻打关卡结果 */
	private boolean win;
	/** 攻打关卡id */
	private int stageId;
	/** 怪物名称 */
	private String monsterName;
	/** 试炼奖励 */
	private com.hifun.soul.gameserver.item.assist.CommonItem reward;
	/** 奖励数量 */
	private int rewardNum;

	public GCAttackRefineStage (){
	}
	
	public GCAttackRefineStage (
			int refineType,
			boolean win,
			int stageId,
			String monsterName,
			com.hifun.soul.gameserver.item.assist.CommonItem reward,
			int rewardNum ){
			this.refineType = refineType;
			this.win = win;
			this.stageId = stageId;
			this.monsterName = monsterName;
			this.reward = reward;
			this.rewardNum = rewardNum;
	}

	@Override
	protected boolean readImpl() {
		refineType = readInteger();
		win = readBoolean();
		stageId = readInteger();
		monsterName = readString();
		reward = new com.hifun.soul.gameserver.item.assist.CommonItem();
						reward.setUUID(readString());
						reward.setItemId(readInteger());
						reward.setName(readString());
						reward.setDesc(readString());
						reward.setType(readInteger());
						reward.setIcon(readInteger());
						reward.setRarity(readInteger());
						reward.setBind(readInteger());
						reward.setOverlapNum(readInteger());
						reward.setBagType(readInteger());
						reward.setBagIndex(readInteger());
						reward.setSellCurrencyType(readShort());
						reward.setSellNum(readInteger());
						reward.setPosition(readInteger());
						reward.setEndure(readInteger());
						reward.setEquipHole(readShort());
						reward.setEmbedCurrencyType(readShort());
						reward.setEmbedCurrencyNum(readInteger());
						reward.setExtractCurrencyType(readShort());
						reward.setExtractCurrencyNum(readInteger());
						reward.setShopCurrencyType(readShort());
						reward.setShopCurrencyNum(readInteger());
							{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		reward.setEquipBaseAttributes(subListequipBaseAttributes);
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
		reward.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		reward.setGemAttributes(subListgemAttributes);
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
		reward.setEquipGemItemInfos(subListequipGemItemInfos);
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
		reward.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
						reward.setExtraSuccessRate(readFloat());
						reward.setUpgradeLevel(readInteger());
						reward.setLimitLevel(readInteger());
						reward.setLimitOccupation(readInteger());
						reward.setMaxEquipHole(readInteger());
							{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		reward.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
						reward.setMaxOverlap(readInteger());
						reward.setIsEquiped(readBoolean());
						reward.setCanSell(readBoolean());
				rewardNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(refineType);
		writeBoolean(win);
		writeInteger(stageId);
		writeString(monsterName);
		writeString(reward.getUUID());
		writeInteger(reward.getItemId());
		writeString(reward.getName());
		writeString(reward.getDesc());
		writeInteger(reward.getType());
		writeInteger(reward.getIcon());
		writeInteger(reward.getRarity());
		writeInteger(reward.getBind());
		writeInteger(reward.getOverlapNum());
		writeInteger(reward.getBagType());
		writeInteger(reward.getBagIndex());
		writeShort(reward.getSellCurrencyType());
		writeInteger(reward.getSellNum());
		writeInteger(reward.getPosition());
		writeInteger(reward.getEndure());
		writeShort(reward.getEquipHole());
		writeShort(reward.getEmbedCurrencyType());
		writeInteger(reward.getEmbedCurrencyNum());
		writeShort(reward.getExtractCurrencyType());
		writeInteger(reward.getExtractCurrencyNum());
		writeShort(reward.getShopCurrencyType());
		writeInteger(reward.getShopCurrencyNum());
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_reward=reward.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_reward.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_reward.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_reward_jequipBaseAttributes = equipBaseAttributes_reward[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_reward_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_reward_jequipBaseAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_reward=reward.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_reward.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_reward.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_reward_jequipSpecialAttributes = equipSpecialAttributes_reward[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_reward_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_reward_jequipSpecialAttributes.getValue());
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_reward=reward.getGemAttributes();
	writeShort(gemAttributes_reward.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_reward.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_reward_jgemAttributes = gemAttributes_reward[jgemAttributes];
													writeInteger(gemAttributes_reward_jgemAttributes.getKey());
													writeInteger(gemAttributes_reward_jgemAttributes.getValue());
									}
			com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_reward=reward.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_reward.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_reward.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_reward_jequipGemItemInfos = equipGemItemInfos_reward[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_reward_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_reward_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_reward_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos=equipGemItemInfos_reward_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_reward_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_reward=reward.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_reward.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_reward.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_reward_jequipUpgradeAttributes = equipUpgradeAttributes_reward[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_reward_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_reward_jequipUpgradeAttributes.getValue());
									}
		writeFloat(reward.getExtraSuccessRate());
		writeInteger(reward.getUpgradeLevel());
		writeInteger(reward.getLimitLevel());
		writeInteger(reward.getLimitOccupation());
		writeInteger(reward.getMaxEquipHole());
			com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_reward=reward.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_reward.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_reward.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_reward_jmaterialsOfEquipPaper = materialsOfEquipPaper_reward[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_reward_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_reward_jmaterialsOfEquipPaper.getValue());
									}
		writeInteger(reward.getMaxOverlap());
		writeBoolean(reward.getIsEquiped());
		writeBoolean(reward.getCanSell());
		writeInteger(rewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ATTACK_REFINE_STAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ATTACK_REFINE_STAGE";
	}

	public int getRefineType(){
		return refineType;
	}
		
	public void setRefineType(int refineType){
		this.refineType = refineType;
	}

	public boolean getWin(){
		return win;
	}
		
	public void setWin(boolean win){
		this.win = win;
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public String getMonsterName(){
		return monsterName;
	}
		
	public void setMonsterName(String monsterName){
		this.monsterName = monsterName;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem getReward(){
		return reward;
	}
		
	public void setReward(com.hifun.soul.gameserver.item.assist.CommonItem reward){
		this.reward = reward;
	}

	public int getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(int rewardNum){
		this.rewardNum = rewardNum;
	}
}