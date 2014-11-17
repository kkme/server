package com.hifun.soul.gameserver.title;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.title.template.HumanTitleTemplate;

/**
 * 军衔模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class TitleTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, HumanTitleTemplate> titleTemplates = new HashMap<Integer, HumanTitleTemplate>();

	@Override
	public void init() {
		titleTemplates = templateService.getAll(HumanTitleTemplate.class);
	}

	public HumanTitleTemplate getHumanTitleTemplate(int title) {
		int maxLevel = getMaxTitleLevel();
		if (title > maxLevel) {
			title = maxLevel;
		}
		return titleTemplates.get(title);
	}

	public List<HumanTitleTemplate> getAllTitleTemplates() {
		List<HumanTitleTemplate> list = new ArrayList<HumanTitleTemplate>();
		for (int id : titleTemplates.keySet()) {
			HumanTitleTemplate t = titleTemplates.get(id);
			list.add(t);
		}
		Collections.sort(list);
		return list;
	}

	public int getMaxTitleLevel() {
		// 由于加上了0级配置，所以最高级减一
		return titleTemplates.size() - 1;
	}

	/**
	 * 获取升衔所需威望
	 */
	public int getTitleUpNeedPrestige(int currentTitle) {
		if (currentTitle > getMaxTitleLevel()) {
			return 0;
		}
		return getHumanTitleTemplate(currentTitle).getNeedPrestige();
	}

	/**
	 * 获取当前军衔的荣誉上限
	 */
	public int getTitleMaxHonor(int currentTitle) {
		return getHumanTitleTemplate(currentTitle).getMaxHonor();
	}

	/**
	 * 获取技能槽开启军衔等级
	 */
	public int getSkillSlotOpenTitle(int slotIndex) {
		List<HumanTitleTemplate> list = getAllTitleTemplates();
		for (HumanTitleTemplate template : list) {
			if (slotIndex + 1 <= template.getTitleSkillNum()) {
				return template.getId();
			}
		}
		return 0;
	}

	/**
	 * 获得军衔名称
	 */
	public String getTitleName(int title) {
		HumanTitleTemplate template = getHumanTitleTemplate(title);
		if (template != null) {
			return template.getTitleName();
		}
		return "";
	}
}
