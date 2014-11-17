package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求使用个人buf
 * 
 * @author SevenSoul
 */
@Component
public class CGUseSelfBuf extends CGMessage{
	
	/** 个人buf类型 */
	private int selfBufType;
	
	public CGUseSelfBuf (){
	}
	
	public CGUseSelfBuf (
			int selfBufType ){
			this.selfBufType = selfBufType;
	}
	
	@Override
	protected boolean readImpl() {
		selfBufType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(selfBufType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_USE_SELF_BUF;
	}
	
	@Override
	public String getTypeName() {
		return "CG_USE_SELF_BUF";
	}

	public int getSelfBufType(){
		return selfBufType;
	}
		
	public void setSelfBufType(int selfBufType){
		this.selfBufType = selfBufType;
	}

	@Override
	public void execute() {
	}
}