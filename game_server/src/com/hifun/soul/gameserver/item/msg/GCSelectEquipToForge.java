package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 选中装备去洗炼
 *
 * @author SevenSoul
 */
@Component
public class GCSelectEquipToForge extends GCMessage{
	
	/** 装备身上带的特殊属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes;
	/** 装备身上带的特殊属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] newEquipSpecialAttributes;
	/** 随机属性区间 */
	private com.hifun.soul.gameserver.item.template.GemAttribute[] attributes;
	/** 洗炼模式(1:免费洗炼;2:灵石洗炼) */
	private int forgeType;
	/** 免费次数 */
	private int freeTimes;
	/** 灵石洗炼需要灵石图标 */
	private int stoneIcon;
	/** 灵石洗炼需要灵石名称 */
	private String stoneName;
	/** 灵石洗炼需要灵石数量 */
	private int stoneNum;
	/** 灵石洗炼需要灵石id */
	private int stoneId;

	public GCSelectEquipToForge (){
	}
	
	public GCSelectEquipToForge (
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] newEquipSpecialAttributes,
			com.hifun.soul.gameserver.item.template.GemAttribute[] attributes,
			int forgeType,
			int freeTimes,
			int stoneIcon,
			String stoneName,
			int stoneNum,
			int stoneId ){
			this.equipSpecialAttributes = equipSpecialAttributes;
			this.newEquipSpecialAttributes = newEquipSpecialAttributes;
			this.attributes = attributes;
			this.forgeType = forgeType;
			this.freeTimes = freeTimes;
			this.stoneIcon = stoneIcon;
			this.stoneName = stoneName;
			this.stoneNum = stoneNum;
			this.stoneId = stoneId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		equipSpecialAttributes = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipSpecialAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			equipSpecialAttributes[i] = objequipSpecialAttributes;
					objequipSpecialAttributes.setKey(readInteger());
							objequipSpecialAttributes.setValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		newEquipSpecialAttributes = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objnewEquipSpecialAttributes = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			newEquipSpecialAttributes[i] = objnewEquipSpecialAttributes;
					objnewEquipSpecialAttributes.setKey(readInteger());
							objnewEquipSpecialAttributes.setValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		attributes = new com.hifun.soul.gameserver.item.template.GemAttribute[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.template.GemAttribute objattributes = new com.hifun.soul.gameserver.item.template.GemAttribute();
			attributes[i] = objattributes;
					objattributes.setPropKey(readInteger());
							objattributes.setPropValue(readInteger());
							objattributes.setPropMaxValue(readInteger());
				}
		forgeType = readInteger();
		freeTimes = readInteger();
		stoneIcon = readInteger();
		stoneName = readString();
		stoneNum = readInteger();
		stoneId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(equipSpecialAttributes.length);
	for(int i=0; i<equipSpecialAttributes.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objequipSpecialAttributes = equipSpecialAttributes[i];
				writeInteger(objequipSpecialAttributes.getKey());
				writeInteger(objequipSpecialAttributes.getValue());
	}
	writeShort(newEquipSpecialAttributes.length);
	for(int i=0; i<newEquipSpecialAttributes.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objnewEquipSpecialAttributes = newEquipSpecialAttributes[i];
				writeInteger(objnewEquipSpecialAttributes.getKey());
				writeInteger(objnewEquipSpecialAttributes.getValue());
	}
	writeShort(attributes.length);
	for(int i=0; i<attributes.length; i++){
	com.hifun.soul.gameserver.item.template.GemAttribute objattributes = attributes[i];
				writeInteger(objattributes.getPropKey());
				writeInteger(objattributes.getPropValue());
				writeInteger(objattributes.getPropMaxValue());
	}
		writeInteger(forgeType);
		writeInteger(freeTimes);
		writeInteger(stoneIcon);
		writeString(stoneName);
		writeInteger(stoneNum);
		writeInteger(stoneId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SELECT_EQUIP_TO_FORGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SELECT_EQUIP_TO_FORGE";
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getEquipSpecialAttributes(){
		return equipSpecialAttributes;
	}

	public void setEquipSpecialAttributes(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes){
		this.equipSpecialAttributes = equipSpecialAttributes;
	}	

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getNewEquipSpecialAttributes(){
		return newEquipSpecialAttributes;
	}

	public void setNewEquipSpecialAttributes(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] newEquipSpecialAttributes){
		this.newEquipSpecialAttributes = newEquipSpecialAttributes;
	}	

	public com.hifun.soul.gameserver.item.template.GemAttribute[] getAttributes(){
		return attributes;
	}

	public void setAttributes(com.hifun.soul.gameserver.item.template.GemAttribute[] attributes){
		this.attributes = attributes;
	}	

	public int getForgeType(){
		return forgeType;
	}
		
	public void setForgeType(int forgeType){
		this.forgeType = forgeType;
	}

	public int getFreeTimes(){
		return freeTimes;
	}
		
	public void setFreeTimes(int freeTimes){
		this.freeTimes = freeTimes;
	}

	public int getStoneIcon(){
		return stoneIcon;
	}
		
	public void setStoneIcon(int stoneIcon){
		this.stoneIcon = stoneIcon;
	}

	public String getStoneName(){
		return stoneName;
	}
		
	public void setStoneName(String stoneName){
		this.stoneName = stoneName;
	}

	public int getStoneNum(){
		return stoneNum;
	}
		
	public void setStoneNum(int stoneNum){
		this.stoneNum = stoneNum;
	}

	public int getStoneId(){
		return stoneId;
	}
		
	public void setStoneId(int stoneId){
		this.stoneId = stoneId;
	}
}