package com.hifun.soul.gameserver.mars.msg.info;

/**
 * 战神之巅场别信息
 * 
 * @author yandajun
 * 
 */
public class MarsFieldInfo {
	private int fieldType;
	private String fieldName;
	private int startLevel;
	private int endLevel;

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public int getEndLevel() {
		return endLevel;
	}

	public void setEndLevel(int endLevel) {
		this.endLevel = endLevel;
	}
}
