package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知替换旧的buff效果
 *
 * @author SevenSoul
 */
@Component
public class GCReplaceOldBuff extends GCMessage{
	
	/** 新的buff信息 */
	private com.hifun.soul.gameserver.skill.buff.BuffInfo newBuff;
	/** 目标ID */
	private long targetGuid;
	/** 需要替换的buffId */
	private int oldBuffId;

	public GCReplaceOldBuff (){
	}
	
	public GCReplaceOldBuff (
			com.hifun.soul.gameserver.skill.buff.BuffInfo newBuff,
			long targetGuid,
			int oldBuffId ){
			this.newBuff = newBuff;
			this.targetGuid = targetGuid;
			this.oldBuffId = oldBuffId;
	}

	@Override
	protected boolean readImpl() {
		newBuff = new com.hifun.soul.gameserver.skill.buff.BuffInfo();
						newBuff.setBuffId(readInteger());
						newBuff.setBuffType(readInteger());
						newBuff.setBuffResourceId(readInteger());
						newBuff.setBuffName(readString());
						newBuff.setBuffDesc(readString());
						newBuff.setBuffSelfType(readInteger());
						newBuff.setRound(readInteger());
				targetGuid = readLong();
		oldBuffId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(newBuff.getBuffId());
		writeInteger(newBuff.getBuffType());
		writeInteger(newBuff.getBuffResourceId());
		writeString(newBuff.getBuffName());
		writeString(newBuff.getBuffDesc());
		writeInteger(newBuff.getBuffSelfType());
		writeInteger(newBuff.getRound());
		writeLong(targetGuid);
		writeInteger(oldBuffId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REPLACE_OLD_BUFF;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REPLACE_OLD_BUFF";
	}

	public com.hifun.soul.gameserver.skill.buff.BuffInfo getNewBuff(){
		return newBuff;
	}
		
	public void setNewBuff(com.hifun.soul.gameserver.skill.buff.BuffInfo newBuff){
		this.newBuff = newBuff;
	}

	public long getTargetGuid(){
		return targetGuid;
	}
		
	public void setTargetGuid(long targetGuid){
		this.targetGuid = targetGuid;
	}

	public int getOldBuffId(){
		return oldBuffId;
	}
		
	public void setOldBuffId(int oldBuffId){
		this.oldBuffId = oldBuffId;
	}
}