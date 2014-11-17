package com.hifun.soul.gameserver.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;

/**
 * 技能数据管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class SkillManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** 按照职业分类的技能映射 */
	private Map<Integer, List<SkillTemplate>> occupationSkills = new HashMap<Integer, List<SkillTemplate>>();

	@Override
	public void init() {
		Map<Integer, SkillTemplate> skills = this.templateService
				.getAll(SkillTemplate.class);
		// 按照职业把技能分类
		for (SkillTemplate skill : skills.values()) {
			if (occupationSkills.get(skill.getOccupation()) == null) {
				occupationSkills.put(skill.getOccupation(),
						new ArrayList<SkillTemplate>());
			}
			occupationSkills.get(skill.getOccupation()).add(skill);
		}
	}

	/**
	 * 根据职业和玩家当前的等级, 获取开启的技能列表;
	 * 
	 * @param occupation
	 *            职业;
	 * @param level
	 *            等级;
	 * @return 不会返回null;
	 */
	public List<SkillTemplate> getOccupationCurrentLevelSkillList(
			Occupation occupation, int level) {
		List<SkillTemplate> result = new ArrayList<SkillTemplate>();
		for (SkillTemplate skill : this.occupationSkills.get(occupation
				.getIndex())) {
			// 策划需求把等级限制去掉 edited by cfh 2012/12/28
//			if (skill.getOpenLevel() > level) {
//				continue;
//			}
			result.add(skill);
		}
		return result;
	}

}
