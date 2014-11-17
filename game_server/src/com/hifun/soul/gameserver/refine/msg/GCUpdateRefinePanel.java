package com.hifun.soul.gameserver.refine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新某个试炼副本
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateRefinePanel extends GCMessage{
	
	/** 免费次数 */
	private int freeTimes;
	/** 攻打关卡的最高纪录 */
	private int bestStageId;
	/** 自动攻打单轮消耗魔晶数量 */
	private int autoBattleCrystal;
	/** 下次刷新副本消耗魔晶数量 */
	private int refreshCrystal;
	/** 一键扫荡消耗的数量 */
	private int attackAllCrystal;
	/** 魔晶刷新次数 */
	private int crystalTimes;
	/** 当前试炼关卡信息 */
	private com.hifun.soul.gameserver.refine.RefineStageInfo refineStageInfo;
	/** 试炼地图的信息 */
	private com.hifun.soul.gameserver.refine.RefineMapInfo[] refineMapInfos;

	public GCUpdateRefinePanel (){
	}
	
	public GCUpdateRefinePanel (
			int freeTimes,
			int bestStageId,
			int autoBattleCrystal,
			int refreshCrystal,
			int attackAllCrystal,
			int crystalTimes,
			com.hifun.soul.gameserver.refine.RefineStageInfo refineStageInfo,
			com.hifun.soul.gameserver.refine.RefineMapInfo[] refineMapInfos ){
			this.freeTimes = freeTimes;
			this.bestStageId = bestStageId;
			this.autoBattleCrystal = autoBattleCrystal;
			this.refreshCrystal = refreshCrystal;
			this.attackAllCrystal = attackAllCrystal;
			this.crystalTimes = crystalTimes;
			this.refineStageInfo = refineStageInfo;
			this.refineMapInfos = refineMapInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		freeTimes = readInteger();
		bestStageId = readInteger();
		autoBattleCrystal = readInteger();
		refreshCrystal = readInteger();
		attackAllCrystal = readInteger();
		crystalTimes = readInteger();
		refineStageInfo = new com.hifun.soul.gameserver.refine.RefineStageInfo();
						refineStageInfo.setMapId(readInteger());
						refineStageInfo.setMapName(readString());
						refineStageInfo.setStageId(readInteger());
						refineStageInfo.setMonsterId(readInteger());
						refineStageInfo.setMonsterName(readString());
						refineStageInfo.setMonsterLevel(readInteger());
						refineStageInfo.setMonsterIcon(readInteger());
							{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	refineStageInfo.setCommonItem(objcommonItem);
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
						refineStageInfo.setRewardNum(readInteger());
						refineStageInfo.setAttackState(readInteger());
						refineStageInfo.setGetted(readBoolean());
						refineStageInfo.setRewardAnima(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		refineMapInfos = new com.hifun.soul.gameserver.refine.RefineMapInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.refine.RefineMapInfo objrefineMapInfos = new com.hifun.soul.gameserver.refine.RefineMapInfo();
			refineMapInfos[i] = objrefineMapInfos;
					objrefineMapInfos.setMapId(readInteger());
							objrefineMapInfos.setMapName(readString());
							objrefineMapInfos.setMapOpenLevel(readInteger());
							objrefineMapInfos.setMapStageNum(readInteger());
							objrefineMapInfos.setMapActivated(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(freeTimes);
		writeInteger(bestStageId);
		writeInteger(autoBattleCrystal);
		writeInteger(refreshCrystal);
		writeInteger(attackAllCrystal);
		writeInteger(crystalTimes);
		writeInteger(refineStageInfo.getMapId());
		writeString(refineStageInfo.getMapName());
		writeInteger(refineStageInfo.getStageId());
		writeInteger(refineStageInfo.getMonsterId());
		writeString(refineStageInfo.getMonsterName());
		writeInteger(refineStageInfo.getMonsterLevel());
		writeInteger(refineStageInfo.getMonsterIcon());
			com.hifun.soul.gameserver.item.assist.CommonItem commonItem_refineStageInfo = refineStageInfo.getCommonItem();
					writeString(commonItem_refineStageInfo.getUUID());
					writeInteger(commonItem_refineStageInfo.getItemId());
					writeString(commonItem_refineStageInfo.getName());
					writeString(commonItem_refineStageInfo.getDesc());
					writeInteger(commonItem_refineStageInfo.getType());
					writeInteger(commonItem_refineStageInfo.getIcon());
					writeInteger(commonItem_refineStageInfo.getRarity());
					writeInteger(commonItem_refineStageInfo.getBind());
					writeInteger(commonItem_refineStageInfo.getOverlapNum());
					writeInteger(commonItem_refineStageInfo.getBagType());
					writeInteger(commonItem_refineStageInfo.getBagIndex());
					writeShort(commonItem_refineStageInfo.getSellCurrencyType());
					writeInteger(commonItem_refineStageInfo.getSellNum());
					writeInteger(commonItem_refineStageInfo.getPosition());
					writeInteger(commonItem_refineStageInfo.getEndure());
					writeShort(commonItem_refineStageInfo.getEquipHole());
					writeShort(commonItem_refineStageInfo.getEmbedCurrencyType());
					writeInteger(commonItem_refineStageInfo.getEmbedCurrencyNum());
					writeShort(commonItem_refineStageInfo.getExtractCurrencyType());
					writeInteger(commonItem_refineStageInfo.getExtractCurrencyNum());
					writeShort(commonItem_refineStageInfo.getShopCurrencyType());
					writeInteger(commonItem_refineStageInfo.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_refineStageInfo=commonItem_refineStageInfo.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_refineStageInfo.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_refineStageInfo.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_refineStageInfo_jequipBaseAttributes = equipBaseAttributes_commonItem_refineStageInfo[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_refineStageInfo_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_refineStageInfo_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_refineStageInfo=commonItem_refineStageInfo.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_refineStageInfo.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_refineStageInfo.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_refineStageInfo_jequipSpecialAttributes = equipSpecialAttributes_commonItem_refineStageInfo[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_refineStageInfo_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_refineStageInfo_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_refineStageInfo=commonItem_refineStageInfo.getGemAttributes();
	writeShort(gemAttributes_commonItem_refineStageInfo.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_refineStageInfo.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_refineStageInfo_jgemAttributes = gemAttributes_commonItem_refineStageInfo[jgemAttributes];
													writeInteger(gemAttributes_commonItem_refineStageInfo_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_refineStageInfo_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_refineStageInfo=commonItem_refineStageInfo.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_refineStageInfo.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_refineStageInfo.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos = equipGemItemInfos_commonItem_refineStageInfo[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos=equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_refineStageInfo_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_refineStageInfo=commonItem_refineStageInfo.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_refineStageInfo.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_refineStageInfo.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_refineStageInfo_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_refineStageInfo[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_refineStageInfo_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_refineStageInfo_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_refineStageInfo.getExtraSuccessRate());
					writeInteger(commonItem_refineStageInfo.getUpgradeLevel());
					writeInteger(commonItem_refineStageInfo.getLimitLevel());
					writeInteger(commonItem_refineStageInfo.getLimitOccupation());
					writeInteger(commonItem_refineStageInfo.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_refineStageInfo=commonItem_refineStageInfo.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_refineStageInfo.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_refineStageInfo.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_refineStageInfo_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_refineStageInfo[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_refineStageInfo_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_refineStageInfo_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_refineStageInfo.getMaxOverlap());
					writeBoolean(commonItem_refineStageInfo.getIsEquiped());
					writeBoolean(commonItem_refineStageInfo.getCanSell());
				writeInteger(refineStageInfo.getRewardNum());
		writeInteger(refineStageInfo.getAttackState());
		writeBoolean(refineStageInfo.getGetted());
		writeInteger(refineStageInfo.getRewardAnima());
	writeShort(refineMapInfos.length);
	for(int i=0; i<refineMapInfos.length; i++){
	com.hifun.soul.gameserver.refine.RefineMapInfo objrefineMapInfos = refineMapInfos[i];
				writeInteger(objrefineMapInfos.getMapId());
				writeString(objrefineMapInfos.getMapName());
				writeInteger(objrefineMapInfos.getMapOpenLevel());
				writeInteger(objrefineMapInfos.getMapStageNum());
				writeBoolean(objrefineMapInfos.getMapActivated());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_REFINE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_REFINE_PANEL";
	}

	public int getFreeTimes(){
		return freeTimes;
	}
		
	public void setFreeTimes(int freeTimes){
		this.freeTimes = freeTimes;
	}

	public int getBestStageId(){
		return bestStageId;
	}
		
	public void setBestStageId(int bestStageId){
		this.bestStageId = bestStageId;
	}

	public int getAutoBattleCrystal(){
		return autoBattleCrystal;
	}
		
	public void setAutoBattleCrystal(int autoBattleCrystal){
		this.autoBattleCrystal = autoBattleCrystal;
	}

	public int getRefreshCrystal(){
		return refreshCrystal;
	}
		
	public void setRefreshCrystal(int refreshCrystal){
		this.refreshCrystal = refreshCrystal;
	}

	public int getAttackAllCrystal(){
		return attackAllCrystal;
	}
		
	public void setAttackAllCrystal(int attackAllCrystal){
		this.attackAllCrystal = attackAllCrystal;
	}

	public int getCrystalTimes(){
		return crystalTimes;
	}
		
	public void setCrystalTimes(int crystalTimes){
		this.crystalTimes = crystalTimes;
	}

	public com.hifun.soul.gameserver.refine.RefineStageInfo getRefineStageInfo(){
		return refineStageInfo;
	}
		
	public void setRefineStageInfo(com.hifun.soul.gameserver.refine.RefineStageInfo refineStageInfo){
		this.refineStageInfo = refineStageInfo;
	}

	public com.hifun.soul.gameserver.refine.RefineMapInfo[] getRefineMapInfos(){
		return refineMapInfos;
	}

	public void setRefineMapInfos(com.hifun.soul.gameserver.refine.RefineMapInfo[] refineMapInfos){
		this.refineMapInfos = refineMapInfos;
	}	
}