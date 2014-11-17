package com.hifun.soul.gameserver.role.properties.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.occupation.template.CharacterPropConverterRateTemplate;

/**
 * 角色一二级属性转化管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class PropertyConverterRateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, Map<Integer, CharacterPropConverterRateTemplate>> level1ToLevel2Rates = new HashMap<Integer, Map<Integer, CharacterPropConverterRateTemplate>>();

	@Override
	public void init() {
		Map<Integer, CharacterPropConverterRateTemplate> convertTemplate = templateService
				.getAll(CharacterPropConverterRateTemplate.class);
		// 按照职业拆分
		for (Map.Entry<Integer, CharacterPropConverterRateTemplate> each : convertTemplate
				.entrySet()) {
			if (level1ToLevel2Rates.get(each.getValue().getOccupation()) == null) {
				level1ToLevel2Rates
						.put(each.getValue().getOccupation(),
								new HashMap<Integer, CharacterPropConverterRateTemplate>());
			}
			level1ToLevel2Rates.get(each.getValue().getOccupation()).put(
					each.getValue().getLevel1PropIndex(), each.getValue());
		}
	}

	/**
	 * 获取职业属性转换比率映射;
	 * 
	 * @param occupation
	 * @return
	 */
	public Map<Integer, CharacterPropConverterRateTemplate> getOccupationPropConvertRateMap(
			int occupation) {
		return level1ToLevel2Rates.get(occupation);
	}

}
