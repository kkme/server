package com.hifun.soul.gameserver.stage.model;
/**
 * 地图据点信息;
 * @author crazyjohn
 *
 */
public class MapStrongHoldInfo {
	/** 据点id */
	private int strongholdId;
	/** 据点背景id */
	private int bgId;
	/** 据点资源id */
	private int resourceId;
	/** 据点x坐标 */
	private int x;
	/** 据点y坐标 */
	private int y;
	/** 据点名称 */
	private String name;
	/** 据点剧情描述 */
	private String desc;
	/** 据点是否开启 */
	private boolean isOpen;
	
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
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
	
	public int getStrongholdId() {
		return strongholdId;
	}
	public void setStrongholdId(int strongholdId) {
		this.strongholdId = strongholdId;
	}
	public int getBgId() {
		return bgId;
	}
	public void setBgId(int bgId) {
		this.bgId = bgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
