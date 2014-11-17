package com.hifun.soul.gameserver.skill.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.RoleType;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.skill.SkillInstance;
import com.hifun.soul.gameserver.skill.SkillStateType;
import com.hifun.soul.gameserver.skill.SkillType;

/**
 * 技能模版数据管理器;<br>
 * FIXME: crazyjohn 技能排序;
 * 
 * @author crazyjohn
 */
@Scope("singleton")
@Component
public class SkillTemplateManager {
	/** 角色技能按职业分类 */
	private Map<Occupation, List<SkillTemplate>> occupationSkills = new HashMap<Occupation, List<SkillTemplate>>();
	/** 怪物技能 */
	private Map<Integer, SkillTemplate> monsterSkills = new HashMap<Integer, SkillTemplate>();
	/** key:技能卷轴的id */
	private Map<Integer, SkillScrollTemplate> skillSlotTemplates = new HashMap<Integer, SkillScrollTemplate>();
	/** key:skillId */
	private Map<Integer, SkillTemplate> skillTemplateMap = new HashMap<Integer, SkillTemplate>();
	/** key:slotIndex */
	private Map<Integer, SkillSlotTemplate> skillSlotTemplateMap = new HashMap<Integer, SkillSlotTemplate>();
	/** key:skillId */
	private Map<Integer, SkillScrollTemplate> skillScrollTemplateMap = new HashMap<Integer, SkillScrollTemplate>();
	/** 技能发展类型信息 */
	private Map<Integer, ArrayList<SkillDevelopInfo>> skillDevelopInfoMap = new HashMap<Integer, ArrayList<SkillDevelopInfo>>();
	/** 职业对应的初始技能 */
	private Map<Integer, Integer> defaultSkillIdMap = new HashMap<Integer, Integer>();
	/** 职业TO技能系魔法信息 */
	private Map<Integer, Map<Integer, List<SkillOccupationTemplate>>> occupationToMagicMap = new HashMap<Integer, Map<Integer, List<SkillOccupationTemplate>>>();

	@Autowired
	private TemplateService templateService;

	public void init() {
		// 初始化技能发展类型
		initSkillDevelopInfoMap();
		skillTemplateMap = templateService.getAll(SkillTemplate.class);
		skillSlotTemplateMap = templateService.getAll(SkillSlotTemplate.class);
		skillScrollTemplateMap = templateService
				.getAll(SkillScrollTemplate.class);
		// 初始化技能卷轴模版数据,构造以技能卷轴id为key的hashMap
		initSkillSlotTemplateMap();

		for (SkillTemplate template : templateService.getAll(
				SkillTemplate.class).values()) {
			RoleType roleType = RoleType.typeOf(template.getHostType());
			if (roleType == null) {
				continue;
			}
			if (roleType == RoleType.MONSTER) {
				monsterSkills.put(template.getId(), template);
				continue;
			}
			Occupation occupation = Occupation.typeOf(template.getOccupation());
			if (occupation == null) {
				continue;
			}
			if (occupationSkills.get(occupation) == null) {
				occupationSkills
						.put(occupation, new ArrayList<SkillTemplate>());
			}
			occupationSkills.get(occupation).add(template);
			// 初始化初始技能信息
			SkillScrollTemplate skillScrollTemplate = skillScrollTemplateMap
					.get(template.getId());
			if (skillScrollTemplate != null
					&& skillScrollTemplate.getSkillDevelopType() == SkillDevelopType.DEFAULT
							.getIndex()) {
				defaultSkillIdMap.put(occupation.getIndex(), template.getId());
			}
		}
		// 职业技能系
		for (SkillOccupationTemplate eachMagic : this.templateService.getAll(
				SkillOccupationTemplate.class).values()) {
			// 取出职业信息
			Map<Integer, List<SkillOccupationTemplate>> skillTypeMap = this.occupationToMagicMap
					.get(eachMagic.getOccupation());
			if (skillTypeMap == null) {
				skillTypeMap = new HashMap<Integer, List<SkillOccupationTemplate>>();
				occupationToMagicMap.put(eachMagic.getOccupation(),
						skillTypeMap);
			}
			// 取出职业对应的技能系信息
			List<SkillOccupationTemplate> typeList = skillTypeMap.get(eachMagic
					.getSkillOccupationType());
			if (typeList == null) {
				typeList = new ArrayList<SkillOccupationTemplate>();
				skillTypeMap.put(eachMagic.getSkillOccupationType(), typeList);
			}
			typeList.add(eachMagic);
		}
	}

	private void initSkillDevelopInfoMap() {
		for (SkillDevelopTypeTemplate template : templateService.getAll(
				SkillDevelopTypeTemplate.class).values()) {
			SkillDevelopInfo skillDevelopInfo = new SkillDevelopInfo();
			skillDevelopInfo
					.setSkillDevelopType(template.getSkillDevelopType());
			skillDevelopInfo.setIcon(template.getIcon());
			skillDevelopInfo.setRecommendPointType(template
					.getRecommendPointType());
			skillDevelopInfo.setAssistCostMagic(template.getAssistCostMagic());
			skillDevelopInfo.setMainCostMagic(template.getMainCostMagic());
			ArrayList<SkillDevelopInfo> skillDevelopInfos = skillDevelopInfoMap
					.get(template.getOccupation());
			if (skillDevelopInfos == null) {
				skillDevelopInfos = new ArrayList<SkillDevelopInfo>();
			}
			skillDevelopInfos.add(skillDevelopInfo);
			skillDevelopInfoMap
					.put(template.getOccupation(), skillDevelopInfos);
		}
	}

	/**
	 * 技能发展类型信息
	 * 
	 * @return
	 */
	public SkillDevelopInfo[] getSkillDevelopInfos(int occupation) {
		if (this.skillDevelopInfoMap.get(occupation) == null) {
			return new SkillDevelopInfo[0];
		}
		return this.skillDevelopInfoMap.get(occupation).toArray(
				new SkillDevelopInfo[0]);
	}

	/**
	 * 构造以技能卷轴id为key的hashMap
	 * 
	 */
	private void initSkillSlotTemplateMap() {
		// for(SkillScrollTemplate template :
		// templateService.getAll(SkillScrollTemplate.class).values()){
		// skillSlotTemplates.put(template.getNeedSkillScrollId(), template);
		// }
	}

	public SkillTemplate getHumanNormalAttackSkill(Human owner) {
		List<SkillTemplate> skills = this.occupationSkills.get(owner
				.getOccupation());
		for (SkillTemplate skill : skills) {
			if (SkillType.typeOf(skill.getSkillType()) == SkillType.NORMAL_ATTACK) {
				return skill;
			}
		}
		return null;
	}

	public SkillTemplate getHumanComboAttackSkill(Human owner) {
		List<SkillTemplate> skills = this.occupationSkills.get(owner
				.getOccupation());
		for (SkillTemplate skill : skills) {
			if (SkillType.typeOf(skill.getSkillType()) == SkillType.COMBO_ATTACK) {
				return skill;
			}
		}
		return null;
	}

	/**
	 * 获取该职业的SkillType为OTHER的技能
	 * 
	 * @param owner
	 * @return
	 */
	public Map<Integer, ISkill> getHumanSuitableSkills(Human owner) {
		Map<Integer, ISkill> result = new HashMap<Integer, ISkill>();
		List<SkillTemplate> allSKills = this.occupationSkills.get(owner
				.getOccupation());
		for (SkillTemplate skill : allSKills) {
			// 不需要普通攻击和combo攻击
			if (skill.getSkillType() != SkillType.OTHER.getIndex()) {
				continue;
			}
			ISkill skillInstance = new SkillInstance(skill, owner);
			if (skill.getScrollTemplate() != null
					&& skill.getScrollTemplate().isDefaultOpen()) {
				// 部分技能默认开放
				skillInstance.setSkillState(SkillStateType.STUDYED.getIndex());
			}
			result.put(skill.getId(), skillInstance);
		}
		return result;
	}

	public SkillTemplate getSkillTemplate(int skillId) {
		return skillTemplateMap.get(skillId);
	}

	public SkillSlotTemplate getSlotTemplate(int slotIndex) {
		return skillSlotTemplateMap.get(slotIndex);
	}

	public SkillScrollTemplate getSkillScrollTemplateBySkillId(int skillId) {
		return skillScrollTemplateMap.get(skillId);
	}

	public SkillScrollTemplate getSkillScrollTemplateBySkillScrollId(
			int skillScrollId) {
		return skillSlotTemplates.get(skillScrollId);
	}

	/**
	 * 获取职业默认的技能
	 * 
	 * @param occupation
	 * @return
	 */
	public int getDefaultSkillId(int occupation) {
		return defaultSkillIdMap.get(occupation);
	}

	/**
	 * 获取等级魔法属性模版;
	 * 
	 * @param level
	 * @param occupation
	 * @param getskillDevelopType
	 * @return
	 */
	public SkillOccupationTemplate getLevelMagicPropTemplate(int level,
			Occupation occupation, SkillDevelopType getskillDevelopType) {
		for (SkillOccupationTemplate each : this.occupationToMagicMap.get(
				occupation.getIndex()).get(getskillDevelopType.getIndex())) {
			if (each.getLevel() == level) {
				return each;
			}
		}
		return null;
	}
}
