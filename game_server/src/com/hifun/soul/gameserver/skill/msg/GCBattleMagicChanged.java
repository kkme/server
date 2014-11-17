package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知魔变化
 *
 * @author SevenSoul
 */
@Component
public class GCBattleMagicChanged extends GCMessage{
	
	/** 目标ID */
	private long targetId;
	/** 魔法变化 */
	private com.hifun.soul.gameserver.skill.msg.MagicChange targetMagicChange;

	public GCBattleMagicChanged (){
	}
	
	public GCBattleMagicChanged (
			long targetId,
			com.hifun.soul.gameserver.skill.msg.MagicChange targetMagicChange ){
			this.targetId = targetId;
			this.targetMagicChange = targetMagicChange;
	}

	@Override
	protected boolean readImpl() {
		targetId = readLong();
		targetMagicChange = new com.hifun.soul.gameserver.skill.msg.MagicChange();
						targetMagicChange.setChangeRed(readInteger());
						targetMagicChange.setChangeYellow(readInteger());
						targetMagicChange.setChangeBlue(readInteger());
						targetMagicChange.setChangeGreen(readInteger());
						targetMagicChange.setChangePurple(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(targetId);
		writeInteger(targetMagicChange.getChangeRed());
		writeInteger(targetMagicChange.getChangeYellow());
		writeInteger(targetMagicChange.getChangeBlue());
		writeInteger(targetMagicChange.getChangeGreen());
		writeInteger(targetMagicChange.getChangePurple());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_MAGIC_CHANGED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_MAGIC_CHANGED";
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}

	public com.hifun.soul.gameserver.skill.msg.MagicChange getTargetMagicChange(){
		return targetMagicChange;
	}
		
	public void setTargetMagicChange(com.hifun.soul.gameserver.skill.msg.MagicChange targetMagicChange){
		this.targetMagicChange = targetMagicChange;
	}
}