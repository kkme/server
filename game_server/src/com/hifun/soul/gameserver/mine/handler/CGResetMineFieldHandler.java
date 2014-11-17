package com.hifun.soul.gameserver.mine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGResetMineField;

/**
 * 重置矿坑
 * @author magicstone
 *
 */
@Component
public class CGResetMineFieldHandler implements IMessageHandlerWithType<CGResetMineField> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RESET_MINE_FIELD;
	}

	@Override
	public void execute(CGResetMineField message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human==null){
			return;
		}
		HumanMineManager mineManager = human.getHumanMineManager();
		mineManager.resetMineFiled(message.getIndex());
	}

}
