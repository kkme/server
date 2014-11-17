package com.hifun.soul.gameserver.bloodtemple;

public class BloodTempleRoom {
	private int roomId;
	private String roomName;
	private int pageIndex;
	private int npcLevel;
	private long ownerId;
	private int ownerOccupationType;
	private String ownerName;
	private int ownerLevel;
	private int ownerTitleId;
	private String ownerTitleName;
	private long ownerLegionId;
	private String ownerLegionName;
	private int revenue;
	private long lootTime;
	private int ownerType;
	private boolean fighting;
	private int protectTime;

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		this.fighting = fighting;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getNpcLevel() {
		return npcLevel;
	}

	public void setNpcLevel(int npcLevel) {
		this.npcLevel = npcLevel;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerOccupationType() {
		return ownerOccupationType;
	}

	public void setOwnerOccupationType(int ownerOccupationType) {
		this.ownerOccupationType = ownerOccupationType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOwnerLevel() {
		return ownerLevel;
	}

	public void setOwnerLevel(int ownerLevel) {
		this.ownerLevel = ownerLevel;
	}

	public int getOwnerTitleId() {
		return ownerTitleId;
	}

	public void setOwnerTitleId(int ownerTitleId) {
		this.ownerTitleId = ownerTitleId;
	}

	public String getOwnerTitleName() {
		return ownerTitleName;
	}

	public void setOwnerTitleName(String ownerTitleName) {
		this.ownerTitleName = ownerTitleName;
	}

	public long getOwnerLegionId() {
		return ownerLegionId;
	}

	public void setOwnerLegionId(long ownerLegionId) {
		this.ownerLegionId = ownerLegionId;
	}

	public String getOwnerLegionName() {
		return ownerLegionName;
	}

	public void setOwnerLegionName(String ownerLegionName) {
		this.ownerLegionName = ownerLegionName;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public long getLootTime() {
		return lootTime;
	}

	public void setLootTime(long lootTime) {
		this.lootTime = lootTime;
	}

	public int getProtectTime() {
		return protectTime;
	}

	public void setProtectTime(int protectTime) {
		this.protectTime = protectTime;
	}

}
