package com.hifun.soul.gameserver.legion.msg;

/**
 * 军团申请列表消息信息
 * 
 * @author yandajun
 */
public class LegionApplyListInfo {
	private long applyHumanId;
	private String applyHumanName;
	private int applyHumanLevel;
	private int applyArenaRank;
	/** 审核按钮是否可见 */
	private boolean checkButtionVisible;

	public long getApplyHumanId() {
		return applyHumanId;
	}

	public void setApplyHumanId(long applyHumanId) {
		this.applyHumanId = applyHumanId;
	}

	public String getApplyHumanName() {
		return applyHumanName;
	}

	public void setApplyHumanName(String applyHumanName) {
		this.applyHumanName = applyHumanName;
	}

	public int getApplyHumanLevel() {
		return applyHumanLevel;
	}

	public void setApplyHumanLevel(int applyHumanLevel) {
		this.applyHumanLevel = applyHumanLevel;
	}

	public int getApplyArenaRank() {
		return applyArenaRank;
	}

	public void setApplyArenaRank(int applyArenaRank) {
		this.applyArenaRank = applyArenaRank;
	}

	public boolean getCheckButtionVisible() {
		return checkButtionVisible;
	}

	public void setCheckButtionVisible(boolean checkButtionVisible) {
		this.checkButtionVisible = checkButtionVisible;
	}

}
