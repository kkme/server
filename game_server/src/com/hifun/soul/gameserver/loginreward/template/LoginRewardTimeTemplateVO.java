package com.hifun.soul.gameserver.loginreward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 登录奖励次数模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LoginRewardTimeTemplateVO extends TemplateObject {

	/**  抽奖次数 */
	@ExcelCellBinding(offset = 1)
	protected int rewardNum;


	public int getRewardNum() {
		return this.rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		if (rewardNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 抽奖次数]rewardNum不可以为0");
		}
		if (rewardNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 抽奖次数]rewardNum的值不得小于1");
		}
		this.rewardNum = rewardNum;
	}
	

	@Override
	public String toString() {
		return "LoginRewardTimeTemplateVO[rewardNum=" + rewardNum + ",]";

	}
}