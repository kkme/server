package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_rank_vip")
public class VipRankEntity extends BaseCommonEntity {

	/** id(与角色ID一致) */
	@Id
	@Column
	private long id;

	/** 角色名称 */
	@Column
	private String humanName;

	/** 角色职业 */
	@Column
	private int occupation;

	/** 角色VIP等级 */
	@Column
	private int vipLevel;

	/** 角色等级 */
	@Column
	private int level;

	/** 角色荣誉值 */
	@Column
	private int honor;

	/** 军团ID */
	@Column
	private long legionId;

	/** 军团名称 */
	@Column
	private String legionName;

	/** 是否有效 */
	@Column
	private boolean isValid;

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public int getHonor() {
		return honor;
	}

	public void setHonor(int honor) {
		this.honor = honor;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

}
