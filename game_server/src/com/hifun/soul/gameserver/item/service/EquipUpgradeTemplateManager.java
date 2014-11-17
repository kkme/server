package com.hifun.soul.gameserver.item.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.item.template.EquipUpgradeTemplate;

/**
 * 装备强化模版管理器
 * @author magicstone
 */
@Scope("singleton")
@Component
public class EquipUpgradeTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	
	/** key:equipId&level value:EquipUpgradeTemplate */
	private Map<String,EquipUpgradeTemplate> equipUpgradeTemplateMap = new HashMap<String,EquipUpgradeTemplate>();
	/** key:equipId&level value:EquipUpgradeTemplate */
	private Map<String,KeyValuePair<Integer,Integer>[]> equipUpgradeAttributesMap = new HashMap<String,KeyValuePair<Integer,Integer>[]>();
	
	@Override
	public void init() {
		Map<Integer,EquipUpgradeTemplate> equipUpgradeTemplates = templateService.getAll(EquipUpgradeTemplate.class);
		
		initEquipUpgradeTemplateMap(equipUpgradeTemplates);
		initEquipUpgradeAttributesMap(equipUpgradeTemplates);
	}

	private void initEquipUpgradeTemplateMap(Map<Integer,EquipUpgradeTemplate> equipUpgradeTemplates) {
		for(EquipUpgradeTemplate template : equipUpgradeTemplates.values()){
			equipUpgradeTemplateMap.put(genKey(template.getEquipId(), template.getLevel()), template);
		}
	}
	
	private void initEquipUpgradeAttributesMap(Map<Integer,EquipUpgradeTemplate> equipUpgradeTemplates) {
		for(EquipUpgradeTemplate template : equipUpgradeTemplates.values()){
			String prop = template.getProps();
			if(prop == null
					|| "".equals(prop.trim())){
				KeyValuePair<Integer,Integer>[] keyValuePairs = KeyValuePair.newKeyValuePairArray(0);
				equipUpgradeAttributesMap.put(genKey(template.getEquipId(), template.getLevel()), 
											keyValuePairs);
				continue;
			}
			
			String[] props = prop.split(";");
			if("0".equals(prop.trim())
					|| props == null
					|| props.length == 0){
				KeyValuePair<Integer,Integer>[] keyValuePairs = KeyValuePair.newKeyValuePairArray(0);
				equipUpgradeAttributesMap.put(genKey(template.getEquipId(), template.getLevel()), 
											keyValuePairs);
				continue;
			}
			
			KeyValuePair<Integer,Integer>[] keyValuePairs = KeyValuePair.newKeyValuePairArray(props.length);
			for(int i=0; i<props.length; i++){
				String[] keyVlaues = props[i].split("=");
				KeyValuePair<Integer,Integer> keyVlauePair = new KeyValuePair<Integer,Integer>();
				keyVlauePair.setKey(Integer.valueOf(keyVlaues[0].trim()));
				// editby:crazyjohn 2014-03-12这里改为可以读取浮点型, 然后转化成int
				keyVlauePair.setValue(Double.valueOf(keyVlaues[1].trim()).intValue());
				
				keyValuePairs[i]=keyVlauePair;
			}
			equipUpgradeAttributesMap.put(genKey(template.getEquipId(), template.getLevel()), keyValuePairs);
			
		}
		return;
	}
	
	private String genKey(int equipId, int level) {
		return equipId + "&" + level;
	}
	
	/**
	 * 获取装备对应等级的强化模版
	 * 
	 * @param equipId
	 * @param level
	 * @return
	 */
	public EquipUpgradeTemplate getEquipUpgradeTemplate(int equipId, int level) {
		return equipUpgradeTemplateMap.get(genKey(equipId,level));
	}
	
	/**
	 * 获取装备强化属性
	 * 
	 * @param equipId
	 * @param level
	 * @return
	 */
	public KeyValuePair<Integer,Integer>[] getEquipUpgradeAttributes(int equipId, int level) {
		KeyValuePair<Integer,Integer>[] keyVlauePairs = equipUpgradeAttributesMap.get(genKey(equipId, level));
		if(keyVlauePairs == null){
			return KeyValuePair.newKeyValuePairArray(0);
		}
		
		return keyVlauePairs;
	}
}
