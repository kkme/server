package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应立即完成军团任务 
 *
 * @author SevenSoul
 */
@Component
public class GCCompleteLegionTask extends GCMessage{
	
	/** 军团任务信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo;

	public GCCompleteLegionTask (){
	}
	
	public GCCompleteLegionTask (
			com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo ){
			this.taskInfo = taskInfo;
	}

	@Override
	protected boolean readImpl() {
		taskInfo = new com.hifun.soul.gameserver.legion.info.LegionTaskInfo();
						taskInfo.setTaskId(readInteger());
						taskInfo.setTaskTheme(readString());
						taskInfo.setIconId(readInteger());
						taskInfo.setColorId(readInteger());
						taskInfo.setRewardMedal(readInteger());
						taskInfo.setRewardLegionExperience(readInteger());
						taskInfo.setHasTaskReward(readBoolean());
						taskInfo.setRemainTime(readInteger());
						taskInfo.setNeedTime(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(taskInfo.getTaskId());
		writeString(taskInfo.getTaskTheme());
		writeInteger(taskInfo.getIconId());
		writeInteger(taskInfo.getColorId());
		writeInteger(taskInfo.getRewardMedal());
		writeInteger(taskInfo.getRewardLegionExperience());
		writeBoolean(taskInfo.getHasTaskReward());
		writeInteger(taskInfo.getRemainTime());
		writeInteger(taskInfo.getNeedTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COMPLETE_LEGION_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COMPLETE_LEGION_TASK";
	}

	public com.hifun.soul.gameserver.legion.info.LegionTaskInfo getTaskInfo(){
		return taskInfo;
	}
		
	public void setTaskInfo(com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo){
		this.taskInfo = taskInfo;
	}
}