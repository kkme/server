package com.hifun.soul.gameserver.mars.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.MarsMemberEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.MarsMember;
import com.hifun.soul.gameserver.mars.convert.MarsMemberToEntityConverter;

/**
 * 全局战神之巅管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalMarsManager implements IInitializeRequired,
		ICachableComponent {
	private Map<Long, MarsMember> marsMemberMap = new HashMap<Long, MarsMember>();
	private MarsMemberToEntityConverter memberConverter = new MarsMemberToEntityConverter();
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();

	@Override
	public void init() {
		this.cacheManager.registerOtherCachableComponent(this);
	}

	public void start(IDBService dbService) {
		// 加载所有的战神之巅玩家信息
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_MARS_MEMBER);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<MarsMemberEntity> entityList = (List<MarsMemberEntity>) result;
			for (MarsMemberEntity entity : entityList) {
				MarsMember marsMember = memberConverter.reverseConvert(entity);
				marsMemberMap.put(marsMember.getHumanId(), marsMember);
			}
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
	 * 增加战神之巅玩家
	 */
	public void addMarsMember(Human human) {
		MarsMember marsMember = new MarsMember();
		marsMember.setHumanId(human.getHumanGuid());
		marsMember.setHumanLevel(human.getLevel());
		marsMember.setHumanName(human.getName());
		marsMember.setOccupation(human.getOccupation().getIndex());
		GameServerAssist.getDataService().insert(
				memberConverter.convert(marsMember));
		marsMemberMap.put(marsMember.getHumanId(), marsMember);
	}

	/**
	 * 获取战神之巅玩家
	 */
	public MarsMember getMarsMember(long humanId) {
		return marsMemberMap.get(humanId);
	}

	/**
	 * 获取阵营当日杀戮值
	 */
	public int getFactionTodayKillValue(int faction) {
		int todayKillValue = 0;
		for (long humanId : marsMemberMap.keySet()) {
			MarsMember marsMember = marsMemberMap.get(humanId);
			if (marsMember.getFaction() == faction) {
				todayKillValue += marsMember.getTodayKillValue();
			}
		}
		return todayKillValue;
	}

	/**
	 * 获取阵营内所有玩家
	 */
	public List<MarsMember> getFactionMarsMembers(int faction) {
		List<MarsMember> memberList = new ArrayList<MarsMember>();
		for (long humanId : marsMemberMap.keySet()) {
			MarsMember marsMember = marsMemberMap.get(humanId);
			if (marsMember.getFaction() == faction) {
				memberList.add(marsMember);
			}
		}
		return memberList;
	}

	/**
	 * 更新玩家信息
	 * 
	 */
	public void updateMarsMember(MarsMember marsMember) {
		cache.addUpdate(marsMember.getHumanId(),
				memberConverter.convert(marsMember));
	}

	/**
	 * 定时任务：重置每日杀戮值
	 */
	public void resetDailyData() {
		for (long humanId : marsMemberMap.keySet()) {
			marsMemberMap.get(humanId).setTodayKillValue(0);
			cache.addUpdate(humanId,
					memberConverter.convert(marsMemberMap.get(humanId)));
		}
	}
}
