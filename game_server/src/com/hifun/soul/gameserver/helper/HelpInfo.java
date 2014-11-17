package com.hifun.soul.gameserver.helper;

public class HelpInfo {

	/** 类型 */
	private int helpType;
	/** 状态 */
	private int state;
	/** 剩余可用次数 */
	private int usedTimes;
	/** 总次数 */
	private int totalTimes;
	
	public int getHelpType() {
		return helpType;
	}
	public void setHelpType(int helpType) {
		this.helpType = helpType;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getUsedTimes() {
		return usedTimes;
	}
	public void setUsedTimes(int usedTimes) {
		this.usedTimes = usedTimes;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	
}
