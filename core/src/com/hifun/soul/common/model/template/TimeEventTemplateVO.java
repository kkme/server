package com.hifun.soul.common.model.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.annotation.NotTranslate;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 定时事件
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TimeEventTemplateVO extends TemplateObject {

	/** 触发时间 HH:mm:ss */
	@NotTranslate
	@ExcelCellBinding(offset = 1)
	protected String triggerTime;


	public String getTriggerTime() {
		return this.triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		if (StringUtils.isEmpty(triggerTime)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[触发时间 HH:mm:ss]triggerTime不可以为空");
		}
		this.triggerTime = triggerTime;
	}
	

	@Override
	public String toString() {
		return "TimeEventTemplateVO[triggerTime=" + triggerTime + ",]";

	}
}