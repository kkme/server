package com.hifun.soul.gameserver.abattoir.msg;

/**
 * 角斗场个人信息
 * 
 * @author yandajun
 * 
 */
public class WrestlerInfo {
	private int titleId;
	private String titleName;
	private int currentPrestige;
	private int maxPrestige;
	private int roomId;
	private String roomName;
	private int remainWrestleNum;
	private int remainTime;
	private int revenue;
	private int totalRevenue;
	private int nextBuyNumCost;
	private int roomPageIndex;

	public WrestlerInfo() {
		titleName = "";
		roomName = "";
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public int getCurrentPrestige() {
		return currentPrestige;
	}

	public void setCurrentPrestige(int currentPrestige) {
		this.currentPrestige = currentPrestige;
	}

	public int getMaxPrestige() {
		return maxPrestige;
	}

	public void setMaxPrestige(int maxPrestige) {
		this.maxPrestige = maxPrestige;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRemainWrestleNum() {
		return remainWrestleNum;
	}

	public void setRemainWrestleNum(int remainWrestleNum) {
		this.remainWrestleNum = remainWrestleNum;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getNextBuyNumCost() {
		return nextBuyNumCost;
	}

	public void setNextBuyNumCost(int nextBuyNumCost) {
		this.nextBuyNumCost = nextBuyNumCost;
	}

	public int getRoomPageIndex() {
		return roomPageIndex;
	}

	public void setRoomPageIndex(int roomPageIndex) {
		this.roomPageIndex = roomPageIndex;
	}

}
