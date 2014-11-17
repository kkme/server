package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开放功能
 *
 * @author SevenSoul
 */
@Component
public class GCOpenGameFuncs extends GCMessage{
	
	/** 开放的功能 */
	private int[] funcs;
	/** 是否新开放 */
	private boolean isNewOpen;

	public GCOpenGameFuncs (){
	}
	
	public GCOpenGameFuncs (
			int[] funcs,
			boolean isNewOpen ){
			this.funcs = funcs;
			this.isNewOpen = isNewOpen;
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
		isNewOpen = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(funcs.length);
	for(int i=0; i<funcs.length; i++){
	Integer objfuncs = funcs[i];
			writeInteger(objfuncs);
}
		writeBoolean(isNewOpen);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_GAME_FUNCS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_GAME_FUNCS";
	}

	public int[] getFuncs(){
		return funcs;
	}

	public void setFuncs(int[] funcs){
		this.funcs = funcs;
	}	

	public boolean getIsNewOpen(){
		return isNewOpen;
	}
		
	public void setIsNewOpen(boolean isNewOpen){
		this.isNewOpen = isNewOpen;
	}
}