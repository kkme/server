package com.hifun.soul.gameserver.rank;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gameserver.rank.model.RankData;
import com.hifun.soul.gameserver.rank.template.RankTemplate;

/**
 * 排行榜抽象类
 * 
 * @author magicstone
 * 
 * @param <T>
 */
@Component
public abstract class Rank<T extends RankData> {
	@Autowired
	private RankTemplateManager rankTemplateDataManager;
	/** 排行榜模板 */
	protected RankTemplate rankInfo;
	/** 排行榜数据列表 */
	protected List<T> rankList;
	/** 上一次刷新时间 */
	protected long lastRefreshTime = 0L;

	public Rank() {
		this.rankList = new ArrayList<T>();		
	}
	
	/**
	 * 初始化排行榜
	 */
	public void init(){		
		rankInfo = rankTemplateDataManager.getRankTemplate(getType().getIndex());
	}
	
	/**
	 * 初始化数据(实时刷新类排行榜，一开始加载数据)
	 */
	public void start(IDBService dbService){
		
	}
	
	public List<T> getRankList() {
		return rankList;
	}

	public void setRankList(List<T> rankList) {
		this.rankList = rankList;
	}

	/**
	 * 获取排行榜名称
	 * 
	 * @return
	 */
	public String getRankName() {
		return rankInfo.getRankName();
	}

	/**
	 * 获取排行榜描述信息
	 * 
	 * @return
	 */
	public String getRankDesc() {
		return rankInfo.getRankDesc();
	}

	/**
	 * 该排行榜是否有奖励
	 * 
	 * @return
	 */
	public boolean hasReward() {
		return rankInfo.isHasReward();
	}

	public long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
	
	public int getSize(){
		return rankList.size();
	}

	/**
	 * 初始化操作
	 */
	protected abstract RankType getType();

	/**
	 * 刷新排行榜数据
	 */
	public abstract void refreshDataList();
}
