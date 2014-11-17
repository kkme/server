package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name="t_tencent_user_info")
public class TencentUserInfoEntity extends BaseCommonEntity{
		
	/** 主键：角色id */
	@Id
	@Column
	private long id;
	/** 账号id */
	@Column
	private long passportId;
	/** 腾讯开放平台用户id */
	@Column
	private String openId;
	/** 黄钻等级 */
	@Column
	private int yellowVipLevel;
	/** 是否年费黄钻用户 */
	@Column
	private boolean isYearYellowVip;
	/** 是否豪华黄钻用户 */
	@Column
	private boolean isYellowHighVip;
	@Override
	public Serializable getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id =(Long)id;		
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getYellowVipLevel() {
		return yellowVipLevel;
	}

	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}

	public boolean isYearYellowVip() {
		return isYearYellowVip;
	}

	public void setYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}

	public boolean isYellowHighVip() {
		return isYellowHighVip;
	}

	public void setYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}
	

}
