package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取关卡评星奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetStageStarReward extends GCMessage{
	
	/** 是否还有评星奖励 */
	private boolean hasNextReward;
	/** 可领取的下一级评星奖励星星数 */
	private int star;
	/** 是否可以领取下一级评星奖励 */
	private boolean canGet;
	/** 关卡评星奖励 */
	private com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] stageStarRewardItemInfos;

	public GCGetStageStarReward (){
	}
	
	public GCGetStageStarReward (
			boolean hasNextReward,
			int star,
			boolean canGet,
			com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] stageStarRewardItemInfos ){
			this.hasNextReward = hasNextReward;
			this.star = star;
			this.canGet = canGet;
			this.stageStarRewardItemInfos = stageStarRewardItemInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		hasNextReward = readBoolean();
		star = readInteger();
		canGet = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		stageStarRewardItemInfos = new com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo objstageStarRewardItemInfos = new com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo();
			stageStarRewardItemInfos[i] = objstageStarRewardItemInfos;
					objstageStarRewardItemInfos.setItemId(readInteger());
							objstageStarRewardItemInfos.setItemNum(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objstageStarRewardItemInfos.setCommonItem(objcommonItem);
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
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(hasNextReward);
		writeInteger(star);
		writeBoolean(canGet);
	writeShort(stageStarRewardItemInfos.length);
	for(int i=0; i<stageStarRewardItemInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo objstageStarRewardItemInfos = stageStarRewardItemInfos[i];
				writeInteger(objstageStarRewardItemInfos.getItemId());
				writeInteger(objstageStarRewardItemInfos.getItemNum());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objstageStarRewardItemInfos = objstageStarRewardItemInfos.getCommonItem();
					writeString(commonItem_objstageStarRewardItemInfos.getUUID());
					writeInteger(commonItem_objstageStarRewardItemInfos.getItemId());
					writeString(commonItem_objstageStarRewardItemInfos.getName());
					writeString(commonItem_objstageStarRewardItemInfos.getDesc());
					writeInteger(commonItem_objstageStarRewardItemInfos.getType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getIcon());
					writeInteger(commonItem_objstageStarRewardItemInfos.getRarity());
					writeInteger(commonItem_objstageStarRewardItemInfos.getBind());
					writeInteger(commonItem_objstageStarRewardItemInfos.getOverlapNum());
					writeInteger(commonItem_objstageStarRewardItemInfos.getBagType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getBagIndex());
					writeShort(commonItem_objstageStarRewardItemInfos.getSellCurrencyType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getSellNum());
					writeInteger(commonItem_objstageStarRewardItemInfos.getPosition());
					writeInteger(commonItem_objstageStarRewardItemInfos.getEndure());
					writeShort(commonItem_objstageStarRewardItemInfos.getEquipHole());
					writeShort(commonItem_objstageStarRewardItemInfos.getEmbedCurrencyType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getEmbedCurrencyNum());
					writeShort(commonItem_objstageStarRewardItemInfos.getExtractCurrencyType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getExtractCurrencyNum());
					writeShort(commonItem_objstageStarRewardItemInfos.getShopCurrencyType());
					writeInteger(commonItem_objstageStarRewardItemInfos.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objstageStarRewardItemInfos.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objstageStarRewardItemInfos.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objstageStarRewardItemInfos_jequipBaseAttributes = equipBaseAttributes_commonItem_objstageStarRewardItemInfos[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objstageStarRewardItemInfos_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objstageStarRewardItemInfos_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objstageStarRewardItemInfos.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objstageStarRewardItemInfos.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objstageStarRewardItemInfos_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objstageStarRewardItemInfos[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objstageStarRewardItemInfos_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objstageStarRewardItemInfos_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getGemAttributes();
	writeShort(gemAttributes_commonItem_objstageStarRewardItemInfos.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objstageStarRewardItemInfos.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objstageStarRewardItemInfos_jgemAttributes = gemAttributes_commonItem_objstageStarRewardItemInfos[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objstageStarRewardItemInfos_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objstageStarRewardItemInfos_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objstageStarRewardItemInfos.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objstageStarRewardItemInfos.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos = equipGemItemInfos_commonItem_objstageStarRewardItemInfos[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos=equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objstageStarRewardItemInfos_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objstageStarRewardItemInfos_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objstageStarRewardItemInfos.getExtraSuccessRate());
					writeInteger(commonItem_objstageStarRewardItemInfos.getUpgradeLevel());
					writeInteger(commonItem_objstageStarRewardItemInfos.getLimitLevel());
					writeInteger(commonItem_objstageStarRewardItemInfos.getLimitOccupation());
					writeInteger(commonItem_objstageStarRewardItemInfos.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos=commonItem_objstageStarRewardItemInfos.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objstageStarRewardItemInfos_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objstageStarRewardItemInfos.getMaxOverlap());
					writeBoolean(commonItem_objstageStarRewardItemInfos.getIsEquiped());
					writeBoolean(commonItem_objstageStarRewardItemInfos.getCanSell());
			}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_STAGE_STAR_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_STAGE_STAR_REWARD";
	}

	public boolean getHasNextReward(){
		return hasNextReward;
	}
		
	public void setHasNextReward(boolean hasNextReward){
		this.hasNextReward = hasNextReward;
	}

	public int getStar(){
		return star;
	}
		
	public void setStar(int star){
		this.star = star;
	}

	public boolean getCanGet(){
		return canGet;
	}
		
	public void setCanGet(boolean canGet){
		this.canGet = canGet;
	}

	public com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] getStageStarRewardItemInfos(){
		return stageStarRewardItemInfos;
	}

	public void setStageStarRewardItemInfos(com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] stageStarRewardItemInfos){
		this.stageStarRewardItemInfos = stageStarRewardItemInfos;
	}	
}