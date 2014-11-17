package com.hifun.soul.gameserver.loginreward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 登录奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LoginRewardTemplateVO extends TemplateObject {

	/**  奖励物品 */
	@ExcelCollectionMapping(clazz = Integer.class, collectionNumber = "1;2;3;4;5;6;7;8;9")
	protected List<Integer> rewards;


	public List<Integer> getRewards() {
		return this.rewards;
	}

	public void setRewards(List<Integer> rewards) {
		if (rewards == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 奖励物品]rewards不可以为空");
		}	
		this.rewards = rewards;
	}
	

	@Override
	public String toString() {
		return "LoginRewardTemplateVO[rewards=" + rewards + ",]";

	}
}