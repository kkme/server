package com.hifun.soul.gameserver.mine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGMineHarvest;

/**
 * 收获矿石处理程序
 * @author magicstone
 *
 */
@Component
public class CGMineHarvestHandler implements IMessageHandlerWithType<CGMineHarvest> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_MINE_HARVEST;
	}

	@Override
	public void execute(CGMineHarvest message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human==null){
			return;
		}
		HumanMineManager mineManager = human.getHumanMineManager();
		mineManager.harvest(message.getIndex());
	}

}
