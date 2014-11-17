package com.hifun.soul.gameserver.rank;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.rank.model.HumanTitleRankData;
import com.hifun.soul.gamedb.entity.TitleRankEntity;

/**
 * 角色军衔排行榜
 * 
 * @author yandajun
 * 
 */
@Component
public class HumanTitleRank<T> extends HumanRank<HumanTitleRankData> implements
		ICachableComponent {
	protected static Logger logger = Loggers.RANK_LOGGER;
	@Autowired
	private IDataService dataService;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();

	@Override
	public void init() {
		super.init();
		cacheManager.registerOtherCachableComponent(this);
	}

	@Override
	public RankType getType() {
		return RankType.HUMAN_TITLE_RANK;
	}

	@Override
	public void refreshDataList() {
		dataService.query(DataQueryConstants.QUERY_HUMAN_TITLE_RANK,
				new IDBCallback<List<?>>() {
					@Override
					public void onSucceed(List<?> result) {
						logger.info("refresh humanTitleRank Data success!");
						if (result == null) {
							return;
						}
						if (result.isEmpty()) {
							rankList.clear();
							return;
						}
						if (!(result.get(0) instanceof TitleRankEntity)) {
							logger.error("Thre result type is not a TitleRankEntity type: "
									+ result.get(0).getClass());
							return;
						}
						rankList.clear();
						for (int i = 0; i < result.size(); i++) {
							TitleRankEntity entity = (TitleRankEntity) result
									.get(i);
							rankList.add(new HumanTitleRankData(entity));
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error("refresh Data faild! errorMessage:"
								+ errorMsg);
					}
				});
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	public void addUpdate(TitleRankEntity entity) {
		cache.addUpdate((Long) entity.getId(), entity);
	}

}
