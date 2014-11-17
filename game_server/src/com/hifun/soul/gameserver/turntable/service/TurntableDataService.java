package com.hifun.soul.gameserver.turntable.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo;

@Scope("singleton")
@Component
public class TurntableDataService {
	private LinkedList<TurntableRewardInfo> rewards = new LinkedList<TurntableRewardInfo>();
	
	public void start(IDBService dbService) {
		loadRewards(dbService);
	}
	
	private void loadRewards(IDBService dbService) {
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_TURNTABLE_REWARD);
		if(!result.isEmpty()){
			@SuppressWarnings("unchecked")
			List<Object[]> rewardInfos = (List<Object[]>)result;
			int i=0;
			for(Object[] objects : rewardInfos){
				if(i>= GameServerAssist.getGameConstants().getTurntableRewardShowNum()){
					break;
				}
				TurntableRewardInfo turntableRewardInfo = new TurntableRewardInfo();
				turntableRewardInfo.setId(Integer.valueOf(objects[0].toString()));
				turntableRewardInfo.setRoleName(objects[1].toString());
				turntableRewardInfo.setRewardName(objects[2].toString());
				rewards.add(turntableRewardInfo);
				i++;
			}
		}
	}
	
	public void addTurntableRewardInfo(TurntableRewardInfo turntableRewardInfo) {
		if(rewards.size() >= GameServerAssist.getGameConstants().getTurntableRewardShowNum()){
			rewards.removeLast();
		}
		rewards.addFirst(turntableRewardInfo);
	}
	
	public TurntableRewardInfo[] getRewardInfos() {
		return rewards.toArray(new TurntableRewardInfo[0]);
	}
			
}
