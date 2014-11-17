package com.hifun.soul.gameserver.boss.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.boss.template.BossRankRewardTemplate;

@Scope("singleton")
@Component
public class BossWarTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	
	@Override
	public void init() {
		
	}
	
	/**
	 * 根据排名找到对应的奖励模版
	 * 
	 * @param rank
	 * @return
	 */
	public BossRankRewardTemplate getSuitableRewardTemplate(int rank) {
		BossRankRewardTemplate suitableTemplate = null;
		Map<Integer,BossRankRewardTemplate> templates = templateService.getAll(BossRankRewardTemplate.class);
		for(BossRankRewardTemplate template : templates.values()){
			if(rank >= template.getMinRank()
					&& rank <= template.getMaxRank()){
				suitableTemplate = template;
				break;
			}
		}
		return suitableTemplate;
	}

}
