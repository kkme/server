package com.hifun.soul.gameserver.meditation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 冥想收益模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MeditationRewardTemplateVO extends TemplateObject {

	/**  冥想时长模式 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.meditation.template.MeditationInfo.class, collectionNumber = "1,2,3,4;5,6,7,8;9,10,11,12;13,14,15,16")
	protected List<com.hifun.soul.gameserver.meditation.template.MeditationInfo> meditationInfo;


	public List<com.hifun.soul.gameserver.meditation.template.MeditationInfo> getMeditationInfo() {
		return this.meditationInfo;
	}

	public void setMeditationInfo(List<com.hifun.soul.gameserver.meditation.template.MeditationInfo> meditationInfo) {
		if (meditationInfo == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 冥想时长模式]meditationInfo不可以为空");
		}	
		this.meditationInfo = meditationInfo;
	}
	

	@Override
	public String toString() {
		return "MeditationRewardTemplateVO[meditationInfo=" + meditationInfo + ",]";

	}
}