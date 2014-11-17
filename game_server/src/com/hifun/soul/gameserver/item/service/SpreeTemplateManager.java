package com.hifun.soul.gameserver.item.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.template.SpreeTemplate;

/**
 * 礼包模板管理器
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class SpreeTemplateManager implements IInitializeRequired {

	private Map<Integer,SpreeTemplate> spreeTemplates = new HashMap<Integer,SpreeTemplate>();
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		this.spreeTemplates = templateService.getAll(SpreeTemplate.class);		
	}
	
	public SpreeTemplate getSpreeTemplate(int itemId){
		return spreeTemplates.get(itemId);
	}

}
