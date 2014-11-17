package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求强化装备位
 * 
 * @author SevenSoul
 */
@Component
public class CGUpgradeEquipBit extends CGMessage{
	
	/** 装备位类型 */
	private int equipBitType;
	/** 是否使用魔晶 */
	private boolean isSelectedCrystal;
	
	public CGUpgradeEquipBit (){
	}
	
	public CGUpgradeEquipBit (
			int equipBitType,
			boolean isSelectedCrystal ){
			this.equipBitType = equipBitType;
			this.isSelectedCrystal = isSelectedCrystal;
	}
	
	@Override
	protected boolean readImpl() {
		equipBitType = readInteger();
		isSelectedCrystal = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBitType);
		writeBoolean(isSelectedCrystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPGRADE_EQUIP_BIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPGRADE_EQUIP_BIT";
	}

	public int getEquipBitType(){
		return equipBitType;
	}
		
	public void setEquipBitType(int equipBitType){
		this.equipBitType = equipBitType;
	}

	public boolean getIsSelectedCrystal(){
		return isSelectedCrystal;
	}
		
	public void setIsSelectedCrystal(boolean isSelectedCrystal){
		this.isSelectedCrystal = isSelectedCrystal;
	}

	@Override
	public void execute() {
	}
}