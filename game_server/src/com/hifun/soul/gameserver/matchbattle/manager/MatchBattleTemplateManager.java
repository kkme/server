package com.hifun.soul.gameserver.matchbattle.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.ConfigException;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleConfigTemplate;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleRankRewardTemplate;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleRewardTemplate;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleStreakWinTemplate;

@Scope("singleton")
@Component
public class MatchBattleTemplateManager implements IInitializeRequired{

	@Autowired
	private TemplateService templateService;
	private Map<Integer,MatchBattleRankRewardTemplate> rankRewardTemplates = new HashMap<Integer, MatchBattleRankRewardTemplate>();
	private Map<Integer,MatchBattleRewardTemplate> battleRewardTemplates = new HashMap<Integer, MatchBattleRewardTemplate>();
	private Map<Integer,MatchBattleStreakWinTemplate> streakWinTemplates = new HashMap<Integer, MatchBattleStreakWinTemplate>();
	private MatchBattleConfigTemplate matchBattleConfig;
	private MatchBattleStreakWinTemplate defaultStreakWinTemplate;
	
	@Override
	public void init() {
		rankRewardTemplates = templateService.getAll(MatchBattleRankRewardTemplate.class);		
		battleRewardTemplates = templateService.getAll(MatchBattleRewardTemplate.class);
		streakWinTemplates = templateService.getAll(MatchBattleStreakWinTemplate.class);
		for(MatchBattleStreakWinTemplate template : streakWinTemplates.values()){
			if(defaultStreakWinTemplate == null || template.getId()>defaultStreakWinTemplate.getId()){
				defaultStreakWinTemplate = template;
			}
		}
		matchBattleConfig = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, MatchBattleConfigTemplate.class);
		if(matchBattleConfig==null){
			throw new ConfigException("匹配战常量设置模板未找到！");
		}
	}
	
	public MatchBattleRewardTemplate getMatchBattleRewardTemplate(int roleLevel){
		return battleRewardTemplates.get(roleLevel);
	}
	
	public MatchBattleStreakWinTemplate getMatchBattleStreakWinTemplate(int streakWinNum){
		if(streakWinTemplates.containsKey(streakWinNum)){
			return streakWinTemplates.get(streakWinNum);
		}else{
			return defaultStreakWinTemplate;
		}
	}
	
	public int getRankRewardItemId(int rank){
		MatchBattleRankRewardTemplate template = rankRewardTemplates.get(rank);
		if(template == null){
			return 0;
		}
		else{
			return template.getRewardItemId();
		}
	}
	
	public MatchBattleConfigTemplate getMatchBattleConfig(){
		return matchBattleConfig;
	}
	
	public int getHasRewardRankSize(){
		return rankRewardTemplates.size();
	}
}
