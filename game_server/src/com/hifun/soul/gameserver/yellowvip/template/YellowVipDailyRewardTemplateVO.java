package com.hifun.soul.gameserver.yellowvip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 黄钻每日奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class YellowVipDailyRewardTemplateVO extends TemplateObject {

	/** 黄钻vip金币奖励 */
	@ExcelCollectionMapping(clazz = Integer.class, collectionNumber = "1;2;3;4;5;6;7;8")
	protected List<Integer> vipCoin;


	public List<Integer> getVipCoin() {
		return this.vipCoin;
	}

	public void setVipCoin(List<Integer> vipCoin) {
		if (vipCoin == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[黄钻vip金币奖励]vipCoin不可以为空");
		}	
		this.vipCoin = vipCoin;
	}
	

	@Override
	public String toString() {
		return "YellowVipDailyRewardTemplateVO[vipCoin=" + vipCoin + ",]";

	}
}