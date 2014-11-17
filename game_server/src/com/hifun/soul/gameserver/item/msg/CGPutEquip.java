package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 装备放到宝石镶嵌面板
 * 
 * @author SevenSoul
 */
@Component
public class CGPutEquip extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包中的位置 */
	private int equipBagIndex;
	
	public CGPutEquip (){
	}
	
	public CGPutEquip (
			int equipBagType,
			int equipBagIndex ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PUT_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PUT_EQUIP";
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

	@Override
	public void execute() {
	}
}