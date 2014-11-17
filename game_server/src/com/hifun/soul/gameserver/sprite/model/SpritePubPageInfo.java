package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵对酒頁簽信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpritePubPageInfo {
	/** 页签id */
	private int pageId;
	/** 页签开启等级 */
	private int pageOpenLevel;
	/** 普通对酒消耗货币类型 */
	private int commonCostType;
	/** 普通对酒消耗货币数量 */
	private int commonCostNum;
	/** 高级对酒消耗货币类型 */
	private int superCostType;
	/** 高级对酒消耗货币数量 */
	private int superCostNum;
	/** 功能开启等级 */
	private int superFingerGuessOpenLevel;
	/** 功能开启vip等级 */
	private int superFingerGuessVipOpenLevel;

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getPageOpenLevel() {
		return pageOpenLevel;
	}

	public void setPageOpenLevel(int pageOpenLevel) {
		this.pageOpenLevel = pageOpenLevel;
	}

	public int getCommonCostType() {
		return commonCostType;
	}

	public void setCommonCostType(int commonCostType) {
		this.commonCostType = commonCostType;
	}

	public int getCommonCostNum() {
		return commonCostNum;
	}

	public void setCommonCostNum(int commonCostNum) {
		this.commonCostNum = commonCostNum;
	}

	public int getSuperCostType() {
		return superCostType;
	}

	public void setSuperCostType(int superCostType) {
		this.superCostType = superCostType;
	}

	public int getSuperCostNum() {
		return superCostNum;
	}

	public void setSuperCostNum(int superCostNum) {
		this.superCostNum = superCostNum;
	}

	public int getSuperFingerGuessOpenLevel() {
		return superFingerGuessOpenLevel;
	}

	public void setSuperFingerGuessOpenLevel(int superFingerGuessOpenLevel) {
		this.superFingerGuessOpenLevel = superFingerGuessOpenLevel;
	}

	public int getSuperFingerGuessVipOpenLevel() {
		return superFingerGuessVipOpenLevel;
	}

	public void setSuperFingerGuessVipOpenLevel(int superFingerGuessVipOpenLevel) {
		this.superFingerGuessVipOpenLevel = superFingerGuessVipOpenLevel;
	}

}
