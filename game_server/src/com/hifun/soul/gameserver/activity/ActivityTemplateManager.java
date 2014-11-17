package com.hifun.soul.gameserver.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.activity.template.ActivityShowTypeTemplate;
import com.hifun.soul.gameserver.activity.template.ActivityTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;

/**
 * 活动模板管理器
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class ActivityTemplateManager implements IInitializeRequired {
	private static Logger logger = Loggers.ACTIVITY_LOGGER;
	private Map<Integer,ActivityTemplate> activityTemplates = new HashMap<Integer,ActivityTemplate>();
	private Map<Integer,ActivityShowTypeTemplate> activityShowTypeTemplates;
	private Map<Integer,Activity> activities = new HashMap<Integer,Activity>();	
	
	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		activityShowTypeTemplates = templateService.getAll(ActivityShowTypeTemplate.class);
		activityTemplates = templateService.getAll(ActivityTemplate.class);
		for (Integer id : activityTemplates.keySet()) {
			Activity activity = this.generateActivityObject(id);
			if(activity==null){
				continue;
			}
			if (new Date().after(activity.getEndDate())) {
				continue;
			}
			activities.put(id, activity);
		}
	}
	
	/**
	 * 获取所有有效的活动列表
	 * 
	 * @return
	 */
	public Map<Integer,Activity> getAllActivities(){
		return activities;
	}
	
	/**
	 * 获取活动模板
	 * @param activityId
	 * @return
	 */
	public ActivityTemplate getActivityTemplate(int activityId){
		return activityTemplates.get(activityId);
	}
	
	
	public Map<Integer,ActivityShowTypeTemplate> getShowTypeTemplates(){
		return this.activityShowTypeTemplates;
	}
	/**
	 * 获取活动显示类型模板
	 * @param showTypeId
	 * @return
	 */
	public ActivityShowTypeTemplate getShowTypeTemplate(int showTypeId){
		if(!activityShowTypeTemplates.containsKey(showTypeId)){
			return null ;
		}
		return this.activityShowTypeTemplates.get(showTypeId);
	}
	
	/**
	 * 根据活动id获取活动对象
	 * 
	 * @param activityId
	 * @return
	 */
	private Activity generateActivityObject(int activityId){
		ActivityTemplate template = activityTemplates.get(activityId);
		if(template==null){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		Activity activity = new Activity();
		try {
			Date endDate = dateFormat.parse(template.getEndDate().trim());
			Date startDate = dateFormat.parse(template.getStartDate().trim());
			activity.setEndDate(endDate);
			activity.setStartDate(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("活动模板配置错误，日期格式不正确。[id=" + activityId + "]", e);
			return null;
		}
		List<KeyValuePair<Long, Long>> activeTimeList=null;
		long finishTime = 0;
		long lastBeginTime = 0;
		if (template.getActiveTimeSpan().trim().length() > 0) {
			String[] activeTimes = template.getActiveTimeSpan().split(",");			
			activeTimeList = new ArrayList<KeyValuePair<Long, Long>>();
			for (String timeSpan : activeTimes) {
				String[] spanArr = timeSpan.replace("[", "").replace("]", "").split("-");
				KeyValuePair<Long, Long> pair = new KeyValuePair<Long, Long>();
				Long start = GameServerAssist.getTimeTaskTemplateManager().getTimePoint(Integer.parseInt(spanArr[0]));
				Long end = GameServerAssist.getTimeTaskTemplateManager().getTimePoint(Integer.parseInt(spanArr[1]));
				if (start == null || end == null) {
					continue;
				}
				pair.setKey(start);
				pair.setValue(end);
				activeTimeList.add(pair);
				if (end > finishTime) {
					finishTime = end;
					lastBeginTime = start;
				}
			}
		}
		activity.setActiveTimeSpan(activeTimeList);
		activity.setActivityShowType(template.getActivityShowType());
		activity.setName(template.getActivityName());
		activity.setDesc(template.getDesc());
		activity.setId(activityId);
		activity.setVisibleLevel(template.getVisibleLevel());
		activity.setOpenLevel(template.getOpenLevel());
		activity.setTiming(template.isTiming());
		activity.setIconId(template.getActivityIconId());
		activity.setSimpleDesc(template.getSimpleDesc());
		if (template.isTiming()) {
			activity.setState(ActivityState.READY);
		} else {
			activity.setState(ActivityState.OPEN);
		}		
		activity.setFinishTime(finishTime);
		activity.setLastBeginTime(lastBeginTime);
		activity.setNextTimeSpanIndex(0);
		return activity;
	}
}
