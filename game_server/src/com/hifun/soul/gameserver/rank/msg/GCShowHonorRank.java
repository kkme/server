package com.hifun.soul.gameserver.rank.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开角色荣誉排行榜
 *
 * @author SevenSoul
 */
@Component
public class GCShowHonorRank extends GCMessage{
	
	/** 榜单人数 */
	private int rankSize;
	/** 当前角色在排行榜的位置（未入榜时为-1） */
	private int humanRankPosition;
	/** 排行榜数据列表 */
	private com.hifun.soul.gameserver.rank.model.HumanHonorRankData[] rankDatas;

	public GCShowHonorRank (){
	}
	
	public GCShowHonorRank (
			int rankSize,
			int humanRankPosition,
			com.hifun.soul.gameserver.rank.model.HumanHonorRankData[] rankDatas ){
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
		rankDatas = new com.hifun.soul.gameserver.rank.model.HumanHonorRankData[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.rank.model.HumanHonorRankData objrankDatas = new com.hifun.soul.gameserver.rank.model.HumanHonorRankData();
			rankDatas[i] = objrankDatas;
					objrankDatas.setHumanGuid(readLong());
							objrankDatas.setRankPosition(readInteger());
							objrankDatas.setHumanName(readString());
							objrankDatas.setOccupation(readInteger());
							objrankDatas.setTitleName(readString());
							objrankDatas.setLegionId(readLong());
							objrankDatas.setLegionName(readString());
							objrankDatas.setLevel(readInteger());
							objrankDatas.setYellowVipLevel(readInteger());
							objrankDatas.setIsYearYellowVip(readBoolean());
							objrankDatas.setIsYellowHighVip(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rankSize);
		writeInteger(humanRankPosition);
	writeShort(rankDatas.length);
	for(int i=0; i<rankDatas.length; i++){
	com.hifun.soul.gameserver.rank.model.HumanHonorRankData objrankDatas = rankDatas[i];
				writeLong(objrankDatas.getHumanGuid());
				writeInteger(objrankDatas.getRankPosition());
				writeString(objrankDatas.getHumanName());
				writeInteger(objrankDatas.getOccupation());
				writeString(objrankDatas.getTitleName());
				writeLong(objrankDatas.getLegionId());
				writeString(objrankDatas.getLegionName());
				writeInteger(objrankDatas.getLevel());
				writeInteger(objrankDatas.getYellowVipLevel());
				writeBoolean(objrankDatas.getIsYearYellowVip());
				writeBoolean(objrankDatas.getIsYellowHighVip());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HONOR_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HONOR_RANK";
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

	public com.hifun.soul.gameserver.rank.model.HumanHonorRankData[] getRankDatas(){
		return rankDatas;
	}

	public void setRankDatas(com.hifun.soul.gameserver.rank.model.HumanHonorRankData[] rankDatas){
		this.rankDatas = rankDatas;
	}	
}