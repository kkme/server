package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.gamedb.annotation.NotBlobHumanSubEntity;

@Entity
@Table(name = "t_match_battle_role")
@AutoCreateHumanEntityHolder(EntityHolderClass = "ExclusiveEntityHolder")
@NotBlobHumanSubEntity
public class HumanMatchBattleEntity extends BaseCommonEntity implements
IHumanSubEntity{
	
	@Id
	@Column
	private long id;
	/** 角色名称 */
	@Column
	private String roleName;
	/** 连胜次数 */
	@Column
	private int consecutiveWinCount;
	/** 最大连胜次数 */
	@Column
	private int maxConsecutiveWinCount;
	/** 胜利场数 */
	@Column
	private int winCount;
	/** 失败场数 */
	@Column
	private int loseCount;
	/** 鼓舞加成 */
	@Column
	private int encourageRate;
	/** 累计获得荣誉 */
	@Column
	private int honourReward;
	/** 累计获得金币 */
	@Column
	private int coinReward;
	/** 上场战斗是否取得胜利 */
	@Column
	private boolean isWinInLastBattle;
	/** 连胜排行 */
	@Column
	private int streakWinRank;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getConsecutiveWinCount() {
		return consecutiveWinCount;
	}
	public void setConsecutiveWinCount(int consecutiveWinCount) {
		this.consecutiveWinCount = consecutiveWinCount;
	}
	public int getMaxConsecutiveWinCount() {
		return maxConsecutiveWinCount;
	}
	public void setMaxConsecutiveWinCount(int maxConsecutiveWinCount) {
		this.maxConsecutiveWinCount = maxConsecutiveWinCount;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	public int getLoseCount() {
		return loseCount;
	}
	public void setLoseCount(int loseCount) {
		this.loseCount = loseCount;
	}
	public int getEncourageRate() {
		return encourageRate;
	}
	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}
	public int getHonourReward() {
		return honourReward;
	}
	public void setHonourReward(int honourReward) {
		this.honourReward = honourReward;
	}
	public int getCoinReward() {
		return coinReward;
	}
	public void setCoinReward(int coinReward) {
		this.coinReward = coinReward;
	}
	public boolean isWinInLastBattle() {
		return isWinInLastBattle;
	}
	public void setWinInLastBattle(boolean isWinInLastBattle) {
		this.isWinInLastBattle = isWinInLastBattle;
	}	
	public int getStreakWinRank() {
		return streakWinRank;
	}
	public void setStreakWinRank(int streakWinRank) {
		this.streakWinRank = streakWinRank;
	}
	@Override
	public void setId(Serializable id) {
		this.id = (Long)id;		
	}
	@Override
	public Serializable getId() {
		return id;
	}
	@Override
	public long getHumanGuid() {
		return id;
	}
}
