package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵buff信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteBuffInfo {
	/** buffId */
	private int buffId;
	/** buff名称 */
	private String name;
	/** 属性id */
	private int propId;
	/** 属性值 */
	private int propValue;
	/** 属性加成类型 */
	private int amendType;
	/** 激活buff需要的套装品质类型 */
	private int activateQuality;
	/** 是否激活 */
	private boolean activated;

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getPropValue() {
		return propValue;
	}

	public void setPropValue(int propValue) {
		this.propValue = propValue;
	}

	public int getAmendType() {
		return amendType;
	}

	public void setAmendType(int amendType) {
		this.amendType = amendType;
	}

	public int getActivateQuality() {
		return activateQuality;
	}

	public void setActivateQuality(int activateQuality) {
		this.activateQuality = activateQuality;
	}

	public boolean getActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
