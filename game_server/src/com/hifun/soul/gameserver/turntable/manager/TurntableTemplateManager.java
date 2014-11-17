package com.hifun.soul.gameserver.turntable.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.ConfigException;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.turntable.template.TurntableTemplate;
import com.hifun.soul.gameserver.vip.template.TurntableCostTemplate;

@Scope("singleton")
@Component
public class TurntableTemplateManager implements IInitializeRequired {
	private Map<Integer,TurntableCostTemplate> turntableCostTemplates;
	private TurntableTemplate turntableTemplate = null;
	@Autowired
	private TemplateService templateService;
	
	@Override
	public void init() {
		turntableCostTemplates = templateService.getAll(TurntableCostTemplate.class);
		turntableTemplate = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, TurntableTemplate.class);
		if(turntableTemplate == null){
			throw new ConfigException("cannot find turntable template !");
		}
	}
	
	public TurntableTemplate getTurntableTemplate(){
		return turntableTemplate;
	}
	
	public int getTurntableCrystalCost(int times){
		if(times<=0){
			return 0;
		}
		if(turntableCostTemplates.containsKey(times)){
			return turntableCostTemplates.get(times).getCrystalCost();
		}else{
			int maxCost = 0;
			for(TurntableCostTemplate template :turntableCostTemplates.values()) {
				if(maxCost<template.getCrystalCost()){
					maxCost = template.getCrystalCost();
				}
			}
			return maxCost;
		}
	}
	
}
