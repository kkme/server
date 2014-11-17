package com.hifun.soul.gameserver.mine.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.mine.template.MineConsumeTemplate;
import com.hifun.soul.gameserver.mine.template.MineFieldRateTemplate;
import com.hifun.soul.gameserver.mine.template.MineFieldTypeTemplate;
import com.hifun.soul.gameserver.mine.template.MineLevelTemplate;

/**
 * 矿场模板管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class MineTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	private Map<Integer, MineConsumeTemplate> mineConsumeTemplates;
	private Map<Integer, MineFieldTypeTemplate> mineFieldTypeTemplates;
	private Map<Integer, MineLevelTemplate> mineLevelTemplates;
	/** key：角色等级下限 ,value:可能出现的怪物id */
	private Map<Integer, List<Integer>> levelAndMonsters = new HashMap<Integer, List<Integer>>();
	/** key：角色等级下限 ,value:可能出现的矿坑类型 */
	private Map<Integer, List<Integer>> levelAndMineFieldTypes = new HashMap<Integer, List<Integer>>();
	/** key：角色等级下限 ,value:可能出现的矿坑类型权重 */
	private Map<Integer, List<Integer>> levelAndMineFieldTypeWeights = new HashMap<Integer, List<Integer>>();

	@Override
	public void init() {
		mineConsumeTemplates = templateService.getAll(MineConsumeTemplate.class);
		mineFieldTypeTemplates = templateService.getAll(MineFieldTypeTemplate.class);
		mineLevelTemplates = templateService.getAll(MineLevelTemplate.class);
		Map<Integer, MineFieldRateTemplate> mineFieldRateTemplates = templateService
				.getAll(MineFieldRateTemplate.class);
		for (MineFieldRateTemplate rateTemplate : mineFieldRateTemplates.values()) {
			int level = rateTemplate.getLevelLimit();
			if (rateTemplate.getMonsterId() != 0) {
				if (levelAndMonsters.containsKey(level)) {
					levelAndMonsters.get(level).add(rateTemplate.getMonsterId());
				} else {
					List<Integer> monsterIdList = new ArrayList<Integer>();
					monsterIdList.add(rateTemplate.getMonsterId());
					levelAndMonsters.put(level, monsterIdList);
				}
			}
			if (levelAndMineFieldTypes.containsKey(level)) {
				levelAndMineFieldTypes.get(level).add(rateTemplate.getMineFieldTypeId());
			} else {
				List<Integer> MineFieldTypeList = new ArrayList<Integer>();
				MineFieldTypeList.add(rateTemplate.getMineFieldTypeId());
				levelAndMineFieldTypes.put(level, MineFieldTypeList);
			}
			if (levelAndMineFieldTypeWeights.containsKey(level)) {
				levelAndMineFieldTypeWeights.get(level).add(rateTemplate.getMineFieldTypeWeight());
			} else {
				List<Integer> MineFieldTypeWeightList = new ArrayList<Integer>();
				MineFieldTypeWeightList.add(rateTemplate.getMineFieldTypeWeight());
				levelAndMineFieldTypeWeights.put(level, MineFieldTypeWeightList);
			}
		}

	}

	/**
	 * 获取矿场消费模板
	 * 
	 * @param buyTimes
	 * @return
	 */
	public MineConsumeTemplate getMineConsumeTemplate(int buyTimes) {
		if (buyTimes <= 0) {
			return null;
		}
		if (!mineConsumeTemplates.containsKey(buyTimes)) {
			return getMineConsumeTemplate(buyTimes - 1);
		}
		return mineConsumeTemplates.get(buyTimes);
	}

	/**
	 * 获取矿坑类型模板
	 * 
	 * @param mineFieldtype
	 * @return
	 */
	public MineFieldTypeTemplate getMineFieldTypeTemplate(int mineFieldType) {
		if (!mineFieldTypeTemplates.containsKey(mineFieldType)) {
			return null;
		}
		return mineFieldTypeTemplates.get(mineFieldType);
	}
	/**
	 * 获取矿坑等级成长模板
	 * @param level
	 * @return
	 */
	public MineLevelTemplate getMineLevelTemplate(int level) {
		return mineLevelTemplates.get(level);
	}
	
	/**
	 * 随机一个矿坑类型
	 * 
	 * @param level
	 *            角色等级
	 * @return
	 */
	public int randomMineFieldType(int level) {
		if (level <= 0) {
			return 0;
		}
		int limitLevel = 0;
		List<Integer> types = null;
		List<Integer> typeWeights = null;
		for (Integer key : levelAndMineFieldTypes.keySet()) {
			if (level >= key) {
				if (key > limitLevel) {
					limitLevel = key;
					types = levelAndMineFieldTypes.get(key);
					typeWeights = levelAndMineFieldTypeWeights.get(key);
				}
			}
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(typeWeights, 1);
		return types.get(indexs[0]);
	}

	/**
	 * 随机一个怪物id
	 * 
	 * @param level
	 *            角色等级
	 * @return
	 */
	public int randomMineMonsterId(int level) {
		if (level <= 0) {
			return 0;
		}
		int limitLevel = 0;
		List<Integer> monsterIds = null;
		for (Integer key : levelAndMonsters.keySet()) {			
			if (level >= key) {
				if (key > limitLevel) {
					limitLevel = key;
					monsterIds = levelAndMonsters.get(key);
				}
			}
		}
		Integer[] monsterIdArray = monsterIds.toArray(new Integer[0]);
		int monsterId = MathUtils.randomFromArray(monsterIdArray);
		return monsterId;
	}

}
