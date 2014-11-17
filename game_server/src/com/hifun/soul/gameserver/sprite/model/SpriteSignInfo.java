package com.hifun.soul.gameserver.sprite.model;

/**
 * 星座信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteSignInfo {
	/** 星图id */
	private int starMapId;
	/** 星座id */
	private int signId;
	/** 星座名称 */
	private String name;
	/** 点亮消耗的星魂数量 */
	private int costStarSoul;
	/** 属性id */
	private int propId;
	/** 属性值 */
	private int propValue;
	/** 加成类型 */
	private int amendType;
	/** 是否已经点亮 */
	private boolean light;
	/** x坐标 */
	private int x;
	/** y坐标 */
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStarMapId() {
		return starMapId;
	}

	public void setStarMapId(int starMapId) {
		this.starMapId = starMapId;
	}

	public int getSignId() {
		return signId;
	}

	public void setSignId(int signId) {
		this.signId = signId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCostStarSoul() {
		return costStarSoul;
	}

	public void setCostStarSoul(int costStarSoul) {
		this.costStarSoul = costStarSoul;
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

	public boolean getLight() {
		return light;
	}

	public void setLight(boolean light) {
		this.light = light;
	}

}
