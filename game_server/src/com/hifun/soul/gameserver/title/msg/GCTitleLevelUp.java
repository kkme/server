package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应升级军衔
 *
 * @author SevenSoul
 */
@Component
public class GCTitleLevelUp extends GCMessage{
	
	/** 当前军衔id */
	private int currentTitleId;
	/** 当日俸禄是否领取 */
	private boolean isGotTitleSalary;

	public GCTitleLevelUp (){
	}
	
	public GCTitleLevelUp (
			int currentTitleId,
			boolean isGotTitleSalary ){
			this.currentTitleId = currentTitleId;
			this.isGotTitleSalary = isGotTitleSalary;
	}

	@Override
	protected boolean readImpl() {
		currentTitleId = readInteger();
		isGotTitleSalary = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentTitleId);
		writeBoolean(isGotTitleSalary);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TITLE_LEVEL_UP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TITLE_LEVEL_UP";
	}

	public int getCurrentTitleId(){
		return currentTitleId;
	}
		
	public void setCurrentTitleId(int currentTitleId){
		this.currentTitleId = currentTitleId;
	}

	public boolean getIsGotTitleSalary(){
		return isGotTitleSalary;
	}
		
	public void setIsGotTitleSalary(boolean isGotTitleSalary){
		this.isGotTitleSalary = isGotTitleSalary;
	}
}