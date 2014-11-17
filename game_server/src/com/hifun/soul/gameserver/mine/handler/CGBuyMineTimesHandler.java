package com.hifun.soul.gameserver.mine.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.mine.msg.CGBuyMineTimes;

/**
 * 购买开采次数处理
 * @author magicstone
 *
 */
@Component
public class CGBuyMineTimesHandler implements IMessageHandlerWithType<CGBuyMineTimes> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_MINE_TIMES;
	}

	@Override
	public void execute(CGBuyMineTimes message) {
		// edit by will@2013-5-9 该消息已过时
//		if(message.getPlayer()==null){
//			return;
//		}
//		Human human = message.getPlayer().getHuman();
//		if(human==null){
//			return;
//		}		
//		HumanMineManager mineManager = human.getHumanMineManager();
//		if(mineManager.buyMineTimes()){
//			mineManager.sendBuyMineTimesMessage();
//		}
	}

}
