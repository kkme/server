package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示军团任务排行
 * 
 * @author SevenSoul
 */
@Component
public class CGShowLegionTaskRank extends CGMessage{
	
	
	public CGShowLegionTaskRank (){
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
		return MessageType.CG_SHOW_LEGION_TASK_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_LEGION_TASK_RANK";
	}

	@Override
	public void execute() {
	}
}