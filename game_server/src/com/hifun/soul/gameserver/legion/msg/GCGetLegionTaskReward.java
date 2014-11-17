package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应获得军团任务奖励 
 *
 * @author SevenSoul
 */
@Component
public class GCGetLegionTaskReward extends GCMessage{
	
	/** 剩余任务数量 */
	private int remainTaskNum;
	/** 军团任务信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo;
	/** 今日积分 */
	private int todayTaskScore;
	/** 军团资金 */
	private int legionCoin;

	public GCGetLegionTaskReward (){
	}
	
	public GCGetLegionTaskReward (
			int remainTaskNum,
			com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo,
			int todayTaskScore,
			int legionCoin ){
			this.remainTaskNum = remainTaskNum;
			this.taskInfo = taskInfo;
			this.todayTaskScore = todayTaskScore;
			this.legionCoin = legionCoin;
	}

	@Override
	protected boolean readImpl() {
		remainTaskNum = readInteger();
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
				todayTaskScore = readInteger();
		legionCoin = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainTaskNum);
		writeInteger(taskInfo.getTaskId());
		writeString(taskInfo.getTaskTheme());
		writeInteger(taskInfo.getIconId());
		writeInteger(taskInfo.getColorId());
		writeInteger(taskInfo.getRewardMedal());
		writeInteger(taskInfo.getRewardLegionExperience());
		writeBoolean(taskInfo.getHasTaskReward());
		writeInteger(taskInfo.getRemainTime());
		writeInteger(taskInfo.getNeedTime());
		writeInteger(todayTaskScore);
		writeInteger(legionCoin);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_LEGION_TASK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_LEGION_TASK_REWARD";
	}

	public int getRemainTaskNum(){
		return remainTaskNum;
	}
		
	public void setRemainTaskNum(int remainTaskNum){
		this.remainTaskNum = remainTaskNum;
	}

	public com.hifun.soul.gameserver.legion.info.LegionTaskInfo getTaskInfo(){
		return taskInfo;
	}
		
	public void setTaskInfo(com.hifun.soul.gameserver.legion.info.LegionTaskInfo taskInfo){
		this.taskInfo = taskInfo;
	}

	public int getTodayTaskScore(){
		return todayTaskScore;
	}
		
	public void setTodayTaskScore(int todayTaskScore){
		this.todayTaskScore = todayTaskScore;
	}

	public int getLegionCoin(){
		return legionCoin;
	}
		
	public void setLegionCoin(int legionCoin){
		this.legionCoin = legionCoin;
	}
}