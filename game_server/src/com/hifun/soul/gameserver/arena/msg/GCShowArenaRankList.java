package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示竞技场排名列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowArenaRankList extends GCMessage{
	
	/** 排行榜所有玩家大小 */
	private int totalSize;
	/** 可挑战玩家列表 */
	private com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers;
	/** 自己的排名位置 */
	private int rankPosition;

	public GCShowArenaRankList (){
	}
	
	public GCShowArenaRankList (
			int totalSize,
			com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers,
			int rankPosition ){
			this.totalSize = totalSize;
			this.arenaMembers = arenaMembers;
			this.rankPosition = rankPosition;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		totalSize = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		arenaMembers = new com.hifun.soul.gameserver.arena.ArenaMember[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.arena.ArenaMember objarenaMembers = new com.hifun.soul.gameserver.arena.ArenaMember();
			arenaMembers[i] = objarenaMembers;
					objarenaMembers.setRank(readInteger());
							objarenaMembers.setRoleId(readLong());
							objarenaMembers.setIcon(readInteger());
							objarenaMembers.setName(readString());
							objarenaMembers.setLevel(readInteger());
							objarenaMembers.setLegionId(readLong());
							objarenaMembers.setLegionName(readString());
							objarenaMembers.setRankRewardId(readInteger());
							objarenaMembers.setRankRewardState(readInteger());
							objarenaMembers.setOccupation(readInteger());
							objarenaMembers.setYellowVipLevel(readInteger());
							objarenaMembers.setIsYearYellowVip(readBoolean());
							objarenaMembers.setIsYellowHighVip(readBoolean());
				}
		rankPosition = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalSize);
	writeShort(arenaMembers.length);
	for(int i=0; i<arenaMembers.length; i++){
	com.hifun.soul.gameserver.arena.ArenaMember objarenaMembers = arenaMembers[i];
				writeInteger(objarenaMembers.getRank());
				writeLong(objarenaMembers.getRoleId());
				writeInteger(objarenaMembers.getIcon());
				writeString(objarenaMembers.getName());
				writeInteger(objarenaMembers.getLevel());
				writeLong(objarenaMembers.getLegionId());
				writeString(objarenaMembers.getLegionName());
				writeInteger(objarenaMembers.getRankRewardId());
				writeInteger(objarenaMembers.getRankRewardState());
				writeInteger(objarenaMembers.getOccupation());
				writeInteger(objarenaMembers.getYellowVipLevel());
				writeBoolean(objarenaMembers.getIsYearYellowVip());
				writeBoolean(objarenaMembers.getIsYellowHighVip());
	}
		writeInteger(rankPosition);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ARENA_RANK_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ARENA_RANK_LIST";
	}

	public int getTotalSize(){
		return totalSize;
	}
		
	public void setTotalSize(int totalSize){
		this.totalSize = totalSize;
	}

	public com.hifun.soul.gameserver.arena.ArenaMember[] getArenaMembers(){
		return arenaMembers;
	}

	public void setArenaMembers(com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers){
		this.arenaMembers = arenaMembers;
	}	

	public int getRankPosition(){
		return rankPosition;
	}
		
	public void setRankPosition(int rankPosition){
		this.rankPosition = rankPosition;
	}
}