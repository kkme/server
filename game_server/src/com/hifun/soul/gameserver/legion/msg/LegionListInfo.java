package com.hifun.soul.gameserver.legion.msg;

/**
 * 军团列表信息(发送给客户端)
 * 
 * @author yandajun
 * 
 */
public class LegionListInfo {
	private long legionId;
	private String legionName;
	private String commanderName;
	private int legionLevel;
	private int memberLimit;
	private int memberNum;
	/** 申请按钮状态 */
	private int applyButtonStatus;

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

	public String getCommanderName() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}

	public int getLegionLevel() {
		return legionLevel;
	}

	public void setLegionLevel(int legionLevel) {
		this.legionLevel = legionLevel;
	}

	public int getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getApplyButtonStatus() {
		return applyButtonStatus;
	}

	public void setApplyButtonStatus(int applyButtonStatus) {
		this.applyButtonStatus = applyButtonStatus;
	}

}
