package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.rank.RankTemplateManager;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.rank.template.RankTemplate;
import com.hifun.soul.gameserver.timetask.AbstractDailyAndIntervalTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

@Component
public class ResetArenaRankRewardTask extends AbstractDailyAndIntervalTask {

	@Autowired
	private RankTemplateManager templateService;
	@Autowired
	private ArenaService arenaService;
	private RankTemplate template = null; 
	
	@Override
	public long getTimeSpan() {
		if(template == null){
			template = templateService.getRankTemplate(RankType.ARENA_RANK.getIndex());
		}
		return template.getDays() * TimeUtils.DAY;
	}

	@Override
	public long getLastRunTime() {
		return arenaService.getLastResetRankRewardTime();
	}

	@Override
	public void setLastRunTime(long time) {
		arenaService.setLastResetRankRewardTime(time);
	}

	@Override
	public void run() {
		if(isStop()){
			return;
		}
		
		arenaService.resetArenaRankReward();
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_ARENA_RANK_REWARD.getIndex();
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}

}
