package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知更新buff效果
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateBuffState extends GCMessage{
	
	/** buff类型 */
	private int buffType;
	/** buffID */
	private int buffId;
	/** 目标ID */
	private long targetId;

	public GCUpdateBuffState (){
	}
	
	public GCUpdateBuffState (
			int buffType,
			int buffId,
			long targetId ){
			this.buffType = buffType;
			this.buffId = buffId;
			this.targetId = targetId;
	}

	@Override
	protected boolean readImpl() {
		buffType = readInteger();
		buffId = readInteger();
		targetId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buffType);
		writeInteger(buffId);
		writeLong(targetId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_BUFF_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_BUFF_STATE";
	}

	public int getBuffType(){
		return buffType;
	}
		
	public void setBuffType(int buffType){
		this.buffType = buffType;
	}

	public int getBuffId(){
		return buffId;
	}
		
	public void setBuffId(int buffId){
		this.buffId = buffId;
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}
}