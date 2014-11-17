package com.hifun.soul.gameserver.refine.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.refine.RefineStageInfo;
import com.hifun.soul.gameserver.refine.template.RefineMapTemplate;
import com.hifun.soul.gameserver.refine.template.RefineStageTemplate;
import com.hifun.soul.gameserver.vip.template.RefineCostTemplate;
import com.hifun.soul.gameserver.vip.template.RefineRefreshCostTemplate;

/**
 * 试炼的模版管理器
 */
@Scope("singleton")
@Component
public class RefineTemplateManager implements IInitializeRequired{
	@Autowired
	private TemplateService templateService;
	@Autowired
	private MonsterFactory monsterFactory;
	private Map<Integer,RefineMapTemplate> refineMapTemplateMap = new HashMap<Integer,RefineMapTemplate>();
	private Map<Integer,Map<Integer,RefineStageTemplate>> refineStageTemplateMap = new HashMap<Integer,Map<Integer,RefineStageTemplate>>();
	private Map<String,RefineStageInfo> refineStageInfoMap = new HashMap<String,RefineStageInfo>();
	private RefineMapTemplate defaultRefineTemplate = null;
	/** 地图的最后一个关卡 */
	private Map<Integer,Integer> lastStageIdMap = new HashMap<Integer,Integer>();
	private RefineCostTemplate refineCostTemplate;
	private Map<Integer,RefineRefreshCostTemplate> refineRefreshCostTemplateMap = new HashMap<Integer,RefineRefreshCostTemplate>();
	/** 没有配置的时候默认刷新消耗模版 */
	private RefineRefreshCostTemplate refineRefreshCostTemplate;
	
	@Override
	public void init() {
		initRefineMapTemplates();
		initRefineStageTemplates();
		refineCostTemplate = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, RefineCostTemplate.class);
		initRefineRefreshCostTemplateMap();
	}
	
	private void initRefineMapTemplates() {
		for(RefineMapTemplate template : templateService
				.getAll(RefineMapTemplate.class).values()){
			refineMapTemplateMap.put(template.getId(), template);
			if(defaultRefineTemplate == null){
				defaultRefineTemplate = template;
			}
			else{
				if(template.getOpenLevel() < defaultRefineTemplate.getOpenLevel()){
					defaultRefineTemplate = template;
				}
			}
		}
	}
	
	private void initRefineStageTemplates() {
		for(RefineStageTemplate template : templateService
				.getAll(RefineStageTemplate.class).values()){
			if(refineStageTemplateMap.get(template.getMapId()) == null){
				refineStageTemplateMap.put(template.getMapId(), new HashMap<Integer,RefineStageTemplate>());
			}
			refineStageTemplateMap.get(template.getMapId()).put(template.getStageId(), template);
			// 将不会变化的先存起来
			refineStageInfoMap.put(genKey(template.getMapId(),template.getStageId()), getRefineStageInfo(template));
			// 计算地图的最后一个关卡
			if(lastStageIdMap.get(template.getMapId()) == null
					|| template.getStageId() > lastStageIdMap.get(template.getMapId())){
				lastStageIdMap.put(template.getMapId(), template.getStageId());
			}
		}
	}
	
	private RefineStageInfo getRefineStageInfo(RefineStageTemplate template) {
		RefineStageInfo refineStageInfo = new RefineStageInfo();
		refineStageInfo.setCommonItem(CommonItemBuilder.genCommonItem(template.getRewardId()));
		refineStageInfo.setMapId(template.getMapId());
		RefineMapTemplate refineMapTemplate = getRefineMapTemplate(template.getMapId());
		refineStageInfo.setMapName(refineMapTemplate.getName());
		MonsterTemplate monsterTemplate = monsterFactory.getMonsterTemplate(template.getMonsterId());
		refineStageInfo.setMonsterIcon(monsterTemplate.getPicId());
		refineStageInfo.setMonsterId(monsterTemplate.getId());
		refineStageInfo.setMonsterLevel(monsterTemplate.getLevel());
		refineStageInfo.setMonsterName(monsterTemplate.getName());
		refineStageInfo.setRewardNum(template.getRewardNum());
		refineStageInfo.setRewardAnima(template.getRewardAura());
		refineStageInfo.setStageId(template.getStageId());
		return refineStageInfo;
	}
	
	private String genKey(int mapId, int stageId){
		return mapId + "&" +stageId;
	}
	
	private void initRefineRefreshCostTemplateMap() {
		for(RefineRefreshCostTemplate refineRefreshCostTemplate : 
			templateService.getAll(RefineRefreshCostTemplate.class).values()){
			refineRefreshCostTemplateMap.put(refineRefreshCostTemplate.getId(), refineRefreshCostTemplate);
			if(this.refineRefreshCostTemplate == null){
				this.refineRefreshCostTemplate = refineRefreshCostTemplate;
			}
			else{
				if(refineRefreshCostTemplate.getId() > this.refineRefreshCostTemplate.getId()){
					this.refineRefreshCostTemplate = refineRefreshCostTemplate;
				}
			}
		}
	}
	
	/**
	 * 魔晶刷新消耗模版
	 * @param times
	 * @return
	 */
	public RefineRefreshCostTemplate getRefineRefreshCostTemplate(int times) {
		RefineRefreshCostTemplate template = refineRefreshCostTemplateMap.get(times);
		if(template == null){
			return refineRefreshCostTemplate;
		}
		return template;
	}
	
	/**
	 * 根据mapId找到试炼对应的地图模版
	 * @param mapId
	 * @return
	 */
	public RefineMapTemplate getRefineMapTemplate(int mapId) {
		return refineMapTemplateMap.get(mapId);
	}
	
	/**
	 * 找到对应试炼地图对应的关卡模版信息
	 * @param mapId
	 * @param stageId
	 * @return
	 */
	public RefineStageTemplate getRefineStageTemplate(int mapId, int stageId) {
		return refineStageTemplateMap.get(mapId).get(stageId);
	}
	
	/**
	 * 试炼地图关卡数量
	 * @param mapId
	 * @return
	 */
	public int getRefineStageSizeInMap(int mapId) {
		return refineStageTemplateMap.get(mapId).size();
	}
	
	/**
	 * 获取模版中配置的试炼关卡信息
	 * @param mapId
	 * @param stageId
	 * @return
	 */
	public RefineStageInfo getRefineStageInfo(int mapId, int stageId) {
		return refineStageInfoMap.get(genKey(mapId,stageId));
	}
	
	/**
	 * 获取初始试炼地图
	 * @return
	 */
	public RefineMapTemplate getDefaultRefineMapTemplate() {
		return defaultRefineTemplate;
	}
	
	/**
	 * 获取所有试炼地图信息
	 * @return
	 */
	public Collection<RefineMapTemplate> getAllRefineMapTemplate() {
		return refineMapTemplateMap.values();
	}
	
	/**
	 * 地图的最后一个关卡
	 * @param mapId
	 * @return
	 */
	public int getLastStageId(int mapId) {
		return lastStageIdMap.get(mapId);
	}
	
	/**
	 * 消耗模版
	 * @return
	 */
	public RefineCostTemplate getRefineCostTemplate(){
		return refineCostTemplate;
	}
	
	/**
	 * 自己等级已经开启的试炼副本
	 * @param level
	 * @return
	 */
	public List<RefineMapTemplate> getSuitableRefineMapTemplates(int level) {
		List<RefineMapTemplate> templates = new ArrayList<RefineMapTemplate>();
		for(RefineMapTemplate template : refineMapTemplateMap.values()){
			if(template.getOpenLevel() <= level){
				templates.add(template);
			}
		}
		return templates;
	}
}
