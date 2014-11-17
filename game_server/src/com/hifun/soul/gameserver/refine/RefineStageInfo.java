package com.hifun.soul.gameserver.refine;

import com.hifun.soul.gameserver.item.assist.CommonItem;

/**
 * 试炼关卡信息
 * @author magicstone
 */
public class RefineStageInfo {
	private int mapId;
	private String mapName;
	private int stageId;
	private int monsterId;
	private String monsterName;
	private int monsterLevel;
	private int monsterIcon;
	private CommonItem commonItem;
	private int rewardNum;
	/** 攻打状态(不能攻打、可以攻打、已经攻打过) */
	private int attackState;
	/** 攻打奖励是否领取过 */
	private boolean getted;
	/** 奖励灵气 */
	private int rewardAnima;
	
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}
	public String getMonsterName() {
		return monsterName;
	}
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	public int getMonsterLevel() {
		return monsterLevel;
	}
	public void setMonsterLevel(int monsterLevel) {
		this.monsterLevel = monsterLevel;
	}
	public int getMonsterIcon() {
		return monsterIcon;
	}
	public void setMonsterIcon(int monsterIcon) {
		this.monsterIcon = monsterIcon;
	}
	public CommonItem getCommonItem() {
		return commonItem;
	}
	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}
	public int getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	public int getAttackState() {
		return attackState;
	}
	public void setAttackState(int attackState) {
		this.attackState = attackState;
	}
	public boolean getGetted() {
		return getted;
	}
	public void setGetted(boolean getted) {
		this.getted = getted;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public int getRewardAnima() {
		return rewardAnima;
	}
	public void setRewardAnima(int rewardAnima) {
		this.rewardAnima = rewardAnima;
	}
	
}
