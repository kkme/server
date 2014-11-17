package com.hifun.soul.gameserver.elitestage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.ConfigException;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.elitestage.template.EliteStageStateRefreshCostTemplate;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTemplate;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTypeTemplate;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.proto.common.EliteStage.EliteStageState;

@Scope("singleton")
@Component
public class EliteStageTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	private Map<Integer, EliteStageTemplate> allEliteStageTemplates;
	private Map<Integer, EliteStageTypeTemplate> eliteStageTypeTemplates;
	private Map<Integer, EliteStageStateRefreshCostTemplate> changeStateCostTemplates;
	/** key:occupation innerKey:stageTypeId */
	private Map<Integer, Map<Integer, List<EliteStageTemplate>>> eliteStageTempalteMap = new HashMap<Integer, Map<Integer, List<EliteStageTemplate>>>();
	/** key:occupation innerKey:stageTypeId */
	private Map<Integer, Map<Integer, EliteStageInfo[]>> eliteStageInfoMap = new HashMap<Integer, Map<Integer, EliteStageInfo[]>>();
	/** 前置关卡 */
	private Map<Integer, Integer> previousEliteStageMap = new HashMap<Integer, Integer>();
	/** key:occupation value:EliteStageTemplates */
	private Map<Integer, Map<Integer, EliteStageTemplate>> occupationEliteStageTemplateMaps = new HashMap<Integer, Map<Integer,EliteStageTemplate>>();

	/**
	 * 初始化精英副本模板
	 * 
	 * <pre>
	 * 注：为了获取精英副本数据更高效，该模板初始化时依赖了怪物模板和物品模板，所以该方法应在怪物模板和物品模板初始化完成后调用
	 * </pre>
	 */
	@Override
	public void init() {
		eliteStageTypeTemplates = templateService.getAll(EliteStageTypeTemplate.class);
		allEliteStageTemplates = templateService.getAll(EliteStageTemplate.class);
		changeStateCostTemplates = templateService.getAll(EliteStageStateRefreshCostTemplate.class);

		for (EliteStageTemplate stage : allEliteStageTemplates.values()) {
			int occupation = stage.getOccupation();
			int stageType = stage.getType();
			// 初始化副本类型对应的所有副本模板
			if (eliteStageTempalteMap.containsKey(occupation)) {
				Map<Integer,List<EliteStageTemplate>> occuStageMap = eliteStageTempalteMap.get(occupation);
				if(occuStageMap.containsKey(stageType)){
					occuStageMap.get(stageType).add(stage);
				}
				else{
					List<EliteStageTemplate> templateList = new ArrayList<EliteStageTemplate>();
					templateList.add(stage);
					occuStageMap.put(stageType, templateList);
				}
			} else {
				Map<Integer,List<EliteStageTemplate>> occuStageMap = new HashMap<Integer, List<EliteStageTemplate>>();
				List<EliteStageTemplate> templateList = new ArrayList<EliteStageTemplate>();
				templateList.add(stage);
				occuStageMap.put(stageType, templateList);
				eliteStageTempalteMap.put(occupation, occuStageMap);
			}
			// 前置关卡字典
			if (stage.getNextStageId() != stage.getId()) {
				previousEliteStageMap.put(stage.getNextStageId(), stage.getId());
			}
			// 初始化职业对应的副本模板数据
			if (occupationEliteStageTemplateMaps.containsKey(stage.getOccupation())) {
				occupationEliteStageTemplateMaps.get(stage.getOccupation()).put(stage.getId(), stage);
			} else {
				Map<Integer, EliteStageTemplate> map = new HashMap<Integer, EliteStageTemplate>();
				map.put(stage.getId(), stage);
				occupationEliteStageTemplateMaps.put(stage.getOccupation(), map);
			}

		}

		initEliteStageInfoMap();
	}

	public EliteStageTypeTemplate getEliteStageTypeTempalte(int stageTypeId) {
		return eliteStageTypeTemplates.get(stageTypeId);
	}

	/**
	 * 根据职业和副本类型获取全局的副本信息
	 * <pre>注：不要修改其中的值</pre>
	 * @param occupation
	 * @param stageTypeId
	 * @return
	 */
	public EliteStageInfo[] getEliteStageInfos(int occupation,int stageTypeId) {
		if(!eliteStageInfoMap.containsKey(occupation) || !(eliteStageInfoMap.get(occupation).containsKey(stageTypeId))){
			return new EliteStageInfo[0];
		}
		return eliteStageInfoMap.get(occupation).get(stageTypeId);
		
	}

	public int getRefreshStageStateCost(int refreshTime) {
		if(!changeStateCostTemplates.containsKey(refreshTime)){
			return getRefreshStageStateCost(refreshTime-1);
		}
		return changeStateCostTemplates.get(refreshTime).getCostCrystal();
	}

	public EliteStageTemplate getEliteStageTemplate(int stageId) {
		if (!allEliteStageTemplates.containsKey(stageId)) {
			return null;
		}
		return allEliteStageTemplates.get(stageId);
	}
	
	public Map<Integer, EliteStageTemplate> getStageTemplatesByOccupation(int occupation){
		if(occupationEliteStageTemplateMaps.get(occupation)==null){
			return new HashMap<Integer, EliteStageTemplate>();
		}
		return occupationEliteStageTemplateMaps.get(occupation);
	}

	/**
	 * 获取对应等级开放的所有精英副本模板列表
	 * 
	 * @param level
	 * @return
	 */
	public List<EliteStageTemplate> getEliteStageTemplateList(int occupation, int level,
			Map<Integer, EliteStageState.Builder> Stages, List<Integer> openedStageList) {
		List<EliteStageTemplate> templateList = new ArrayList<EliteStageTemplate>();
		if (!occupationEliteStageTemplateMaps.containsKey(occupation)) {
			return templateList;
		}
		Map<Integer, EliteStageTemplate> eliteStageTemplates = occupationEliteStageTemplateMaps.get(occupation);
		int nextStageId = 0;
		for (EliteStageTemplate template : eliteStageTemplates.values()) {
			if (Stages.containsKey(template.getId())) {
				continue;
			}
			if (level >= template.getOpenLevel()) {
				if (!previousEliteStageMap.containsKey(template.getId())) {
					templateList.add(template);
				}
			}
		}
		for (EliteStageState.Builder stageState : Stages.values()) {
			if (stageState.getConquerState() == false) {
				continue;
			}
			nextStageId = eliteStageTemplates.get(stageState.getStageId()).getNextStageId();
			if (Stages.containsKey(nextStageId)) {
				continue;
			}
			if (stageState.getStageId() == nextStageId) {
				continue;
			}
			EliteStageTemplate template = eliteStageTemplates.get(nextStageId);
			if (template.getOpenLevel() > level) {
				continue;
			}
			templateList.add(template);
		}
		return templateList;
	}

	/**
	 * 获取对应等级开放的精英副本类型模板列表
	 * 
	 * @param level
	 * @return
	 */
	public List<EliteStageTypeTemplate> getEliteStageTypeTemplateList(int level) {
		List<EliteStageTypeTemplate> templateList = new ArrayList<EliteStageTypeTemplate>();
		for (EliteStageTypeTemplate type : eliteStageTypeTemplates.values()) {
			if (type == null) {
				continue;
			}
			if (level >= type.getOpenLevel()) {
				templateList.add(type);
			}
		}
		return templateList;
	}

	/**
	 * 初始化精英副本Map
	 * 
	 */
	private void initEliteStageInfoMap() {
		MonsterFactory monsterFactory = ApplicationContext.getApplicationContext().getBean(MonsterFactory.class);
		ItemTemplateManager itemTemplateManager = ApplicationContext.getApplicationContext().getBean(
				ItemTemplateManager.class);
		for (Integer occupation : eliteStageTempalteMap.keySet()) {
			Map<Integer,EliteStageInfo[]> typeStageMap = new HashMap<Integer, EliteStageInfo[]>();
			for (Integer stageTypeId : eliteStageTempalteMap.get(occupation).keySet()) {
				List<EliteStageTemplate> stageTemplateList = eliteStageTempalteMap.get(occupation).get(stageTypeId);
				int size = stageTemplateList.size();
				EliteStageInfo[] eliteStages = new EliteStageInfo[size];
				for (int i = 0; i < size; i++) {
					EliteStageTemplate stageTemplate = stageTemplateList.get(i);
					eliteStages[i] = new EliteStageInfo();
					eliteStages[i].setStageId(stageTemplate.getId());
					eliteStages[i].setOpenLevel(stageTemplate.getOpenLevel());
					eliteStages[i].setCoinNum(stageTemplate.getCoinNum());
					eliteStages[i].setExp(stageTemplate.getExp());
					eliteStages[i].setTechPoint(stageTemplate.getTechPoint());
					eliteStages[i].setChallengeState(false);
					eliteStages[i].setOpenState(0);
					MonsterTemplate monster = monsterFactory.getMonsterTemplate(stageTemplate.getMonsterId());
					if(monster==null){
						throw new ConfigException("精英副本数据配置错误：怪物配置表中未找到monsterId为"+stageTemplate.getMonsterId()+"的怪物");
					}
					eliteStages[i].setMonsterIconId(monster.getPicId());
					eliteStages[i].setMonsterName(monster.getName());
					
					String[] itemNames = new String[0];
					String itemIds = stageTemplate.getItemIds();
					if (itemIds != null && itemIds.trim().length() > 0) {
						String[] itemIdArr = itemIds.trim().split(",");
						itemNames = new String[itemIdArr.length];
						for (int j=0;j<itemIdArr.length;j++) {
							int itemId = Integer.parseInt(itemIdArr[j]);
							itemNames[j] = itemTemplateManager.getItemTemplate(itemId).getName();
						}
					}
					eliteStages[i].setItemNames(itemNames);
					eliteStages[i].setEnergyNum(SharedConstants.ELITE_ENERGY_NUM);					
				}
				Arrays.sort(eliteStages, new Comparator<EliteStageInfo>() {

					@Override
					public int compare(EliteStageInfo o1, EliteStageInfo o2) {						
						return o1.getStageId() - o2.getStageId();
					}
				});
				typeStageMap.put(stageTypeId, eliteStages);
			}
			eliteStageInfoMap.put(occupation, typeStageMap);
		}
	}

}
