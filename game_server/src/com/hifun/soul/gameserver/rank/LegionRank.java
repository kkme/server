package com.hifun.soul.gameserver.rank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.rank.model.LegionRankData;

public abstract class LegionRank<T extends LegionRankData> extends Rank<T>
		implements ICachableComponent, Sortable {
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
	public abstract void initDataList(IDBService dbService);

	@Override
	public abstract void sort();

	@Override
	public void refreshDataList() {
		initDataList(null);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	protected abstract LegionRankData legionToRankData(Legion legion);
}
