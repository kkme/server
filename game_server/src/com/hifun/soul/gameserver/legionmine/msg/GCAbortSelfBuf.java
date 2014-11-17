package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应取消个人buf
 *
 * @author SevenSoul
 */
@Component
public class GCAbortSelfBuf extends GCMessage{
	
	/** 个人buf类型 */
	private int selfBufType;
	/** 是否正在使用 */
	private boolean using;

	public GCAbortSelfBuf (){
	}
	
	public GCAbortSelfBuf (
			int selfBufType,
			boolean using ){
			this.selfBufType = selfBufType;
			this.using = using;
	}

	@Override
	protected boolean readImpl() {
		selfBufType = readInteger();
		using = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(selfBufType);
		writeBoolean(using);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ABORT_SELF_BUF;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ABORT_SELF_BUF";
	}

	public int getSelfBufType(){
		return selfBufType;
	}
		
	public void setSelfBufType(int selfBufType){
		this.selfBufType = selfBufType;
	}

	public boolean getUsing(){
		return using;
	}
		
	public void setUsing(boolean using){
		this.using = using;
	}
}