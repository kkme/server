package com.hifun.soul.gameserver.item.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.item.template.ItemForgeRandomTemplate;
import com.hifun.soul.gameserver.item.template.ItemForgeTemplate;

/**
 * 装备强化模版管理器
 * @author magicstone
 */
@Scope("singleton")
@Component
public class ForgeTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** key:quality&lockNum value:ItemForgeTemplate */
	private Map<String,ItemForgeTemplate> itemForgeTemplateMap = new HashMap<String,ItemForgeTemplate>();
	/** 洗练随机属性配置 */
	private Map<Integer,ItemForgeRandomTemplate> itemForgeRandomTemplateMap = new HashMap<Integer,ItemForgeRandomTemplate>();
	
	@Override
	public void init() {
		initItemForgeTemplateMap();
		initItemForgeRandomTemplateMap();
	}

	/**
	 * 灵石洗炼模版初始化
	 */
	private void initItemForgeTemplateMap() {
		for(ItemForgeTemplate itemForgeTemplate : templateService.getAll(ItemForgeTemplate.class).values()){
			itemForgeTemplateMap.put(genKey(itemForgeTemplate.getItemQuality(),itemForgeTemplate.getLockNum()), itemForgeTemplate);
		}
	}
	
	private void initItemForgeRandomTemplateMap() {
		for(ItemForgeRandomTemplate itemForgeRandomTemplate : templateService.getAll(ItemForgeRandomTemplate.class).values()){
			itemForgeRandomTemplateMap.put(itemForgeRandomTemplate.getId(),itemForgeRandomTemplate);
		}
	}
	
	private String genKey(int equipId, int level) {
		return equipId + "&" + level;
	}
	
	/**
	 * 获取灵石洗炼的模版
	 * @param quality
	 * @param lockNum
	 * @return
	 */
	public ItemForgeTemplate getItemForgeTemplate(int quality, int lockNum) {
		return itemForgeTemplateMap.get(genKey(quality,lockNum));
	}
	
	/**
	 * 获得随机到的洗练模版
	 * @return
	 */
	public ItemForgeRandomTemplate getItemForgeRandomTemplate() {
		int value = MathUtils.random(0, (int)SharedConstants.DEFAULT_FLOAT_BASE);
		for(ItemForgeRandomTemplate itemForgeRandomTemplate : itemForgeRandomTemplateMap.values()){
			if(value >= itemForgeRandomTemplate.getMinRate()
					&& value <= itemForgeRandomTemplate.getMaxRate()){
				return itemForgeRandomTemplate;
			}
		}
		return null;
	}
}
