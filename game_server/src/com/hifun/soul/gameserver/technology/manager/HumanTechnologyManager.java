package com.hifun.soul.gameserver.technology.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanTechnologyEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.technology.converter.TechnologyInfoToEntityConverter;
import com.hifun.soul.gameserver.technology.msg.TechnologyInfo;
import com.hifun.soul.gameserver.technology.template.TechnologyLevelUpTemplate;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;

public class HumanTechnologyManager implements ICachableComponent,IHumanPersistenceManager,IHumanPropertiesLoadForBattle {
	private Logger logger = Loggers.TECHNOLOGY_LOGGER;
	private Human human;
	/** 所有科技信息 */
	private Map<Integer, Integer> technologyMap = new HashMap<Integer, Integer>();
	/** 转化器 */
	private TechnologyInfoToEntityConverter converter;
	/** 缓存 */
	private CacheEntry<Integer, TechnologyInfo> technologyInfoCaches = new CacheEntry<Integer, TechnologyInfo>();

	public HumanTechnologyManager(Human human) {
		this.human = human;
		converter = new TechnologyInfoToEntityConverter(human);

		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 获得当前科技点数
	 * 
	 * @return
	 */
	public int getTechnologyPoints() {
		return human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.TECHNOLOGY_POINT);
	}

	/**
	 * 添加科技点数
	 * 
	 * @param points
	 * @param notify TODO
	 */
	public void addTechnologyPoints(int points,boolean notify, TechPointLogReason reason, String param) {
		if(points==0){
			return;
		}
		if (points < 0) {
			logger.error("when addTechnologyPoints, technology points less than zero! humanGuid:"
					+ human.getHumanGuid() + "; points:" + points);
			return;
		}
		int afterPoint = getTechnologyPoints() + points;
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.TECHNOLOGY_POINT,
						afterPoint);
		if(notify){
			// 发送浮动提示消息
			String langTechnologyPoint = GameServerAssist.getSysLangService().read(LangConstants.TECHNOLOGY_POINT);
			String operate = points>=0?"+":"-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN, points, langTechnologyPoint, operate);
		}
		// 发送属性变更日志
		GameServerAssist.getLogService().sendTechPointLog(human, reason, param, points, afterPoint);
	}

	/**
	 * 消耗科技点数
	 * 
	 * @param costPoints
	 */
	public boolean costTechnologyPoints(int costPoints) {
		if (costPoints <= 0) {
			logger.error("when reduceTechnologyPoints, technology points less than zero! humanGuid:"
					+ human.getHumanGuid() + "; points:" + costPoints);
			return false;
		}
		if (getTechnologyPoints() < costPoints) {
			human.sendWarningMessage(LangConstants.TECHNOLOGY_POINTS_NOT_ENOUGH);
			return false;
		}
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.TECHNOLOGY_POINT,
						getTechnologyPoints() - costPoints);

		return true;
	}

	/**
	 * 获取科技等级
	 * 
	 * @param technologyType
	 * @return
	 */
	public int getTechnologyLevel(Integer technologyType) {
		Integer level = technologyMap.get(technologyType);
		if (level == null) {
			return 0;
		}

		return level.intValue();
	}

	/**
	 * 设置科技等级
	 * 
	 * @param technologyType
	 * @param level
	 */
	public void addTechnologyLevel(Integer technologyType) {
		Integer level = getTechnologyLevel(technologyType);
		TechnologyLevelUpTemplate nowTemplate 
			= GameServerAssist.getTechnologyTemplateManager().getTechnologyLevelUpTemplate(technologyType, level);
		if (nowTemplate == null) {
			logger.error(String.format(
					"can not find TechnologyLevelUpTemplate! humanGuid:%s; level:%s",
					human.getHumanGuid(), level));
			return;
		}
		TechnologyLevelUpTemplate nextTemplate 
			= GameServerAssist.getTechnologyTemplateManager().getTechnologyLevelUpTemplate(technologyType, level+1);
		if (nextTemplate == null) {
			return;
		}
		technologyMap.put(technologyType, level+1);

		// 同步缓存
		TechnologyInfo technologyInfo = new TechnologyInfo();
		technologyInfo.setLevel(level + 1);
		technologyInfo.setTechnologyId(technologyType);
		technologyInfoCaches.addUpdate(technologyType, technologyInfo);

		String reasonText = PropertyLogReason.TECHNOLOGY_UPGRADE.getReasonText();
		String param = MessageFormat.format(reasonText,technologyType, level+1);
		// 科技等级改变修改属性
		human.getHumanPropertiesManager().amendProperty(
				human, 
				AmendMethod.ADD,
				nextTemplate.getKey(), 
				nextTemplate.getPropAddValue()-nowTemplate.getPropAddValue(),
				PropertyLogReason.TECHNOLOGY_UPGRADE, 
				param);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanTechnology.Builder builder : humanEntity.getBuilder()
				.getTechnologyBuilderList()) {
			technologyMap.put(builder.getTechnology().getTechnologyId(), builder.getTechnology().getLevel());
			// 登录需要调用属性加成接口
			TechnologyLevelUpTemplate nowTemplate 
				= GameServerAssist.getTechnologyTemplateManager().getTechnologyLevelUpTemplate(builder.getTechnology().getTechnologyId(), builder.getTechnology().getLevel());
			human.getHumanPropertiesManager().amendProperty(
					human,
					AmendMethod.ADD, 
					nowTemplate.getKey(),
					nowTemplate.getPropAddValue(),
					null, "");
		}
	}
	
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity, HumanPropertyManager propertyManager) {
		for (HumanTechnology.Builder builder : humanEntity.getBuilder()
				.getTechnologyBuilderList()) {
			TechnologyLevelUpTemplate nowTemplate 
				= GameServerAssist.getTechnologyTemplateManager().getTechnologyLevelUpTemplate(builder.getTechnology().getTechnologyId(), builder.getTechnology().getLevel());
			propertyManager.amendProperty(
					human,
					AmendMethod.ADD, 
					nowTemplate.getKey(),
					nowTemplate.getPropAddValue(),
					null, "");
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer technologyType : technologyMap.keySet()) {
			TechnologyInfo technologyInfo = new TechnologyInfo();
			technologyInfo.setLevel(technologyMap.get(technologyType));
			technologyInfo.setTechnologyId(technologyType);

			humanEntity.getBuilder().addTechnology(
					this.converter.convert(technologyInfo).getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();

		for (TechnologyInfo technologyInfo : technologyInfoCaches.getAllUpdateData()) {
			HumanTechnologyEntity entity = this.converter.convert(technologyInfo);
			updateList.add(entity);
		}

		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();

		for (TechnologyInfo technologyInfo : technologyInfoCaches.getAllDeleteData()) {
			HumanTechnologyEntity entity = this.converter.convert(technologyInfo);
			deleteList.add(entity);
		}

		return deleteList;
	}
	
	public Map<Integer,Integer> getTechnologyMap() {
		return technologyMap;
	}

}
