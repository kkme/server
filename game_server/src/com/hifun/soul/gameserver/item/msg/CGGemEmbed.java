package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求镶嵌宝石
 * 
 * @author SevenSoul
 */
@Component
public class CGGemEmbed extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包中的位置 */
	private int equipBagIndex;
	/** 选中宝石所在的背包类型 */
	private int gemBagType;
	/** 选中宝石所在的背包中的位置 */
	private int gemBagIndex;
	/** 要镶嵌的位置 */
	private short index;
	
	public CGGemEmbed (){
	}
	
	public CGGemEmbed (
			int equipBagType,
			int equipBagIndex,
			int gemBagType,
			int gemBagIndex,
			short index ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
			this.gemBagType = gemBagType;
			this.gemBagIndex = gemBagIndex;
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		gemBagType = readInteger();
		gemBagIndex = readInteger();
		index = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		writeInteger(gemBagType);
		writeInteger(gemBagIndex);
		writeShort(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GEM_EMBED;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GEM_EMBED";
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

	public int getGemBagType(){
		return gemBagType;
	}
		
	public void setGemBagType(int gemBagType){
		this.gemBagType = gemBagType;
	}

	public int getGemBagIndex(){
		return gemBagIndex;
	}
		
	public void setGemBagIndex(int gemBagIndex){
		this.gemBagIndex = gemBagIndex;
	}

	public short getIndex(){
		return index;
	}
		
	public void setIndex(short index){
		this.index = index;
	}

	@Override
	public void execute() {
	}
}