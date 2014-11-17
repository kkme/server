package com.hifun.soul.gameserver.timetask.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.timetask.template.TimeTasksTemplate;
import com.hifun.soul.gameserver.timetask.template.TimesTemplate;

@Scope("singleton")
@Component
public class TimeTaskTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	/** key:时间点id value:距离当天零点的毫秒数 */
	private Map<Integer, Long> timePointMap = new HashMap<Integer, Long>();
	/** key:时间点id value:距离当天零点的小时数 */
	private Map<Integer, Integer> timeHourMap = new HashMap<Integer, Integer>();

	/** key:定时任务类型 value:定时任务的时间点 */
	private Map<Integer, List<Long>> timeTaskMap = new HashMap<Integer, List<Long>>();

	@Override
	public void init() {
		initTimePointMap();
		initTimeTaskMap();
	}

	private void initTimePointMap() {
		Map<Integer, TimesTemplate> times = templateService
				.getAll(TimesTemplate.class);
		for (TimesTemplate template : times.values()) {
			timePointMap.put(template.getId(), template.getMillSeconds());
			timeHourMap.put(template.getId(), template.getHours());
		}
	}

	private void initTimeTaskMap() {
		Map<Integer, TimeTasksTemplate> timeTasks = templateService
				.getAll(TimeTasksTemplate.class);
		for (TimeTasksTemplate template : timeTasks.values()) {
			String taskTimes = template.getTaskTimes();
			if (taskTimes == null || taskTimes.trim().length() == 0) {
				continue;
			}

			String[] taskTimesArray = taskTimes.split(",");
			for (String taskTime : taskTimesArray) {
				Long time = timePointMap.get(Integer.valueOf(taskTime));
				if (time == null) {
					continue;
				}

				List<Long> timesArray = timeTaskMap.get(template.getId());
				if (timesArray == null) {
					timesArray = new ArrayList<Long>();
					timesArray.add(time);
				} else {
					timesArray.add(time);
				}

				timeTaskMap.put(template.getId(), timesArray);
			}
		}
	}

	public List<Long> getTaskTimes(int timeTaskType) {
		List<Long> times = timeTaskMap.get(timeTaskType);

		if (times == null) {
			times = new ArrayList<Long>();
			return times;
		}
		return times;
	}

	/**
	 * 获取时间点（距离当天0点的毫秒数）
	 * 
	 * @param timeId
	 * @return
	 */
	public Long getTimePoint(int timeId) {
		return timePointMap.get(timeId);
	}

	/**
	 * 获取触发时间描述
	 * 
	 * @return
	 */
	public String getTriggerTimeDesc(int timeId) {
		return templateService.getAll(TimesTemplate.class).get(timeId)
				.getTriggerTime();
	}

	/**
	 * 获得定时任务距离零点的小时数
	 */
	public String[] getHours(int timeTaskType) {
		List<String> hours = new ArrayList<String>();
		Map<Integer, TimeTasksTemplate> timeTasks = templateService
				.getAll(TimeTasksTemplate.class);
		for (TimeTasksTemplate template : timeTasks.values()) {
			String taskTimes = template.getTaskTimes();
			if (taskTimes == null || taskTimes.trim().length() == 0) {
				continue;
			}
			if (timeTaskType == template.getId()) {
				String[] taskTimesArray = taskTimes.split(",");
				for (String taskTime : taskTimesArray) {
					Integer hour = timeHourMap.get(Integer.valueOf(taskTime));
					hours.add(hour.toString());
				}
			}
		}
		return hours.toArray(new String[0]);
	}
}
