package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 进入匹配战场景
 *
 * @author SevenSoul
 */
@Component
public class GCJoinMatchBattleScene extends GCMessage{
	
	/** 参战角色信息 */
	private com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] roleInfo;
	/** 连胜榜单 */
	private com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo;
	/** 活动剩余时间(单位秒) */
	private int remainTime;
	/** 连胜次数 */
	private int consecutiveWinCount;
	/** 最大连胜次数 */
	private int maxConsecutiveWinCount;
	/** 胜利次数 */
	private int winCount;
	/** 失败次数 */
	private int loseCount;
	/** 鼓舞百分比 */
	private int encourageRate;
	/** 金币鼓舞消耗的金币 */
	private int encourageCoinCost;
	/** 魔晶鼓舞消耗的魔晶 */
	private int encourageCrystalCost;
	/** 灵石鼓舞消耗的灵石 */
	private int encourageForgeStoneCost;
	/** 奖励荣誉累计 */
	private int honourReward;
	/** 奖励金币累计 */
	private int coinReward;
	/** 匹配战准备CD时间 */
	private int waitCDTime;

	public GCJoinMatchBattleScene (){
	}
	
	public GCJoinMatchBattleScene (
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] roleInfo,
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo,
			int remainTime,
			int consecutiveWinCount,
			int maxConsecutiveWinCount,
			int winCount,
			int loseCount,
			int encourageRate,
			int encourageCoinCost,
			int encourageCrystalCost,
			int encourageForgeStoneCost,
			int honourReward,
			int coinReward,
			int waitCDTime ){
			this.roleInfo = roleInfo;
			this.RankInfo = RankInfo;
			this.remainTime = remainTime;
			this.consecutiveWinCount = consecutiveWinCount;
			this.maxConsecutiveWinCount = maxConsecutiveWinCount;
			this.winCount = winCount;
			this.loseCount = loseCount;
			this.encourageRate = encourageRate;
			this.encourageCoinCost = encourageCoinCost;
			this.encourageCrystalCost = encourageCrystalCost;
			this.encourageForgeStoneCost = encourageForgeStoneCost;
			this.honourReward = honourReward;
			this.coinReward = coinReward;
			this.waitCDTime = waitCDTime;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		roleInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objroleInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo();
			roleInfo[i] = objroleInfo;
					objroleInfo.setRoleId(readLong());
							objroleInfo.setRoleName(readString());
							objroleInfo.setBattleState(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		RankInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo objRankInfo = new com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo();
			RankInfo[i] = objRankInfo;
					objRankInfo.setRoleName(readString());
							objRankInfo.setConsecutiveWinCount(readInteger());
							objRankInfo.setRoleId(readLong());
							objRankInfo.setRoleName(readString());
							objRankInfo.setOccupation(readInteger());
							objRankInfo.setLevel(readInteger());
							objRankInfo.setConsecutiveWinCount(readInteger());
				}
		remainTime = readInteger();
		consecutiveWinCount = readInteger();
		maxConsecutiveWinCount = readInteger();
		winCount = readInteger();
		loseCount = readInteger();
		encourageRate = readInteger();
		encourageCoinCost = readInteger();
		encourageCrystalCost = readInteger();
		encourageForgeStoneCost = readInteger();
		honourReward = readInteger();
		coinReward = readInteger();
		waitCDTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(roleInfo.length);
	for(int i=0; i<roleInfo.length; i++){
	com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo objroleInfo = roleInfo[i];
				writeLong(objroleInfo.getRoleId());
				writeString(objroleInfo.getRoleName());
				writeInteger(objroleInfo.getBattleState());
	}
	writeShort(RankInfo.length);
	for(int i=0; i<RankInfo.length; i++){
	com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo objRankInfo = RankInfo[i];
				writeString(objRankInfo.getRoleName());
				writeInteger(objRankInfo.getConsecutiveWinCount());
				writeLong(objRankInfo.getRoleId());
				writeString(objRankInfo.getRoleName());
				writeInteger(objRankInfo.getOccupation());
				writeInteger(objRankInfo.getLevel());
				writeInteger(objRankInfo.getConsecutiveWinCount());
	}
		writeInteger(remainTime);
		writeInteger(consecutiveWinCount);
		writeInteger(maxConsecutiveWinCount);
		writeInteger(winCount);
		writeInteger(loseCount);
		writeInteger(encourageRate);
		writeInteger(encourageCoinCost);
		writeInteger(encourageCrystalCost);
		writeInteger(encourageForgeStoneCost);
		writeInteger(honourReward);
		writeInteger(coinReward);
		writeInteger(waitCDTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_MATCH_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_MATCH_BATTLE_SCENE";
	}

	public com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] getRoleInfo(){
		return roleInfo;
	}

	public void setRoleInfo(com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo[] roleInfo){
		this.roleInfo = roleInfo;
	}	

	public com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] getRankInfo(){
		return RankInfo;
	}

	public void setRankInfo(com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo[] RankInfo){
		this.RankInfo = RankInfo;
	}	

	public int getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(int remainTime){
		this.remainTime = remainTime;
	}

	public int getConsecutiveWinCount(){
		return consecutiveWinCount;
	}
		
	public void setConsecutiveWinCount(int consecutiveWinCount){
		this.consecutiveWinCount = consecutiveWinCount;
	}

	public int getMaxConsecutiveWinCount(){
		return maxConsecutiveWinCount;
	}
		
	public void setMaxConsecutiveWinCount(int maxConsecutiveWinCount){
		this.maxConsecutiveWinCount = maxConsecutiveWinCount;
	}

	public int getWinCount(){
		return winCount;
	}
		
	public void setWinCount(int winCount){
		this.winCount = winCount;
	}

	public int getLoseCount(){
		return loseCount;
	}
		
	public void setLoseCount(int loseCount){
		this.loseCount = loseCount;
	}

	public int getEncourageRate(){
		return encourageRate;
	}
		
	public void setEncourageRate(int encourageRate){
		this.encourageRate = encourageRate;
	}

	public int getEncourageCoinCost(){
		return encourageCoinCost;
	}
		
	public void setEncourageCoinCost(int encourageCoinCost){
		this.encourageCoinCost = encourageCoinCost;
	}

	public int getEncourageCrystalCost(){
		return encourageCrystalCost;
	}
		
	public void setEncourageCrystalCost(int encourageCrystalCost){
		this.encourageCrystalCost = encourageCrystalCost;
	}

	public int getEncourageForgeStoneCost(){
		return encourageForgeStoneCost;
	}
		
	public void setEncourageForgeStoneCost(int encourageForgeStoneCost){
		this.encourageForgeStoneCost = encourageForgeStoneCost;
	}

	public int getHonourReward(){
		return honourReward;
	}
		
	public void setHonourReward(int honourReward){
		this.honourReward = honourReward;
	}

	public int getCoinReward(){
		return coinReward;
	}
		
	public void setCoinReward(int coinReward){
		this.coinReward = coinReward;
	}

	public int getWaitCDTime(){
		return waitCDTime;
	}
		
	public void setWaitCDTime(int waitCDTime){
		this.waitCDTime = waitCDTime;
	}
}