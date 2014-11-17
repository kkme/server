package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求刷新军团任务列表
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshLegionTaskList extends CGMessage{
	
	
	public CGRefreshLegionTaskList (){
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
		return MessageType.CG_REFRESH_LEGION_TASK_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_LEGION_TASK_LIST";
	}

	@Override
	public void execute() {
	}
}