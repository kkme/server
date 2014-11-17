package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应退出军团
 *
 * @author SevenSoul
 */
@Component
public class GCQuitLegion extends GCMessage{
	
	/** 成功与否 */
	private int result;

	public GCQuitLegion (){
	}
	
	public GCQuitLegion (
			int result ){
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUIT_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUIT_LEGION";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}