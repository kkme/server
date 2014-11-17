package com.hifun.soul.gameserver.stage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.util.MathUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.template.RewardItemData;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.stage.model.StageDramaInfo;
import com.hifun.soul.gameserver.stage.model.StageInfo;
import com.hifun.soul.gameserver.stage.model.StageMapInfo;
import com.hifun.soul.gameserver.stage.model.StageSimpleInfo;
import com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo;
import com.hifun.soul.gameserver.stage.template.StageDramaTemplate;
import com.hifun.soul.gameserver.stage.template.StageMapTemplate;
import com.hifun.soul.gameserver.stage.template.StagePassRewardTemplate;
import com.hifun.soul.gameserver.stage.template.StageRewardConsumeTemplate;
import com.hifun.soul.gameserver.stage.template.StageRewardTemplate;
import com.hifun.soul.gameserver.stage.template.StageStarRewardTemplate;
import com.hifun.soul.gameserver.stage.template.StageStrongholdTemplate;
import com.hifun.soul.gameserver.stage.template.StageTemplate;
import com.hifun.soul.gameserver.stagestar.StageStarType;
import com.hifun.soul.gameserver.stagestar.template.StageStarCdTemplate;
import com.hifun.soul.gameserver.stagestar.template.StageStarTemplate;
import com.hifun.soul.gameserver.turntable.MinAndMaxValue;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;

@Scope("singleton")
@Component
public class StageTemplateManager implements IInitializeRequired {
	private Logger logger = Loggers.STAGE_LOGGER;
	@Autowired
	private TemplateService templateService;
	/** key:mapId */
	private Map<Integer, List<StageSimpleInfo>> stageListMap = new HashMap<Integer, List<StageSimpleInfo>>();
	/** key:stageId&triggerType */
	private Map<String, List<StageDramaInfo>> stageDramaInfoMap = new HashMap<String, List<StageDramaInfo>>();
	private Map<Integer, StageMapInfo> stageMapInfoMap = new HashMap<Integer, StageMapInfo>();
	private Map<Integer, StageTemplate> stageTemplateMap = new HashMap<Integer, StageTemplate>();
	private Map<Integer, StageRewardConsumeTemplate> stageRewardConsumeTemplateMap = new HashMap<Integer, StageRewardConsumeTemplate>();
	private Map<Integer, StagePassRewardTemplate> stagePassRewardTemplateMap = new HashMap<Integer, StagePassRewardTemplate>();
	private Map<Integer, StageRewardTemplate> stageRewardTemplateMap = new HashMap<Integer, StageRewardTemplate>();
	private StageStarTemplate stageStarTemplate;
	private Map<Integer, Integer> stageStarReduceCdMap = new HashMap<Integer, Integer>();
	private Integer[] stageStarRewardTypeList = null;
	private Map<Integer, StageStarRewardTemplate> stageStarRewardTemplateMap = new HashMap<Integer, StageStarRewardTemplate>();
/** 地图id和据点列表之间的映射 */
	private Map<Integer, List<StageStrongholdTemplate>> mapStrongholdInfos = new HashMap<Integer, List<StageStrongholdTemplate>>();
	/** 据点id关卡列表之间的映射 */
	private Map<Integer, List<StageSimpleInfo>> strongholdStageMap = new HashMap<Integer, List<StageSimpleInfo>>();

	@Override
	public void init() {
		stageStarTemplate = templateService.get(
				SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
				StageStarTemplate.class);
		initTemplate();
		initStageListMap();
		initStageDramaInfoMap();
		initStageMapInfo();
		initStageStarReduceCdMap();
		// 初始化地图和据点映射
		initStrongholdInfos();
		// 关卡评星奖励信息
		initStageStarRewardInfo();
		// 地图完美通关奖励信息
		initPerfectRewardItemInfos();
	}

	/**
	 * 初始化地图和据点信息的映射;
	 */
	private void initStrongholdInfos() {
		Map<Integer, StageStrongholdTemplate> strongholdMap = templateService
				.getAll(StageStrongholdTemplate.class);
		for (StageStrongholdTemplate each : strongholdMap.values()) {
			List<StageStrongholdTemplate> eachList = mapStrongholdInfos
					.get(each.getMapId());
			if (eachList == null) {
				eachList = new ArrayList<StageStrongholdTemplate>();
				mapStrongholdInfos.put(each.getMapId(), eachList);
			}
			eachList.add(each);
		}
	}

	/**
	 * 初始化所有模版
	 */
	private void initTemplate() {
		stageTemplateMap = templateService.getAll(StageTemplate.class);
		stageRewardConsumeTemplateMap = templateService
				.getAll(StageRewardConsumeTemplate.class);
		stagePassRewardTemplateMap = templateService
				.getAll(StagePassRewardTemplate.class);
		stageRewardTemplateMap = templateService
				.getAll(StageRewardTemplate.class);
	}

	private void initStageListMap() {
		for (StageTemplate eachStageTemplate : stageTemplateMap.values()) {
			List<StageSimpleInfo> stageInfoList = stageListMap
					.get(eachStageTemplate.getMapId());
			List<StageSimpleInfo> strongholdStageList = this.strongholdStageMap
					.get(eachStageTemplate.getStrongholdId());
			// 整体
			if (stageInfoList == null) {
				stageInfoList = new ArrayList<StageSimpleInfo>();
				stageListMap.put(eachStageTemplate.getMapId(), stageInfoList);
			}
			// 據點信息
			if (strongholdStageList == null) {
				strongholdStageList = new ArrayList<StageSimpleInfo>();
				this.strongholdStageMap.put(
						eachStageTemplate.getStrongholdId(),
						strongholdStageList);
			}
			StageSimpleInfo stageSimpleInfo = converStageTemplateToStageSimpleInfo(eachStageTemplate);
			if (stageSimpleInfo != null) {
				stageInfoList.add(stageSimpleInfo);
				strongholdStageList.add(stageSimpleInfo);
			}
		}
	}

	public StageSimpleInfo converStageTemplateToStageSimpleInfo(
			StageTemplate template) {
		if (template == null) {
			return null;
		}
		StageSimpleInfo stageSimpleInfo = new StageSimpleInfo();
		stageSimpleInfo.setMapId(template.getMapId());
		stageSimpleInfo.setStageId(template.getId());
		stageSimpleInfo.setStageName(template.getStageName());
		stageSimpleInfo.setStageDesc(template.getStageDesc());
		stageSimpleInfo.setX(template.getX());
		stageSimpleInfo.setY(template.getY());
		stageSimpleInfo.setMinLevel(template.getMinLevel());
		stageSimpleInfo.setRewardCurrencyNum(template.getRewardCurrencyNum());
		stageSimpleInfo.setRewardCurrencyType(template.getRewardCurrencyType());
		stageSimpleInfo.setRewardExperience(template.getRewardExperience());
		MonsterTemplate monsterTemplate = templateService.get(
				template.getMonsterId(), MonsterTemplate.class);
		if (monsterTemplate != null) {
			stageSimpleInfo.setMonsterIcon(monsterTemplate.getPicId());
			stageSimpleInfo.setMonsterName(monsterTemplate.getName());
			stageSimpleInfo.setMonsterLevel(monsterTemplate.getLevel());
			stageSimpleInfo.setMonsterOccupation(monsterTemplate
					.getOccupationType());
			stageSimpleInfo.setMonsterFirstAttack(monsterTemplate
					.getFirstAttack());
		}
		return stageSimpleInfo;
	}

	private void initStageDramaInfoMap() {
		Map<Integer, StageDramaTemplate> stageDramaTemplateMap = templateService
				.getAll(StageDramaTemplate.class);
		for (StageDramaTemplate template : stageDramaTemplateMap.values()) {
			StageDramaInfo stageDramaInfo = genStageDramaInfo(template);
			String key = genKey(template.getStageId(),
					template.getTriggerType());
			List<StageDramaInfo> stageDramaInfos = stageDramaInfoMap.get(key);
			if (stageDramaInfos == null) {
				stageDramaInfos = new ArrayList<StageDramaInfo>();
			}
			stageDramaInfos.add(stageDramaInfo);
			Collections.sort(stageDramaInfos, new StageDramaSorter());
			stageDramaInfoMap.put(key, stageDramaInfos);
		}
	}

	private StageDramaInfo genStageDramaInfo(StageDramaTemplate template) {
		StageDramaInfo stageDramaInfo = new StageDramaInfo();
		if (template != null) {
			stageDramaInfo.setContent(template.getContent());
			stageDramaInfo.setDramaType(template.getDramaType());
			stageDramaInfo.setIcon(template.getIcon());
			stageDramaInfo.setName(template.getName());
			stageDramaInfo.setOrder(template.getOrder());
			stageDramaInfo.setPositionType(template.getPositionType());
			stageDramaInfo.setStageId(template.getStageId());
		}
		return stageDramaInfo;
	}

	private String genKey(int stageId, int triggerType) {
		return stageId + "&" + triggerType;
	}

	private void initStageMapInfo() {
		Map<Integer, StageMapTemplate> stageMapTemplates = templateService
				.getAll(StageMapTemplate.class);
		for (StageMapTemplate template : stageMapTemplates.values()) {
			if (template == null) {
				continue;
			}
			StageMapInfo stageMapInfo = new StageMapInfo();
			stageMapInfo.setMapId(template.getId());
			stageMapInfo.setMapName(template.getMapName());
			stageMapInfo.setMapDesc(template.getMapDesc());
			stageMapInfo.setIcon(template.getIcon());
			stageMapInfo.setChapter(template.getChapter());
			stageMapInfo.setSimpleIcon(template.getSimpleIcon());
			stageMapInfo.setNextMapId(template.getNextMapId());
			stageMapInfoMap.put(template.getId(), stageMapInfo);
		}
	}

	/**
	 * 初始化关卡星级和扫荡cd减免之间的关系
	 */
	private void initStageStarReduceCdMap() {
		for (StageStarCdTemplate stageStarCdTemplate : templateService.getAll(
				StageStarCdTemplate.class).values()) {
			stageStarReduceCdMap.put(stageStarCdTemplate.getId(),
					stageStarCdTemplate.getReduceCd());
		}
	}

	/**
	 * 初始化关卡评星奖励的信息
	 */
	private void initStageStarRewardInfo() {
		Map<Integer, StageStarRewardTemplate> starRewardTemplates = templateService
				.getAll(StageStarRewardTemplate.class);
		// 初始化stageStarRewardTypeList,保证以id从小到达排序
		stageStarRewardTypeList = starRewardTemplates.keySet().toArray(
				new Integer[0]);
		Arrays.sort(stageStarRewardTypeList, new Comparator<Integer>() {
			@Override
			public int compare(Integer star1, Integer star2) {
				return star1 - star2;
			}
		});
		// 初始化StageStarRewardTemplate
		for (int i = 0; i < stageStarRewardTypeList.length; i++) {
			StageStarRewardTemplate stageStarRewardTemplate = starRewardTemplates
					.get(stageStarRewardTypeList[i]);
			if (i < stageStarRewardTypeList.length - 1) {
				stageStarRewardTemplate
						.setNextRewardStar(stageStarRewardTypeList[i + 1]);
			} else {
				stageStarRewardTemplate.setNextRewardStar(0);
			}
			List<StageStarRewardItemInfo> stageStarRewardItemInfos = new ArrayList<StageStarRewardItemInfo>();
			for (RewardItemData rewardItemData : stageStarRewardTemplate
					.getRewardItemList()) {
				if (rewardItemData == null) {
					continue;
				}
				CommonItem commonItem = CommonItemBuilder
						.genCommonItem(rewardItemData.getItemId());
				if (commonItem != null) {
					StageStarRewardItemInfo stageStarRewardItemInfo = new StageStarRewardItemInfo();
					stageStarRewardItemInfo.setItemId(rewardItemData
							.getItemId());
					stageStarRewardItemInfo.setItemNum(rewardItemData
							.getItemNum());
					stageStarRewardItemInfo.setCommonItem(CommonItemBuilder
							.genCommonItem(rewardItemData.getItemId()));
					stageStarRewardItemInfos.add(stageStarRewardItemInfo);
				}
			}
			stageStarRewardTemplate
					.setStageStarRewardItemInfos(stageStarRewardItemInfos);
			stageStarRewardTemplateMap.put(stageStarRewardTypeList[i],
					stageStarRewardTemplate);
		}
	}

	/**
	 * 初始化完美通关奖励信息
	 */
	private void initPerfectRewardItemInfos() {
		Map<Integer, StagePassRewardTemplate> stagePassRewardTemplates = templateService
				.getAll(StagePassRewardTemplate.class);
		for (StagePassRewardTemplate template : stagePassRewardTemplates
				.values()) {
			List<StageStarRewardItemInfo> perfectRewardItemInfos = new ArrayList<StageStarRewardItemInfo>();
			for (RewardItemData rewardItemData : template
					.getPerfectRewardItemList()) {
				if (rewardItemData == null) {
					continue;
				}
				StageStarRewardItemInfo stageStarRewardItemInfo = new StageStarRewardItemInfo();
				stageStarRewardItemInfo.setItemId(rewardItemData.getItemId());
				stageStarRewardItemInfo.setItemNum(rewardItemData.getItemNum());
				stageStarRewardItemInfo.setCommonItem(CommonItemBuilder
						.genCommonItem(rewardItemData.getItemId()));
				perfectRewardItemInfos.add(stageStarRewardItemInfo);
			}
			template.setPerfectRewardItemInfos(perfectRewardItemInfos);
			stagePassRewardTemplateMap.put(template.getId(), template);
		}

	}

	/**
	 * 找到对应的剧情
	 * 
	 * @param stageId
	 * @param triggerType
	 * @return
	 */
	public List<StageDramaInfo> getStageDramaInfos(int stageId, int triggerType) {
		String key = genKey(stageId, triggerType);
		return stageDramaInfoMap.get(key);
	}

	public StageTemplate getStageTemplate(int stageId) {
		return stageTemplateMap.get(stageId);
	}

	public StageRewardConsumeTemplate getStageRewardConsumeTemplate(
			int consumeCount) {
		return stageRewardConsumeTemplateMap.get(consumeCount);
	}

	public StagePassRewardTemplate getStagePassRewardTemplate(int mapId) {
		return stagePassRewardTemplateMap.get(mapId);
	}

	public StageRewardTemplate getStageRewardTemplate(int rewardItemId) {
		return stageRewardTemplateMap.get(rewardItemId);
	}

	/**
	 * 找到关卡所在地图的信息
	 * 
	 * @param mapId
	 * @return
	 */
	public StageMapInfo getStageMapInfo(int mapId) {
		return stageMapInfoMap.get(mapId);
	}

	/**
	 * mapId对应的地图关卡总数量
	 * 
	 * @param mapId
	 * @return
	 */
	public int getStageCount(int mapId) {
		List<StageSimpleInfo> stageList = stageListMap.get(mapId);
		if (stageList == null) {
			return 0;
		}
		return stageList.size();
	}

	/**
	 * mapId对应的地图已经通关的关卡数量
	 * 
	 * @param stageId
	 * @param mapId
	 * @return
	 */
	public int getPassStageCount(int stageId, int mapId) {
		List<StageSimpleInfo> stageList = stageListMap.get(mapId);
		int count = 0;
		if (stageList != null) {
			for (StageSimpleInfo stageSimpleInfo : stageList) {
				if (stageSimpleInfo.getStageId() < stageId) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 获取当前地图所有关卡信息
	 * 
	 * @param stageId
	 * @param mapId
	 * @return
	 */
	public List<StageSimpleInfo> getStageSimpleInfos(int stageId, int mapId) {
		List<StageSimpleInfo> stageSimpleInfos = stageListMap.get(mapId);
		if (stageSimpleInfos == null) {
			return new ArrayList<StageSimpleInfo>();
		}
		for (StageSimpleInfo stageSimpleInfo : stageSimpleInfos) {
			if (stageSimpleInfo.getStageId() < stageId) {
				stageSimpleInfo.setPass(true);
			} else {
				stageSimpleInfo.setPass(false);
			}
		}
		return stageSimpleInfos;
	}

	/**
	 * 获取指定据点的所有关卡信息;
	 * 
	 * @param strongholdId
	 *            据点id;
	 * @param stageId
	 *            当前的关卡id;
	 * @return
	 */
	public List<StageSimpleInfo> getStrongholdStageInfo(int strongholdId,
			int stageId) {
		List<StageSimpleInfo> allInfos = strongholdStageMap.get(strongholdId);
		for (StageSimpleInfo stageSimpleInfo : allInfos) {
			if (stageSimpleInfo.getStageId() < stageId) {
				stageSimpleInfo.setPass(true);
			} else {
				stageSimpleInfo.setPass(false);
			}
		}
		return allInfos;
	}

	/**
	 * 获取mapId上通关的关卡
	 * 
	 * @param stageId
	 * @param mapId
	 * @return
	 */
	public List<StageSimpleInfo> getPassStageSimpleInfos(int stageId, int mapId) {
		List<StageSimpleInfo> stageSimpleInfos = new ArrayList<StageSimpleInfo>();
		if (stageListMap.get(mapId) == null) {
			return stageSimpleInfos;
		}
		for (StageSimpleInfo stageSimpleInfo : stageListMap.get(mapId)) {
			if (stageSimpleInfo.getStageId() < stageId) {
				stageSimpleInfos.add(stageSimpleInfo);
			}
		}
		return stageSimpleInfos;
	}

	/**
	 * 是否是该地图的最后一个关卡
	 * 
	 * @param stageId
	 * @return
	 */
	public boolean isMapLastStage(int stageId) {
		StageTemplate stageTemplate = getStageTemplate(stageId);
		if (stageTemplate == null) {
			return false;
		}
		int lastStageId = getMapLastStageId(stageTemplate.getMapId());
		return lastStageId == stageId;
	}

	/**
	 * 获取地图的最后关卡id
	 * 
	 * @param mapId
	 * @return
	 */
	public int getMapLastStageId(int mapId) {
		int lastStageId = 0;
		List<StageSimpleInfo> stageInfos = stageListMap.get(mapId);
		for (StageSimpleInfo stageSimpleInfo : stageInfos) {
			lastStageId = stageSimpleInfo.getStageId() > lastStageId ? stageSimpleInfo
					.getStageId() : lastStageId;
		}
		return lastStageId;
	}

	/**
	 * 获取mapId上未通关的关卡
	 * 
	 * @param stageId
	 * @param mapId
	 * @return
	 */
	public List<StageSimpleInfo> getUnPassStageSimpleInfos(int stageId,
			int mapId) {
		List<StageSimpleInfo> stageSimpleInfos = new ArrayList<StageSimpleInfo>();
		if (stageListMap.get(mapId) == null) {
			return stageSimpleInfos;
		}
		for (StageSimpleInfo stageSimpleInfo : stageListMap.get(mapId)) {
			if (stageSimpleInfo.getStageId() >= stageId) {
				stageSimpleInfos.add(stageSimpleInfo);
			}
		}
		return stageSimpleInfos;
	}

	/**
	 * 获取关卡所在的地图
	 * 
	 * @param stageId
	 * @return
	 */
	public int getMapIdByStageId(int stageId) {
		StageTemplate stageTemplate = getStageTemplate(stageId);
		if (stageTemplate == null) {
			return 0;
		}
		return stageTemplate.getMapId();
	}

	/**
	 * 关卡的详细信息
	 * 
	 * @param stageId
	 * @return
	 */
	public StageInfo getStageInfo(int stageId) {
		StageInfo stageInfo = new StageInfo();
		StageTemplate stageTemplate = getStageTemplate(stageId);
		if (stageTemplate == null) {
			logger.error(String.format("StageTemplate is null! stageId:%s",
					stageId));
			return stageInfo;
		}
		stageInfo.setMinLevel(stageTemplate.getMinLevel());
		stageInfo.setMonsterId(stageTemplate.getMonsterId());
		MonsterTemplate monsterTemplate = templateService.get(
				stageTemplate.getMonsterId(), MonsterTemplate.class);
		if (monsterTemplate != null) {
			stageInfo.setMonsterName(monsterTemplate.getName());
			stageInfo.setMonsterLevel(monsterTemplate.getLevel());
			stageInfo.setMonsterOccupation(monsterTemplate.getOccupationType());
		}
		stageInfo.setRewardCurrencyNum(stageTemplate.getRewardCurrencyNum());
		stageInfo.setRewardCurrencyType(stageTemplate.getRewardCurrencyType());
		stageInfo.setRewardExperience(stageTemplate.getRewardExperience());
		stageInfo.setStageDesc(stageTemplate.getStageDesc());
		stageInfo.setStageId(stageId);
		stageInfo.setStageName(stageTemplate.getStageName());
		stageInfo.setX(stageTemplate.getX());
		stageInfo.setY(stageTemplate.getY());
		return stageInfo;
	}

	
	/**
	 * 随机获取物品
	 * 
	 * @param itemRateDatas
	 * @return
	 */
	public int getRandomItem(List<ItemRateData> itemRateDatas) {
		Map<Integer, MinAndMaxValue> rates = new HashMap<Integer, MinAndMaxValue>();
		int nowRate = 0;
		for (int i = 0; i < itemRateDatas.size(); i++) {
			MinAndMaxValue value = new MinAndMaxValue();
			value.setMinValue(nowRate);
			nowRate += itemRateDatas.get(i).getRate();
			value.setMaxValue(nowRate);
			rates.put(i, value);
		}
		if (nowRate <= 0) {
			return 0;
		}
		int rate = MathUtils.randomInt(nowRate);
		int selectIndex = -1;
		for (Integer index : rates.keySet()) {
			MinAndMaxValue value = rates.get(index);
			if (value == null)
				continue;
			if (value.getMinValue() <= rate && rate <= value.getMaxValue()) {
				selectIndex = index;
				break;
			}
		}
		if (selectIndex == -1) {
			return 0;
		} else {
			return itemRateDatas.get(selectIndex).getItemId();
		}
	}

	/**
	 * 根据关卡评星判断可以获得的奖励比例
	 * 
	 * @param star
	 * @return
	 */
	public float getRewardRateByStar(int star) {
		if (star == StageStarType.StageStarOne.getIndex()) {
			return stageStarTemplate.getOneStarRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		} else if (star == StageStarType.StageStarTwo.getIndex()) {
			return stageStarTemplate.getTwoStarRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		} else if (star == StageStarType.StageStarThree.getIndex()) {
			return stageStarTemplate.getThreeStarRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		} else if (star == StageStarType.StageStarFour.getIndex()) {
			return stageStarTemplate.getFourStarRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		} else if (star >= StageStarType.StageStarFive.getIndex()) {
			return stageStarTemplate.getFiveStarRate()
					/ SharedConstants.DEFAULT_FLOAT_BASE;
		}
		return stageStarTemplate.getFailRate()
				/ SharedConstants.DEFAULT_FLOAT_BASE;
	}

	/**
	 * 根据关卡评星获取需要减免的扫荡cd
	 * 
	 * @param star
	 * @return
	 */
	public Integer getReduceCdByStar(int star) {
		Integer reduceCd = stageStarReduceCdMap.get(star);
		if (reduceCd == null) {
			reduceCd = 0;
		}
		return reduceCd;
	}

	/**
	 * 可以扫荡的条件
	 * 
	 * @param star
	 * @return
	 */
	public boolean canAutoBattle(int star) {
		return star >= stageStarTemplate.getAutoBattleMinStageStar();
	}

	/**
	 * 关卡评星奖励类型
	 * 
	 * @return
	 */
	public Integer[] getStageStarRewardTypes() {
		return stageStarRewardTypeList;
	}

	/**
	 * 根据星星数量找到对应的奖励模版
	 * 
	 * @param star
	 * @return
	 */
	public StageStarRewardTemplate getStageStarRewardTemplate(int star) {
		return stageStarRewardTemplateMap.get(star);
	}

	/**
	 * 关卡评星奖励配置的最大的星星数
	 * 
	 * @return
	 */
	public int getMaxStageStarRewardNum() {
		if (stageStarRewardTypeList == null
				|| stageStarRewardTypeList.length == 0) {
			return 0;
		}
		return stageStarRewardTypeList[stageStarRewardTypeList.length - 1];
	}


	/**
	 * 根据地图id获取地图据点信息;
	 * 
	 * @param mapId
	 * @return
	 */
	public List<StageStrongholdTemplate> getMapStrongholdListByMapId(int mapId) {
		return mapStrongholdInfos.get(mapId);
	}

}

class StageDramaSorter implements Comparator<StageDramaInfo> {
	@Override
	public int compare(StageDramaInfo arg0, StageDramaInfo arg1) {
		if (arg0.getOrder() != arg1.getOrder()) {
			return arg1.getOrder() - arg0.getOrder();
		}
		return 0;
	}
}
