package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知buff效果开始
 *
 * @author SevenSoul
 */
@Component
public class GCStartBuffEffect extends GCMessage{
	
	/** buff信息 */
	private com.hifun.soul.gameserver.skill.buff.BuffInfo buffInfo;
	/** 目标ID */
	private long targetId;

	public GCStartBuffEffect (){
	}
	
	public GCStartBuffEffect (
			com.hifun.soul.gameserver.skill.buff.BuffInfo buffInfo,
			long targetId ){
			this.buffInfo = buffInfo;
			this.targetId = targetId;
	}

	@Override
	protected boolean readImpl() {
		buffInfo = new com.hifun.soul.gameserver.skill.buff.BuffInfo();
						buffInfo.setBuffId(readInteger());
						buffInfo.setBuffType(readInteger());
						buffInfo.setBuffResourceId(readInteger());
						buffInfo.setBuffName(readString());
						buffInfo.setBuffDesc(readString());
						buffInfo.setBuffSelfType(readInteger());
						buffInfo.setRound(readInteger());
				targetId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buffInfo.getBuffId());
		writeInteger(buffInfo.getBuffType());
		writeInteger(buffInfo.getBuffResourceId());
		writeString(buffInfo.getBuffName());
		writeString(buffInfo.getBuffDesc());
		writeInteger(buffInfo.getBuffSelfType());
		writeInteger(buffInfo.getRound());
		writeLong(targetId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_BUFF_EFFECT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_BUFF_EFFECT";
	}

	public com.hifun.soul.gameserver.skill.buff.BuffInfo getBuffInfo(){
		return buffInfo;
	}
		
	public void setBuffInfo(com.hifun.soul.gameserver.skill.buff.BuffInfo buffInfo){
		this.buffInfo = buffInfo;
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}
}