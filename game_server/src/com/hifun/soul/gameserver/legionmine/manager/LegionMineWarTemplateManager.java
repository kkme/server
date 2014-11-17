package com.hifun.soul.gameserver.legionmine.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.legionmine.template.LegionBufTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineBattleRewardTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineConstantsTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRewardTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRichRateTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineSelfBufTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineSurroundStateTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineTypeTemplate;

/**
 * 军团矿战模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class LegionMineWarTemplateManager implements IInitializeRequired {
	private Map<Integer, LegionMineConstantsTemplate> mineConstantsTemplates = new HashMap<Integer, LegionMineConstantsTemplate>();
	private Map<Integer, LegionMineTypeTemplate> mineTypeTemplates = new HashMap<Integer, LegionMineTypeTemplate>();
	private Map<Integer, LegionMineTemplate> mineTemplates = new HashMap<Integer, LegionMineTemplate>();
	private Map<Integer, LegionBufTemplate> legionBufTemplates = new HashMap<Integer, LegionBufTemplate>();
	private Map<Integer, LegionMineSurroundStateTemplate> surroundStateTemplates = new HashMap<Integer, LegionMineSurroundStateTemplate>();
	private Map<Integer, LegionMineRichRateTemplate> richRateTmplates = new HashMap<Integer, LegionMineRichRateTemplate>();
	private Map<Integer, LegionMineSelfBufTemplate> selfBufTemplates = new HashMap<Integer, LegionMineSelfBufTemplate>();
	private Map<Integer, LegionMineRewardTemplate> rewardTemplates = new HashMap<Integer, LegionMineRewardTemplate>();
	/** 等级下限 - 战斗奖励模板列表 */
	private Map<Integer, List<LegionMineBattleRewardTemplate>> levelBattleTemplates = new HashMap<Integer, List<LegionMineBattleRewardTemplate>>();
	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		mineConstantsTemplates = templateService
				.getAll(LegionMineConstantsTemplate.class);
		mineTypeTemplates = templateService
				.getAll(LegionMineTypeTemplate.class);
		mineTemplates = templateService.getAll(LegionMineTemplate.class);
		legionBufTemplates = templateService.getAll(LegionBufTemplate.class);
		surroundStateTemplates = templateService
				.getAll(LegionMineSurroundStateTemplate.class);
		richRateTmplates = templateService
				.getAll(LegionMineRichRateTemplate.class);
		selfBufTemplates = templateService
				.getAll(LegionMineSelfBufTemplate.class);
		rewardTemplates = templateService
				.getAll(LegionMineRewardTemplate.class);
		for (LegionMineBattleRewardTemplate template : templateService.getAll(
				LegionMineBattleRewardTemplate.class).values()) {
			if (levelBattleTemplates.containsKey(template.getMinLevel())) {
				levelBattleTemplates.get(template.getMinLevel()).add(template);
			} else {
				List<LegionMineBattleRewardTemplate> templateList = new ArrayList<LegionMineBattleRewardTemplate>();
				templateList.add(template);
				levelBattleTemplates.put(template.getMinLevel(), templateList);
			}
		}
	}

	public Map<Integer, LegionMineTemplate> getLegionMineTemplates() {
		return mineTemplates;
	}

	public LegionMineTemplate getLegionMineTemplate(int mineIndex) {
		return mineTemplates.get(mineIndex);
	}

	public LegionMineConstantsTemplate getConstantsTemplate() {
		return mineConstantsTemplates
				.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID);
	}

	public Map<Integer, LegionBufTemplate> getLegionBufTemplates() {
		return legionBufTemplates;
	}

	public LegionBufTemplate getLegionBufTemplate(int occupyNum) {
		int bufType = 0;
		for (int id : legionBufTemplates.keySet()) {
			LegionBufTemplate template = legionBufTemplates.get(id);
			if (occupyNum > template.getMineNum()) {
				break;
			}
			bufType++;
		}
		return legionBufTemplates.get(bufType);
	}

	public Map<Integer, LegionMineSurroundStateTemplate> getSurroundStateTemplates() {
		return surroundStateTemplates;
	}

	public LegionMineSurroundStateTemplate getSurroundStateTemplate(
			double surroundRate) {
		int surroundState = 0;
		for (int id : surroundStateTemplates.keySet()) {
			LegionMineSurroundStateTemplate template = surroundStateTemplates
					.get(id);
			if (surroundRate >= template.getSurroundRate()) {
				surroundState++;
			}
			break;
		}
		return surroundStateTemplates.get(surroundState);
	}

	public Map<Integer, LegionMineRichRateTemplate> getRichRateTemplates() {
		return richRateTmplates;
	}

	public LegionMineTypeTemplate getMineTypeTemplate(int mineType) {
		return mineTypeTemplates.get(mineType);
	}

	public Map<Integer, LegionMineSelfBufTemplate> getSelfBufTemplates() {
		return selfBufTemplates;
	}

	public LegionMineSelfBufTemplate getSelfBufTemplate(int selfBufType) {
		return selfBufTemplates.get(selfBufType);
	}

	public LegionMineRewardTemplate getRewardTemplate(int rank) {
		for (int id : rewardTemplates.keySet()) {
			LegionMineRewardTemplate template = rewardTemplates.get(id);
			if (rank >= template.getRankLowest()
					&& rank <= template.getRankHighest()) {
				return rewardTemplates.get(id);
			}
		}
		return null;
	}

	/**
	 * 根据等级随机战斗奖励物品
	 */
	public KeyValuePair<Integer, Integer> getRandomBattleReward(int level) {
		KeyValuePair<Integer, Integer> battleReward = new KeyValuePair<Integer, Integer>();
		int limitLevel = 0;
		List<LegionMineBattleRewardTemplate> templateList = null;
		for (int minLevel : levelBattleTemplates.keySet()) {
			// 取最大的等级下限列表数据
			if (level >= minLevel && minLevel > limitLevel) {
				limitLevel = minLevel;
				templateList = levelBattleTemplates.get(minLevel);
			}
		}
		List<Integer> weights = new ArrayList<Integer>();
		for (LegionMineBattleRewardTemplate template : templateList) {
			weights.add(template.getRewardRate());
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(weights, 1);
		if (indexs != null) {
			for (int index : indexs) {
				LegionMineBattleRewardTemplate template = templateList
						.get(index);
				int itemId = template.getItemId();
				int itemNum = template.getItemNum();
				battleReward.setKey(itemId);
				battleReward.setValue(itemNum);
				return battleReward;
			}
		}
		return null;
	}
}
