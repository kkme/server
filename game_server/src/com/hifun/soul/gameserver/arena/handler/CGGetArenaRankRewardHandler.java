package com.hifun.soul.gameserver.arena.handler;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.arena.msg.CGGetArenaRankReward;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGetArenaRankRewardHandler implements
		IMessageHandlerWithType<CGGetArenaRankReward> {


	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ARENA_RANK_REWARD;
	}

	@Override
	public void execute(CGGetArenaRankReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		human.getHumanArenaManager().recieveArenaRankReward();		
	}

}
