package com.hifun.soul.gameserver.sign.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanSignEntity;
import com.hifun.soul.gamedb.entity.HumanStarMapEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.StarMapActivateEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.sign.msg.GCActivateNewStarMap;
import com.hifun.soul.gameserver.sign.msg.GCActivateSign;
import com.hifun.soul.gameserver.sprite.model.SpriteSignInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo;
import com.hifun.soul.gameserver.sprite.template.SpriteSignTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteStarMapTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;
import com.hifun.soul.proto.data.entity.Entity.HumanSign;
import com.hifun.soul.proto.data.entity.Entity.HumanStarMap;

/**
 * 角色星座管理器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanSignManager implements IHumanPersistenceManager,
		ICachableComponent, IHumanPropertiesLoadForBattle {
	private Human human;
	/** 模版管理器 */
	private SpriteTemplateManager templateManager;
	/** 星图信息 */
	private List<SpriteStarMapInfo> starMapList;
	/** 星图缓存数据 */
	private CacheEntry<Integer, SpriteStarMapInfo> starMapCache = new CacheEntry<Integer, SpriteStarMapInfo>();
	/** 星座信息 */
	private Map<Integer, List<SpriteSignInfo>> starMapSigns;
	/** 星座缓存数据 */
	private CacheEntry<Integer, SpriteSignInfo> signCache = new CacheEntry<Integer, SpriteSignInfo>();

	public HumanSignManager(Human owner) {
		this.human = owner;
		templateManager = GameServerAssist.getSpriteTemplateManager();
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		// 星图数据
		for (SpriteStarMapInfo each : this.starMapCache.getAllUpdateData()) {
			HumanStarMapEntity entity = new HumanStarMapEntity();
			entity.getBuilder().setHumanGuid(this.human.getHumanGuid());
			entity.getBuilder().getStarMapBuilder()
					.setActivated(each.getActivated());
			entity.getBuilder().getStarMapBuilder()
					.setStarMapId(each.getStarMapId());
			updateList.add(entity);
		}
		// 星座数据
		for (SpriteSignInfo each : this.signCache.getAllUpdateData()) {
			HumanSignEntity entity = new HumanSignEntity();
			entity.getBuilder().setHumanGuid(this.human.getHumanGuid());
			entity.getBuilder().getSignBuilder().setActivated(each.getLight());
			entity.getBuilder().getSignBuilder().setSignId(each.getSignId());
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 先从模版数据构造所有星座
		buildAllSignInfosFromTemplate();
		// 激活星图;
		activateFirstStarMap();
		// 构建星图数据
		for (HumanStarMap.Builder eachSpriteBuilder : humanEntity.getBuilder()
				.getStarMapBuilderList()) {
			// 设置星图的存储状态
			SpriteStarMapInfo starMapInfo = this
					.getStarMapById(eachSpriteBuilder.getStarMap()
							.getStarMapId());
			starMapInfo.setActivated(eachSpriteBuilder.getStarMap()
					.getActivated());
		}
		// 构建星座数据
		for (HumanSign.Builder eachSign : humanEntity.getBuilder()
				.getSignBuilderList()) {
			SpriteSignInfo signInfo = this.getSignById(eachSign.getSign()
					.getSignId());
			signInfo.setLight(eachSign.getSign().getActivated());
			// 星图属性修正
			if (signInfo.getLight()) {
				this.amendProperty(human.getPropertyManager(), signInfo);
			}
		}

	}

	/**
	 * 修正属性值;
	 * 
	 * @param signInfo
	 */
	private void amendProperty(HumanPropertyManager propertyManager,
			SpriteSignInfo signInfo) {
		propertyManager.amendProperty(human,
				AmendMethod.valueOf(signInfo.getAmendType()),
				signInfo.getPropId(), signInfo.getPropValue(),
				PropertyLogReason.SIGN, "");
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 存储星图数据
		for (SpriteStarMapInfo eachInfo : this.starMapList) {
			HumanStarMap.Builder eachBuilder = HumanStarMap.newBuilder();
			eachBuilder.getStarMapBuilder().setActivated(
					eachInfo.getActivated());
			eachBuilder.setHumanGuid(humanEntity.getId());
			eachBuilder.getStarMapBuilder().setStarMapId(
					eachInfo.getStarMapId());
			humanEntity.getBuilder().addStarMap(eachBuilder);
		}
		// 存储星座数据
		for (List<SpriteSignInfo> eachList : this.starMapSigns.values()) {
			for (SpriteSignInfo eachInfo : eachList) {
				// 只存点亮的数据;
				if (!eachInfo.getLight()) {
					continue;
				}
				HumanSign.Builder eachBuilder = HumanSign.newBuilder();
				eachBuilder.getSignBuilder().setActivated(eachInfo.getLight());
				eachBuilder.setHumanGuid(humanEntity.getId());
				eachBuilder.getSignBuilder().setSignId(eachInfo.getSignId());
				humanEntity.getBuilder().addSign(eachBuilder);
			}
		}
	}

	/**
	 * 获取星图列表;
	 * 
	 * @return
	 */
	public List<SpriteStarMapInfo> getStarMapList() {
		return starMapList;
	}

	/**
	 * 从模版中构建数据;
	 */
	private void buildAllSignInfosFromTemplate() {
		// 构建星图信息
		this.starMapList = this.templateManager.getSpriteStarMapList();
		// 构建星座信息
		starMapSigns = this.templateManager.getStarMapSigns();
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 获取当前的星座列表;
	 * 
	 * @return
	 */
	public List<SpriteSignInfo> getCurrentSignList() {
		return this.starMapSigns.get(this.getCurrentStarMapInfo()
				.getStarMapId());
	}

	/**
	 * 根据星图id获取星座列表;
	 * 
	 * @param starMapId
	 * @return
	 */
	public List<SpriteSignInfo> getSignListByStarMapId(int starMapId) {
		return this.starMapSigns.get(starMapId);
	}

	/**
	 * 根據id激活指定的星座;
	 * 
	 * @param signId
	 */
	public void activateSignById(int signId) {
		// 是否有之前的星座未激活
		if (haveAnyPreSignNotActivated(signId)) {
			// 前置星座未激活
			human.sendErrorMessage(LangConstants.ACTIVATE_PRESIGN_FIRST);
			return;
		}
		SpriteSignInfo signInfo = getSignById(signId);
		// 是否已激活
		if (signInfo.getLight()) {
			return;
		}
		// 星魂是否足够
		if (human.getStarSoul() < signInfo.getCostStarSoul()) {
			// 提示星魂不够
			human.sendErrorMessage(LangConstants.STAR_SOUL_NOT_ENOUGH);
			return;
		}
		// 扣除星魂
		human.setStarSoul(human.getStarSoul() - signInfo.getCostStarSoul());
		signInfo.setLight(true);
		// 属性修正
		this.amendProperty(human.getPropertyManager(), signInfo);
		// 写入缓存
		this.signCache.addUpdate(signInfo.getSignId(), signInfo);
		if (currentMapLastSign(signId, signInfo.getStarMapId())) {
			// 开启下一张星图
			tryOpenNextStarMap(signId, signInfo.getStarMapId());
		}

		// 发通知
		GCActivateSign signMessage = new GCActivateSign();
		signMessage.setSignId(signId);
		signMessage.setRemainStarSoul(human.getStarSoul());
		human.sendMessage(signMessage);
		// 发送点亮星座事件
		human.getEventBus().fireEvent(new StarMapActivateEvent());
	}

	/**
	 * 是否是当前星图的最后一个星座;
	 * 
	 * @param signId
	 * @param starMapId
	 * @return
	 */
	private boolean currentMapLastSign(int signId, int starMapId) {
		if (signId != this.starMapSigns.get(starMapId)
				.get(this.starMapSigns.get(starMapId).size() - 1).getSignId()) {
			return false;
		}
		return true;
	}

	/**
	 * 开启下一张星图;
	 * 
	 * @param signId
	 * @param currentStarMapId
	 */
	private void tryOpenNextStarMap(int signId, int currentStarMapId) {
		SpriteStarMapInfo currentMap = this.getStarMapById(currentStarMapId);
		SpriteStarMapInfo nextStarMap = this.getStarMapById(currentMap
				.getNextStarMapId());
		if (nextStarMap == null) {
			return;
		}
		// 等级不满足
		if (nextStarMap.getOpenLevel() > human.getLevel()) {
			return;
		}
		nextStarMap.setActivated(true);
		// 写入缓存
		this.starMapCache.addUpdate(nextStarMap.getStarMapId(), nextStarMap);
		// 需要通知客户端激活星图
		GCActivateNewStarMap activateMapMessage = new GCActivateNewStarMap();
		activateMapMessage.setStarMapId(nextStarMap.getStarMapId());
		human.sendMessage(activateMapMessage);
	}

	/**
	 * 获取当前的星图;
	 * 
	 * @return
	 */
	public SpriteStarMapInfo getCurrentStarMapInfo() {
		// 如果没有激活的星图;
		if (!activateAnyStarMap()) {
			return null;
		}
		for (SpriteStarMapInfo eachMap : this.starMapList) {
			// 如果激活了,但是还没完成
			if (eachMap.getActivated() && !isAllSignsLight(eachMap)) {
				return eachMap;
			}
			// 如果激活了，也完成了, 但是下一张没开或者没有下一张了
			if (eachMap.getActivated() && isAllSignsLight(eachMap)
					&& canNotOpenNextStarMap(eachMap)) {
				return eachMap;
			}
		}
		return null;
	}

	/**
	 * 是否无法打开下一张星图了;
	 * 
	 * @param eachMap
	 * @return
	 */
	private boolean canNotOpenNextStarMap(SpriteStarMapInfo eachMap) {
		// 1. 没有下一张了
		if (this.getStarMapById(eachMap.getNextStarMapId()) == null) {
			return true;
		}
		// 2. 下一张开启等级不够
		SpriteStarMapInfo nextMap = this.getStarMapById(eachMap
				.getNextStarMapId());
		if (nextMap.getOpenLevel() > human.getLevel()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据Id获取星图;
	 * 
	 * @param id
	 * @return
	 */
	private SpriteStarMapInfo getStarMapById(int id) {
		for (SpriteStarMapInfo each : this.starMapList) {
			if (each.getStarMapId() == id) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 是否有已經激活的星图;
	 * 
	 * @return
	 */
	public boolean activateAnyStarMap() {
		SpriteStarMapInfo firstStarMap = this.starMapList.get(0);
		if (human.getLevel() < firstStarMap.getOpenLevel()) {
			return false;
		}
		return true;
	}

	/**
	 * 激活第一章星图
	 */
	private void activateFirstStarMap() {
		SpriteStarMapInfo firstStarMap = this.starMapList.get(0);
		if (human.getLevel() >= firstStarMap.getOpenLevel()
				&& !firstStarMap.getActivated()) {
			firstStarMap.setActivated(true);
			// 写入缓存
			this.starMapCache.addUpdate(firstStarMap.getStarMapId(),
					firstStarMap);
		}
	}

	/**
	 * 点亮当前星图中所有星座;
	 * 
	 * @param eachMap
	 * @return
	 */
	private boolean isAllSignsLight(SpriteStarMapInfo eachMap) {
		for (SpriteSignInfo each : this.starMapSigns
				.get(eachMap.getStarMapId())) {
			if (!each.getLight()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据id获取星座;
	 * 
	 * @param signId
	 * @return
	 */
	private SpriteSignInfo getSignById(int signId) {
		SpriteSignTemplate template = GameServerAssist.getTemplateService()
				.get(signId, SpriteSignTemplate.class);
		List<SpriteSignInfo> signList = this.starMapSigns.get(template
				.getStarMapId());
		for (SpriteSignInfo each : signList) {
			if (each.getSignId() == signId) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 是否有前置星座未激活;
	 * 
	 * @param signId
	 * @return
	 */
	private boolean haveAnyPreSignNotActivated(int signId) {
		SpriteSignTemplate template = GameServerAssist.getTemplateService()
				.get(signId, SpriteSignTemplate.class);
		List<SpriteSignInfo> signList = this.starMapSigns.get(template
				.getStarMapId());
		for (SpriteSignInfo each : signList) {
			if (each.getSignId() == signId) {
				return false;
			} else {
				if (!each.getLight()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断当前星图的前置星图是否已经全部激活;
	 * 
	 * @param template
	 * @return
	 */
	public boolean preStarMapAllSignsLight(SpriteStarMapTemplate template) {
		SpriteStarMapInfo preMap = getPreStarMapInfoById(template.getId());
		if (preMap == null) {
			return true;
		}
		if (isAllSignsLight(preMap)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定id的前置地图;
	 * 
	 * @param id
	 * @return
	 */
	private SpriteStarMapInfo getPreStarMapInfoById(int id) {
		for (SpriteStarMapInfo each : this.starMapList) {
			if (each.getNextStarMapId() == id) {
				return each;
			}
		}
		return null;
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		// 加载一下数据
		onLoad(humanEntity);
		for (Integer mapId : starMapSigns.keySet()) {
			List<SpriteSignInfo> signInfoList = starMapSigns.get(mapId);
			for (SpriteSignInfo signInfo : signInfoList) {
				if (signInfo.getLight()) {
					amendProperty(propertyManager, signInfo);
				}
			}
		}
	}

}
