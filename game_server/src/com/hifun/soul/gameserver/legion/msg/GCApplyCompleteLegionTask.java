package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应申请立即完成军团任务 
 *
 * @author SevenSoul
 */
@Component
public class GCApplyCompleteLegionTask extends GCMessage{
	
	/** 任务ID */
	private int taskId;
	/** 消耗魔晶 */
	private int costCrystal;

	public GCApplyCompleteLegionTask (){
	}
	
	public GCApplyCompleteLegionTask (
			int taskId,
			int costCrystal ){
			this.taskId = taskId;
			this.costCrystal = costCrystal;
	}

	@Override
	protected boolean readImpl() {
		taskId = readInteger();
		costCrystal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(taskId);
		writeInteger(costCrystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_APPLY_COMPLETE_LEGION_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_COMPLETE_LEGION_TASK";
	}

	public int getTaskId(){
		return taskId;
	}
		
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}

	public int getCostCrystal(){
		return costCrystal;
	}
		
	public void setCostCrystal(int costCrystal){
		this.costCrystal = costCrystal;
	}
}