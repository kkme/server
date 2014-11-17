package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 角色信息展示
 *
 * @author SevenSoul
 */
@Component
public class GCCharacterShowInfo extends GCMessage{
	
	/** 角色id */
	private long humanId;
	/** 角色名称 */
	private String name;
	/** 穿上的装备 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] equipments;
	/** 角色属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties;
	/** 装备位置上星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipedHoroscopeInfos;

	public GCCharacterShowInfo (){
	}
	
	public GCCharacterShowInfo (
			long humanId,
			String name,
			com.hifun.soul.gameserver.item.assist.CommonItem[] equipments,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipedHoroscopeInfos ){
			this.humanId = humanId;
			this.name = name;
			this.equipments = equipments;
			this.properties = properties;
			this.equipedHoroscopeInfos = equipedHoroscopeInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		humanId = readLong();
		name = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		equipments = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objequipments = new com.hifun.soul.gameserver.item.assist.CommonItem();
			equipments[i] = objequipments;
					objequipments.setUUID(readString());
							objequipments.setItemId(readInteger());
							objequipments.setName(readString());
							objequipments.setDesc(readString());
							objequipments.setType(readInteger());
							objequipments.setIcon(readInteger());
							objequipments.setRarity(readInteger());
							objequipments.setBind(readInteger());
							objequipments.setOverlapNum(readInteger());
							objequipments.setBagType(readInteger());
							objequipments.setBagIndex(readInteger());
							objequipments.setSellCurrencyType(readShort());
							objequipments.setSellNum(readInteger());
							objequipments.setPosition(readInteger());
							objequipments.setEndure(readInteger());
							objequipments.setEquipHole(readShort());
							objequipments.setEmbedCurrencyType(readShort());
							objequipments.setEmbedCurrencyNum(readInteger());
							objequipments.setExtractCurrencyType(readShort());
							objequipments.setExtractCurrencyNum(readInteger());
							objequipments.setShopCurrencyType(readShort());
							objequipments.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objequipments.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objequipments.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objequipments.setGemAttributes(subListgemAttributes);
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
		objequipments.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objequipments.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objequipments.setExtraSuccessRate(readFloat());
							objequipments.setUpgradeLevel(readInteger());
							objequipments.setLimitLevel(readInteger());
							objequipments.setLimitOccupation(readInteger());
							objequipments.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objequipments.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objequipments.setMaxOverlap(readInteger());
							objequipments.setIsEquiped(readBoolean());
							objequipments.setCanSell(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		properties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objproperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			properties[i] = objproperties;
					objproperties.setKey(readInteger());
							objproperties.setValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		equipedHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objequipedHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
			equipedHoroscopeInfos[i] = objequipedHoroscopeInfos;
					objequipedHoroscopeInfos.setHoroscopeBag(readInteger());
							objequipedHoroscopeInfos.setIndex(readInteger());
							objequipedHoroscopeInfos.setHoroscopeId(readInteger());
							objequipedHoroscopeInfos.setName(readString());
							objequipedHoroscopeInfos.setDesc(readString());
							objequipedHoroscopeInfos.setColor(readInteger());
							objequipedHoroscopeInfos.setLevel(readInteger());
							objequipedHoroscopeInfos.setExperience(readInteger());
							objequipedHoroscopeInfos.setMaxExperience(readInteger());
							objequipedHoroscopeInfos.setKey(readInteger());
							objequipedHoroscopeInfos.setValue(readInteger());
							objequipedHoroscopeInfos.setIcon(readInteger());
							objequipedHoroscopeInfos.setNextHoroscopeId(readInteger());
							objequipedHoroscopeInfos.setPropertyAddType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanId);
		writeString(name);
	writeShort(equipments.length);
	for(int i=0; i<equipments.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objequipments = equipments[i];
				writeString(objequipments.getUUID());
				writeInteger(objequipments.getItemId());
				writeString(objequipments.getName());
				writeString(objequipments.getDesc());
				writeInteger(objequipments.getType());
				writeInteger(objequipments.getIcon());
				writeInteger(objequipments.getRarity());
				writeInteger(objequipments.getBind());
				writeInteger(objequipments.getOverlapNum());
				writeInteger(objequipments.getBagType());
				writeInteger(objequipments.getBagIndex());
				writeShort(objequipments.getSellCurrencyType());
				writeInteger(objequipments.getSellNum());
				writeInteger(objequipments.getPosition());
				writeInteger(objequipments.getEndure());
				writeShort(objequipments.getEquipHole());
				writeShort(objequipments.getEmbedCurrencyType());
				writeInteger(objequipments.getEmbedCurrencyNum());
				writeShort(objequipments.getExtractCurrencyType());
				writeInteger(objequipments.getExtractCurrencyNum());
				writeShort(objequipments.getShopCurrencyType());
				writeInteger(objequipments.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objequipments=objequipments.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objequipments.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objequipments.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objequipments_jequipBaseAttributes = equipBaseAttributes_objequipments[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objequipments_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objequipments_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objequipments=objequipments.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objequipments.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objequipments.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objequipments_jequipSpecialAttributes = equipSpecialAttributes_objequipments[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objequipments_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objequipments_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objequipments=objequipments.getGemAttributes();
	writeShort(gemAttributes_objequipments.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objequipments.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objequipments_jgemAttributes = gemAttributes_objequipments[jgemAttributes];
													writeInteger(gemAttributes_objequipments_jgemAttributes.getKey());
													writeInteger(gemAttributes_objequipments_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objequipments=objequipments.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objequipments.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objequipments.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objequipments_jequipGemItemInfos = equipGemItemInfos_objequipments[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objequipments_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objequipments_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objequipments_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos=equipGemItemInfos_objequipments_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objequipments_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objequipments=objequipments.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objequipments.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objequipments.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objequipments_jequipUpgradeAttributes = equipUpgradeAttributes_objequipments[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objequipments_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objequipments_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objequipments.getExtraSuccessRate());
				writeInteger(objequipments.getUpgradeLevel());
				writeInteger(objequipments.getLimitLevel());
				writeInteger(objequipments.getLimitOccupation());
				writeInteger(objequipments.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objequipments=objequipments.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objequipments.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objequipments.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objequipments_jmaterialsOfEquipPaper = materialsOfEquipPaper_objequipments[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objequipments_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objequipments_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objequipments.getMaxOverlap());
				writeBoolean(objequipments.getIsEquiped());
				writeBoolean(objequipments.getCanSell());
	}
	writeShort(properties.length);
	for(int i=0; i<properties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objproperties = properties[i];
				writeInteger(objproperties.getKey());
				writeInteger(objproperties.getValue());
	}
	writeShort(equipedHoroscopeInfos.length);
	for(int i=0; i<equipedHoroscopeInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objequipedHoroscopeInfos = equipedHoroscopeInfos[i];
				writeInteger(objequipedHoroscopeInfos.getHoroscopeBag());
				writeInteger(objequipedHoroscopeInfos.getIndex());
				writeInteger(objequipedHoroscopeInfos.getHoroscopeId());
				writeString(objequipedHoroscopeInfos.getName());
				writeString(objequipedHoroscopeInfos.getDesc());
				writeInteger(objequipedHoroscopeInfos.getColor());
				writeInteger(objequipedHoroscopeInfos.getLevel());
				writeInteger(objequipedHoroscopeInfos.getExperience());
				writeInteger(objequipedHoroscopeInfos.getMaxExperience());
				writeInteger(objequipedHoroscopeInfos.getKey());
				writeInteger(objequipedHoroscopeInfos.getValue());
				writeInteger(objequipedHoroscopeInfos.getIcon());
				writeInteger(objequipedHoroscopeInfos.getNextHoroscopeId());
				writeInteger(objequipedHoroscopeInfos.getPropertyAddType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARACTER_SHOW_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARACTER_SHOW_INFO";
	}

	public long getHumanId(){
		return humanId;
	}
		
	public void setHumanId(long humanId){
		this.humanId = humanId;
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getEquipments(){
		return equipments;
	}

	public void setEquipments(com.hifun.soul.gameserver.item.assist.CommonItem[] equipments){
		this.equipments = equipments;
	}	

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getProperties(){
		return properties;
	}

	public void setProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties){
		this.properties = properties;
	}	

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getEquipedHoroscopeInfos(){
		return equipedHoroscopeInfos;
	}

	public void setEquipedHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipedHoroscopeInfos){
		this.equipedHoroscopeInfos = equipedHoroscopeInfos;
	}	
}