package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求立即完成军团任务
 * 
 * @author SevenSoul
 */
@Component
public class CGCompleteLegionTask extends CGMessage{
	
	/** 任务ID */
	private int taskId;
	
	public CGCompleteLegionTask (){
	}
	
	public CGCompleteLegionTask (
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
		return MessageType.CG_COMPLETE_LEGION_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COMPLETE_LEGION_TASK";
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