package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团任务排行 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionTaskRank extends GCMessage{
	
	/** 军团任务信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo[] rankInfos;

	public GCShowLegionTaskRank (){
	}
	
	public GCShowLegionTaskRank (
			com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo[] rankInfos ){
			this.rankInfos = rankInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rankInfos = new com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo objrankInfos = new com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo();
			rankInfos[i] = objrankInfos;
					objrankInfos.setRank(readInteger());
							objrankInfos.setHumanName(readString());
							objrankInfos.setHumanLevel(readInteger());
							objrankInfos.setScore(readInteger());
							objrankInfos.setRewardMedal(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(rankInfos.length);
	for(int i=0; i<rankInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo objrankInfos = rankInfos[i];
				writeInteger(objrankInfos.getRank());
				writeString(objrankInfos.getHumanName());
				writeInteger(objrankInfos.getHumanLevel());
				writeInteger(objrankInfos.getScore());
				writeInteger(objrankInfos.getRewardMedal());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_TASK_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_TASK_RANK";
	}

	public com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo[] getRankInfos(){
		return rankInfos;
	}

	public void setRankInfos(com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo[] rankInfos){
		this.rankInfos = rankInfos;
	}	
}