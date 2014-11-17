package com.hifun.soul.core.schedule;

import java.util.Date;

import com.hifun.soul.core.msg.sys.ScheduledMessage;

/**
 * 定时任务管理器
 * 
 */
public interface IScheduleService {

	/**
	 * 
	 * @param msg
	 * @param delay 延迟时间(单位:豪秒)
	 */
	public abstract void scheduleOnce(ScheduledMessage msg, long delay);

	/**
	 * 
	 * @param msg
	 * @param d 指定日期
	 */
	public abstract void scheduleOnce(ScheduledMessage msg, Date d);

	/**
	 * 
	 * @param msg
	 * @param delay 延迟时间
	 * @param period 周期时间(单位:豪秒)
	 */
	public abstract void scheduleWithFixedDelay(ScheduledMessage msg, long delay, long period);
}