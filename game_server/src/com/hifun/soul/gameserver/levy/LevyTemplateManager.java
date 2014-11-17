package com.hifun.soul.gameserver.levy;

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
import com.hifun.soul.gameserver.levy.template.LevyAuraTemplate;
import com.hifun.soul.gameserver.levy.template.LevyBetRevenueTemplate;
import com.hifun.soul.gameserver.levy.template.LevyCertainWinCostTemplate;
import com.hifun.soul.gameserver.levy.template.MagicStoneCollectConsumeTemplate;
import com.hifun.soul.gameserver.levy.template.MagicStoneTemplate;
import com.hifun.soul.gameserver.levy.template.MainCityMonsterTemplate;

/**
 * 主城相关模板管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class LevyTemplateManager implements IInitializeRequired {
	private Map<Integer, MagicStoneTemplate> magicStoneTemplates;
	private Map<Integer, MagicStoneCollectConsumeTemplate> consumeTempaltes;
	@Autowired
	private TemplateService templateService;
	private int maxCostCrystalNum = 0;
	private MagicStoneTemplate[] allMagicStoneTemplateArray;
	private MagicStoneTemplate[] targetMagicStoneTemplateArray;
	private Map<Integer, LevyCertainWinCostTemplate> winCostTemplates;
	private Map<Integer, LevyBetRevenueTemplate> betRevenueTemplates;
	private Map<Integer, MainCityMonsterTemplate> monsterTemplates = new HashMap<Integer, MainCityMonsterTemplate>();
	private Map<Integer, LevyAuraTemplate> auraTemplates = new HashMap<Integer, LevyAuraTemplate>();

	@Override
	public void init() {
		magicStoneTemplates = templateService.getAll(MagicStoneTemplate.class);
		allMagicStoneTemplateArray = new MagicStoneTemplate[magicStoneTemplates
				.size()];
		List<MagicStoneTemplate> targetStoneTemplateList = new ArrayList<MagicStoneTemplate>();
		int index = 0;
		for (MagicStoneTemplate template : magicStoneTemplates.values()) {
			allMagicStoneTemplateArray[index] = template;
			index++;
			if (template.isInTarget()) {
				targetStoneTemplateList.add(template);
			}
		}
		targetMagicStoneTemplateArray = targetStoneTemplateList
				.toArray(new MagicStoneTemplate[0]);
		consumeTempaltes = templateService
				.getAll(MagicStoneCollectConsumeTemplate.class);
		for (MagicStoneCollectConsumeTemplate template : consumeTempaltes
				.values()) {
			if (template.getCostCrystalNum() > maxCostCrystalNum) {
				maxCostCrystalNum = template.getCostCrystalNum();
			}
		}
		winCostTemplates = templateService
				.getAll(LevyCertainWinCostTemplate.class);
		betRevenueTemplates = templateService
				.getAll(LevyBetRevenueTemplate.class);
		monsterTemplates = templateService
				.getAll(MainCityMonsterTemplate.class);
		auraTemplates = templateService.getAll(LevyAuraTemplate.class);
	}

	/**
	 * 根据已经花费魔晶收集次数获取需要花费的魔晶数
	 * 
	 * @param num
	 * @return
	 */
	public int getCollectCostCrystalNum(int num) {
		if (num <= 0) {
			return 0;
		}
		if (consumeTempaltes.containsKey(num)) {
			return consumeTempaltes.get(num).getCostCrystalNum();
		}
		return maxCostCrystalNum;
	}

	/**
	 * 随机获得魔法石数组
	 * 
	 * @param count
	 * @param isTarget
	 * @return
	 */
	public MagicStoneInfo[] getRandomMagicStones(int count, boolean isTarget) {
		MagicStoneTemplate[] range = isTarget ? targetMagicStoneTemplateArray
				: allMagicStoneTemplateArray;
		MagicStoneInfo[] magicStones = new MagicStoneInfo[count];
		for (int i = 0; i < count; i++) {
			MagicStoneTemplate template = MathUtils.randomFromArray(range);
			magicStones[i] = new MagicStoneInfo();
			magicStones[i].setId(template.getId());
			magicStones[i].setIcon(template.getIcon());
			magicStones[i].setCollected(false);
			magicStones[i].setTargetIndex(-1);
		}
		return magicStones;
	}

	/**
	 * 根据id获取魔法石模板
	 * 
	 * @param id
	 * @return
	 */
	public MagicStoneTemplate getMagicStoneTemplate(Integer id) {
		if (!magicStoneTemplates.containsKey(id)) {
			return null;
		}
		return magicStoneTemplates.get(id);
	}

	/**
	 * 根据次数获取必胜消耗
	 */
	public int getCertainWinCost(int num) {
		if (num > winCostTemplates.size()) {
			return 0;
		}
		return winCostTemplates.get(num).getCrystal();
	}

	/**
	 * 根据等级获得押注胜利的培养币
	 */
	public int getTrainCoin(int level) {
		return betRevenueTemplates.get(level).getTrainCoin();
	}

	/**
	 * 根据等级获取主城怪物模板
	 */
	public MainCityMonsterTemplate getMonsterTemplate(int level) {
		return monsterTemplates.get(level);
	}

	/**
	 * 根据等级和集齐的宝石数量获取通灵收益
	 */
	public int getAura(int level, int num) {
		LevyAuraTemplate template = auraTemplates.get(level);
		switch (num) {
		case 1:
			return template.getOneAura();
		case 2:
			return template.getTwoAura();
		case 3:
			return template.getThreeAura();
		case 4:
			return template.getFourAura();
		case HumanLevyManager.COLLECT_TARGET_SIZE:
			return template.getWinAura();
		}
		return 0;
	}
}
