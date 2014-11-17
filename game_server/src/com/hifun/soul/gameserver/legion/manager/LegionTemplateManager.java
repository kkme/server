package com.hifun.soul.gameserver.legion.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionFuncInfo;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;
import com.hifun.soul.gameserver.legion.template.LegionFuncTemplate;
import com.hifun.soul.gameserver.legion.template.LegionHonorBuildingTemplate;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;
import com.hifun.soul.gameserver.legion.template.LegionLevelTemplate;
import com.hifun.soul.gameserver.legion.template.LegionMeditationBuildingTemplate;
import com.hifun.soul.gameserver.legion.template.LegionMeditationLevelTemplate;
import com.hifun.soul.gameserver.legion.template.LegionMeditationTemplate;
import com.hifun.soul.gameserver.legion.template.LegionPositionTemplate;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;
import com.hifun.soul.gameserver.legion.template.LegionShopBuildingTemplate;
import com.hifun.soul.gameserver.legion.template.LegionShopTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTaskBuildingTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTaskRankTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTaskTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyBuildingTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTypeTemplate;
import com.hifun.soul.gameserver.role.Occupation;

/**
 * 军团模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class LegionTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, LegionPositionTemplate> legionPositionTemplates = new HashMap<Integer, LegionPositionTemplate>();
	private Map<Integer, LegionLevelTemplate> legionLevelTemplates = new HashMap<Integer, LegionLevelTemplate>();
	private Map<Integer, LegionRightTemplate> legionRightTemplates = new HashMap<Integer, LegionRightTemplate>();
	private Map<Integer, LegionConstantsTemplate> legionConstantsTemplates = new HashMap<Integer, LegionConstantsTemplate>();

	private Map<Integer, LegionMeditationBuildingTemplate> meditationBuildingTemplates = new HashMap<Integer, LegionMeditationBuildingTemplate>();
	private Map<Integer, LegionMeditationTemplate> meditationTemplates = new HashMap<Integer, LegionMeditationTemplate>();
	private Map<Integer, LegionMeditationLevelTemplate> meditationLevelTemplates = new HashMap<Integer, LegionMeditationLevelTemplate>();
	private Map<Integer, LegionTechnologyBuildingTemplate> technologyBuildingTemplates = new HashMap<Integer, LegionTechnologyBuildingTemplate>();
	private Map<Integer, LegionTechnologyTypeTemplate> technologyTypeTemplates = new HashMap<Integer, LegionTechnologyTypeTemplate>();
	private Map<Integer, LegionTechnologyTemplate> technologyTemplates = new HashMap<Integer, LegionTechnologyTemplate>();
	private Map<Integer, LegionShopBuildingTemplate> shopBuildingTemplates = new HashMap<Integer, LegionShopBuildingTemplate>();
	private Map<Integer, LegionShopTemplate> shopTemplates = new HashMap<Integer, LegionShopTemplate>();
	private Map<Integer, LegionHonorBuildingTemplate> honorBuildingTemplates = new HashMap<Integer, LegionHonorBuildingTemplate>();
	private Map<Integer, LegionHonorTemplate> honorTemplates = new HashMap<Integer, LegionHonorTemplate>();
	private Map<Integer, LegionTaskBuildingTemplate> taskBuildingTemplates = new HashMap<Integer, LegionTaskBuildingTemplate>();
	private Map<Integer, LegionTaskTemplate> taskTemplates = new HashMap<Integer, LegionTaskTemplate>();
	private Map<Integer, LegionTaskRankTemplate> taskRankTemplates = new HashMap<Integer, LegionTaskRankTemplate>();
	private Map<Integer, LegionFuncTemplate> funcTemplates = new HashMap<Integer, LegionFuncTemplate>();

	@Override
	public void init() {
		legionPositionTemplates = templateService
				.getAll(LegionPositionTemplate.class);
		legionLevelTemplates = templateService
				.getAll(LegionLevelTemplate.class);
		legionRightTemplates = templateService
				.getAll(LegionRightTemplate.class);
		legionConstantsTemplates = templateService
				.getAll(LegionConstantsTemplate.class);
		meditationBuildingTemplates = templateService
				.getAll(LegionMeditationBuildingTemplate.class);
		meditationTemplates = templateService
				.getAll(LegionMeditationTemplate.class);
		meditationLevelTemplates = templateService
				.getAll(LegionMeditationLevelTemplate.class);
		technologyBuildingTemplates = templateService
				.getAll(LegionTechnologyBuildingTemplate.class);
		technologyTypeTemplates = templateService
				.getAll(LegionTechnologyTypeTemplate.class);
		technologyTemplates = templateService
				.getAll(LegionTechnologyTemplate.class);
		shopBuildingTemplates = templateService
				.getAll(LegionShopBuildingTemplate.class);
		shopTemplates = templateService.getAll(LegionShopTemplate.class);
		honorBuildingTemplates = templateService
				.getAll(LegionHonorBuildingTemplate.class);
		honorTemplates = templateService.getAll(LegionHonorTemplate.class);
		taskBuildingTemplates = templateService
				.getAll(LegionTaskBuildingTemplate.class);
		taskTemplates = templateService.getAll(LegionTaskTemplate.class);
		taskRankTemplates = templateService
				.getAll(LegionTaskRankTemplate.class);
		funcTemplates = templateService.getAll(LegionFuncTemplate.class);
	}

	public LegionPositionTemplate getLegionPositionTemplate(int position) {
		return legionPositionTemplates.get(position);
	}

	/**
	 * 获得最高职位
	 */
	public int getHighestLegionPosition() {
		return getLegionPositionTemplate(legionPositionTemplates.size())
				.getId();
	}

	/**
	 * 获得最低职位
	 */
	public int getLowestLegionPosition() {
		return getLegionPositionTemplate(
				SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID).getId();
	}

	public LegionLevelTemplate getLegionLevelTemplate(int level) {
		return legionLevelTemplates.get(level);
	}

	/**
	 * 获取军团最大等级
	 */
	public int getLegionMaxLevel() {
		return legionLevelTemplates.size();
	}

	public LegionRightTemplate getLegionRightTemplate(int position) {
		return legionRightTemplates.get(position);
	}

	public LegionConstantsTemplate getConstantsTemplate() {
		return legionConstantsTemplates
				.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID);
	}

	/**
	 * 获取建筑最大等级
	 */
	public int getMaxBuildingLevel(LegionBuildingType buildingType) {
		switch (buildingType) {
		case MEDITATION:
			return meditationBuildingTemplates.size();
		case SHOP:
			return shopBuildingTemplates.size();
		case TECHNOLOGY:
			return technologyBuildingTemplates.size();
		case TASK:
			return taskBuildingTemplates.size();
		case HONOR:
			return honorBuildingTemplates.size();
		default:
			return 0;
		}
	}

	/**
	 * 获取升级建筑需要军团等级
	 */
	public int getUpgradeBuildingNeedLegionLevel(
			LegionBuildingType buildingType, int level) {
		if (level == 0) {
			level = 1;
		}
		switch (buildingType) {
		case MEDITATION:
			if (level > meditationBuildingTemplates.size()) {
				return 0;
			}
			return meditationBuildingTemplates.get(level).getNeedLegionLevel();
		case SHOP:
			if (level > shopBuildingTemplates.size()) {
				return 0;
			}
			return shopBuildingTemplates.get(level).getNeedLegionLevel();
		case TECHNOLOGY:
			if (level > technologyBuildingTemplates.size()) {
				return 0;
			}
			return technologyBuildingTemplates.get(level).getNeedLegionLevel();
		case TASK:
			if (level > taskBuildingTemplates.size()) {
				return 0;
			}
			return taskBuildingTemplates.get(level).getNeedLegionLevel();
		case HONOR:
			if (level > honorBuildingTemplates.size()) {
				return 0;
			}
			return honorBuildingTemplates.get(level).getNeedLegionLevel();
		default:
			return 0;
		}
	}

	/**
	 * 获取升级建筑消耗军团资金
	 */
	public int getUpgradeBuildingCost(LegionBuildingType buildingType, int level) {
		if (level == 0) {
			level = 1;
		}
		switch (buildingType) {
		case MEDITATION:
			if (level > meditationBuildingTemplates.size()) {
				return 0;
			}
			return meditationBuildingTemplates.get(level).getNeedLegionCoin();
		case SHOP:
			if (level > shopBuildingTemplates.size()) {
				return 0;
			}
			return shopBuildingTemplates.get(level).getNeedLegionCoin();
		case TECHNOLOGY:
			if (level > technologyBuildingTemplates.size()) {
				return 0;
			}
			return technologyBuildingTemplates.get(level).getNeedLegionCoin();
		case TASK:
			if (level > taskBuildingTemplates.size()) {
				return 0;
			}
			return taskBuildingTemplates.get(level).getNeedLegionCoin();
		case HONOR:
			if (level > honorBuildingTemplates.size()) {
				return 0;
			}
			return honorBuildingTemplates.get(level).getNeedLegionCoin();
		default:
			return 0;
		}
	}

	/**
	 * 获取建筑等级开放数量
	 */
	public int getBuildingOpenNum(LegionBuildingType buildingType, int level) {
		if (level <= 0) {
			level = 1;
		}
		switch (buildingType) {
		case MEDITATION:
			if (level > meditationBuildingTemplates.size()) {
				return 0;
			}
			return meditationBuildingTemplates.get(level).getAmendEffect();
		case SHOP:
			if (level > shopBuildingTemplates.size()) {
				return 0;
			}
			return shopBuildingTemplates.get(level).getSellItemNum();
		case TECHNOLOGY:
			if (level > technologyBuildingTemplates.size()) {
				return 0;
			}
			return technologyBuildingTemplates.get(level).getOpenTechNum();
		case TASK:
			if (level > taskBuildingTemplates.size()) {
				return 0;
			}
			return taskBuildingTemplates.get(level).getTaskNum();
		case HONOR:
			if (level > honorBuildingTemplates.size()) {
				return 0;
			}
			return honorBuildingTemplates.get(level).getExchangeNum();
		default:
			return 0;
		}
	}

	public Map<Integer, LegionMeditationTemplate> getMeditationTemplates() {
		return meditationTemplates;
	}

	public LegionMeditationTemplate getMeditationTemplate(int meditationType) {
		return meditationTemplates.get(meditationType);
	}

	public int getLegionMeditation(int level, int meditationType) {
		int meditation = 0;
		switch (meditationType) {
		// 小思
		case 1:
			meditation = meditationLevelTemplates.get(level)
					.getLittleThinkMeditation();
			break;
		case 2:
			meditation = meditationLevelTemplates.get(level)
					.getDeepThinkMeditation();
			break;
		case 3:
			meditation = meditationLevelTemplates.get(level)
					.getWeightThinkMeditation();
			break;
		}
		return meditation;
	}

	/**
	 * 根据商品ID获取模板
	 */
	public LegionShopTemplate getShopTemplate(int itemId) {
		for (Integer id : shopTemplates.keySet()) {
			LegionShopTemplate template = shopTemplates.get(id);
			if (template.getItemId() == itemId) {
				return shopTemplates.get(id);
			}
		}
		return null;
	}

	/**
	 * 根据玩家等级和职业获取可见的军团物品模板
	 */
	public List<LegionShopTemplate> getShopTemplateList(int buildingLevel,
			int humanLevel, int occupation) {
		List<LegionShopTemplate> templateList = new ArrayList<LegionShopTemplate>();
		for (Integer id : shopTemplates.keySet()) {
			LegionShopTemplate template = shopTemplates.get(id);
			if (buildingLevel >= template.getNeedBuildingLevel()) {
				CommonItem commonItem = CommonItemBuilder
						.genCommonItem(template.getItemId());
				if (canSee(commonItem, humanLevel, occupation)) {
					templateList.add(template);
				}
			}
		}
		return templateList;
	}

	/**
	 * 是否可见
	 * 
	 */
	public boolean canSee(CommonItem commonItem, int level, int occupation) {
		if (level >= commonItem.getCanSeeLevelLimit()
				&& (occupation == commonItem.getCanSeeOccupationLimit() || Occupation
						.typeOf(commonItem.getCanSeeOccupationLimit()) == null)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取展示到商店的某类物品模板
	 */
	public List<LegionShopTemplate> getShopTemplateListByItemType(
			int buildingLevel, int itemType, int humanLevel, int occupation) {
		if (itemType == 0) {
			return getShopTemplateList(buildingLevel, humanLevel, occupation);
		}
		List<LegionShopTemplate> allTemplateList = getShopTemplateList(
				buildingLevel, humanLevel, occupation);
		List<LegionShopTemplate> returnTemplateList = new ArrayList<LegionShopTemplate>();
		for (LegionShopTemplate template : allTemplateList) {
			if (template.getItemType() == itemType) {
				returnTemplateList.add(template);
			}
		}
		return returnTemplateList;
	}

	/**
	 * 获取展示到商店的物品分类
	 */
	public Set<Integer> getShopItemTypes(int buildingLevel, int humanLevel,
			int occupation) {
		Map<Integer, Integer> typeMap = new HashMap<Integer, Integer>();
		List<LegionShopTemplate> templateList = getShopTemplateList(
				buildingLevel, humanLevel, occupation);
		for (LegionShopTemplate template : templateList) {
			typeMap.put(template.getItemType(), 1);
		}
		return typeMap.keySet();
	}

	/**
	 * 获取所有开启的科技类型模板
	 */
	public List<LegionTechnologyTypeTemplate> getTechnologyTypeTemplates(
			int buildingLevel) {
		List<LegionTechnologyTypeTemplate> typeTemplateList = new ArrayList<LegionTechnologyTypeTemplate>();
		for (Integer technologyType : technologyTypeTemplates.keySet()) {
			if (buildingLevel >= technologyTypeTemplates.get(technologyType)
					.getNeedBuildingLevel()) {
				typeTemplateList.add(technologyTypeTemplates
						.get(technologyType));
			}
		}
		return typeTemplateList;
	}

	/**
	 * 获取某个科技类型模板
	 */
	public LegionTechnologyTypeTemplate getTechnologyTypeTemplate(
			int technologyType) {
		return technologyTypeTemplates.get(technologyType);
	}

	/**
	 * 获取科技升级模板
	 */
	public LegionTechnologyTemplate getTechnologyTemplate(int technologyType,
			int technologyLevel) {
		for (Integer id : technologyTemplates.keySet()) {
			LegionTechnologyTemplate template = technologyTemplates.get(id);
			if (template.getTechnologyType() == technologyType
					&& template.getTechnologyLevel() == technologyLevel) {
				return template;
			}
		}
		return null;
	}

	/**
	 * 获取科技的最高级别
	 */
	public int getTechnologyMaxLevel(int technologyType) {
		int maxLevel = 0;
		for (Integer id : technologyTemplates.keySet()) {
			LegionTechnologyTemplate template = technologyTemplates.get(id);
			if (template.getTechnologyType() == technologyType) {
				maxLevel++;
			}
		}
		return maxLevel;
	}

	/**
	 * 获取开启的头衔模板
	 */
	public List<LegionHonorTemplate> getHonorTemplateList(int buildingLevel) {
		List<LegionHonorTemplate> templateList = new ArrayList<LegionHonorTemplate>();
		for (Integer titleId : honorTemplates.keySet()) {
			if (buildingLevel >= honorTemplates.get(titleId)
					.getNeedBuildingLevle()) {
				templateList.add(honorTemplates.get(titleId));
			}
		}
		return templateList;
	}

	/**
	 * 获取某个头衔模板
	 */
	public LegionHonorTemplate getHonorTemplate(int titleId) {
		return honorTemplates.get(titleId);
	}

	/**
	 * 获取所有任务主题
	 */
	public Map<Integer, String> getTaskThemes() {
		Map<Integer, String> taskThemeMap = new HashMap<Integer, String>();
		for (Integer id : taskTemplates.keySet()) {
			LegionTaskTemplate template = taskTemplates.get(id);
			taskThemeMap.put(template.getShemeType(), template.getShemeName());
		}
		return taskThemeMap;
	}

	public LegionTaskTemplate getTaskTemplate(int templateId) {
		return taskTemplates.get(templateId);
	}

	public List<LegionTaskTemplate> getTaskTemplateList() {
		return new ArrayList<LegionTaskTemplate>(taskTemplates.values());
	}

	public LegionTaskRankTemplate getTaskRankTemplate(int rank) {
		return taskRankTemplates.get(rank);
	}

	public LegionFuncTemplate getFuncTemplate(LegionBuildingType buildingType) {
		return funcTemplates.get(buildingType.getIndex());
	}

	/**
	 * 获取军团子功能开启信息
	 */
	public List<LegionFuncInfo> getLegionFuncInfos() {
		List<LegionFuncInfo> funcInfoList = new ArrayList<LegionFuncInfo>();
		for (Integer buildingType : funcTemplates.keySet()) {
			LegionFuncInfo funcInfo = new LegionFuncInfo();
			funcInfo.setBuildingType(buildingType);
			funcInfo.setOpenLegionLevel(funcTemplates.get(buildingType)
					.getOpenLegionLevel());
			funcInfoList.add(funcInfo);
		}
		return funcInfoList;
	}
}
