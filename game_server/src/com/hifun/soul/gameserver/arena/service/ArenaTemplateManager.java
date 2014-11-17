package com.hifun.soul.gameserver.arena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.arena.template.ArenaBattleRewardTemplate;
import com.hifun.soul.gameserver.rank.RankTemplateManager;
import com.hifun.soul.gameserver.rank.template.RankRewardTemplate;
import com.hifun.soul.gameserver.vip.template.ArenaBuyTimeCostTemplate;

@Scope("singleton")
@Component
public class ArenaTemplateManager implements IInitializeRequired {
	
	@Autowired
	private TemplateService templateService;
	@Autowired
	private RankTemplateManager templateManager;
	/** 购买竞技场战斗次数消耗配置  */
	private Map<Integer,Integer> buyTimeCostMap = new HashMap<Integer, Integer>();
	private int defaultCostCrystal;
	
	@Override
	public void init() {
		for(ArenaBuyTimeCostTemplate template : templateService
				.getAll(ArenaBuyTimeCostTemplate.class).values()){
			buyTimeCostMap.put(template.getId(), template.getCrystal());
			if(template.getCrystal() > defaultCostCrystal){
				defaultCostCrystal = template.getCrystal();
			}
		}
	}
	
	/**
	 * 获取消耗
	 * @param buyTimes
	 * @return
	 */
	public int getBuyTimeCost(int buyTimes) {
		if(buyTimeCostMap.get(buyTimes) != null){
			return buyTimeCostMap.get(buyTimes);
		}
		return defaultCostCrystal>0?defaultCostCrystal:5;
	}
	
	/**
	 * 获取奖励模版
	 * 
	 * @param level
	 * @return
	 */
	public ArenaBattleRewardTemplate getSuitableBattleRewardTemplate(int level) {
		ArenaBattleRewardTemplate suitTemplate = null;
		// 获取所有的奖励模版
		Map<Integer,ArenaBattleRewardTemplate> templateMap = templateService.getAll(ArenaBattleRewardTemplate.class);
		if(templateMap == null){
			return suitTemplate;
		}
		for(ArenaBattleRewardTemplate template : templateMap.values()){
			if(level >= template.getMinLevel()
					&& level <= template.getMaxLevel()){
				suitTemplate = template;
				break;
			}
		}
		
		return suitTemplate;
	}
	
	/**
	 * 根据排名找到对应的排名奖品id
	 * 
	 * @param rank
	 * @return
	 */
	public int getSuitableRankRewardId(int rankId, int rankLevel) {
		int rewardId = 0;
		List<RankRewardTemplate> templateList = templateManager.getRankRewardTemplates(rankId);
		if(templateList == null){
			return rewardId;
		}
		
		for(RankRewardTemplate template : templateList){
			if(rankLevel >= template.getRankingPositionBegin()
					&& rankLevel <= template.getRankingPositionEnd()){
				rewardId = template.getId();
			}
		}
		
		return rewardId;
	}
}
