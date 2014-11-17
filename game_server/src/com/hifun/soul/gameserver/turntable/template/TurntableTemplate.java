package com.hifun.soul.gameserver.turntable.template;

import java.util.List;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class TurntableTemplate extends TurntableTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		List<com.hifun.soul.gameserver.turntable.template.ItemRateData> itemRateDatas = getItemRateDatas();
		for(ItemRateData itemRateData : itemRateDatas){
			if(itemRateData.getItemId() <= 0){
				throw new TemplateConfigException(getSheetName(),getId(),"TurntableTemplate itemId can not be zero！");
			}
		}
	}

}
