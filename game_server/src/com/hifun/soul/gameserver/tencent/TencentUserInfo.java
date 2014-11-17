package com.hifun.soul.gameserver.tencent;

public class TencentUserInfo {
	/** 角色id */
	private long id;
	/** 账号id */
	private long passportId;
	/** 腾讯开放平台用户id */
	private String openId;
	/** 黄钻等级 */
	private int yellowVipLevel;
	/** 是否年费黄钻用户 */
	private boolean isYearYellowVip;
	/** 是否豪华黄钻用户 */
	private boolean isYellowHighVip;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
