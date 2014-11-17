package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示商城道具列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowShopItemList extends GCMessage{
	
	/** 当前页面索引 */
	private short pageIndex;
	/** 总页数 */
	private short pageCount;
	/** 商店道具列表 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] shopItemList;

	public GCShowShopItemList (){
	}
	
	public GCShowShopItemList (
			short pageIndex,
			short pageCount,
			com.hifun.soul.gameserver.item.assist.CommonItem[] shopItemList ){
			this.pageIndex = pageIndex;
			this.pageCount = pageCount;
			this.shopItemList = shopItemList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		pageIndex = readShort();
		pageCount = readShort();
		count = readShort();
		count = count < 0 ? 0 : count;
		shopItemList = new com.hifun.soul.gameserver.item.assist.CommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.CommonItem objshopItemList = new com.hifun.soul.gameserver.item.assist.CommonItem();
			shopItemList[i] = objshopItemList;
					objshopItemList.setUUID(readString());
							objshopItemList.setItemId(readInteger());
							objshopItemList.setName(readString());
							objshopItemList.setDesc(readString());
							objshopItemList.setType(readInteger());
							objshopItemList.setIcon(readInteger());
							objshopItemList.setRarity(readInteger());
							objshopItemList.setBind(readInteger());
							objshopItemList.setOverlapNum(readInteger());
							objshopItemList.setBagType(readInteger());
							objshopItemList.setBagIndex(readInteger());
							objshopItemList.setSellCurrencyType(readShort());
							objshopItemList.setSellNum(readInteger());
							objshopItemList.setPosition(readInteger());
							objshopItemList.setEndure(readInteger());
							objshopItemList.setEquipHole(readShort());
							objshopItemList.setEmbedCurrencyType(readShort());
							objshopItemList.setEmbedCurrencyNum(readInteger());
							objshopItemList.setExtractCurrencyType(readShort());
							objshopItemList.setExtractCurrencyNum(readInteger());
							objshopItemList.setShopCurrencyType(readShort());
							objshopItemList.setShopCurrencyNum(readInteger());
								{
	int subCountequipBaseAttributes = readShort();
		com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] subListequipBaseAttributes  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountequipBaseAttributes);
		objshopItemList.setEquipBaseAttributes(subListequipBaseAttributes);
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
		objshopItemList.setEquipSpecialAttributes(subListequipSpecialAttributes);
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
		objshopItemList.setGemAttributes(subListgemAttributes);
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
		objshopItemList.setEquipGemItemInfos(subListequipGemItemInfos);
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
		objshopItemList.setEquipUpgradeAttributes(subListequipUpgradeAttributes);
	for(int jequipUpgradeAttributes = 0; jequipUpgradeAttributes < subCountequipUpgradeAttributes; jequipUpgradeAttributes++){
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipUpgradeAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
		subListequipUpgradeAttributes[jequipUpgradeAttributes] = objequipUpgradeAttributes;
						objequipUpgradeAttributes.setKey(readInteger());
								objequipUpgradeAttributes.setValue(readInteger());
							}
	}
							objshopItemList.setExtraSuccessRate(readFloat());
							objshopItemList.setUpgradeLevel(readInteger());
							objshopItemList.setLimitLevel(readInteger());
							objshopItemList.setLimitOccupation(readInteger());
							objshopItemList.setMaxEquipHole(readInteger());
								{
	int subCountmaterialsOfEquipPaper = readShort();
		com.hifun.soul.core.util.KeyValuePair<String,Integer>[] subListmaterialsOfEquipPaper  = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(subCountmaterialsOfEquipPaper);
		objshopItemList.setMaterialsOfEquipPaper(subListmaterialsOfEquipPaper);
	for(int jmaterialsOfEquipPaper = 0; jmaterialsOfEquipPaper < subCountmaterialsOfEquipPaper; jmaterialsOfEquipPaper++){
						com.hifun.soul.core.util.KeyValuePair<String,Integer> objmaterialsOfEquipPaper = new com.hifun.soul.core.util.KeyValuePair<String,Integer>();
		subListmaterialsOfEquipPaper[jmaterialsOfEquipPaper] = objmaterialsOfEquipPaper;
						objmaterialsOfEquipPaper.setKey(readString());
								objmaterialsOfEquipPaper.setValue(readInteger());
							}
	}
							objshopItemList.setMaxOverlap(readInteger());
							objshopItemList.setIsEquiped(readBoolean());
							objshopItemList.setCanSell(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(pageIndex);
		writeShort(pageCount);
	writeShort(shopItemList.length);
	for(int i=0; i<shopItemList.length; i++){
	com.hifun.soul.gameserver.item.assist.CommonItem objshopItemList = shopItemList[i];
				writeString(objshopItemList.getUUID());
				writeInteger(objshopItemList.getItemId());
				writeString(objshopItemList.getName());
				writeString(objshopItemList.getDesc());
				writeInteger(objshopItemList.getType());
				writeInteger(objshopItemList.getIcon());
				writeInteger(objshopItemList.getRarity());
				writeInteger(objshopItemList.getBind());
				writeInteger(objshopItemList.getOverlapNum());
				writeInteger(objshopItemList.getBagType());
				writeInteger(objshopItemList.getBagIndex());
				writeShort(objshopItemList.getSellCurrencyType());
				writeInteger(objshopItemList.getSellNum());
				writeInteger(objshopItemList.getPosition());
				writeInteger(objshopItemList.getEndure());
				writeShort(objshopItemList.getEquipHole());
				writeShort(objshopItemList.getEmbedCurrencyType());
				writeInteger(objshopItemList.getEmbedCurrencyNum());
				writeShort(objshopItemList.getExtractCurrencyType());
				writeInteger(objshopItemList.getExtractCurrencyNum());
				writeShort(objshopItemList.getShopCurrencyType());
				writeInteger(objshopItemList.getShopCurrencyNum());
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_objshopItemList=objshopItemList.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_objshopItemList.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_objshopItemList.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_objshopItemList_jequipBaseAttributes = equipBaseAttributes_objshopItemList[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_objshopItemList_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_objshopItemList_jequipBaseAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_objshopItemList=objshopItemList.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_objshopItemList.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_objshopItemList.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_objshopItemList_jequipSpecialAttributes = equipSpecialAttributes_objshopItemList[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_objshopItemList_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_objshopItemList_jequipSpecialAttributes.getValue());
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_objshopItemList=objshopItemList.getGemAttributes();
	writeShort(gemAttributes_objshopItemList.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_objshopItemList.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_objshopItemList_jgemAttributes = gemAttributes_objshopItemList[jgemAttributes];
													writeInteger(gemAttributes_objshopItemList_jgemAttributes.getKey());
													writeInteger(gemAttributes_objshopItemList_jgemAttributes.getValue());
									}
					com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_objshopItemList=objshopItemList.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_objshopItemList.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_objshopItemList.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_objshopItemList_jequipGemItemInfos = equipGemItemInfos_objshopItemList[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_objshopItemList_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_objshopItemList_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_objshopItemList_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos=equipGemItemInfos_objshopItemList_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_objshopItemList_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_objshopItemList=objshopItemList.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_objshopItemList.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_objshopItemList.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_objshopItemList_jequipUpgradeAttributes = equipUpgradeAttributes_objshopItemList[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_objshopItemList_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_objshopItemList_jequipUpgradeAttributes.getValue());
									}
				writeFloat(objshopItemList.getExtraSuccessRate());
				writeInteger(objshopItemList.getUpgradeLevel());
				writeInteger(objshopItemList.getLimitLevel());
				writeInteger(objshopItemList.getLimitOccupation());
				writeInteger(objshopItemList.getMaxEquipHole());
					com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_objshopItemList=objshopItemList.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_objshopItemList.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_objshopItemList.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_objshopItemList_jmaterialsOfEquipPaper = materialsOfEquipPaper_objshopItemList[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_objshopItemList_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_objshopItemList_jmaterialsOfEquipPaper.getValue());
									}
				writeInteger(objshopItemList.getMaxOverlap());
				writeBoolean(objshopItemList.getIsEquiped());
				writeBoolean(objshopItemList.getCanSell());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_SHOP_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_SHOP_ITEM_LIST";
	}

	public short getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(short pageIndex){
		this.pageIndex = pageIndex;
	}

	public short getPageCount(){
		return pageCount;
	}
		
	public void setPageCount(short pageCount){
		this.pageCount = pageCount;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getShopItemList(){
		return shopItemList;
	}

	public void setShopItemList(com.hifun.soul.gameserver.item.assist.CommonItem[] shopItemList){
		this.shopItemList = shopItemList;
	}	
}