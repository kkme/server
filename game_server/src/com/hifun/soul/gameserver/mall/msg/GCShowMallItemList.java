package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示商城道具列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowMallItemList extends GCMessage{
	
	/** 商城道具种类 */
	private int mallItemType;
	/** 当前页面索引 */
	private short pageIndex;
	/** 总页数 */
	private short pageCount;
	/** 商城道具列表 */
	private com.hifun.soul.gameserver.mall.msg.MallItemInfo[] mallItemList;

	public GCShowMallItemList (){
	}
	
	public GCShowMallItemList (
			int mallItemType,
			short pageIndex,
			short pageCount,
			com.hifun.soul.gameserver.mall.msg.MallItemInfo[] mallItemList ){
			this.mallItemType = mallItemType;
			this.pageIndex = pageIndex;
			this.pageCount = pageCount;
			this.mallItemList = mallItemList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		mallItemType = readInteger();
		pageIndex = readShort();
		pageCount = readShort();
		count = readShort();
		count = count < 0 ? 0 : count;
		mallItemList = new com.hifun.soul.gameserver.mall.msg.MallItemInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mall.msg.MallItemInfo objmallItemList = new com.hifun.soul.gameserver.mall.msg.MallItemInfo();
			mallItemList[i] = objmallItemList;
					objmallItemList.setItemId(readInteger());
							objmallItemList.setCurrencyType(readShort());
							objmallItemList.setNum(readInteger());
							objmallItemList.setDiscount(readBoolean());
							objmallItemList.setDiscountRate(readFloat());
							objmallItemList.setMallItemType(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.CommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.CommonItem();
	objmallItemList.setCommonItem(objcommonItem);
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
		writeInteger(mallItemType);
		writeShort(pageIndex);
		writeShort(pageCount);
	writeShort(mallItemList.length);
	for(int i=0; i<mallItemList.length; i++){
	com.hifun.soul.gameserver.mall.msg.MallItemInfo objmallItemList = mallItemList[i];
				writeInteger(objmallItemList.getItemId());
				writeShort(objmallItemList.getCurrencyType());
				writeInteger(objmallItemList.getNum());
				writeBoolean(objmallItemList.getDiscount());
				writeFloat(objmallItemList.getDiscountRate());
				writeInteger(objmallItemList.getMallItemType());
					com.hifun.soul.gameserver.item.assist.CommonItem commonItem_objmallItemList = objmallItemList.getCommonItem();
					writeString(commonItem_objmallItemList.getUUID());
					writeInteger(commonItem_objmallItemList.getItemId());
					writeString(commonItem_objmallItemList.getName());
					writeString(commonItem_objmallItemList.getDesc());
					writeInteger(commonItem_objmallItemList.getType());
					writeInteger(commonItem_objmallItemList.getIcon());
					writeInteger(commonItem_objmallItemList.getRarity());
					writeInteger(commonItem_objmallItemList.getBind());
					writeInteger(commonItem_objmallItemList.getOverlapNum());
					writeInteger(commonItem_objmallItemList.getBagType());
					writeInteger(commonItem_objmallItemList.getBagIndex());
					writeShort(commonItem_objmallItemList.getSellCurrencyType());
					writeInteger(commonItem_objmallItemList.getSellNum());
					writeInteger(commonItem_objmallItemList.getPosition());
					writeInteger(commonItem_objmallItemList.getEndure());
					writeShort(commonItem_objmallItemList.getEquipHole());
					writeShort(commonItem_objmallItemList.getEmbedCurrencyType());
					writeInteger(commonItem_objmallItemList.getEmbedCurrencyNum());
					writeShort(commonItem_objmallItemList.getExtractCurrencyType());
					writeInteger(commonItem_objmallItemList.getExtractCurrencyNum());
					writeShort(commonItem_objmallItemList.getShopCurrencyType());
					writeInteger(commonItem_objmallItemList.getShopCurrencyNum());
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipBaseAttributes_commonItem_objmallItemList=commonItem_objmallItemList.getEquipBaseAttributes();
	writeShort(equipBaseAttributes_commonItem_objmallItemList.length);
	for(int jequipBaseAttributes=0; jequipBaseAttributes<equipBaseAttributes_commonItem_objmallItemList.length; jequipBaseAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipBaseAttributes_commonItem_objmallItemList_jequipBaseAttributes = equipBaseAttributes_commonItem_objmallItemList[jequipBaseAttributes];
													writeInteger(equipBaseAttributes_commonItem_objmallItemList_jequipBaseAttributes.getKey());
													writeInteger(equipBaseAttributes_commonItem_objmallItemList_jequipBaseAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes_commonItem_objmallItemList=commonItem_objmallItemList.getEquipSpecialAttributes();
	writeShort(equipSpecialAttributes_commonItem_objmallItemList.length);
	for(int jequipSpecialAttributes=0; jequipSpecialAttributes<equipSpecialAttributes_commonItem_objmallItemList.length; jequipSpecialAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipSpecialAttributes_commonItem_objmallItemList_jequipSpecialAttributes = equipSpecialAttributes_commonItem_objmallItemList[jequipSpecialAttributes];
													writeInteger(equipSpecialAttributes_commonItem_objmallItemList_jequipSpecialAttributes.getKey());
													writeInteger(equipSpecialAttributes_commonItem_objmallItemList_jequipSpecialAttributes.getValue());
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] gemAttributes_commonItem_objmallItemList=commonItem_objmallItemList.getGemAttributes();
	writeShort(gemAttributes_commonItem_objmallItemList.length);
	for(int jgemAttributes=0; jgemAttributes<gemAttributes_commonItem_objmallItemList.length; jgemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> gemAttributes_commonItem_objmallItemList_jgemAttributes = gemAttributes_commonItem_objmallItemList[jgemAttributes];
													writeInteger(gemAttributes_commonItem_objmallItemList_jgemAttributes.getKey());
													writeInteger(gemAttributes_commonItem_objmallItemList_jgemAttributes.getValue());
									}
						com.hifun.soul.gameserver.item.assist.GemItemInfo[] equipGemItemInfos_commonItem_objmallItemList=commonItem_objmallItemList.getEquipGemItemInfos();
	writeShort(equipGemItemInfos_commonItem_objmallItemList.length);
	for(int jequipGemItemInfos=0; jequipGemItemInfos<equipGemItemInfos_commonItem_objmallItemList.length; jequipGemItemInfos++){
					com.hifun.soul.gameserver.item.assist.GemItemInfo equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos = equipGemItemInfos_commonItem_objmallItemList[jequipGemItemInfos];
													writeInteger(equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.getItemId());
													writeInteger(equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.getIndex());
													writeInteger(equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.getRarity());
														com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos=equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.getEquipGemAttributes();
	writeShort(equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.length);
	for(int jequipGemAttributes=0; jequipGemAttributes<equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos.length; jequipGemAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos_jequipGemAttributes = equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos[jequipGemAttributes];
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos_jequipGemAttributes.getKey());
													writeInteger(equipGemAttributes_equipGemItemInfos_commonItem_objmallItemList_jequipGemItemInfos_jequipGemAttributes.getValue());
									}
									}
						com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipUpgradeAttributes_commonItem_objmallItemList=commonItem_objmallItemList.getEquipUpgradeAttributes();
	writeShort(equipUpgradeAttributes_commonItem_objmallItemList.length);
	for(int jequipUpgradeAttributes=0; jequipUpgradeAttributes<equipUpgradeAttributes_commonItem_objmallItemList.length; jequipUpgradeAttributes++){
					com.hifun.soul.core.util.KeyValuePair<Integer,Integer> equipUpgradeAttributes_commonItem_objmallItemList_jequipUpgradeAttributes = equipUpgradeAttributes_commonItem_objmallItemList[jequipUpgradeAttributes];
													writeInteger(equipUpgradeAttributes_commonItem_objmallItemList_jequipUpgradeAttributes.getKey());
													writeInteger(equipUpgradeAttributes_commonItem_objmallItemList_jequipUpgradeAttributes.getValue());
									}
					writeFloat(commonItem_objmallItemList.getExtraSuccessRate());
					writeInteger(commonItem_objmallItemList.getUpgradeLevel());
					writeInteger(commonItem_objmallItemList.getLimitLevel());
					writeInteger(commonItem_objmallItemList.getLimitOccupation());
					writeInteger(commonItem_objmallItemList.getMaxEquipHole());
						com.hifun.soul.core.util.KeyValuePair<String,Integer>[] materialsOfEquipPaper_commonItem_objmallItemList=commonItem_objmallItemList.getMaterialsOfEquipPaper();
	writeShort(materialsOfEquipPaper_commonItem_objmallItemList.length);
	for(int jmaterialsOfEquipPaper=0; jmaterialsOfEquipPaper<materialsOfEquipPaper_commonItem_objmallItemList.length; jmaterialsOfEquipPaper++){
					com.hifun.soul.core.util.KeyValuePair<String,Integer> materialsOfEquipPaper_commonItem_objmallItemList_jmaterialsOfEquipPaper = materialsOfEquipPaper_commonItem_objmallItemList[jmaterialsOfEquipPaper];
													writeString(materialsOfEquipPaper_commonItem_objmallItemList_jmaterialsOfEquipPaper.getKey());
													writeInteger(materialsOfEquipPaper_commonItem_objmallItemList_jmaterialsOfEquipPaper.getValue());
									}
					writeInteger(commonItem_objmallItemList.getMaxOverlap());
					writeBoolean(commonItem_objmallItemList.getIsEquiped());
					writeBoolean(commonItem_objmallItemList.getCanSell());
			}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MALL_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MALL_ITEM_LIST";
	}

	public int getMallItemType(){
		return mallItemType;
	}
		
	public void setMallItemType(int mallItemType){
		this.mallItemType = mallItemType;
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

	public com.hifun.soul.gameserver.mall.msg.MallItemInfo[] getMallItemList(){
		return mallItemList;
	}

	public void setMallItemList(com.hifun.soul.gameserver.mall.msg.MallItemInfo[] mallItemList){
		this.mallItemList = mallItemList;
	}	
}