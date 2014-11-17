package com.hifun.soul.gameserver.friend;

import java.util.HashSet;
import java.util.Set;

/**
 * 玩家所有的好友关系
 */
public class FriendInfos {
	private long humanId;
	private String humanName;
	private int humanLevel;
	private int humanOccupation;
	private Set<Long> friendIds = new HashSet<Long>();
	private Set<Long> sendEnergyToSelfFriendIds = new HashSet<Long>();
	private Set<Long> sendEnergyToOtherFriendIds = new HashSet<Long>();
	private Set<Long> recievedEnergyFriendIds = new HashSet<Long>();
//	private Set<Long> selfSendFriendApplyIds = new HashSet<Long>();
	private Set<Long> otherSendFriendApplyIds = new HashSet<Long>();
	private Set<Long> latestFriendIds = new HashSet<Long>();
	private int yellowVipLevel;
	private boolean isYearYellowVip;
	private boolean isYellowHighVip;
	
	public long getHumanId() {
		return humanId;
	}
	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}
	public String getHumanName() {
		return humanName;
	}
	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}
	public int getHumanLevel() {
		return humanLevel;
	}
	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}
	public int getHumanOccupation() {
		return humanOccupation;
	}
	public void setHumanOccupation(int humanOccupation) {
		this.humanOccupation = humanOccupation;
	}
	public Set<Long> getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(Set<Long> friendIds) {
		this.friendIds = friendIds;
	}
	public Set<Long> getSendEnergyToSelfFriendIds() {
		return sendEnergyToSelfFriendIds;
	}
	public void setSendEnergyToSelfFriendIds(Set<Long> sendEnergyToSelfFriendIds) {
		this.sendEnergyToSelfFriendIds = sendEnergyToSelfFriendIds;
	}
	public Set<Long> getSendEnergyToOtherFriendIds() {
		return sendEnergyToOtherFriendIds;
	}
	public void setSendEnergyToOtherFriendIds(Set<Long> sendEnergyToOtherFriendIds) {
		this.sendEnergyToOtherFriendIds = sendEnergyToOtherFriendIds;
	}
	public Set<Long> getRecievedEnergyFriendIds() {
		return recievedEnergyFriendIds;
	}
	public void setRecievedEnergyFriendIds(Set<Long> recievedEnergyFriendIds) {
		this.recievedEnergyFriendIds = recievedEnergyFriendIds;
	}
//	public Set<Long> getSelfSendFriendApplyIds() {
//		return selfSendFriendApplyIds;
//	}
//	public void setSelfSendFriendApplyIds(Set<Long> selfSendFriendApplyIds) {
//		this.selfSendFriendApplyIds = selfSendFriendApplyIds;
//	}
	public Set<Long> getOtherSendFriendApplyIds() {
		return otherSendFriendApplyIds;
	}
	public void setOtherSendFriendApplyIds(Set<Long> otherSendFriendApplyIds) {
		this.otherSendFriendApplyIds = otherSendFriendApplyIds;
	}
	public Set<Long> getLatestFriendIds() {
		return latestFriendIds;
	}
	public void setLatestFriendIds(Set<Long> latestFriendIds) {
		this.latestFriendIds = latestFriendIds;
	}
	public int getYellowVipLevel() {
		return yellowVipLevel;
	}
	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}
	public boolean getIsYearYellowVip() {
		return isYearYellowVip;
	}
	public void setIsYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}
	public boolean getIsYellowHighVip() {
		return isYellowHighVip;
	}
	public void setIsYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}
	
}
