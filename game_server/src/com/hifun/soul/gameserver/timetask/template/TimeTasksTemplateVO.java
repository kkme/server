package com.hifun.soul.gameserver.timetask.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 时间点任务模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TimeTasksTemplateVO extends TemplateObject {

	/**  以逗号分隔的时间点 */
	@ExcelCellBinding(offset = 1)
	protected String taskTimes;


	public String getTaskTimes() {
		return this.taskTimes;
	}

	public void setTaskTimes(String taskTimes) {
		if (StringUtils.isEmpty(taskTimes)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 以逗号分隔的时间点]taskTimes不可以为空");
		}
		this.taskTimes = taskTimes;
	}
	

	@Override
	public String toString() {
		return "TimeTasksTemplateVO[taskTimes=" + taskTimes + ",]";

	}
}