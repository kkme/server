package com.hifun.soul.gameserver.building.msg;

/**
 * 建筑包含的功能信息，用于与客户端通信
 * 
 * @author magicstone
 *
 */
public class BuildingFuncInfo {

	private int funcId;
	
	private String name;
	
	private String desc;
	
	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
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
	
}
