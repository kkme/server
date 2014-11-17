package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_special_shop_notify")
public class SpecialShopNotifyEntity extends BaseCommonEntity {
	
	@Id
	@Column
	private long id;
	
	/** 角色名称 */
	@Column
	private String roleName;
	
	/** 奖励名称 */
	@Column
	private String rewardName;
	
	/** 神秘商品id */
	@Column
	private int specialShopItemId;
	
	/** 物品id */
	@Column
	private int itemId;
	
	/** 物品数量 */
	@Column
	private int itemNum;
	
	public Serializable getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	@Override
	public void setId(Serializable id) {
		this.id=(Long)id;
	}

	public int getSpecialShopItemId() {
		return specialShopItemId;
	}

	public void setSpecialShopItemId(int specialShopItemId) {
		this.specialShopItemId = specialShopItemId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	
}
