package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知buff效果结束
 *
 * @author SevenSoul
 */
@Component
public class GCStopBuffEffect extends GCMessage{
	
	/** buff类型 */
	private int buffType;
	/** 目标ID */
	private long targetId;
	/** buffID */
	private int buffId;

	public GCStopBuffEffect (){
	}
	
	public GCStopBuffEffect (
			int buffType,
			long targetId,
			int buffId ){
			this.buffType = buffType;
			this.targetId = targetId;
			this.buffId = buffId;
	}

	@Override
	protected boolean readImpl() {
		buffType = readInteger();
		targetId = readLong();
		buffId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buffType);
		writeLong(targetId);
		writeInteger(buffId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STOP_BUFF_EFFECT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STOP_BUFF_EFFECT";
	}

	public int getBuffType(){
		return buffType;
	}
		
	public void setBuffType(int buffType){
		this.buffType = buffType;
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}

	public int getBuffId(){
		return buffId;
	}
		
	public void setBuffId(int buffId){
		this.buffId = buffId;
	}
}