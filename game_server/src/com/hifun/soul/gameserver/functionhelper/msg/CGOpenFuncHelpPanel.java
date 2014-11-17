package com.hifun.soul.gameserver.functionhelper.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开功能助手面板请求
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenFuncHelpPanel extends CGMessage{
	
	
	public CGOpenFuncHelpPanel (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_FUNC_HELP_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_FUNC_HELP_PANEL";
	}

	@Override
	public void execute() {
	}
}