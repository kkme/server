package com.hifun.soul.gameserver.horoscope.msg;


/**
 * 
 * 星运的信息
 * 
 * @author magicstone
 *
 */
public class HoroscopeInfo {

	// 星运uuid
	private String uuid;
	// 星运所在包
	private int horoscopeBag;
	// 星运所在包中的位置
	private int index;
	// 星运id
	private int horoscopeId;
	// 星运名字
	private String name;
	// 星运说明
	private String desc;
	// 颜色
	private int color;
	// 星运等级
	private int level;
	// 当前经验
	private int experience;
	// 升级需要的经验
	private int maxExperience;
	// 星运效果
	private int key;
	// 效果值
	private int value;
	// 图标
	private int icon;
	// 下一级星运id
	private int nextHoroscopeId;
	// 属性加成方式
	private int propertyAddType;
	
	public int getHoroscopeBag() {
		return horoscopeBag;
	}
	public void setHoroscopeBag(int horoscopeBag) {
		this.horoscopeBag = horoscopeBag;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getHoroscopeId() {
		return horoscopeId;
	}
	public void setHoroscopeId(int horoscopeId) {
		this.horoscopeId = horoscopeId;
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
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getMaxExperience() {
		return maxExperience;
	}
	public void setMaxExperience(int maxExperience) {
		this.maxExperience = maxExperience;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getNextHoroscopeId() {
		return nextHoroscopeId;
	}
	public void setNextHoroscopeId(int nextHoroscopeId) {
		this.nextHoroscopeId = nextHoroscopeId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getPropertyAddType() {
		return propertyAddType;
	}
	public void setPropertyAddType(int propertyAddType) {
		this.propertyAddType = propertyAddType;
	}
	
}
