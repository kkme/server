package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打造装备
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipMake extends CGMessage{
	
	/** 图纸所在背包类型 */
	private int paperBagType;
	/** 图纸所在背包索引 */
	private int paperBagIndex;
	/** 装备所在背包类型 */
	private int equipBagType;
	/** 装备所在背包索引 */
	private int equipBagIndex;
	
	public CGEquipMake (){
	}
	
	public CGEquipMake (
			int paperBagType,
			int paperBagIndex,
			int equipBagType,
			int equipBagIndex ){
			this.paperBagType = paperBagType;
			this.paperBagIndex = paperBagIndex;
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		paperBagType = readInteger();
		paperBagIndex = readInteger();
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(paperBagType);
		writeInteger(paperBagIndex);
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EQUIP_MAKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_MAKE";
	}

	public int getPaperBagType(){
		return paperBagType;
	}
		
	public void setPaperBagType(int paperBagType){
		this.paperBagType = paperBagType;
	}

	public int getPaperBagIndex(){
		return paperBagIndex;
	}
		
	public void setPaperBagIndex(int paperBagIndex){
		this.paperBagIndex = paperBagIndex;
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