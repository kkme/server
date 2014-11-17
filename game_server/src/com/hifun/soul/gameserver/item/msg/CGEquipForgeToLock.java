package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 装备洗炼锁定
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipForgeToLock extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包中的位置 */
	private int equipBagIndex;
	/** 锁定随机属性的数量 */
	private int lockNum;
	
	public CGEquipForgeToLock (){
	}
	
	public CGEquipForgeToLock (
			int equipBagType,
			int equipBagIndex,
			int lockNum ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
			this.lockNum = lockNum;
	}
	
	@Override
	protected boolean readImpl() {
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		lockNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		writeInteger(lockNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EQUIP_FORGE_TO_LOCK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_FORGE_TO_LOCK";
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

	public int getLockNum(){
		return lockNum;
	}
		
	public void setLockNum(int lockNum){
		this.lockNum = lockNum;
	}

	@Override
	public void execute() {
	}
}