package com.hifun.soul.gameserver.rechargetx.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 充值是否已经结束
 *
 * @author SevenSoul
 */
@Component
public class GCRechargeFinished extends GCMessage{
	
	/** 是否已经成功(大于0充值成功) */
	private boolean success;
	/** 充值成功或者错误信息 */
	private String desc;

	public GCRechargeFinished (){
	}
	
	public GCRechargeFinished (
			boolean success,
			String desc ){
			this.success = success;
			this.desc = desc;
	}

	@Override
	protected boolean readImpl() {
		success = readBoolean();
		desc = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(success);
		writeString(desc);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECHARGE_FINISHED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECHARGE_FINISHED";
	}

	public boolean getSuccess(){
		return success;
	}
		
	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}
}