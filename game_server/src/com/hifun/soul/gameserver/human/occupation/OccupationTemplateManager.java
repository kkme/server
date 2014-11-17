package com.hifun.soul.gameserver.human.occupation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate;
import com.hifun.soul.gameserver.role.Occupation;

/**
 * 职业数据管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class OccupationTemplateManager implements IInitializeRequired {
	private Map<Integer, CharacterOccupationTemplate> occupationTemplates;
	@Autowired
	private TemplateService service;
	
	@Override
	public void init() {
		occupationTemplates = service.getAll(CharacterOccupationTemplate.class);		
	}

	public Collection<CharacterOccupationTemplate> getAll() {
		return Collections.unmodifiableCollection(this.occupationTemplates
				.values());
	}

	public CharacterOccupationTemplate getOccupationTemplateByOccupation(
			Occupation occupation) {
		for (CharacterOccupationTemplate each : occupationTemplates.values()) {
			if (each.getOccupationType() == occupation.getType()) {
				return each;
			}
		}
		return null;
	}

}
