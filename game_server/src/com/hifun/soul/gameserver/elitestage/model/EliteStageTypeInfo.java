package com.hifun.soul.gameserver.elitestage.model;

/**
 * 精英副本类型信息
 * 
 * @author magicstone
 * 
 */
public class EliteStageTypeInfo {
	private int stageType;
	private String typeName;
	private int openLevel;

	public int getStageType() {
		return stageType;
	}

	public void setStageType(int stageType) {
		this.stageType = stageType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getOpenLevel() {
		return openLevel;
	}

	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}
}
