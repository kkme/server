package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 用充能攻击
 *
 * @author SevenSoul
 */
@Component
public class GCChargedBoss extends GCMessage{
	
	/** 充能打击造成的伤害 */
	private int damage;

	public GCChargedBoss (){
	}
	
	public GCChargedBoss (
			int damage ){
			this.damage = damage;
	}

	@Override
	protected boolean readImpl() {
		damage = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(damage);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARGED_BOSS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARGED_BOSS";
	}

	public int getDamage(){
		return damage;
	}
		
	public void setDamage(int damage){
		this.damage = damage;
	}
}