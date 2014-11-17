package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_market_activity_config")
public class MarketActivitySettingEntity extends BaseCommonEntity {
	@Id
	@Column
	private int id;
	@Column
	private int type;
	@Column
	private boolean enable;
	@Column
	private int roleLevel;	
	@Column
	private int vipLevel;
	public Integer getId() {
		return id;
	}
	public void setId(Serializable id) {
		this.id = (Integer)id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

}
