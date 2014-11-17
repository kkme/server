package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团悬赏令标签 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionTaskTab extends GCMessage{
	
	/** 军团建筑信息 */
	private com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo;
	/** 系统刷新时间 */
	private String systemRefreshTime;
	/** 刷新消费 */
	private int refreshCost;
	/** 系统主题 */
	private String systemTheme;
	/** 今日积分 */
	private int todayScore;
	/** 是否有排名奖励 */
	private boolean hasRankReward;
	/** 最大任务数量 */
	private int maxTaskNum;
	/** 剩余任务数量 */
	private int remainTaskNum;
	/** 军团任务信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos;

	public GCShowLegionTaskTab (){
	}
	
	public GCShowLegionTaskTab (
			com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo,
			String systemRefreshTime,
			int refreshCost,
			String systemTheme,
			int todayScore,
			boolean hasRankReward,
			int maxTaskNum,
			int remainTaskNum,
			com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos ){
			this.legionBuildingInfo = legionBuildingInfo;
			this.systemRefreshTime = systemRefreshTime;
			this.refreshCost = refreshCost;
			this.systemTheme = systemTheme;
			this.todayScore = todayScore;
			this.hasRankReward = hasRankReward;
			this.maxTaskNum = maxTaskNum;
			this.remainTaskNum = remainTaskNum;
			this.taskInfos = taskInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		legionBuildingInfo = new com.hifun.soul.gameserver.legion.info.LegionBuildingInfo();
						legionBuildingInfo.setCurrentNum(readInteger());
						legionBuildingInfo.setNextNum(readInteger());
						legionBuildingInfo.setBuildingLevel(readInteger());
						legionBuildingInfo.setNeedLegionCoin(readInteger());
						legionBuildingInfo.setCurrentLegionCoin(readInteger());
				systemRefreshTime = readString();
		refreshCost = readInteger();
		systemTheme = readString();
		todayScore = readInteger();
		hasRankReward = readBoolean();
		maxTaskNum = readInteger();
		remainTaskNum = readInteger();
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
		writeInteger(legionBuildingInfo.getCurrentNum());
		writeInteger(legionBuildingInfo.getNextNum());
		writeInteger(legionBuildingInfo.getBuildingLevel());
		writeInteger(legionBuildingInfo.getNeedLegionCoin());
		writeInteger(legionBuildingInfo.getCurrentLegionCoin());
		writeString(systemRefreshTime);
		writeInteger(refreshCost);
		writeString(systemTheme);
		writeInteger(todayScore);
		writeBoolean(hasRankReward);
		writeInteger(maxTaskNum);
		writeInteger(remainTaskNum);
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
		return MessageType.GC_SHOW_LEGION_TASK_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_TASK_TAB";
	}

	public com.hifun.soul.gameserver.legion.info.LegionBuildingInfo getLegionBuildingInfo(){
		return legionBuildingInfo;
	}
		
	public void setLegionBuildingInfo(com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo){
		this.legionBuildingInfo = legionBuildingInfo;
	}

	public String getSystemRefreshTime(){
		return systemRefreshTime;
	}
		
	public void setSystemRefreshTime(String systemRefreshTime){
		this.systemRefreshTime = systemRefreshTime;
	}

	public int getRefreshCost(){
		return refreshCost;
	}
		
	public void setRefreshCost(int refreshCost){
		this.refreshCost = refreshCost;
	}

	public String getSystemTheme(){
		return systemTheme;
	}
		
	public void setSystemTheme(String systemTheme){
		this.systemTheme = systemTheme;
	}

	public int getTodayScore(){
		return todayScore;
	}
		
	public void setTodayScore(int todayScore){
		this.todayScore = todayScore;
	}

	public boolean getHasRankReward(){
		return hasRankReward;
	}
		
	public void setHasRankReward(boolean hasRankReward){
		this.hasRankReward = hasRankReward;
	}

	public int getMaxTaskNum(){
		return maxTaskNum;
	}
		
	public void setMaxTaskNum(int maxTaskNum){
		this.maxTaskNum = maxTaskNum;
	}

	public int getRemainTaskNum(){
		return remainTaskNum;
	}
		
	public void setRemainTaskNum(int remainTaskNum){
		this.remainTaskNum = remainTaskNum;
	}

	public com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] getTaskInfos(){
		return taskInfos;
	}

	public void setTaskInfos(com.hifun.soul.gameserver.legion.info.LegionTaskInfo[] taskInfos){
		this.taskInfos = taskInfos;
	}	
}