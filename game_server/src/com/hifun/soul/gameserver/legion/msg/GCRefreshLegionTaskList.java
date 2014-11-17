package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应刷新军团任务列表 
 *
 * @author SevenSoul
 */
@Component
public class GCRefreshLegionTaskList extends GCMessage{
	
	/** 军团任务信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos;

	public GCRefreshLegionTaskList (){
	}
	
	public GCRefreshLegionTaskList (
			com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos ){
			this.taskInfos = taskInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		taskInfos = new com.hifun.soul.gameserver.legion.info.LegionTaskInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionTaskInfo objtaskInfos = new com.hifun.soul.gameserver.legion.info.LegionTaskInfo();
			taskInfos[i] = objtaskInfos;
					objtaskInfos.setTaskId(readInteger());
							objtaskInfos.setTaskTheme(readString());
							objtaskInfos.setIconId(readInteger());
							objtaskInfos.setColorId(readInteger());
							objtaskInfos.setRewardMedal(readInteger());
							objtaskInfos.setRewardLegionExperience(readInteger());
							objtaskInfos.setHasTaskReward(readBoolean());
							objtaskInfos.setRemainTime(readInteger());
							objtaskInfos.setNeedTime(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(taskInfos.length);
	for(int i=0; i<taskInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionTaskInfo objtaskInfos = taskInfos[i];
				writeInteger(objtaskInfos.getTaskId());
				writeString(objtaskInfos.getTaskTheme());
				writeInteger(objtaskInfos.getIconId());
				writeInteger(objtaskInfos.getColorId());
				writeInteger(objtaskInfos.getRewardMedal());
				writeInteger(objtaskInfos.getRewardLegionExperience());
				writeBoolean(objtaskInfos.getHasTaskReward());
				writeInteger(objtaskInfos.getRemainTime());
				writeInteger(objtaskInfos.getNeedTime());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REFRESH_LEGION_TASK_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REFRESH_LEGION_TASK_LIST";
	}

	public com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] getTaskInfos(){
		return taskInfos;
	}

	public void setTaskInfos(com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos){
		this.taskInfos = taskInfos;
	}	
}