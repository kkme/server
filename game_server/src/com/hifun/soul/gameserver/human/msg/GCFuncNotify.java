package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 单个功能提示
 *
 * @author SevenSoul
 */
@Component
public class GCFuncNotify extends GCMessage{
	
	/** 功能类型 */
	private int funcType;
	/** 是否提示 */
	private boolean notify;

	public GCFuncNotify (){
	}
	
	public GCFuncNotify (
			int funcType,
			boolean notify ){
			this.funcType = funcType;
			this.notify = notify;
	}

	@Override
	protected boolean readImpl() {
		funcType = readInteger();
		notify = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(funcType);
		writeBoolean(notify);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FUNC_NOTIFY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FUNC_NOTIFY";
	}

	public int getFuncType(){
		return funcType;
	}
		
	public void setFuncType(int funcType){
		this.funcType = funcType;
	}

	public boolean getNotify(){
		return notify;
	}
		
	public void setNotify(boolean notify){
		this.notify = notify;
	}
}