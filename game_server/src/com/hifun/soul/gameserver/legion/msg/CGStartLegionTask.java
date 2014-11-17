package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求接受军团任务
 * 
 * @author SevenSoul
 */
@Component
public class CGStartLegionTask extends CGMessage{
	
	/** 任务ID */
	private int taskId;
	
	public CGStartLegionTask (){
	}
	
	public CGStartLegionTask (
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
		return MessageType.CG_START_LEGION_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_LEGION_TASK";
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