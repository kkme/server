package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示拦截榜
 *
 * @author SevenSoul
 */
@Component
public class GCShowRobRankTab extends GCMessage{
	
	/** 昨日拦截榜 */
	private com.hifun.soul.gameserver.escort.info.RobRankInfo[] yesterdayRanks;
	/** 今日拦截榜 */
	private com.hifun.soul.gameserver.escort.info.RobRankInfo[] todayRanks;
	/** 昨日个人拦截金币 */
	private int yesterdaySelfRobCoin;
	/** 今日个人拦截金币 */
	private int todaySelfRobCoin;
	/** 昨日个人拦截排名 */
	private int yesterdaySelfRobRank;
	/** 今日个人拦截排名 */
	private int todaySelfRobRank;
	/** 是否有奖励 */
	private boolean hasReward;

	public GCShowRobRankTab (){
	}
	
	public GCShowRobRankTab (
			com.hifun.soul.gameserver.escort.info.RobRankInfo[] yesterdayRanks,
			com.hifun.soul.gameserver.escort.info.RobRankInfo[] todayRanks,
			int yesterdaySelfRobCoin,
			int todaySelfRobCoin,
			int yesterdaySelfRobRank,
			int todaySelfRobRank,
			boolean hasReward ){
			this.yesterdayRanks = yesterdayRanks;
			this.todayRanks = todayRanks;
			this.yesterdaySelfRobCoin = yesterdaySelfRobCoin;
			this.todaySelfRobCoin = todaySelfRobCoin;
			this.yesterdaySelfRobRank = yesterdaySelfRobRank;
			this.todaySelfRobRank = todaySelfRobRank;
			this.hasReward = hasReward;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		yesterdayRanks = new com.hifun.soul.gameserver.escort.info.RobRankInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.escort.info.RobRankInfo objyesterdayRanks = new com.hifun.soul.gameserver.escort.info.RobRankInfo();
			yesterdayRanks[i] = objyesterdayRanks;
					objyesterdayRanks.setRank(readInteger());
							objyesterdayRanks.setRobberName(readString());
							objyesterdayRanks.setRobCoin(readInteger());
							objyesterdayRanks.setRewardCoin(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		todayRanks = new com.hifun.soul.gameserver.escort.info.RobRankInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.escort.info.RobRankInfo objtodayRanks = new com.hifun.soul.gameserver.escort.info.RobRankInfo();
			todayRanks[i] = objtodayRanks;
					objtodayRanks.setRank(readInteger());
							objtodayRanks.setRobberName(readString());
							objtodayRanks.setRobCoin(readInteger());
							objtodayRanks.setRewardCoin(readInteger());
				}
		yesterdaySelfRobCoin = readInteger();
		todaySelfRobCoin = readInteger();
		yesterdaySelfRobRank = readInteger();
		todaySelfRobRank = readInteger();
		hasReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(yesterdayRanks.length);
	for(int i=0; i<yesterdayRanks.length; i++){
	com.hifun.soul.gameserver.escort.info.RobRankInfo objyesterdayRanks = yesterdayRanks[i];
				writeInteger(objyesterdayRanks.getRank());
				writeString(objyesterdayRanks.getRobberName());
				writeInteger(objyesterdayRanks.getRobCoin());
				writeInteger(objyesterdayRanks.getRewardCoin());
	}
	writeShort(todayRanks.length);
	for(int i=0; i<todayRanks.length; i++){
	com.hifun.soul.gameserver.escort.info.RobRankInfo objtodayRanks = todayRanks[i];
				writeInteger(objtodayRanks.getRank());
				writeString(objtodayRanks.getRobberName());
				writeInteger(objtodayRanks.getRobCoin());
				writeInteger(objtodayRanks.getRewardCoin());
	}
		writeInteger(yesterdaySelfRobCoin);
		writeInteger(todaySelfRobCoin);
		writeInteger(yesterdaySelfRobRank);
		writeInteger(todaySelfRobRank);
		writeBoolean(hasReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ROB_RANK_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ROB_RANK_TAB";
	}

	public com.hifun.soul.gameserver.escort.info.RobRankInfo[] getYesterdayRanks(){
		return yesterdayRanks;
	}

	public void setYesterdayRanks(com.hifun.soul.gameserver.escort.info.RobRankInfo[] yesterdayRanks){
		this.yesterdayRanks = yesterdayRanks;
	}	

	public com.hifun.soul.gameserver.escort.info.RobRankInfo[] getTodayRanks(){
		return todayRanks;
	}

	public void setTodayRanks(com.hifun.soul.gameserver.escort.info.RobRankInfo[] todayRanks){
		this.todayRanks = todayRanks;
	}	

	public int getYesterdaySelfRobCoin(){
		return yesterdaySelfRobCoin;
	}
		
	public void setYesterdaySelfRobCoin(int yesterdaySelfRobCoin){
		this.yesterdaySelfRobCoin = yesterdaySelfRobCoin;
	}

	public int getTodaySelfRobCoin(){
		return todaySelfRobCoin;
	}
		
	public void setTodaySelfRobCoin(int todaySelfRobCoin){
		this.todaySelfRobCoin = todaySelfRobCoin;
	}

	public int getYesterdaySelfRobRank(){
		return yesterdaySelfRobRank;
	}
		
	public void setYesterdaySelfRobRank(int yesterdaySelfRobRank){
		this.yesterdaySelfRobRank = yesterdaySelfRobRank;
	}

	public int getTodaySelfRobRank(){
		return todaySelfRobRank;
	}
		
	public void setTodaySelfRobRank(int todaySelfRobRank){
		this.todaySelfRobRank = todaySelfRobRank;
	}

	public boolean getHasReward(){
		return hasReward;
	}
		
	public void setHasReward(boolean hasReward){
		this.hasReward = hasReward;
	}
}