package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 关卡评星奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageStarRewardTemplateVO extends TemplateObject {

	/**  奖励物品列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.RewardItemData.class, collectionNumber = "1,2;3,4;5,6;7,8")
	protected List<com.hifun.soul.gameserver.item.template.RewardItemData> rewardItemList;


	public List<com.hifun.soul.gameserver.item.template.RewardItemData> getRewardItemList() {
		return this.rewardItemList;
	}

	public void setRewardItemList(List<com.hifun.soul.gameserver.item.template.RewardItemData> rewardItemList) {
		this.rewardItemList = rewardItemList;
	}
	

	@Override
	public String toString() {
		return "StageStarRewardTemplateVO[rewardItemList=" + rewardItemList + ",]";

	}
}