package com.hifun.soul.gameserver.rank;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gameserver.rank.model.HumanVipRankData;
import com.hifun.soul.gamedb.entity.VipRankEntity;

/**
 * 角色VIP排行榜(实时改变)
 * 
 * @author yandajun
 * 
 */
@Component
public class HumanVipRank<T> extends HumanRank<HumanVipRankData> implements
		ICachableComponent, Sortable {
	protected static Logger logger = Loggers.RANK_LOGGER;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();

	@Override
	public void init() {
		super.init();
		cacheManager.registerOtherCachableComponent(this);
	}

	public void start(IDBService dbService) {
		initDataList(dbService);
	}

	/**
	 * 初始化排行榜数据
	 */
	public void initDataList(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_HUMAN_VIP_RANK);
		logger.info("refresh humanVipRank Data success!");
		if (result == null) {
			return;
		}
		if (result.isEmpty()) {
			rankList.clear();
			return;
		}
		if (!(result.get(0) instanceof VipRankEntity)) {
			logger.error("Thre result type is not a VipRankEntity type: "
					+ result.get(0).getClass());
			return;
		}
		rankList.clear();
		for (int i = 0; i < result.size(); i++) {
			VipRankEntity entity = (VipRankEntity) result.get(i);
			rankList.add(new HumanVipRankData(entity));
		}
	}

	@Override
	public RankType getType() {
		return RankType.HUMAN_VIP_RANK;
	}

	/**
	 * 对排行榜数据重新排序
	 */
	@Override
	public void sort() {
		Collections.sort(rankList);
	}

	@Override
	public void refreshDataList() {
		sort();
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	public void addUpdate(VipRankEntity entity) {
		cache.addUpdate((Long) entity.getId(), entity);
		rankList.add(new HumanVipRankData(entity));
		refreshDataList();
	}

}
