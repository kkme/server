package com.hifun.soul.gameserver.turntable.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 转盘模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TurntableTemplateVO extends TemplateObject {

	/**  物品获取的概率 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.turntable.template.ItemRateData.class, collectionNumber = "1,2;3,4;5,6;7,8;9,10;11,12;13,14;15,16;17,18;19,20;21,22;23,24;25,26;27,28;29,30;31,32")
	protected List<com.hifun.soul.gameserver.turntable.template.ItemRateData> itemRateDatas;


	public List<com.hifun.soul.gameserver.turntable.template.ItemRateData> getItemRateDatas() {
		return this.itemRateDatas;
	}

	public void setItemRateDatas(List<com.hifun.soul.gameserver.turntable.template.ItemRateData> itemRateDatas) {
		if (itemRateDatas == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 物品获取的概率]itemRateDatas不可以为空");
		}	
		this.itemRateDatas = itemRateDatas;
	}
	

	@Override
	public String toString() {
		return "TurntableTemplateVO[itemRateDatas=" + itemRateDatas + ",]";

	}
}