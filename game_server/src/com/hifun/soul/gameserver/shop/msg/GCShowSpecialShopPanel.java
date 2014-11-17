package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开神秘商店面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowSpecialShopPanel extends GCMessage{
	
	/** 神秘商店道具列表 */
	private com.hifun.soul.gameserver.shop.SpecialShopItem[] specialShopItemList;
	/** 奖品列表 */
	private com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList;
	/** 刷新神秘商店花费魔晶数 */
	private int crystal;
	/** 剩余神秘商店刷新次数 */
	private int refreshTime;
	/** 刷新时间点 */
	private String[] refreshHours;

	public GCShowSpecialShopPanel (){
	}
	
	public GCShowSpecialShopPanel (
			com.hifun.soul.gameserver.shop.SpecialShopItem[] specialShopItemList,
			com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList,
			int crystal,
			int refreshTime,
			String[] refreshHours ){
			this.specialShopItemList = specialShopItemList;
			this.specialShopNotifyList = specialShopNotifyList;
			this.crystal = crystal;
			this.refreshTime = refreshTime;
			this.refreshHours = refreshHours;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		specialShopItemList = new com.hifun.soul.gameserver.shop.SpecialShopItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.shop.SpecialShopItem objspecialShopItemList = new com.hifun.soul.gameserver.shop.SpecialShopItem();
			specialShopItemList[i] = objspecialShopItemList;
					objspecialShopItemList.setId(readInteger());
							objspecialShopItemList.setItemId(readInteger());
							objspecialShopItemList.setItemNum(readInteger());
							objspecialShopItemList.setCurrencyType(readInteger());
							objspecialShopItemList.setCurrencyNum(readInteger());
							objspecialShopItemList.setIsBuy(readBoolean());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objspecialShopItemList.setCommonItem(objcommonItem);
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
		specialShopNotifyList = new com.hifun.soul.gameserver.shop.SpecialShopNotify[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.shop.SpecialShopNotify objspecialShopNotifyList = new com.hifun.soul.gameserver.shop.SpecialShopNotify();
			specialShopNotifyList[i] = objspecialShopNotifyList;
					objspecialShopNotifyList.setId(readInteger());
							objspecialShopNotifyList.setRoleName(readString());
							objspecialShopNotifyList.setItemName(readString());
							objspecialShopNotifyList.setItemNum(readInteger());
				}
		crystal = readInteger();
		refreshTime = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		refreshHours = new String[count];
		for(int i=0; i<count; i++){
			refreshHours[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(specialShopItemList.length);
	for(int i=0; i<specialShopItemList.length; i++){
	com.hifun.soul.gameserver.shop.SpecialShopItem objspecialShopItemList = specialShopItemList[i];
				writeInteger(objspecialShopItemList.getId());
				writeInteger(objspecialShopItemList.getItemId());
				writeInteger(objspecialShopItemList.getItemNum());
				writeInteger(objspecialShopItemList.getCurrencyType());
				writeInteger(objspecialShopItemList.getCurrencyNum());
				writeBoolean(objspecialShopItemList.getIsBuy());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objspecialShopItemList = objspecialShopItemList.getCommonItem();
					writeString(commonItem_objspecialShopItemList.getUUID());
					writeInteger(commonItem_objspecialShopItemList.getItemId());
					writeString(commonItem_objspecialShopItemList.getName());
					writeString(commonItem_objspecialShopItemList.getDesc());
					writeInteger(commonItem_objspecialShopItemList.getType());
					writeInteger(commonItem_objspecialShopItemList.getIcon());
					writeInteger(commonItem_objspecialShopItemList.getRarity());
					writeInteger(commonItem_objspecialShopItemList.getBind());
					writeInteger(commonItem_objspecialShopItemList.getOverlapNum());
					writeInteger(commonItem_objspecialShopItemList.getBagType());
					writeInteger(commonItem_objspecialShopItemList.getBagIndex());
					writeShort(commonItem_objspecialShopItemList.getSellCurrencyType());
					writeInteger(commonItem_objspecialShopItemList.getSellNum());
					writeInteger(commonItem_objspecialShopItemList.getPosition());
					writeInteger(commonItem_objspecialShopItemList.getEndure());
					writeShort(commonItem_objspecialShopItemList.getEquipHole());
					writeShort(commonItem_objspecialShopItemList.getEmbedCurrencyType());
					writeInteger(commonItem_objspecialShopItemList.getEmbedCurrencyNum());
					writeShort(commonItem_objspecialShopItemList.getExtractCurrencyType());
					writeInteger(commonItem_objspecialShopItemList.getExtractCurrencyNum());
					writeShort(commonItem_objspecialShopItemList.getShopCurrencyType());
					writeInteger(commonItem_objspecialShopItemList.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objspecialShopItemList.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objspecialShopItemList.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objspecialShopItemList_jequipBaseAttributes = equipBaseAttributes_commonItem_objspecialShopItemList[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objspecialShopItemList_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objspecialShopItemList_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objspecialShopItemList.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objspecialShopItemList.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objspecialShopItemList_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objspecialShopItemList[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objspecialShopItemList_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objspecialShopItemList_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getGemAttributes();
	writeShort(gemAttributes_commonItem_objspecialShopItemList.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objspecialShopItemList.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objspecialShopItemList_jgemAttributes = gemAttributes_commonItem_objspecialShopItemList[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objspecialShopItemList_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objspecialShopItemList_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objspecialShopItemList.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objspecialShopItemList.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos = equipGemItemInfos_commonItem_objspecialShopItemList[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos=equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objspecialShopItemList_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objspecialShopItemList.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objspecialShopItemList.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objspecialShopItemList_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objspecialShopItemList[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objspecialShopItemList_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objspecialShopItemList_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objspecialShopItemList.getExtraSuccessRate());
					writeInteger(commonItem_objspecialShopItemList.getUpgradeLevel());
					writeInteger(commonItem_objspecialShopItemList.getLimitLevel());
					writeInteger(commonItem_objspecialShopItemList.getLimitOccupation());
					writeInteger(commonItem_objspecialShopItemList.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objspecialShopItemList=commonItem_objspecialShopItemList.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objspecialShopItemList.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objspecialShopItemList.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objspecialShopItemList_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objspecialShopItemList[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objspecialShopItemList_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objspecialShopItemList_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objspecialShopItemList.getMaxOverlap());
					writeBoolean(commonItem_objspecialShopItemList.getIsEquiped());
					writeBoolean(commonItem_objspecialShopItemList.getCanSell());
			}
	writeShort(specialShopNotifyList.length);
	for(int i=0; i<specialShopNotifyList.length; i++){
	com.hifun.soul.gameserver.shop.SpecialShopNotify objspecialShopNotifyList = specialShopNotifyList[i];
				writeInteger(objspecialShopNotifyList.getId());
				writeString(objspecialShopNotifyList.getRoleName());
				writeString(objspecialShopNotifyList.getItemName());
				writeInteger(objspecialShopNotifyList.getItemNum());
	}
		writeInteger(crystal);
		writeInteger(refreshTime);
	writeShort(refreshHours.length);
	for(int i=0; i<refreshHours.length; i++){
	String objrefreshHours = refreshHours[i];
			writeString(objrefreshHours);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_SPECIAL_SHOP_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_SPECIAL_SHOP_PANEL";
	}

	public com.hifun.soul.gameserver.shop.SpecialShopItem[] getSpecialShopItemList(){
		return specialShopItemList;
	}

	public void setSpecialShopItemList(com.hifun.soul.gameserver.shop.SpecialShopItem[] specialShopItemList){
		this.specialShopItemList = specialShopItemList;
	}	

	public com.hifun.soul.gameserver.shop.SpecialShopNotify[] getSpecialShopNotifyList(){
		return specialShopNotifyList;
	}

	public void setSpecialShopNotifyList(com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList){
		this.specialShopNotifyList = specialShopNotifyList;
	}	

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public int getRefreshTime(){
		return refreshTime;
	}
		
	public void setRefreshTime(int refreshTime){
		this.refreshTime = refreshTime;
	}

	public String[] getRefreshHours(){
		return refreshHours;
	}

	public void setRefreshHours(String[] refreshHours){
		this.refreshHours = refreshHours;
	}	
}