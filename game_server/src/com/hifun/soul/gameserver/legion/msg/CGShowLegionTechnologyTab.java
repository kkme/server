package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示军团科技厅标签
 * 
 * @author SevenSoul
 */
@Component
public class CGShowLegionTechnologyTab extends CGMessage{
	
	
	public CGShowLegionTechnologyTab (){
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
		return MessageType.CG_SHOW_LEGION_TECHNOLOGY_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_LEGION_TECHNOLOGY_TAB";
	}

	@Override
	public void execute() {
	}
}