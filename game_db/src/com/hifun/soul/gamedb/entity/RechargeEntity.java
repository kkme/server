package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name="t_recharge")
public class RechargeEntity extends BaseCommonEntity{

	@Id
	@Column
	private int id;
	/** 充值角色id */
	@Column
	private long humanId;
	/** 充值账户id */
	@Column
	private long passportId;
	/** 充值花费 */
	@Column
	private int rechargeCost;
	/** 充值获得的二级货币总数 */
	@Column
	private int rechargeNum;
	/** 奖励数量 */
	@Column
	private int rewardNum;
	/** 是否首次充值 */
	@Column
	private boolean isFirstRecharge;	
	/** 充值时间 */
	@Column
	private Date rechargeTime;
	/** 充值方式(正常充值\GM充值) */
	@Column
	private int rechargeType;
	/** 备注 */
	@Column
	private String memo;
	
	@Override
	public Integer getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer)id;
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getRechargeCost() {
		return rechargeCost;
	}

	public void setRechargeCost(int rechargeCost) {
		this.rechargeCost = rechargeCost;
	}

	public int getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public int getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}

	public boolean isFirstRecharge() {
		return isFirstRecharge;
	}

	public void setFirstRecharge(boolean isFirstRecharge) {
		this.isFirstRecharge = isFirstRecharge;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public int getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(int rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
