package com.hifun.soul.gameserver.timetask.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.util.TimeUtils;

@ExcelRowBinding
public class TimesTemplate extends TimesTemplateVO {

	/** 模版配置时间距离当天零点的毫秒数 */
	private long millSeconds;
	/** 距离当天零点的小时数 */
	private int hours;
	
	public void setTriggerTime(String triggerTime)
	{
		super.setTriggerTime(triggerTime);
		try {
			String[] times = triggerTime.split(":");
			if(times == null
					|| times.length < 3){
				throw new Exception();
			}
			
			int hours = Integer.valueOf(times[0]);
			int minutes = Integer.valueOf(times[1]);
			int seconds = Integer.valueOf(times[2]);
			
			millSeconds = hours * TimeUtils.HOUR + minutes * TimeUtils.MIN + seconds * TimeUtils.SECOND;
			this.hours = hours;
		} catch (Exception e) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					1, "[触发时间 HH:mm:ss]triggerTime 无法解析");
		}
	}
	
	public long getMillSeconds() {
		return millSeconds;
	}
	
	public int getHours() {
		return hours;
	}
	
	@Override
	public void check() throws TemplateConfigException {
		
	}
}
