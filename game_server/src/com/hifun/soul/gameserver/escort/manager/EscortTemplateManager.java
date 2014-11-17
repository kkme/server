package com.hifun.soul.gameserver.escort.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.escort.template.EscortConstantsTemplate;
import com.hifun.soul.gameserver.escort.template.EscortHelpRewardTemplate;
import com.hifun.soul.gameserver.escort.template.EscortMonsterTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRefreshMonsterCostTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRefreshMonsterRateTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRewardTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRobRankRewardTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRobRewardTemplate;
import com.hifun.soul.gameserver.vip.template.EscortBuyRobNumCostTemplate;

/**
 * 押运管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class EscortTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, EscortConstantsTemplate> constantsTemplates = new HashMap<Integer, EscortConstantsTemplate>();
	private Map<Integer, EscortBuyRobNumCostTemplate> robNumCostTemplates = new HashMap<Integer, EscortBuyRobNumCostTemplate>();
	private Map<Integer, EscortMonsterTemplate> monsterTemplates = new HashMap<Integer, EscortMonsterTemplate>();
	private Map<Integer, EscortRewardTemplate> escortRewardTemplates = new HashMap<Integer, EscortRewardTemplate>();
	private Map<Integer, EscortRefreshMonsterCostTemplate> refreshCostTemplates = new HashMap<Integer, EscortRefreshMonsterCostTemplate>();
	private Map<Integer, EscortHelpRewardTemplate> helpRewardTemplates = new HashMap<Integer, EscortHelpRewardTemplate>();
	private Map<Integer, EscortRobRewardTemplate> robRewardTemplates = new HashMap<Integer, EscortRobRewardTemplate>();
	private Map<Integer, EscortRefreshMonsterRateTemplate> refreshRateTemplates = new HashMap<Integer, EscortRefreshMonsterRateTemplate>();
	private Map<Integer, EscortRobRankRewardTemplate> robRankRewardTemplates = new HashMap<Integer, EscortRobRankRewardTemplate>();

	@Override
	public void init() {
		constantsTemplates = templateService
				.getAll(EscortConstantsTemplate.class);
		robNumCostTemplates = templateService
				.getAll(EscortBuyRobNumCostTemplate.class);
		monsterTemplates = templateService.getAll(EscortMonsterTemplate.class);
		escortRewardTemplates = templateService
				.getAll(EscortRewardTemplate.class);
		refreshCostTemplates = templateService
				.getAll(EscortRefreshMonsterCostTemplate.class);
		helpRewardTemplates = templateService
				.getAll(EscortHelpRewardTemplate.class);
		robRewardTemplates = templateService
				.getAll(EscortRobRewardTemplate.class);
		refreshRateTemplates = templateService
				.getAll(EscortRefreshMonsterRateTemplate.class);
		robRankRewardTemplates = templateService
				.getAll(EscortRobRankRewardTemplate.class);
	}

	public EscortConstantsTemplate getConstantsTemplate() {
		return constantsTemplates
				.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID);
	}

	public int getBuyRobNumCost(int num) {
		if (robNumCostTemplates.get(num) == null) {
			return 0;
		} else {
			return robNumCostTemplates.get(num).getCrystal();
		}
	}

	public Map<Integer, EscortMonsterTemplate> getMonsterTemplates() {
		return monsterTemplates;
	}

	public EscortMonsterTemplate getMonsterTemplate(int monsterType) {
		return monsterTemplates.get(monsterType);
	}

	public EscortRewardTemplate getEscortRewardTemplate(int level) {
		return escortRewardTemplates.get(level);
	}

	public int getRefreshMonsterCost(int num) {
		if (num > refreshCostTemplates.size()) {
			num = refreshCostTemplates.size();
		}
		return refreshCostTemplates.get(num).getCrystal();
	}

	public float getHelpRewardCoinRate(int levelDiff) {
		if (levelDiff < 0) {
			levelDiff = 0;
		}
		for (int id : helpRewardTemplates.keySet()) {
			if (levelDiff >= helpRewardTemplates.get(id).getLevelDiffLowest()
					&& levelDiff <= helpRewardTemplates.get(id)
							.getLevelDiffHighest()) {
				return helpRewardTemplates.get(id).getRewardCoinRate();
			}
		}
		return 0;
	}

	public float getRobRewardCoinRate(int levelDiff) {
		if (levelDiff < 0) {
			levelDiff = 0;
		}
		for (int id : robRewardTemplates.keySet()) {
			if (levelDiff >= robRewardTemplates.get(id).getLevelDiffLowest()
					&& levelDiff <= robRewardTemplates.get(id)
							.getLevelDiffHighest()) {
				return robRewardTemplates.get(id).getRewardCoinRate();
			}
		}
		return 0;
	}

	public int getMaxRobRank() {
		return robRankRewardTemplates.size();
	}

	public EscortRefreshMonsterRateTemplate getRefreshRateTemplate(
			int monsterType) {
		return refreshRateTemplates.get(monsterType);
	}

	public int getRobRankRewardCoin(int rank) {
		if (robRankRewardTemplates.get(rank) == null) {
			return 0;
		}
		return robRankRewardTemplates.get(rank).getRewardCoin();
	}
}
