package com.hifun.soul.gameserver.mine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGAllMineHarvest;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAllMineHarvestHandler implements IMessageHandlerWithType<CGAllMineHarvest> {
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ALL_MINE_HARVEST;
	}

	@Override
	public void execute(CGAllMineHarvest message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开启
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ALL_MINE_HARVEST, true)){
			return;
		}
		
		HumanMineManager mineManager = human.getHumanMineManager();
		mineManager.harvestAllMine();
	}

}
