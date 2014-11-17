package com.hifun.soul.gameserver.yellowvip.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.ConfigException;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.yellowvip.template.YearYellowVipRewardTemplate;
import com.hifun.soul.gameserver.yellowvip.template.YellowVipDailyRewardTemplate;
import com.hifun.soul.gameserver.yellowvip.template.YellowVipLevelUpRewardTemplate;
import com.hifun.soul.gameserver.yellowvip.template.YellowVipOnceRewardTemplate;

@Scope("singleton")
@Component
public class YellowVipTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer,YellowVipLevelUpRewardTemplate> levelUpTemplates;
	private Map<Integer,YellowVipDailyRewardTemplate> dailyRewardTemplates;
	private YellowVipOnceRewardTemplate onceTemplate;	
	private YearYellowVipRewardTemplate yearVipDailyRewardTemplate;
	
	@Override
	public void init() {
		levelUpTemplates = templateService.getAll(YellowVipLevelUpRewardTemplate.class);
		dailyRewardTemplates = templateService.getAll(YellowVipDailyRewardTemplate.class);
		onceTemplate = templateService.getAll(YellowVipOnceRewardTemplate.class).get(SharedConstants.FIRST_ID);
		if(onceTemplate == null){
			throw new ConfigException("黄钻新手礼包未配置！");
		}		
		yearVipDailyRewardTemplate = templateService.getAll(YearYellowVipRewardTemplate.class).get(SharedConstants.FIRST_ID);
		if(yearVipDailyRewardTemplate == null){
			throw new ConfigException("年费黄钻每日礼包未配置！");
		}
	}
	
	public int[] getLevelUpRewardKeys(){
		int[] result = new int[levelUpTemplates.size()];
		int index=0;
		for(Integer level : levelUpTemplates.keySet()){
			result[index] = level;
			index++;
		}
		return result;
	}
	
	public int getOnceRewardItemId(){
		return onceTemplate.getItemId();
	}
	
	public YellowVipDailyRewardTemplate getYellowVipDailyRewardTemplate(int humanLevel){
		return dailyRewardTemplates.get(humanLevel);
	}
	
	public YearYellowVipRewardTemplate getYearYellowVipRewardTemplate(){
		return yearVipDailyRewardTemplate;
	}
	
	public YellowVipLevelUpRewardTemplate getYellowVipLevelUpRewardTemplate(int level){
		return levelUpTemplates.get(level);
	}

}
