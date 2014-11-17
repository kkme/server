package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_friend")
public class FriendEntity extends BaseCommonEntity {
	/** 玩家id */
	@Id
	@Column
	private long id;
	/** 玩家名称 */
	@Column
	private String humanName;
	/** 玩家等级 */
	@Column
	private int humanLevel;
	/** 玩家职业 */
	@Column
	private int humanOccupation;
	/** 好友列表 */
	@Column
	private String friendIds;
	/** 给自己送体力的好友列表 */
	@Column
	private String sendEnergyToSelfFriendIds;
	/** 自己送出体力的好友列表 */
	@Column
	private String sendEnergyToOtherFriendIds;
	/** 自己已经接收过体力的好友列表 */
	@Column
	private String recievedEnergyFriendIds;
	/** 自己发出的好友请求 */
	@Column
	private String selfSendFriendApplyIds;
	/** 别人向自己发送的好友申请 */
	@Column
	private String otherSendFriendApplyIds;
	/** 最近联系人 */
	@Column
	private String latestFriendIds;
	
	public Serializable getId() {
		return id;
	}
	@Override
	public void setId(Serializable id) {
		this.id=(Long)id;
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
	public String getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(String friendIds) {
		this.friendIds = friendIds;
	}
	public String getSendEnergyToSelfFriendIds() {
		return sendEnergyToSelfFriendIds;
	}
	public void setSendEnergyToSelfFriendIds(String sendEnergyToSelfFriendIds) {
		this.sendEnergyToSelfFriendIds = sendEnergyToSelfFriendIds;
	}
	public String getSendEnergyToOtherFriendIds() {
		return sendEnergyToOtherFriendIds;
	}
	public void setSendEnergyToOtherFriendIds(String sendEnergyToOtherFriendIds) {
		this.sendEnergyToOtherFriendIds = sendEnergyToOtherFriendIds;
	}
	public String getRecievedEnergyFriendIds() {
		return recievedEnergyFriendIds;
	}
	public void setRecievedEnergyFriendIds(String recievedEnergyFriendIds) {
		this.recievedEnergyFriendIds = recievedEnergyFriendIds;
	}
	public String getSelfSendFriendApplyIds() {
		return selfSendFriendApplyIds;
	}
	public void setSelfSendFriendApplyIds(String selfSendFriendApplyIds) {
		this.selfSendFriendApplyIds = selfSendFriendApplyIds;
	}
	public String getOtherSendFriendApplyIds() {
		return otherSendFriendApplyIds;
	}
	public void setOtherSendFriendApplyIds(String otherSendFriendApplyIds) {
		this.otherSendFriendApplyIds = otherSendFriendApplyIds;
	}
	public String getLatestFriendIds() {
		return latestFriendIds;
	}
	public void setLatestFriendIds(String latestFriendIds) {
		this.latestFriendIds = latestFriendIds;
	}	
	
}
