package com.hifun.soul.gameserver.building.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.building.template.BuildingFuncTemplate;
import com.hifun.soul.gameserver.building.template.BuildingTemplate;
import com.hifun.soul.gameserver.building.template.BuildingUpgradeRatioTemplate;
import com.hifun.soul.gameserver.building.template.BuildingUpgradeTemplate;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;

/**
 * 建筑服务
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class BuildingTemplateManager implements IInitializeRequired{
	@Autowired
	private TemplateService templateService;
	
	/** key:buildingId value:Set<GameFuncTemplate> */
	private Map<Integer,Set<GameFuncTemplate>> buildingFuncMap = new HashMap<Integer,Set<GameFuncTemplate>>();
	
	@Override
	public void init() {
		initBuildingFuncTemplateMap(templateService);
	}
	
	private void initBuildingFuncTemplateMap(TemplateService templateService) {
		Map<Integer,BuildingFuncTemplate> buildingFuncTemplates = 
				templateService.getAll(BuildingFuncTemplate.class);
		
		for(BuildingFuncTemplate template : buildingFuncTemplates.values()){
			Set<GameFuncTemplate> funcs = null;
			if(buildingFuncMap.get(template.getBuildingId()) == null){
				funcs = new HashSet<GameFuncTemplate>();
				buildingFuncMap.put(template.getBuildingId(), funcs);
			}
			else{
				funcs = buildingFuncMap.get(template.getBuildingId());
			}
			
			GameFuncTemplate gameFuncTemplate = 
					templateService.get(template.getId(), GameFuncTemplate.class);
			if(gameFuncTemplate != null){
				funcs.add(gameFuncTemplate);
			}
		}
	}
	
	public Set<GameFuncTemplate> getGameFuncTemplates(Integer buildingId) {
		return buildingFuncMap.get(buildingId);
	}
	
	public BuildingTemplate getBuildingTemplate(Integer buildingId) {
		return templateService.get(buildingId, BuildingTemplate.class);
	}
	
	public BuildingUpgradeTemplate getBuildingUpgradeTemplate(Integer level) {
		return templateService.get(level, BuildingUpgradeTemplate.class);
	}
	
	public BuildingUpgradeRatioTemplate getBuildingUpgradeRatioTemplate(Integer buildingId) {
		return templateService.get(buildingId, BuildingUpgradeRatioTemplate.class);
	}
	
	/**
	 * level升级到level+1是需要消耗的货币数量
	 * 
	 * @param buildingId
	 * @param level
	 * @return
	 */
	public int getCostMoneyNum(int buildingId, int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		BuildingUpgradeRatioTemplate upgradeRatioTemplate = 
				getBuildingUpgradeRatioTemplate(buildingId);
		if(upgradeTemplate == null) {
			return Integer.MAX_VALUE;
		}
		if(upgradeRatioTemplate == null) {
			return Integer.MAX_VALUE;
		}
		
		return (int) (upgradeTemplate.getNum() * upgradeRatioTemplate.getCostRatio());
	}
	
	/**
	 * level升级到level+1是需要消耗的木材数量
	 * 
	 * @param buildingId
	 * @param level
	 * @return
	 */
	public int getWoodNum(int buildingId, int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		BuildingUpgradeRatioTemplate upgradeRatioTemplate = 
				getBuildingUpgradeRatioTemplate(buildingId);
		if(upgradeTemplate == null) {
			return Integer.MAX_VALUE;
		}
		if(upgradeRatioTemplate == null) {
			return Integer.MAX_VALUE;
		}
		
		return (int) (upgradeTemplate.getWood() * upgradeRatioTemplate.getWoodRatio());
	}
	
	/**
	 * level升级到level+1是需要消耗的矿石数量
	 * 
	 * @param buildingId
	 * @param level
	 * @return
	 */
	public int getMineNum(int buildingId, int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		BuildingUpgradeRatioTemplate upgradeRatioTemplate = 
				getBuildingUpgradeRatioTemplate(buildingId);
		if(upgradeTemplate == null) {
			return Integer.MAX_VALUE;
		}
		if(upgradeRatioTemplate == null) {
			return Integer.MAX_VALUE;
		}
		
		return (int) (upgradeTemplate.getMine() * upgradeRatioTemplate.getMineRatio());
	}
	
	/**
	 * level升级到level+1是需要消耗的宝石矿数量
	 * 
	 * @param buildingId
	 * @param level
	 * @return
	 */
	public int getGemNum(int buildingId, int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		BuildingUpgradeRatioTemplate upgradeRatioTemplate = 
				getBuildingUpgradeRatioTemplate(buildingId);
		if(upgradeTemplate == null) {
			return Integer.MAX_VALUE;
		}
		if(upgradeRatioTemplate == null) {
			return Integer.MAX_VALUE;
		}
		
		return (int) (upgradeTemplate.getGem() * upgradeRatioTemplate.getGemRatio());
	}
	
	/**
	 * level升级到level+1是需要花费的cd时长
	 * 
	 * @param buildingId
	 * @param level
	 * @return
	 */
	public long getCdTime(int buildingId, int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		BuildingUpgradeRatioTemplate upgradeRatioTemplate = 
				getBuildingUpgradeRatioTemplate(buildingId);
		if(upgradeTemplate == null
				|| upgradeRatioTemplate == null) {
			return SharedConstants.BUILDING_MAX_CD;
		}
		
		return (long) (upgradeTemplate.getCd() * TimeUtils.MIN * upgradeRatioTemplate.getCdRatio());
	}
	
	/**
	 * 返回从level升级到level+1需要花费的货币类型
	 * 
	 * @param level
	 * @return
	 */
	public short getCurrencyType(int level) {
		BuildingUpgradeTemplate upgradeTemplate = 
				getBuildingUpgradeTemplate(level);
		return upgradeTemplate.getCurrencyType();
	}

}
