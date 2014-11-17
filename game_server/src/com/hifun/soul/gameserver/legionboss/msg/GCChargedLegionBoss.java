package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 用充能攻击
 *
 * @author SevenSoul
 */
@Component
public class GCChargedLegionBoss extends GCMessage{
	
	/** 充能打击造成的伤害 */
	private int damage;

	public GCChargedLegionBoss (){
	}
	
	public GCChargedLegionBoss (
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
		return MessageType.GC_CHARGED_LEGION_BOSS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARGED_LEGION_BOSS";
	}

	public int getDamage(){
		return damage;
	}
		
	public void setDamage(int damage){
		this.damage = damage;
	}
}