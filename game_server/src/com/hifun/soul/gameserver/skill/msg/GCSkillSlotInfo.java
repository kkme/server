package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回技能栏tips
 *
 * @author SevenSoul
 */
@Component
public class GCSkillSlotInfo extends GCMessage{
	
	/** 角色等级要求 */
	private int humanLevel;
	/** 等级满足消耗金币 */
	private int coin;
	/** 等级不满足消耗魔晶 */
	private int crystal;

	public GCSkillSlotInfo (){
	}
	
	public GCSkillSlotInfo (
			int humanLevel,
			int coin,
			int crystal ){
			this.humanLevel = humanLevel;
			this.coin = coin;
			this.crystal = crystal;
	}

	@Override
	protected boolean readImpl() {
		humanLevel = readInteger();
		coin = readInteger();
		crystal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(humanLevel);
		writeInteger(coin);
		writeInteger(crystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SKILL_SLOT_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SKILL_SLOT_INFO";
	}

	public int getHumanLevel(){
		return humanLevel;
	}
		
	public void setHumanLevel(int humanLevel){
		this.humanLevel = humanLevel;
	}

	public int getCoin(){
		return coin;
	}
		
	public void setCoin(int coin){
		this.coin = coin;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}
}