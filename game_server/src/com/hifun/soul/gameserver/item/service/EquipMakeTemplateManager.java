package com.hifun.soul.gameserver.item.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.template.EquipMakeGuideTemplate;
import com.hifun.soul.gameserver.item.template.EquipMakeTemplate;

/**
 * 装备制作模板管理器
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class EquipMakeTemplateManager implements IInitializeRequired  {
	private Map<Integer,EquipMakeTemplate> equipMakeTemplates = new HashMap<Integer,EquipMakeTemplate>();
	@Autowired
	private TemplateService templateService;
	/** key：打造装备的id */
	private Map<Integer,EquipMakeTemplate> templates = new HashMap<Integer,EquipMakeTemplate>();
	private Map<Integer,EquipMakeGuideTemplate> equipMakeGuideTemplates = new HashMap<Integer, EquipMakeGuideTemplate>();
	
	@Override
	public void init() {
		Map<Integer,EquipMakeTemplate> makeTemplates = templateService.getAll(EquipMakeTemplate.class);
		equipMakeGuideTemplates = templateService.getAll(EquipMakeGuideTemplate.class);
		for(Integer id: makeTemplates.keySet()){
			equipMakeTemplates.put(makeTemplates.get(id).getPaperId(), makeTemplates.get(id));
			templates.put(makeTemplates.get(id).getNeedEquipId(), makeTemplates.get(id));
		}
		
	}
	
	/**
	 * 根据图纸id获取制作装备的模板
	 * 
	 * @param paperId
	 * @return
	 */
	public EquipMakeTemplate getEquipMakeTemplate(int paperId){
		return this.equipMakeTemplates.get(paperId);
	}
	
	/**
	 * 获取该装备进化的模版
	 * 
	 * @param equipId
	 * @return
	 */
	public EquipMakeTemplate getEquipMakeTemplateByEquipId(int equipId){
		return this.templates.get(equipId);
	}
	
	/**
	 * 配置的材料引导
	 * 
	 * @param itemId
	 * @return
	 */
	public EquipMakeGuideTemplate getEquipMakeGuideTemplate(int itemId){
		return equipMakeGuideTemplates.get(itemId);
	}

}
