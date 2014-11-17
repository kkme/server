package com.hifun.soul.gameserver.rank.manager;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gamedb.entity.TitleRankEntity;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.HumanTitleRank;

import com.hifun.soul.gameserver.rank.model.HumanTitleRankData;

/**
 * 军衔排行榜管理
 * 
 * @author yandajun
 * 
 */
public class HumanTitleRankManager implements IEventListener {
	private Human _human;

	public HumanTitleRankManager(Human human) {
		this._human = human;
		this._human.getEventBus().addListener(EventType.TITLE_UP_EVENT, this);
		this._human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	@Override
	public void onEvent(IEvent event) {
		@SuppressWarnings("unchecked")
		HumanTitleRank<HumanTitleRankData> levelRank = ApplicationContext
				.getApplicationContext().getBean(HumanTitleRank.class);
		TitleRankEntity entity = new TitleRankEntity();
		entity.setId(_human.getHumanGuid());
		entity.setHumanName(_human.getName());
		entity.setTitle(_human.getCurrentTitle());
		entity.setLevel(_human.getLevel());
		entity.setOccupation(_human.getOccupation().getIndex());
		entity.setValid(true);
		levelRank.addUpdate(entity);
	}
}
