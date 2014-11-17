package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取过关奖励成功
 *
 * @author SevenSoul
 */
@Component
public class GCGetPassStageReward extends GCMessage{
	
	/** 领取是否成功 */
	private boolean success;

	public GCGetPassStageReward (){
	}
	
	public GCGetPassStageReward (
			boolean success ){
			this.success = success;
	}

	@Override
	protected boolean readImpl() {
		success = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(success);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_PASS_STAGE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_PASS_STAGE_REWARD";
	}

	public boolean getSuccess(){
		return success;
	}
		
	public void setSuccess(boolean success){
		this.success = success;
	}
}