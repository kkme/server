package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知清除buff效果
 *
 * @author SevenSoul
 */
@Component
public class GCClearBuff extends GCMessage{
	
	/** 目标ID */
	private long targetGuid;
	/** 清除类型 */
	private int clearType;

	public GCClearBuff (){
	}
	
	public GCClearBuff (
			long targetGuid,
			int clearType ){
			this.targetGuid = targetGuid;
			this.clearType = clearType;
	}

	@Override
	protected boolean readImpl() {
		targetGuid = readLong();
		clearType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(targetGuid);
		writeInteger(clearType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLEAR_BUFF;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLEAR_BUFF";
	}

	public long getTargetGuid(){
		return targetGuid;
	}
		
	public void setTargetGuid(long targetGuid){
		this.targetGuid = targetGuid;
	}

	public int getClearType(){
		return clearType;
	}
		
	public void setClearType(int clearType){
		this.clearType = clearType;
	}
}