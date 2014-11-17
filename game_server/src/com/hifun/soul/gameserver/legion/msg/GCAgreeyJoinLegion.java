package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应同意加入军团
 *
 * @author SevenSoul
 */
@Component
public class GCAgreeyJoinLegion extends GCMessage{
	
	/** 成功与否 */
	private int result;
	/** 原因 */
	private String reason;

	public GCAgreeyJoinLegion (){
	}
	
	public GCAgreeyJoinLegion (
			int result,
			String reason ){
			this.result = result;
			this.reason = reason;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		reason = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		writeString(reason);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_AGREE_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_AGREEY_JOIN_LEGION";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public String getReason(){
		return reason;
	}
		
	public void setReason(String reason){
		this.reason = reason;
	}
}