package com.hifun.soul.gameserver.matchbattle.msg.internal;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRoleState;

public class AutoJoinNextMatchScheduleMessage  extends SceneScheduleMessage{
	private MatchBattleRole matchBattleRole;
	public AutoJoinNextMatchScheduleMessage(MatchBattleRole role){
		matchBattleRole = role;
		matchBattleRole.setAutoJoinNextMatchScheduleMessage(this);
	}
	@Override
	public void execute() {		
		if(isCanceled()){
			return;
		}
		//正在战斗中、已经退出活动和已经在匹配状态，不再进行匹配
		if(matchBattleRole.getBattleState() == MatchBattleRoleState.IN_BATTLE
				|| matchBattleRole.getBattleState() == MatchBattleRoleState.NOT_JOIN
				|| matchBattleRole.getBattleState() == MatchBattleRoleState.WAIT_MATCH){
			return;
		}
		if(matchBattleRole.isInBattleScene()){
			return;
		}
		if(matchBattleRole.isAutoJoinBattle()){
			matchBattleRole.setBattleState(MatchBattleRoleState.WAIT_MATCH);
			if(matchBattleRole.getAutoJoinNextMatchScheduleMessage() != null){
				matchBattleRole.getAutoJoinNextMatchScheduleMessage().cancel();
			}
			matchBattleRole.setAutoJoinNextMatchScheduleMessage(null);
			GameServerAssist.getMatchBattleService().battleRoleStateChange(matchBattleRole);
		}
	}
	@Override
	public void cancel(){
		if(matchBattleRole.getAutoJoinNextMatchScheduleMessage()!=null){
			matchBattleRole.setAutoJoinNextMatchScheduleMessage(null);
		}
	}
}
