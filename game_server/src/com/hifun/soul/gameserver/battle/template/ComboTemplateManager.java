package com.hifun.soul.gameserver.battle.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.template.TemplateService;

/**
 * 连击模版管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class ComboTemplateManager {
	@Autowired
	private TemplateService templateService;
	private List<ComboTemplate> sortedList = new ArrayList<ComboTemplate>();

	public void init() {
		for (ComboTemplate combo : this.templateService.getAll(
				ComboTemplate.class).values()) {
			sortedList.add(combo);
		}
		// 排序
		// Collections.sort(sortedList, new Comparator<ComboTemplate>() {
		//
		// @Override
		// public int compare(ComboTemplate o1, ComboTemplate o2) {
		// if (o1.getId() >= o2.getId()) {
		// return 1;
		// }
		// return -1;
		// }
		// });
	}

	/**
	 * 根据连击数获取连击伤害;
	 * 
	 * @param combo
	 *            连击数;
	 * @return
	 */
	public ComboTemplate getComboDamage(int combo) {
		if (combo < 0) {
			throw new IllegalArgumentException();
		}
		if (combo == 0) {
			return new ComboTemplate();
		}
		ComboTemplate template = templateService
				.get(combo, ComboTemplate.class);
		if (template == null) {
			// 此时返回最大值
			return sortedList.get(sortedList.size() - 1);
		}
		return template;
	}
}
