package com.hifun.soul.gameserver.title.msg;

import java.util.List;

import com.hifun.soul.gameserver.title.HumanTitle;

/**
 * 角色对应军衔信息
 * 
 * @author yandajun
 * 
 */
public class HumanTitleInfo {
	/** 当前军衔id */
	private int currentTitleId;
	/** 当前声望 */
	private int currentHonor;
	/** 军衔列表 */
	private List<HumanTitle> titleList;
	/** 当日俸禄是否领取 */
	private boolean isGetTitleSalay;

	public int getCurrentTitleId() {
		return currentTitleId;
	}

	public void setCurrentTitleId(int currentTitleId) {
		this.currentTitleId = currentTitleId;
	}

	public int getCurrentHonor() {
		return currentHonor;
	}

	public void setCurrentHonor(int currentHonor) {
		this.currentHonor = currentHonor;
	}

	public List<HumanTitle> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<HumanTitle> titleList) {
		this.titleList = titleList;
	}

	public boolean isGetTitleSalay() {
		return isGetTitleSalay;
	}

	public void setGetTitleSalay(boolean isGetTitleSalay) {
		this.isGetTitleSalay = isGetTitleSalay;
	}

}
