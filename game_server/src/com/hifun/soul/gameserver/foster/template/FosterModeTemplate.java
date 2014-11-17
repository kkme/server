package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class FosterModeTemplate extends FosterModeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		if(this.getRandomLowRatio()>SharedConstants.DEFAULT_FLOAT_BASE){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"下限范围填写错误，不能大于浮点型数据的基数");
		}
	}

}
