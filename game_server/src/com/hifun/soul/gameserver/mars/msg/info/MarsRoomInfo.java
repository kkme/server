package com.hifun.soul.gameserver.mars.msg.info;

/**
 * 战神之巅房间信息
 * 
 * @author yandajun
 * 
 */
public class MarsRoomInfo {
	private int roomType;
	private int isLocked;
	private int produceStarSoul;
	private int produceKillValue;
	private MarsPlayerInfo ownerInfo;
	private MarsBetInfo[] betInfos;

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public int getProduceStarSoul() {
		return produceStarSoul;
	}

	public void setProduceStarSoul(int produceStarSoul) {
		this.produceStarSoul = produceStarSoul;
	}

	public int getProduceKillValue() {
		return produceKillValue;
	}

	public void setProduceKillValue(int produceKillValue) {
		this.produceKillValue = produceKillValue;
	}

	public MarsPlayerInfo getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(MarsPlayerInfo ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public MarsBetInfo[] getBetInfos() {
		return betInfos;
	}

	public void setBetInfos(MarsBetInfo[] betInfos) {
		this.betInfos = betInfos;
	}
}
