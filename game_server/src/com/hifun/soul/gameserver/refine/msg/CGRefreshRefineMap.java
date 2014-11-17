package com.hifun.soul.gameserver.refine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 刷新试炼副本
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshRefineMap extends CGMessage{
	
	/** 试炼副本id */
	private int refineType;
	
	public CGRefreshRefineMap (){
	}
	
	public CGRefreshRefineMap (
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
		return MessageType.CG_REFRESH_REFINE_MAP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_REFINE_MAP";
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