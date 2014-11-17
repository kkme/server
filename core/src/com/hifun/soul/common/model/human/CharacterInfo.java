package com.hifun.soul.common.model.human;

/**
 * 
 * 角色整体信息，下发到客户端的时候用
 * 
 * @author magicstone
 * 
 */
public class CharacterInfo {
	private long guid;
	/** 角色等级 */
	private int level;
	/** 角色名称 */
	private String name;
	/** 角色职业 */
	private int occupation;
	/** 军团ID */
	private long legionId;
	/** 军团名称 */
	private String legionName;
	/** 军衔名称 */
	private String titleName;
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public long getGuid() {
		return guid;
	}

	public void setGuid(long guid) {
		this.guid = guid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

}
