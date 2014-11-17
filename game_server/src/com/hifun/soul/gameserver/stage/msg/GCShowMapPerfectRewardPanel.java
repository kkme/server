package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开地图完美通关奖励的板子
 *
 * @author SevenSoul
 */
@Component
public class GCShowMapPerfectRewardPanel extends GCMessage{
	
	/** 地图id */
	private int mapId;
	/** 地图完美通关奖励是否可领取 */
	private boolean canGet;
	/** 地图完美通关奖励 */
	private com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] mapPerfectRewardItemInfos;
	/** 没有达到满分的关卡集合 */
	private com.hifun.soul.gameserver.stage.model.StageUnPerfect[] unPerfectStages;

	public GCShowMapPerfectRewardPanel (){
	}
	
	public GCShowMapPerfectRewardPanel (
			int mapId,
			boolean canGet,
			com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] mapPerfectRewardItemInfos,
			com.hifun.soul.gameserver.stage.model.StageUnPerfect[] unPerfectStages ){
			this.mapId = mapId;
			this.canGet = canGet;
			this.mapPerfectRewardItemInfos = mapPerfectRewardItemInfos;
			this.unPerfectStages = unPerfectStages;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		mapId = readInteger();
		canGet = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		mapPerfectRewardItemInfos = new com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo objmapPerfectRewardItemInfos = new com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo();
			mapPerfectRewardItemInfos[i] = objmapPerfectRewardItemInfos;
					objmapPerfectRewardItemInfos.setItemId(readInteger());
							objmapPerfectRewardItemInfos.setItemNum(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objmapPerfectRewardItemInfos.setCommonItem(objcommonItem);
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
		count = readShort();
		count = count < 0 ? 0 : count;
		unPerfectStages = new com.hifun.soul.gameserver.stage.model.StageUnPerfect[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageUnPerfect objunPerfectStages = new com.hifun.soul.gameserver.stage.model.StageUnPerfect();
			unPerfectStages[i] = objunPerfectStages;
					objunPerfectStages.setStageId(readInteger());
							objunPerfectStages.setStar(readInteger());
							objunPerfectStages.setStageName(readString());
							objunPerfectStages.setPass(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mapId);
		writeBoolean(canGet);
	writeShort(mapPerfectRewardItemInfos.length);
	for(int i=0; i<mapPerfectRewardItemInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo objmapPerfectRewardItemInfos = mapPerfectRewardItemInfos[i];
				writeInteger(objmapPerfectRewardItemInfos.getItemId());
				writeInteger(objmapPerfectRewardItemInfos.getItemNum());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objmapPerfectRewardItemInfos = objmapPerfectRewardItemInfos.getCommonItem();
					writeString(commonItem_objmapPerfectRewardItemInfos.getUUID());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getItemId());
					writeString(commonItem_objmapPerfectRewardItemInfos.getName());
					writeString(commonItem_objmapPerfectRewardItemInfos.getDesc());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getIcon());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getRarity());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getBind());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getOverlapNum());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getBagType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getBagIndex());
					writeShort(commonItem_objmapPerfectRewardItemInfos.getSellCurrencyType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getSellNum());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getPosition());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getEndure());
					writeShort(commonItem_objmapPerfectRewardItemInfos.getEquipHole());
					writeShort(commonItem_objmapPerfectRewardItemInfos.getEmbedCurrencyType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getEmbedCurrencyNum());
					writeShort(commonItem_objmapPerfectRewardItemInfos.getExtractCurrencyType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getExtractCurrencyNum());
					writeShort(commonItem_objmapPerfectRewardItemInfos.getShopCurrencyType());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos_jequipBaseAttributes = equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objmapPerfectRewardItemInfos_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objmapPerfectRewardItemInfos_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getGemAttributes();
	writeShort(gemAttributes_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objmapPerfectRewardItemInfos.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objmapPerfectRewardItemInfos_jgemAttributes = gemAttributes_commonItem_objmapPerfectRewardItemInfos[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objmapPerfectRewardItemInfos_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objmapPerfectRewardItemInfos_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos = equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos=equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objmapPerfectRewardItemInfos_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objmapPerfectRewardItemInfos_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objmapPerfectRewardItemInfos.getExtraSuccessRate());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getUpgradeLevel());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getLimitLevel());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getLimitOccupation());
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos=commonItem_objmapPerfectRewardItemInfos.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objmapPerfectRewardItemInfos_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objmapPerfectRewardItemInfos.getMaxOverlap());
					writeBoolean(commonItem_objmapPerfectRewardItemInfos.getIsEquiped());
					writeBoolean(commonItem_objmapPerfectRewardItemInfos.getCanSell());
			}
	writeShort(unPerfectStages.length);
	for(int i=0; i<unPerfectStages.length; i++){
	com.hifun.soul.gameserver.stage.model.StageUnPerfect objunPerfectStages = unPerfectStages[i];
				writeInteger(objunPerfectStages.getStageId());
				writeInteger(objunPerfectStages.getStar());
				writeString(objunPerfectStages.getStageName());
				writeBoolean(objunPerfectStages.getPass());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MAP_PERFECT_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MAP_PERFECT_REWARD_PANEL";
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	public boolean getCanGet(){
		return canGet;
	}
		
	public void setCanGet(boolean canGet){
		this.canGet = canGet;
	}

	public com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] getMapPerfectRewardItemInfos(){
		return mapPerfectRewardItemInfos;
	}

	public void setMapPerfectRewardItemInfos(com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo[] mapPerfectRewardItemInfos){
		this.mapPerfectRewardItemInfos = mapPerfectRewardItemInfos;
	}	

	public com.hifun.soul.gameserver.stage.model.StageUnPerfect[] getUnPerfectStages(){
		return unPerfectStages;
	}

	public void setUnPerfectStages(com.hifun.soul.gameserver.stage.model.StageUnPerfect[] unPerfectStages){
		this.unPerfectStages = unPerfectStages;
	}	
}