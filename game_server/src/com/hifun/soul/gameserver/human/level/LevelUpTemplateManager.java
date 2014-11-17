package com.hifun.soul.gameserver.human.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.template.HumanLevelUpTemplate;

@Scope("singleton")
@Component
public class LevelUpTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	Map<Integer, HumanLevelUpTemplate> templates = new HashMap<Integer,HumanLevelUpTemplate>();
	private List<HumanLevelUpTemplate> sortedLevelUpTemplates = new ArrayList<HumanLevelUpTemplate>();
	private int maxLevel = 0;
	@Override
	public void init() {
		templates = this.templateService
				.getAll(HumanLevelUpTemplate.class);
		for (HumanLevelUpTemplate each : templates.values()) {
			this.sortedLevelUpTemplates.add(each);
			if(each.getId()>maxLevel){
				maxLevel = each.getId();
			}
		}

		Collections.sort(this.sortedLevelUpTemplates,
				new Comparator<HumanLevelUpTemplate>() {

					@Override
					public int compare(HumanLevelUpTemplate o1,
							HumanLevelUpTemplate o2) {
						if (o1.getExperience() < o2.getExperience()) {
							return -1;
						}
						if (o1.getExperience() > o2.getExperience()) {
							return 1;
						}
						return 0;
					}

				});
	}

	public List<HumanLevelUpTemplate> getSortedTemplates() {
		return Collections.unmodifiableList(this.sortedLevelUpTemplates);
	}

	public HumanLevelUpTemplate getMaxLevelTemplate() {
		return sortedLevelUpTemplates
				.get(this.sortedLevelUpTemplates.size() - 1);
	}
	
	/**
	 * 根据等级获取相应的税收数量
	 * @param level
	 * @return
	 */
	public int getLevyRevenue(int level){
		return templates.get(level).getLevyRevenue();
	}
	
	/**
	 * 根据角色等级获取宝石的产量
	 * 
	 * @param roleLevel 角色等级
	 * @return 角色等级对应的宝石产量
	 */
	public int getHarvestGemNum(int level){
		return templates.get(level).getHarvestGemNum();
	}
	
	/**
	 * 获取配置的角色最大等级
	 * @return
	 */
	public int getHumanMaxLevel(){
		return maxLevel;
	}
	
	/**
	 * 获取level应该有的属性点
	 * @param level
	 * @return
	 */
	public int getTotalPropPoints(int level) {
		int propPoints = 0;
		for(HumanLevelUpTemplate template : templates.values()){
			if(level > template.getId()){
				propPoints += template.getLevel1propCount();
			}
		}
		return propPoints;
	}
	
	/**
	 * 获取level应该有的技能点
	 * @param level
	 * @return
	 */
	public int getTotalSkillPoints(int level) {
		int skillPoints = 0;
		for(HumanLevelUpTemplate template : templates.values()){
			if(level > template.getId()){
				skillPoints += template.getSkillPoint();
			}
		}
		return skillPoints;
	}
}
