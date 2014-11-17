package com.hifun.soul.gameserver.functionhelper.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求功能助手信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowFuncHelpInfos extends CGMessage{
	
	/** 分类id */
	private int id;
	
	public CGShowFuncHelpInfos (){
	}
	
	public CGShowFuncHelpInfos (
			int id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_FUNC_HELP_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_FUNC_HELP_INFOS";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	@Override
	public void execute() {
	}
}