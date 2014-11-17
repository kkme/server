package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_abattoir_room")
public class AbattoirRoomEntity extends BaseCommonEntity {

	/** id */
	@Id
	@Column
	private int id;

	/** 房主Id */
	@Column
	private long ownerId;

	/** 房主名称 */
	@Column
	private String ownerName;
	
	/** 房主职业类型 */
	@Column
	private int ownerOccupationType;
	
	/** 房主等级 */
	@Column
	private int ownerLevel;

	/** 抢夺时间 */
	@Column
	private long lootTime;

	/** 房间类型 */
	@Column
	private int ownerType;

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public long getLootTime() {
		return lootTime;
	}

	public void setLootTime(long lootTime) {
		this.lootTime = lootTime;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer) id;
	}

}
