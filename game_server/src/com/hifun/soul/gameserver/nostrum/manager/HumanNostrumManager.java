package com.hifun.soul.gameserver.nostrum.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanNostrumEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.nostrum.Nostrum;
import com.hifun.soul.gameserver.nostrum.converter.NostrumToEntityConverter;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.proto.data.entity.Entity.HumanNostrum;

/**
 * 角色秘药管理器
 * 
 * @author yandajun
 * 
 */
public class HumanNostrumManager implements IHumanPersistenceManager,
		ICachableComponent, IHumanPropertiesLoadForBattle {
	private Human human;
	private Map<Integer, Nostrum> nostrumMap = new HashMap<Integer, Nostrum>();
	NostrumToEntityConverter converter;
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();

	public HumanNostrumManager(Human human) {
		this.human = human;
		converter = new NostrumToEntityConverter(human);
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanNostrum.Builder builder : humanEntity.getBuilder()
				.getNostrumBuilderList()) {
			Nostrum nostrum = new Nostrum();
			nostrum.setPropertyId(builder.getNostrum().getPropertyId());
			nostrum.setEndTime(builder.getNostrum().getEndTime());
			nostrum.setPropertyAmendRate(builder.getNostrum()
					.getPropertyAmendRate());
			nostrum.setPropertyAmendMethod(builder.getNostrum()
					.getPropertyAmendMethod());
			nostrumMap.put(builder.getNostrum().getPropertyId(), nostrum);
		}
		// 秘药属性加成
		amendNostrumProperties();
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer propertyId : nostrumMap.keySet()) {
			Nostrum nostrum = nostrumMap.get(propertyId);
			HumanNostrumEntity nostrumEntity = converter.convert(nostrum);
			humanEntity.getBuilder().addNostrum(nostrumEntity.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	/**
	 * 获取秘药列表
	 */
	public List<Nostrum> getNostrumList() {
		List<Nostrum> nostrumList = new ArrayList<Nostrum>();
		for (Integer propertyId : nostrumMap.keySet()) {
			Nostrum nostrum = nostrumMap.get(propertyId);
			nostrumList.add(nostrum);
		}
		return nostrumList;
	}

	/**
	 * 到期删除秘药
	 */
	public void removeNostrum(int propertyId) {
		// 减去属性效果
		Nostrum nostrum = nostrumMap.get(propertyId);
		AmendMethod amendType = AmendMethod.valueOf(nostrum
				.getPropertyAmendMethod());
		int propertyValue = nostrum.getPropertyAmendRate();
		human.getPropertyManager().amendProperty(human, amendType, propertyId,
				-propertyValue, PropertyLogReason.NOSTRUM, "");
		cache.addDelete(propertyId, converter.convert(nostrum));
		nostrumMap.remove(propertyId);
	}

	/**
	 * 添加秘药
	 */
	public void addNostrum(int propertyId, int minutes, int propertyAmendRate,
			int propertyAmendMethod) {
		Nostrum nostrum = null;
		if (nostrumMap.get(propertyId) != null) {
			nostrum = nostrumMap.get(propertyId);
			nostrum.setEndTime(nostrum.getEndTime() + TimeUtils.MIN * minutes);
		} else {
			nostrum = new Nostrum();
			nostrum.setPropertyId(propertyId);
			nostrum.setPropertyAmendRate(propertyAmendRate);
			nostrum.setPropertyAmendMethod(propertyAmendMethod);
			nostrum.setEndTime(GameServerAssist.getSystemTimeService().now()
					+ TimeUtils.MIN * minutes);
		}
		nostrumMap.put(propertyId, nostrum);
		amendNostrumProperty(propertyId);
		cache.addUpdate(nostrum.getPropertyId(), converter.convert(nostrum));
	}

	/**
	 * 处理到期的秘药
	 */
	public void outOfDateNostrums() {
		// 到期需要删除的秘药
		List<Integer> deleteNostrums = new ArrayList<Integer>();
		for (Integer propertyId : nostrumMap.keySet()) {
			Nostrum nostrum = nostrumMap.get(propertyId);
			if (GameServerAssist.getSystemTimeService().now() >= nostrum
					.getEndTime()) {
				deleteNostrums.add(propertyId);
			}
		}
		for (Integer propertyId : deleteNostrums) {
			removeNostrum(propertyId);
		}
	}

	/**
	 * 所有秘药属性加成
	 */
	public void amendNostrumProperties() {
		for (Integer propertyId : nostrumMap.keySet()) {
			amendNostrumProperty(propertyId);
		}
	}

	/**
	 * 单一秘药属性加成
	 */
	private void amendNostrumProperty(int propertyId) {
		Nostrum nostrum = nostrumMap.get(propertyId);
		AmendMethod amendType = AmendMethod.valueOf(nostrum
				.getPropertyAmendMethod());
		int propertyValue = nostrum.getPropertyAmendRate();
		human.getPropertyManager().amendProperty(human, amendType, propertyId,
				propertyValue, PropertyLogReason.NOSTRUM, "");
	}

	/**
	 * 秘药战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		for (HumanNostrum.Builder builder : humanEntity.getBuilder()
				.getNostrumBuilderList()) {
			AmendMethod amendType = AmendMethod.valueOf(builder.getNostrum()
					.getPropertyAmendMethod());
			propertyManager.amendProperty(human, amendType, builder
					.getNostrum().getPropertyId(), builder.getNostrum()
					.getPropertyAmendRate(), PropertyLogReason.NOSTRUM, "");
		}
	}
}
