package com.hifun.soul.gameserver.technology.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo;
import com.hifun.soul.gameserver.technology.msg.TechnologyInfo;
import com.hifun.soul.gameserver.technology.template.TechnologyLevelUpTemplate;
import com.hifun.soul.gameserver.technology.template.TechnologyTemplate;

/**
 * 
 * 科技模版数据管理
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class TechnologyTemplateManager implements IInitializeRequired {
	
	@Autowired
	private TemplateService templateService;
	private Map<String,TechnologyLevelUpTemplate> technologyTemplateMap = new HashMap<String,TechnologyLevelUpTemplate>();
	private Map<String,TechnologyDetailInfo> technologyDetailInfoMap = new HashMap<String,TechnologyDetailInfo>();
	private TechnologySorter sorter = new TechnologySorter();
	private Map<Integer,Integer> technologyMaxLevelMap = new HashMap<Integer,Integer>();
	private List<TechnologyTemplate> sortedTechTemplates ; 
	@Override
	public void init() {
		initTechnologyTemplateMap();
		
		initTechnologyDetailInfoMap();		
		
		sortedTechTemplates = new ArrayList<TechnologyTemplate>(templateService.getAll(TechnologyTemplate.class).values());
		Collections.sort(sortedTechTemplates,sorter);
	}
	
	private void initTechnologyTemplateMap() {
		Map<Integer,TechnologyLevelUpTemplate> technologyTemplates = templateService.getAll(TechnologyLevelUpTemplate.class);
		
		for(TechnologyLevelUpTemplate template : technologyTemplates.values()){
			technologyTemplateMap.put(genKey(template.getTechnologyId(), template.getLevel()), 
									  template);
		}
	}
	
	private void initTechnologyDetailInfoMap() {
		Map<Integer,TechnologyLevelUpTemplate> technologyTemplates = templateService.getAll(TechnologyLevelUpTemplate.class);
		for(TechnologyLevelUpTemplate template : technologyTemplates.values()){
			int techId = template.getTechnologyId();
			technologyDetailInfoMap.put(genKey(techId, template.getLevel()),
									  	convertTechnologyTemplateToTechnologyDetailInfo(template));
			if(technologyMaxLevelMap.containsKey(techId)){
				if(template.getLevel()>technologyMaxLevelMap.get(techId)){
					technologyMaxLevelMap.put(techId, template.getLevel());
				}
			}
			else{
				technologyMaxLevelMap.put(techId, template.getLevel());
			}
		}
	}
	
	/**
	 * 生成TechnologyDetailInfo
	 * 
	 * @param technologyLevelUpTemplate
	 * @return
	 */
	private TechnologyDetailInfo convertTechnologyTemplateToTechnologyDetailInfo(TechnologyLevelUpTemplate technologyLevelUpTemplate) {
		TechnologyDetailInfo technologyDetailInfo = new TechnologyDetailInfo();
		TechnologyTemplate technologyTemplate = templateService.get(technologyLevelUpTemplate.getTechnologyId(), TechnologyTemplate.class);
		if(technologyTemplate == null){
			return technologyDetailInfo;
		}
		
		technologyDetailInfo.setTechnologyId(technologyLevelUpTemplate.getTechnologyId());
		technologyDetailInfo.setCostValue(technologyLevelUpTemplate.getCostValue());
		technologyDetailInfo.setIcon(technologyTemplate.getIcon());
		technologyDetailInfo.setName(technologyTemplate.getName());
		technologyDetailInfo.setPropAddValue(technologyLevelUpTemplate.getPropAddValue());
		technologyDetailInfo.setPropName(technologyTemplate.getDesc());
		technologyDetailInfo.setRoleLevel(technologyLevelUpTemplate.getRoleLevel());
		technologyDetailInfo.setLevel(technologyLevelUpTemplate.getLevel());
		
		TechnologyLevelUpTemplate nextTechnologyLevelUpTemplate = 
				technologyTemplateMap.get(genKey(technologyLevelUpTemplate.getTechnologyId(),technologyLevelUpTemplate.getLevel()+1));
		if(nextTechnologyLevelUpTemplate == null){
			technologyDetailInfo.setNextPropAddValue(0);
		}
		else{
			technologyDetailInfo.setNextPropAddValue(nextTechnologyLevelUpTemplate.getPropAddValue());
		}
		
		return technologyDetailInfo;
	}
	
	/**
	 * 获取所有科技模版
	 * 
	 * @return
	 */
	public Collection<TechnologyTemplate> getAllTechnologyTemplate() {		
		return sortedTechTemplates;
	}
	
	/**
	 * 获取该等级开放的科技
	 * 
	 * @param level
	 * @return
	 */
	public List<TechnologyTemplate> getSuitableTechnologyTemplate(int level) {
		List<TechnologyTemplate> suitableTemplates = new ArrayList<TechnologyTemplate>();
		for(TechnologyTemplate template : sortedTechTemplates){
			if(level >= template.getOpenLevel()){
				suitableTemplates.add(template);
			}
		}
		return suitableTemplates;
	}
	
	/**
	 * 获取科技升级模版信息
	 * 
	 * @param technologyId
	 * @param level
	 * @return
	 */
	public TechnologyLevelUpTemplate getTechnologyLevelUpTemplate(int technologyId, int level) {
		return technologyTemplateMap.get(genKey(technologyId, level));
	}

	/**
	 * 科技详细信息
	 * 
	 * @param technologyId
	 * @param level
	 * @return
	 */
	public TechnologyDetailInfo getTechnologyDetailInfo(int technologyId, int level) {
		return technologyDetailInfoMap.get(genKey(technologyId, level));
	}
	
	/**
	 * 根据TechnologyDetailInfo获取TechnologyInfo
	 * 
	 * @param technologyDetailInfo
	 * @return
	 */
	public TechnologyInfo getTechnologyInfo(TechnologyDetailInfo technologyDetailInfo) {
		TechnologyInfo technologyInfo = new TechnologyInfo();
		technologyInfo.setTechnologyId(technologyDetailInfo.getTechnologyId());
		technologyInfo.setName(technologyDetailInfo.getName());
		technologyInfo.setLevel(technologyDetailInfo.getLevel());
		technologyInfo.setIcon(technologyDetailInfo.getIcon());
		technologyInfo.setMaxLevelReached(maxLevelReached(technologyDetailInfo.getTechnologyId(), technologyDetailInfo.getLevel()));
		technologyInfo.setCostTechPointNum(technologyDetailInfo.getCostValue());
		technologyInfo.setRoleLevel(technologyDetailInfo.getRoleLevel());
		return technologyInfo;
	}
	
	private String genKey(int technologyId, int level) {
		return technologyId + "&" + level;
	}
	
	/**
	 * 是否达到满级
	 * @param techId
	 * @param currentLevel
	 * @return
	 */
	public boolean maxLevelReached(int techId,int currentLevel){
		if(!technologyMaxLevelMap.containsKey(techId)){
			return true;
		}
		return currentLevel >= technologyMaxLevelMap.get(techId);			
	}
}
