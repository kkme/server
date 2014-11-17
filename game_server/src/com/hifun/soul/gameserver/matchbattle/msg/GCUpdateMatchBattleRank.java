package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新排行
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMatchBattleRank extends GCMessage{
	
	/** 连胜榜单 */
	private com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo;

	public GCUpdateMatchBattleRank (){
	}
	
	public GCUpdateMatchBattleRank (
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo ){
			this.RankInfo = RankInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		RankInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo objRankInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo();
			RankInfo[i] = objRankInfo;
					objRankInfo.setRoleId(readLong());
							objRankInfo.setRoleName(readString());
							objRankInfo.setOccupation(readInteger());
							objRankInfo.setLevel(readInteger());
							objRankInfo.setConsecutiveWinCount(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(RankInfo.length);
	for(int i=0; i<RankInfo.length; i++){
	com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo objRankInfo = RankInfo[i];
				writeLong(objRankInfo.getRoleId());
				writeString(objRankInfo.getRoleName());
				writeInteger(objRankInfo.getOccupation());
				writeInteger(objRankInfo.getLevel());
				writeInteger(objRankInfo.getConsecutiveWinCount());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MATCH_BATTLE_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MATCH_BATTLE_RANK";
	}

	public com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] getRankInfo(){
		return RankInfo;
	}

	public void setRankInfo(com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo){
		this.RankInfo = RankInfo;
	}	
}