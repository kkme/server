package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 装备洗炼
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipForge extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包中的位置 */
	private int equipBagIndex;
	/** 锁定随机属性的位置 */
	private int[] locks;
	
	public CGEquipForge (){
	}
	
	public CGEquipForge (
			int equipBagType,
			int equipBagIndex,
			int[] locks ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
			this.locks = locks;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
				locks = new int[count];
				for(int i=0; i<count; i++){
			locks[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		writeShort(locks.length);
		for(int i=0; i<locks.length; i++){
			writeInteger(locks[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EQUIP_FORGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_FORGE";
	}

	public int getEquipBagType(){
		return equipBagType;
	}
		
	public void setEquipBagType(int equipBagType){
		this.equipBagType = equipBagType;
	}

	public int getEquipBagIndex(){
		return equipBagIndex;
	}
		
	public void setEquipBagIndex(int equipBagIndex){
		this.equipBagIndex = equipBagIndex;
	}

	public int[] getLocks(){
		return locks;
	}

	public void setLocks(int[] locks){
		this.locks = locks;
	}	

	@Override
	public void execute() {
	}
}