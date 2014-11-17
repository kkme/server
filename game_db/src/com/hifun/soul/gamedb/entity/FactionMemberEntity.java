package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

/**
 * 阵营成员实体;
 * 
 * @author crazyjohn
 * 
 */
@Entity
@Table(name = "t_faction_member")
public class FactionMemberEntity extends BaseCommonEntity {
	/** 玩家guid */
	@Id
	@Column
	private long guid;
	/** 玩家阵营 */
	@Column
	private int factionType;
	@Override
	public Serializable getId() {
		return guid;
	}

	@Override
	public void setId(Serializable id) {
		this.guid = (Long) id;
	}

	public long getGuid() {
		return guid;
	}

	public void setGuid(long guid) {
		this.guid = guid;
	}

	public int getFactionType() {
		return factionType;
	}

	public void setFactionType(int factionType) {
		this.factionType = factionType;
	}

}
