package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求卸下某个位置的宝石
 * 
 * @author SevenSoul
 */
@Component
public class CGGemExtract extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包中的位置 */
	private int equipBagIndex;
	/** 宝石在孔的位置 */
	private short gemIndex;
	
	public CGGemExtract (){
	}
	
	public CGGemExtract (
			int equipBagType,
			int equipBagIndex,
			short gemIndex ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
			this.gemIndex = gemIndex;
	}
	
	@Override
	protected boolean readImpl() {
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		gemIndex = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		writeShort(gemIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GEM_EXTRACT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GEM_EXTRACT";
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

	public short getGemIndex(){
		return gemIndex;
	}
		
	public void setGemIndex(short gemIndex){
		this.gemIndex = gemIndex;
	}

	@Override
	public void execute() {
	}
}