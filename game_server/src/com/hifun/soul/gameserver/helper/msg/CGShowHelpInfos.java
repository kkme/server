package com.hifun.soul.gameserver.helper.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求小助手信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowHelpInfos extends CGMessage{
	
	/** 是否需要打开板子 */
	private boolean isNeedOpen;
	
	public CGShowHelpInfos (){
	}
	
	public CGShowHelpInfos (
			boolean isNeedOpen ){
			this.isNeedOpen = isNeedOpen;
	}
	
	@Override
	protected boolean readImpl() {
		isNeedOpen = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isNeedOpen);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_HELP_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_HELP_INFOS";
	}

	public boolean getIsNeedOpen(){
		return isNeedOpen;
	}
		
	public void setIsNeedOpen(boolean isNeedOpen){
		this.isNeedOpen = isNeedOpen;
	}

	@Override
	public void execute() {
	}
}