package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_prisoner")
public class PrisonerEntity extends BaseCommonEntity {

	@Id
	@Column
	private long id;
	@Column
	private String humanName;
	@Column
	private int humanLevel;
	@Column
	private int identityType;
	@Column
	private long masterId;
	@Column
	private int arrestedNum;
	@Column
	private int buyArrestNum;
	@Column
	private int rescuedNum;
	@Column
	private int interactedNum;
	@Column
	private int extractedExperience;
	@Column
	private int forHelpedNum;
	@Column
	private int revoltedNum;
	@Column
	private String enemyIds;
	@Column
	private String arrestIds;
	@Column
	private long beSlaveTime;
	@Column
	private long lastBeExtractedTime;
	@Column
	private long lastBeInteractedTime;
	@Column
	private long lastInteractTime;
	@Column
	private int beSlaveSelfLevel;
	@Column
	private int beSlaveMasterLevel;
	@Column
	private int offLineExperience;

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
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

	public int getIdentityType() {
		return identityType;
	}

	public void setIdentityType(int identityType) {
		this.identityType = identityType;
	}

	public int getArrestedNum() {
		return arrestedNum;
	}

	public void setArrestedNum(int arrestedNum) {
		this.arrestedNum = arrestedNum;
	}

	public int getRescuedNum() {
		return rescuedNum;
	}

	public void setRescuedNum(int rescuedNum) {
		this.rescuedNum = rescuedNum;
	}

	public int getInteractedNum() {
		return interactedNum;
	}

	public void setInteractedNum(int interactedNum) {
		this.interactedNum = interactedNum;
	}

	public int getExtractedExperience() {
		return extractedExperience;
	}

	public void setExtractedExperience(int extractedExperience) {
		this.extractedExperience = extractedExperience;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public int getForHelpedNum() {
		return forHelpedNum;
	}

	public void setForHelpedNum(int forHelpedNum) {
		this.forHelpedNum = forHelpedNum;
	}

	public int getRevoltedNum() {
		return revoltedNum;
	}

	public void setRevoltedNum(int revoltedNum) {
		this.revoltedNum = revoltedNum;
	}

	public String getEnemyIds() {
		return enemyIds;
	}

	public void setEnemyIds(String enemyIds) {
		this.enemyIds = enemyIds;
	}

	public String getArrestIds() {
		return arrestIds;
	}

	public void setArrestIds(String arrestIds) {
		this.arrestIds = arrestIds;
	}

	public int getBuyArrestNum() {
		return buyArrestNum;
	}

	public void setBuyArrestNum(int buyArrestNum) {
		this.buyArrestNum = buyArrestNum;
	}

	public long getBeSlaveTime() {
		return beSlaveTime;
	}

	public void setBeSlaveTime(long beSlaveTime) {
		this.beSlaveTime = beSlaveTime;
	}

	public long getLastBeExtractedTime() {
		return lastBeExtractedTime;
	}

	public void setLastBeExtractedTime(long lastBeExtractedTime) {
		this.lastBeExtractedTime = lastBeExtractedTime;
	}

	public long getLastBeInteractedTime() {
		return lastBeInteractedTime;
	}

	public void setLastBeInteractedTime(long lastBeInteractedTime) {
		this.lastBeInteractedTime = lastBeInteractedTime;
	}

	public long getLastInteractTime() {
		return lastInteractTime;
	}

	public void setLastInteractTime(long lastInteractTime) {
		this.lastInteractTime = lastInteractTime;
	}

	public int getBeSlaveSelfLevel() {
		return beSlaveSelfLevel;
	}

	public void setBeSlaveSelfLevel(int beSlaveSelfLevel) {
		this.beSlaveSelfLevel = beSlaveSelfLevel;
	}

	public int getBeSlaveMasterLevel() {
		return beSlaveMasterLevel;
	}

	public void setBeSlaveMasterLevel(int beSlaveMasterLevel) {
		this.beSlaveMasterLevel = beSlaveMasterLevel;
	}

	public int getOffLineExperience() {
		return offLineExperience;
	}

	public void setOffLineExperience(int offLineExperience) {
		this.offLineExperience = offLineExperience;
	}

}
