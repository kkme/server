package com.hifun.soul.gameserver.arena;

public class ArenaMember {
	/** 排名 */
	private int rank;
	/** 角色id */
	private long roleId;
	/** 角色头像图标 */
	private int icon;
	/** 名称 */
	private String name;
	/** 等级 */
	private int level;
	/** 军团ID */
	private long legionId;
	/** 军团名称 */
	private String legionName;
	/** 排名奖励id */
	private int rankRewardId;
	/** 排名奖励领取情况 */
	private int rankRewardState;
	/** 职业 */
	private int occupation;
	/** 黄钻等级 */
	private int yellowVipLevel;
	/** 是否年费黄钻用户 */
	private boolean isYearYellowVip;
	/** 是否豪华黄钻用户 */
	private boolean isYellowHighVip;
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getRankRewardId() {
		return rankRewardId;
	}
	public void setRankRewardId(int rankRewardId) {
		this.rankRewardId = rankRewardId;
	}
	public int getRankRewardState() {
		return rankRewardState;
	}
	public void setRankRewardState(int rankRewardState) {
		this.rankRewardState = rankRewardState;
	}
	public int getOccupation() {
		return occupation;
	}
	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	public int getYellowVipLevel() {
		return yellowVipLevel;
	}
	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}
	public boolean getIsYearYellowVip() {
		return isYearYellowVip;
	}
	public void setIsYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}
	public boolean getIsYellowHighVip() {
		return isYellowHighVip;
	}
	public void setIsYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}
	public long getLegionId() {
		return legionId;
	}
	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}
	public String getLegionName() {
		return legionName;
	}
	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}
	
}
