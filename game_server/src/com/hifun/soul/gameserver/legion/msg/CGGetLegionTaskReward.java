package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求获得军团任务奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetLegionTaskReward extends CGMessage{
	
	/** 任务ID */
	private int taskId;
	
	public CGGetLegionTaskReward (){
	}
	
	public CGGetLegionTaskReward (
			int taskId ){
			this.taskId = taskId;
	}
	
	@Override
	protected boolean readImpl() {
		taskId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(taskId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_LEGION_TASK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_LEGION_TASK_REWARD";
	}

	public int getTaskId(){
		return taskId;
	}
		
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}

	@Override
	public void execute() {
	}
}