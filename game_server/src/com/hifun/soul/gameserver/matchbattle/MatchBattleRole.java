package com.hifun.soul.gameserver.matchbattle;

import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gamedb.entity.HumanMatchBattleEntity;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.internal.AutoJoinNextMatchScheduleMessage;
import com.hifun.soul.gameserver.player.state.PlayerState;

public class MatchBattleRole {
	private Human human;
	/** 状态 */
	private MatchBattleRoleState battleState = MatchBattleRoleState.NOT_JOIN;
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
	/** 奖励荣誉累计 */
	private int honourReward;
	/** 奖励金币累计 */
	private int coinReward;
	/** 是否自动参战 */
	private boolean isAutoJoinBattle = false;
	/** 上一场战斗是否取得胜利 */
	private boolean isWinInLastBattle = false;
	/** 是否需要更新 */
	private boolean needUpdate = false;
	/** 连胜排行 */
	private int streakWinRank;
	/** 是否还在战斗场景中（以客户端完全退出战斗返回到匹配战场景为准） */
	private boolean isInBattleScene = false;
	/** 自动匹配下一场战斗的调度消息 */
	private AutoJoinNextMatchScheduleMessage autoJoinNextMatchScheduleMessage;
	/** 取得最大连胜的时间戳 */
	private long maxConsecutiveWinTimeStamp=0;
	
	public MatchBattleRole(Human human){
		this.human = human;
	}
	
	public void setHuman(Human human){
		this.human = human;
	}
	public MatchBattleRole(Human human, HumanMatchBattleEntity entity) {
		this.human = human;
		coinReward = entity.getCoinReward();
		consecutiveWinCount = entity.getConsecutiveWinCount();
		encourageRate = entity.getEncourageRate();
		honourReward = entity.getHonourReward();
		loseCount = entity.getLoseCount();
		maxConsecutiveWinCount = entity.getMaxConsecutiveWinCount();
		winCount = entity.getWinCount();
		isWinInLastBattle = entity.isWinInLastBattle();
		streakWinRank = entity.getStreakWinRank();
	}

	public Human getHuman(){
		return this.human;
	}
	
	public long getRoleId() {
		return human.getHumanGuid();
	}
	
	public String getRoleName() {
		return human.getName();
	}
	
	public MatchBattleRoleState getBattleState() {
		return battleState;
	}
	public void setBattleState(MatchBattleRoleState battleState) {
		this.battleState = battleState;
	}
	
	public int getConsecutiveWinCount() {
		return consecutiveWinCount;
	}

	public int getMaxConsecutiveWinCount() {
		return maxConsecutiveWinCount;
	}

	public int getWinCount() {
		return winCount;
	}

	public int getLoseCount() {
		return loseCount;
	}


	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
		needUpdate = true;
	}

	public int getHonourReward() {
		return honourReward;
	}
	
	public void addTotalRewardHonour(int honourNum){
		honourReward+=honourNum;
		this.human.addArenaHonor(honourNum, true, HonourLogReason.MATCH_BATTLE_REWARD_HONOUR, "");
		needUpdate = true;
	}
	
	public int getCoinReward() {
		return coinReward;
	}
	
	public void addTotalRewardCoin(int coinNum){
		coinReward+=coinNum;
		this.human.getWallet().addMoney(CurrencyType.COIN, coinNum, true, MoneyLogReason.MATCH_BATTLE_REWARD, "");
		needUpdate = true;
	}

	public boolean isAutoJoinBattle() {
		return isAutoJoinBattle;
	}

	public void setAutoJoinBattle(boolean isAutoJoinBattle) {
		this.isAutoJoinBattle = isAutoJoinBattle;
	}
	
	public int getStreakWinRank() {
		return streakWinRank;
	}

	public void setStreakWinRank(int streakWinRank) {
		this.streakWinRank = streakWinRank;
		needUpdate = true;
	}

	public int getLevel(){
		return human.getLevel();
	}
		
	public boolean isInBattleScene() {
		return isInBattleScene;
	}

	public void setInBattleScene(boolean isInBattleScene) {
		this.isInBattleScene = isInBattleScene;
		if(!this.isInBattleScene){
			if(isAutoJoinBattle&&canJoinNextBattle()){
				battleState = MatchBattleRoleState.WAIT_MATCH;
			}else{
				battleState = MatchBattleRoleState.READY;
			}
		}
	}

	public AutoJoinNextMatchScheduleMessage getAutoJoinNextMatchScheduleMessage() {
		return autoJoinNextMatchScheduleMessage;
	}

	public void setAutoJoinNextMatchScheduleMessage(AutoJoinNextMatchScheduleMessage autoJoinNextMatchScheduleMessage) {
		this.autoJoinNextMatchScheduleMessage = autoJoinNextMatchScheduleMessage;
	}
	
	public long getMaxConsecutiveWinTimeStamp() {
		return maxConsecutiveWinTimeStamp;
	}

	public void setMaxConsecutiveWinTimeStamp(long maxConsecutiveWinTimeStamp) {
		this.maxConsecutiveWinTimeStamp = maxConsecutiveWinTimeStamp;
	}

	public MatchBattleRoleInfo convertToMatchBattleRoleInfo(){
		MatchBattleRoleInfo roleInfo = new MatchBattleRoleInfo();
		roleInfo.setRoleId(this.getRoleId());
		roleInfo.setRoleName(this.getRoleName());
		roleInfo.setBattleState(this.getBattleState().getIndex());
		return roleInfo;
	}
	
	public MatchBattleRankRoleInfo converToRankRole(){
		MatchBattleRankRoleInfo rankInfo = new MatchBattleRankRoleInfo();
		rankInfo.setRoleId(this.getRoleId());
		rankInfo.setConsecutiveWinCount(this.getMaxConsecutiveWinCount());
		rankInfo.setLevel(this.getLevel());
		rankInfo.setOccupation(this.human.getOccupation().getIndex());
		rankInfo.setRoleName(this.getRoleName());
		return rankInfo;
	}
	
	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	/**
	 * 战斗胜利的处理
	 */
	public void onBattleWin(){
		winCount++;
		if(isWinInLastBattle){
			consecutiveWinCount++;
		}else{
			consecutiveWinCount=1;
		}
		if(maxConsecutiveWinCount<consecutiveWinCount){
			maxConsecutiveWinCount = consecutiveWinCount;
			maxConsecutiveWinTimeStamp = GameServerAssist.getSystemTimeService().now();
		}
		isWinInLastBattle = true;		
		battleState = MatchBattleRoleState.READY;
//		if(isAutoJoinBattle&&canJoinNextBattle()){
//			battleState = MatchBattleRoleState.WAIT_MATCH;
//		}
		needUpdate = true;
	}
	
	/**
	 * 战斗失败的处理
	 */
	public void onBattleLose(){
		isWinInLastBattle = false;
		loseCount++;
		consecutiveWinCount = 0;		
		battleState = MatchBattleRoleState.READY;
//		if(isAutoJoinBattle&&canJoinNextBattle()){
//			battleState = MatchBattleRoleState.WAIT_MATCH;
//		}
		needUpdate = true;
	}
	

	/**
	 * 重置
	 */
	public void resetData(){
		this.consecutiveWinCount = 0;
		this.maxConsecutiveWinCount = 0;
		this.winCount = 0;
		this.loseCount = 0;
		this.encourageRate = 0;		
		this.honourReward = 0;
		this.coinReward = 0;
		this.isAutoJoinBattle = false;
		this.maxConsecutiveWinTimeStamp = 0;
		battleState = MatchBattleRoleState.NOT_JOIN;
		needUpdate = true;
		isInBattleScene = false;
	}
	
	/**
	 * 是否能进入下一轮自动匹配
	 * @return
	 */
	protected boolean canJoinNextBattle(){		
		// 如果角色在线, 而且正在战斗中, 则匹配失败;
		if (human.getPlayer().getState() == PlayerState.BATTLING) {
			return false;
		}
		// 如果玩家在扫荡中, 不能进入准备状态
		if (human.getPlayer().getState() == PlayerState.AUTOBATTLEING) {	
			return false;
		}
		// 如果玩家还没有完全退出战斗场景，不能进入准备状态
		if(isInBattleScene){
			return false;
		}
		return human.getHumanCdManager().canAddCd(CdType.MATCH_BATTLE_CD, 1000);		
	}
	

	public HumanMatchBattleEntity toEntity() {
		HumanMatchBattleEntity entity = new HumanMatchBattleEntity();
		entity.setCoinReward(coinReward);
		entity.setConsecutiveWinCount(consecutiveWinCount);
		entity.setEncourageRate(encourageRate);
		entity.setHonourReward(honourReward);
		entity.setId(human.getHumanGuid());
		entity.setLoseCount(loseCount);
		entity.setMaxConsecutiveWinCount(maxConsecutiveWinCount);
		entity.setRoleName(human.getName());
		entity.setWinCount(winCount);
		entity.setWinInLastBattle(isWinInLastBattle);
		entity.setStreakWinRank(streakWinRank);
		return entity;
	}
}
