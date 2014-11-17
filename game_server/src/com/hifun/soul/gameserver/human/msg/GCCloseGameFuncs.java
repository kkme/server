package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 关闭功能
 *
 * @author SevenSoul
 */
@Component
public class GCCloseGameFuncs extends GCMessage{
	
	/** 关闭的功能 */
	private int[] funcs;

	public GCCloseGameFuncs (){
	}
	
	public GCCloseGameFuncs (
			int[] funcs ){
			this.funcs = funcs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		funcs = new int[count];
		for(int i=0; i<count; i++){
			funcs[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(funcs.length);
	for(int i=0; i<funcs.length; i++){
	Integer objfuncs = funcs[i];
			writeInteger(objfuncs);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLOSE_GAME_FUNCS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLOSE_GAME_FUNCS";
	}

	public int[] getFuncs(){
		return funcs;
	}

	public void setFuncs(int[] funcs){
		this.funcs = funcs;
	}	
}