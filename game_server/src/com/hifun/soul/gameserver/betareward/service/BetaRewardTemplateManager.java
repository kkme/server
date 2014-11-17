package com.hifun.soul.gameserver.betareward.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.betareward.template.BetaRewardTemplate;
import com.hifun.soul.gameserver.betareward.template.BetaUserTemplate;

@Scope("singleton")
@Component
public class BetaRewardTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<String,BetaUserTemplate> betaUserTemplateMap = new HashMap<String,BetaUserTemplate>();
	private Map<Integer,BetaRewardTemplate> betaRewardTemplateMap = new HashMap<Integer,BetaRewardTemplate>();
	
	@Override
	public void init() {
		initBetaUserTemplateMap();
		initBetaRewardTemplateMap();
	}
	
	private void initBetaUserTemplateMap(){
		for(BetaUserTemplate template : templateService
				.getAll(BetaUserTemplate.class).values()){
			betaUserTemplateMap.put(template.getPassPortId(), template);
		}
	}
	
	private void initBetaRewardTemplateMap(){
		for(BetaRewardTemplate template : templateService
				.getAll(BetaRewardTemplate.class).values()){
			betaRewardTemplateMap.put(template.getId(), template);
		}
	}
	
	/**
	 * 封测用户模版
	 * @param passPortId
	 * @return
	 */
	public BetaUserTemplate getBetaUserTemplate(String passPortId){
		return betaUserTemplateMap.get(passPortId);
	}
	
	/**
	 * 封测奖励模版
	 * @param level
	 * @return
	 */
	public BetaRewardTemplate getBetaRewardTemplate(int level){
		return betaRewardTemplateMap.get(level);
	}
}
