package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 关卡通用奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageRewardTemplateVO extends TemplateObject {

	/**  奖励物品列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.turntable.template.ItemRateData.class, collectionNumber = "1,2;3,4;5,6;7,8;9,10;11,12;13,14;15,16;17,18;19,20")
	protected List<com.hifun.soul.gameserver.turntable.template.ItemRateData> rewardItemList;


	public List<com.hifun.soul.gameserver.turntable.template.ItemRateData> getRewardItemList() {
		return this.rewardItemList;
	}

	public void setRewardItemList(List<com.hifun.soul.gameserver.turntable.template.ItemRateData> rewardItemList) {
		this.rewardItemList = rewardItemList;
	}
	

	@Override
	public String toString() {
		return "StageRewardTemplateVO[rewardItemList=" + rewardItemList + ",]";

	}
}