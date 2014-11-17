package com.hifun.soul.gameserver.onlinereward.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.onlinereward.template.OnlineRewardTemplate;

@Scope("singleton")
@Component
public class OnlineRewardTemplateManager implements IInitializeRequired{

	@Autowired
	private TemplateService templateService;
	
	private Map<String,OnlineRewardTemplate> templates = new HashMap<String,OnlineRewardTemplate>();
	
	@Override
	public void init() {
		for(OnlineRewardTemplate template : templateService.getAll(OnlineRewardTemplate.class).values()){
			if(template != null){
				templates.put(genKey(template.getTimes(), template.getOccupation()), template);
			}
		}
	}
	
	public OnlineRewardTemplate getOnlineRewardTemplate(int times, int occupation) {
		return templates.get(genKey(times,occupation));
	}
	
	private String genKey(int times, int occupation) {
		return times + "&" + occupation;
	}

}
