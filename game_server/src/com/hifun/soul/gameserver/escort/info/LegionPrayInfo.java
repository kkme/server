package com.hifun.soul.gameserver.escort.info;

/**
 * 军团祈福信息
 * 
 * @author yandajun
 * 
 */
public class LegionPrayInfo {
	private long legionId;
	private long prayMemberId;
	private String prayMemberName;
	private int prayRemainTime;
	private long prayEndTime;
	private int prayRevenue;

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public long getPrayMemberId() {
		return prayMemberId;
	}

	public void setPrayMemberId(long prayMemberId) {
		this.prayMemberId = prayMemberId;
	}

	public String getPrayMemberName() {
		return prayMemberName;
	}

	public void setPrayMemberName(String prayMemberName) {
		this.prayMemberName = prayMemberName;
	}

	public int getPrayRemainTime() {
		return prayRemainTime;
	}

	public void setPrayRemainTime(int prayRemainTime) {
		this.prayRemainTime = prayRemainTime;
	}

	public long getPrayEndTime() {
		return prayEndTime;
	}

	public void setPrayEndTime(long prayEndTime) {
		this.prayEndTime = prayEndTime;
	}

	public int getPrayRevenue() {
		return prayRevenue;
	}

	public void setPrayRevenue(int prayRevenue) {
		this.prayRevenue = prayRevenue;
	}
}
