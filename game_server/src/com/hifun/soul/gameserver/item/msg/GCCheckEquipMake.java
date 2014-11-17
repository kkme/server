package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 判断装备上否可以升级
 *
 * @author SevenSoul
 */
@Component
public class GCCheckEquipMake extends GCMessage{
	
	/** 武器 */
	private boolean weapon;
	/** 饰品 */
	private boolean necklace;
	/** 头盔 */
	private boolean hat;
	/** 衣服 */
	private boolean cloth;
	/** 鞋子 */
	private boolean shoes;

	public GCCheckEquipMake (){
	}
	
	public GCCheckEquipMake (
			boolean weapon,
			boolean necklace,
			boolean hat,
			boolean cloth,
			boolean shoes ){
			this.weapon = weapon;
			this.necklace = necklace;
			this.hat = hat;
			this.cloth = cloth;
			this.shoes = shoes;
	}

	@Override
	protected boolean readImpl() {
		weapon = readBoolean();
		necklace = readBoolean();
		hat = readBoolean();
		cloth = readBoolean();
		shoes = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(weapon);
		writeBoolean(necklace);
		writeBoolean(hat);
		writeBoolean(cloth);
		writeBoolean(shoes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHECK_EQUIP_MAKE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHECK_EQUIP_MAKE";
	}

	public boolean getWeapon(){
		return weapon;
	}
		
	public void setWeapon(boolean weapon){
		this.weapon = weapon;
	}

	public boolean getNecklace(){
		return necklace;
	}
		
	public void setNecklace(boolean necklace){
		this.necklace = necklace;
	}

	public boolean getHat(){
		return hat;
	}
		
	public void setHat(boolean hat){
		this.hat = hat;
	}

	public boolean getCloth(){
		return cloth;
	}
		
	public void setCloth(boolean cloth){
		this.cloth = cloth;
	}

	public boolean getShoes(){
		return shoes;
	}
		
	public void setShoes(boolean shoes){
		this.shoes = shoes;
	}
}