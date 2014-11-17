package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求装备强化
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipUpgrade extends CGMessage{
	
	/** 选中装备所在的背包类型 */
	private int equipBagType;
	/** 选中装备所在的背包位置 */
	private int equipBagIndex;
	/** 守护石id */
	private int guardStoneId;
	/** 幸运石id */
	private int fortuneStoneId;
	
	public CGEquipUpgrade (){
	}
	
	public CGEquipUpgrade (
			int equipBagType,
			int equipBagIndex,
			int guardStoneId,
			int fortuneStoneId ){
			this.equipBagType = equipBagType;
			this.equipBagIndex = equipBagIndex;
			this.guardStoneId = guardStoneId;
			this.fortuneStoneId = fortuneStoneId;
	}
	
	@Override
	protected boolean readImpl() {
		equipBagType = readInteger();
		equipBagIndex = readInteger();
		guardStoneId = readInteger();
		fortuneStoneId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(equipBagType);
		writeInteger(equipBagIndex);
		writeInteger(guardStoneId);
		writeInteger(fortuneStoneId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EQUIP_UPGRADE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_UPGRADE";
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

	public int getGuardStoneId(){
		return guardStoneId;
	}
		
	public void setGuardStoneId(int guardStoneId){
		this.guardStoneId = guardStoneId;
	}

	public int getFortuneStoneId(){
		return fortuneStoneId;
	}
		
	public void setFortuneStoneId(int fortuneStoneId){
		this.fortuneStoneId = fortuneStoneId;
	}

	@Override
	public void execute() {
	}
}