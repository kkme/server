package com.hifun.soul.gameserver.refine;

public class RefinePanelInfo {
	/** 免费次数 */
	private int freeTimes;
	/** 攻打关卡的最高纪录 */
	private int bestStageId;
	/** 自动攻打单轮消耗魔晶数量 */
	private int autoBattleCrystal;
	/** 下次刷新副本消耗魔晶数量 */
	private int refreshCrystal;
	/** 一键扫荡消耗的数量 */
	private int attackAllCrystal;
	/** 魔晶刷新次数 */
	private int crystalTimes;
	/** 当前试炼关卡信息 */
	private com.hifun.soul.gameserver.refine.RefineStageInfo refineStageInfo;
	
	public int getFreeTimes() {
		return freeTimes;
	}
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}
	public int getBestStageId() {
		return bestStageId;
	}
	public void setBestStageId(int bestStageId) {
		this.bestStageId = bestStageId;
	}
	public int getAutoBattleCrystal() {
		return autoBattleCrystal;
	}
	public void setAutoBattleCrystal(int autoBattleCrystal) {
		this.autoBattleCrystal = autoBattleCrystal;
	}
	public int getRefreshCrystal() {
		return refreshCrystal;
	}
	public void setRefreshCrystal(int refreshCrystal) {
		this.refreshCrystal = refreshCrystal;
	}
	public int getAttackAllCrystal() {
		return attackAllCrystal;
	}
	public void setAttackAllCrystal(int attackAllCrystal) {
		this.attackAllCrystal = attackAllCrystal;
	}
	public int getCrystalTimes() {
		return crystalTimes;
	}
	public void setCrystalTimes(int crystalTimes) {
		this.crystalTimes = crystalTimes;
	}
	public com.hifun.soul.gameserver.refine.RefineStageInfo getRefineStageInfo() {
		return refineStageInfo;
	}
	public void setRefineStageInfo(
			com.hifun.soul.gameserver.refine.RefineStageInfo refineStageInfo) {
		this.refineStageInfo = refineStageInfo;
	}
}
