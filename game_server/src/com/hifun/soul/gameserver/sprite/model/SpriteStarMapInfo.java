package com.hifun.soul.gameserver.sprite.model;

/**
 * 星图信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteStarMapInfo {
	/** 星图id */
	private int starMapId;
	/** 星图名称 */
	private String name;
	/** 开启星图需要的玩家等级 */
	private int openLevel;
	/** 星图是否激活 */
	private boolean activated;
	/** 下一张星图id */
	private int nextStarMapId;

	public int getStarMapId() {
		return starMapId;
	}

	public void setStarMapId(int starMapId) {
		this.starMapId = starMapId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public int getOpenLevel() {
		return openLevel;
	}

	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}

	public int getNextStarMapId() {
		return nextStarMapId;
	}

	public void setNextStarMapId(int nextStarMapId) {
		this.nextStarMapId = nextStarMapId;
	}

}
