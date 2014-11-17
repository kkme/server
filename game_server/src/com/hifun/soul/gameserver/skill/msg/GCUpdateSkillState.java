package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知更新技能冷却状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateSkillState extends GCMessage{
	
	/** 所有者ID */
	private long ownerId;
	/** 是否已经冷却 */
	private boolean isCooldown;

	public GCUpdateSkillState (){
	}
	
	public GCUpdateSkillState (
			long ownerId,
			boolean isCooldown ){
			this.ownerId = ownerId;
			this.isCooldown = isCooldown;
	}

	@Override
	protected boolean readImpl() {
		ownerId = readLong();
		isCooldown = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(ownerId);
		writeBoolean(isCooldown);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_SKILL_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_SKILL_STATE";
	}

	public long getOwnerId(){
		return ownerId;
	}
		
	public void setOwnerId(long ownerId){
		this.ownerId = ownerId;
	}

	public boolean getIsCooldown(){
		return isCooldown;
	}
		
	public void setIsCooldown(boolean isCooldown){
		this.isCooldown = isCooldown;
	}
}