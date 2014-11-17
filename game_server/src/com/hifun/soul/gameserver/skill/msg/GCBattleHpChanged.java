package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 *  服务器通知血量变化
 *
 * @author SevenSoul
 */
@Component
public class GCBattleHpChanged extends GCMessage{
	
	/** 血量变化类型(受击或者其它) */
	private int changeType;
	/** 目标ID */
	private long targetId;
	/** 血量变化 */
	private int changeHp;

	public GCBattleHpChanged (){
	}
	
	public GCBattleHpChanged (
			int changeType,
			long targetId,
			int changeHp ){
			this.changeType = changeType;
			this.targetId = targetId;
			this.changeHp = changeHp;
	}

	@Override
	protected boolean readImpl() {
		changeType = readInteger();
		targetId = readLong();
		changeHp = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(changeType);
		writeLong(targetId);
		writeInteger(changeHp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_HP_CHANGED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_HP_CHANGED";
	}

	public int getChangeType(){
		return changeType;
	}
		
	public void setChangeType(int changeType){
		this.changeType = changeType;
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}

	public int getChangeHp(){
		return changeHp;
	}
		
	public void setChangeHp(int changeHp){
		this.changeHp = changeHp;
	}
}