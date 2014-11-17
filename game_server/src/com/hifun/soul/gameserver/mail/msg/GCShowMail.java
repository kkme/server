package com.hifun.soul.gameserver.mail.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发送邮件信息到客户端
 *
 * @author SevenSoul
 */
@Component
public class GCShowMail extends GCMessage{
	
	/** 邮件ID */
	private long mailId;
	/** 邮件ID */
	private int mailType;
	/** 主题 */
	private String theme;
	/** 发件人Id */
	private long sendHumanId;
	/** 发件人姓名 */
	private String sendHumanName;
	/** 邮件内容 */
	private String content;
	/** 是否已领取 */
	private boolean isPickUp;
	/** 过期天数（仅在邮件含有奖励物品时有效,0表示已过期,-1表示没有有效期限制） */
	private int expireDays;
	/** 附件携带的物品 */
	private com.hifun.soul.gameserver.item.assist.CommonItem[] items;

	public GCShowMail (){
	}
	
	public GCShowMail (
			long mailId,
			int mailType,
			String theme,
			long sendHumanId,
			String sendHumanName,
			String content,
			boolean isPickUp,
			int expireDays,
			com.hifun.soul.gameserver.item.assist.CommonItem[] items ){
			this.mailId = mailId;
			this.mailType = mailType;
			this.theme = theme;
			this.sendHumanId = sendHumanId;
			this.sendHumanName = sendHumanName;
			this.content = content;
			this.isPickUp = isPickUp;
			this.expireDays = expireDays;
			this.items = items;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		mailId = readLong();
		mailType = readInteger();
		theme = readString();
		sendHumanId = readLong();
		sendHumanName = readString();
		content = readString();
		isPickUp = readBoolean();
		expireDays = readInteger();
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
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(mailId);
		writeInteger(mailType);
		writeString(theme);
		writeLong(sendHumanId);
		writeString(sendHumanName);
		writeString(content);
		writeBoolean(isPickUp);
		writeInteger(expireDays);
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
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MAIL";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}

	public int getMailType(){
		return mailType;
	}
		
	public void setMailType(int mailType){
		this.mailType = mailType;
	}

	public String getTheme(){
		return theme;
	}
		
	public void setTheme(String theme){
		this.theme = theme;
	}

	public long getSendHumanId(){
		return sendHumanId;
	}
		
	public void setSendHumanId(long sendHumanId){
		this.sendHumanId = sendHumanId;
	}

	public String getSendHumanName(){
		return sendHumanName;
	}
		
	public void setSendHumanName(String sendHumanName){
		this.sendHumanName = sendHumanName;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public boolean getIsPickUp(){
		return isPickUp;
	}
		
	public void setIsPickUp(boolean isPickUp){
		this.isPickUp = isPickUp;
	}

	public int getExpireDays(){
		return expireDays;
	}
		
	public void setExpireDays(int expireDays){
		this.expireDays = expireDays;
	}

	public com.hifun.soul.gameserver.item.assist.CommonItem[] getItems(){
		return items;
	}

	public void setItems(com.hifun.soul.gameserver.item.assist.CommonItem[] items){
		this.items = items;
	}	
}