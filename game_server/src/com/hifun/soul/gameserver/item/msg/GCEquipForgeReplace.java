package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备洗炼替换
 *
 * @author SevenSoul
 */
@Component
public class GCEquipForgeReplace extends GCMessage{
	
	/** 装备身上带的特殊属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes;

	public GCEquipForgeReplace (){
	}
	
	public GCEquipForgeReplace (
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes ){
			this.equipSpecialAttributes = equipSpecialAttributes;
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
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EQUIP_FORGE_REPLACE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_FORGE_REPLACE";
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getEquipSpecialAttributes(){
		return equipSpecialAttributes;
	}

	public void setEquipSpecialAttributes(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes){
		this.equipSpecialAttributes = equipSpecialAttributes;
	}	
}