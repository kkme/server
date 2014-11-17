package com.hifun.soul.gameserver.rank.manager;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gamedb.entity.RankEntity;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.HumanLevelRank;

import com.hifun.soul.gameserver.rank.model.HumanLevelRankData;

public class HumanLevelRankManager implements IEventListener {
	private Human _human;

	public HumanLevelRankManager(Human human) {
		this._human = human;
		this._human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		this._human.getEventBus().addListener(EventType.TITLE_UP_EVENT, this);
	}

	@Override
	public void onEvent(IEvent event) {
		@SuppressWarnings("unchecked")
		HumanLevelRank<HumanLevelRankData> levelRank = ApplicationContext
				.getApplicationContext().getBean(HumanLevelRank.class);
		RankEntity entity = new RankEntity();
		entity.setId(_human.getHumanGuid());
		entity.setHumanName(_human.getName());
		entity.setLevel(_human.getLevel());
		entity.setHonor(_human.getCurrentTitle());
		entity.setOccupation(_human.getOccupation().getIndex());
		entity.setValid(true);
		levelRank.addUpdate(entity);
	}
}
