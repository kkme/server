package com.hifun.soul.gameserver.reward.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.reward.RewardType;
import com.hifun.soul.gameserver.reward.msg.CGGetCommonReward;

@Component
public class CGGetCommonRewardHandler implements IMessageHandlerWithType<CGGetCommonReward> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_GET_COMMON_REWARD;
	}

	@Override
	public void execute(CGGetCommonReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		switch(message.getId()){
			case RewardType.ARENA_RANK_REWARD:
				human.getHumanArenaManager().recieveArenaRankReward();
				break;
			case RewardType.BOSS_KILL_REWARD:
				human.getHumanBossManager().recieveKillBossReward();
				break;
			case RewardType.BOSS_WAR_DAMAGE_REWARD:
				human.getHumanBossManager().recieveBossDamageReward();
				break;
			case RewardType.BOSS_WAR_RANK_REWARD:
				human.getHumanBossManager().recieveBossWarRankReward();
				break;
			case RewardType.MATCH_BATTLE_STREAK_WIN_RANK_REWARD:
				human.getHumanMatchBattleManager().recieveStreakWinRankReward();
				break;
			case RewardType.MARS_KILL_REWARD:
				human.getHumanMarsManager().recieveMarsKillReward();
				break;
			case RewardType.LEGION_BOSS_DAMAGE_REWARD:
				human.getHumanLegionBossManager().recieveBossDamageReward();
				break;
			case RewardType.LEGION_MINE_RANK_REWARD:
				human.getHumanLegionMineWarManager().receiveLegionMineRankReward();
				break;
			case RewardType.LEGION_MINE_BATTLE_REWARD:
				human.getHumanLegionMineWarManager().receiveLegionMineBattleReward();
				break;
		}
	}

}
