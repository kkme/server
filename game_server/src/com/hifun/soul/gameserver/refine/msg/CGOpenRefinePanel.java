package com.hifun.soul.gameserver.refine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开某个试炼副本
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenRefinePanel extends CGMessage{
	
	/** 试炼副本id */
	private int refineType;
	
	public CGOpenRefinePanel (){
	}
	
	public CGOpenRefinePanel (
			int refineType ){
			this.refineType = refineType;
	}
	
	@Override
	protected boolean readImpl() {
		refineType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(refineType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_REFINE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_REFINE_PANEL";
	}

	public int getRefineType(){
		return refineType;
	}
		
	public void setRefineType(int refineType){
		this.refineType = refineType;
	}

	@Override
	public void execute() {
	}
}