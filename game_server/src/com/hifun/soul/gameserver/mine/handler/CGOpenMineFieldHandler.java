package com.hifun.soul.gameserver.mine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGOpenMineField;

/**
 * 开启矿坑消息处理
 * @author magicstone
 *
 */
@Component
public class CGOpenMineFieldHandler implements IMessageHandlerWithType<CGOpenMineField> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_MINE_FIELD;
	}

	@Override
	public void execute(CGOpenMineField message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human==null){
			return;
		}
		int index = message.getIndex();
		if(index<0 || index>=HumanMineManager.MAX_MINE_FIELD_NUM){
			return;
		}
		HumanMineManager mineManager = human.getHumanMineManager();
		
		mineManager.openMineField(index);
	}

}
