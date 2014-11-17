package com.hifun.soul.gameserver.rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.rank.template.*;

/**
 * 排行榜模板管理类
 * 
 * @author magicstone
 * 
 */
@Component
public class RankTemplateManager implements IInitializeRequired {
	private Map<Integer, RankTemplate> rankTemplates = new HashMap<Integer, RankTemplate>();
	private Map<Integer, RankRewardTemplate> rankRewardTemplates = new HashMap<Integer, RankRewardTemplate>();
	private Map<Integer, List<RankRewardTemplate>> rankRewardListMap = new HashMap<Integer, List<RankRewardTemplate>>();
	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		rankRewardTemplates = templateService.getAll(RankRewardTemplate.class);
		for(Integer id : rankRewardTemplates.keySet()){
			RankRewardTemplate template = rankRewardTemplates.get(id);
			if(!rankRewardListMap.containsKey(template.getRankId())){
				rankRewardListMap.put(template.getRankId(), new ArrayList<RankRewardTemplate>());				
			}
			rankRewardListMap.get(template.getRankId()).add(template);
		}
		Map<Integer, RankTemplate> ranks = templateService.getAll(RankTemplate.class);
		for(Integer id: ranks.keySet()){
			rankTemplates.put(ranks.get(id).getRankId(), ranks.get(id));
		}
	}
	
	/**
	 * 获取排行榜奖励模板
	 * @param rankId
	 * @param rankingPosition
	 * @return
	 */
	public RankRewardTemplate getRankRewardTemplate(Integer rankId,Integer rankingPosition){
		for(RankRewardTemplate template : rankRewardListMap.get(rankId)){
			if(template.getRankId()!= rankId){
				continue;
			}
			if(rankingPosition>=template.getRankingPositionBegin() && rankingPosition<=template.getRankingPositionEnd()){
				return template;
			}
		}
		return null;
	}
	
	/**
	 * 获取排行榜奖励模版
	 * 
	 * @param rankId
	 * @return
	 */
	public List<RankRewardTemplate> getRankRewardTemplates(Integer rankId){
		return rankRewardListMap.get(rankId);
	}
	
	/**
	 * 获取排行榜模板
	 * @param rankId
	 * @param rankingPosition
	 * @return
	 */
	public RankTemplate getRankTemplate(int rankId){
		return rankTemplates.get(rankId);
	}
	
}
