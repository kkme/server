package com.hifun.soul.common.model.array;

/**
 * 阵形详细信息
 * @author SevenSoul
 *
 */
public class DetailArrayInfo {

	/** 阵形id */
	private int id;
	/** 阵形名称 */
	private String name;
	/** 等级 */
	private int level;
	/** 描述 */
	private String desc;
	/** 是否为默认阵形 */
	private int defaultArray;
	/** 九宫格每一位置是否开启的标记 */
	private int[] indexOpenFlag;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getDefaultArray() {
		return defaultArray;
	}

	public void setDefaultArray(int defaultArray) {
		this.defaultArray = defaultArray;
	}
	
	public int[] getIndexOpenFlag() {
		return indexOpenFlag;
	}
	
	public void setIndexOpenFlag(int[] indexOpenFlag) {
		this.indexOpenFlag = indexOpenFlag;
	}
}
