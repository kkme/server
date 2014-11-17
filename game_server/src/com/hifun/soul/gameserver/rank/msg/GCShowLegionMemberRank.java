package com.hifun.soul.gameserver.rank.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开军团成员排行榜
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionMemberRank extends GCMessage{
	
	/** 榜单个数 */
	private int rankSize;
	/** 当前角色所在军团在排行榜的位置（未入榜时为-1） */
	private int humanRankPosition;
	/** 排行榜数据列表 */
	private com.hifun.soul.gameserver.rank.model.LegionMemberRankData[] rankDatas;

	public GCShowLegionMemberRank (){
	}
	
	public GCShowLegionMemberRank (
			int rankSize,
			int humanRankPosition,
			com.hifun.soul.gameserver.rank.model.LegionMemberRankData[] rankDatas ){
			this.rankSize = rankSize;
			this.humanRankPosition = humanRankPosition;
			this.rankDatas = rankDatas;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		rankSize = readInteger();
		humanRankPosition = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rankDatas = new com.hifun.soul.gameserver.rank.model.LegionMemberRankData[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.rank.model.LegionMemberRankData objrankDatas = new com.hifun.soul.gameserver.rank.model.LegionMemberRankData();
			rankDatas[i] = objrankDatas;
					objrankDatas.setRankPosition(readInteger());
							objrankDatas.setLegionId(readLong());
							objrankDatas.setLegionName(readString());
							objrankDatas.setCommanderName(readString());
							objrankDatas.setLegionLevel(readInteger());
							objrankDatas.setMemberNum(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rankSize);
		writeInteger(humanRankPosition);
	writeShort(rankDatas.length);
	for(int i=0; i<rankDatas.length; i++){
	com.hifun.soul.gameserver.rank.model.LegionMemberRankData objrankDatas = rankDatas[i];
				writeInteger(objrankDatas.getRankPosition());
				writeLong(objrankDatas.getLegionId());
				writeString(objrankDatas.getLegionName());
				writeString(objrankDatas.getCommanderName());
				writeInteger(objrankDatas.getLegionLevel());
				writeInteger(objrankDatas.getMemberNum());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_MEMBER_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_MEMBER_RANK";
	}

	public int getRankSize(){
		return rankSize;
	}
		
	public void setRankSize(int rankSize){
		this.rankSize = rankSize;
	}

	public int getHumanRankPosition(){
		return humanRankPosition;
	}
		
	public void setHumanRankPosition(int humanRankPosition){
		this.humanRankPosition = humanRankPosition;
	}

	public com.hifun.soul.gameserver.rank.model.LegionMemberRankData[] getRankDatas(){
		return rankDatas;
	}

	public void setRankDatas(com.hifun.soul.gameserver.rank.model.LegionMemberRankData[] rankDatas){
		this.rankDatas = rankDatas;
	}	
}