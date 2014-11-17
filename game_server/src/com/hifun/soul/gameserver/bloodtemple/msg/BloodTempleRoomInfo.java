package com.hifun.soul.gameserver.bloodtemple.msg;

/**
 * 嗜血神殿房间信息
 * 
 * @author yandajun
 * 
 */
public class BloodTempleRoomInfo implements Comparable<BloodTempleRoomInfo> {
	private int roomId;
	private String roomName;
	private long ownerId;
	private int ownerOccupationType;
	private int ownerType;
	private String ownerName;
	private int ownerLevel;
	private int ownerTitleId;
	private String ownerTitleName;
	private long ownerLegionId;
	private String ownerLegionName;
	private int revenue;
	private int protectTime;

	public BloodTempleRoomInfo() {
		roomName = "";
		ownerName = "";
		ownerTitleName = "";
		ownerLegionName = "";
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

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public int getOwnerLevel() {
		return ownerLevel;
	}

	public void setOwnerLevel(int ownerLevel) {
		this.ownerLevel = ownerLevel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public int getProtectTime() {
		return protectTime;
	}

	public void setProtectTime(int protectTime) {
		this.protectTime = protectTime;
	}

	@Override
	public int compareTo(BloodTempleRoomInfo o) {
		if (o.getRoomId() > this.roomId) {
			return -1;
		} else if (o.getRoomId() < this.roomId) {
			return 1;
		}
		return 0;
	}

}
