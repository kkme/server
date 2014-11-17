package com.hifun.soul.gameserver.abattoir.msg;

/**
 * 角斗场房间信息
 * 
 * @author yandajun
 * 
 */
public class AbattoirRoomInfo implements Comparable<AbattoirRoomInfo> {
	private int roomId;
	private String roomName;
	private long ownerId;
	private int ownerType;
	private int ownerOccupationType;
	private String ownerName;
	private int ownerLevel;
	private int revenue;
	private int protectTime;

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

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public int getOwnerOccupationType() {
		return ownerOccupationType;
	}

	public void setOwnerOccupationType(int ownerOccupationType) {
		this.ownerOccupationType = ownerOccupationType;
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
	public int compareTo(AbattoirRoomInfo o) {
		if (o.getRoomId() > this.roomId) {
			return -1;
		} else if (o.getRoomId() < this.roomId) {
			return 1;
		}
		return 0;
	}

}
