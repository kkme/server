package com.hifun.soul.gameserver.antiindulge;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

/**
 * 防沉迷管理器
 * 
 * @author magicstone
 * 
 */
public class HumanAntiIndulgeManager {
	private Human human;
	private float revenueRate = 1f;
	public HumanAntiIndulgeManager(Human human) {
		this.human = human;
	}

	public Human getOwner() {
		return human;
	}

	/**
	 * 获取当前收益倍率
	 * 
	 * @return
	 */
	public float getRevenueRate() {
		if (GameServerAssist.getGameConstants().getAntiIndulgeSwitch()) {
			return revenueRate;
		} else {
			return 1f;
		}
	}

	public void setRevenueRate(float revenueRate) {
		if (revenueRate > 1) {
			this.revenueRate = 1f;
		} 
		else if(revenueRate<0){
			this.revenueRate = 0f;
		}else {
			this.revenueRate = revenueRate;
		}
	}

}
