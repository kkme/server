package com.hifun.soul.gameserver.vip.handler;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.vip.msg.CGVipShowPannel;
/**
 * 请求打开VIP面板处理类
 * 
 * @author magicstone
 * 
 */
@Component
public class CGVipShowPannelHandler implements IMessageHandlerWithType<CGVipShowPannel> {	
	@Override
	public short getMessageType() {
		return MessageType.CG_VIP_SHOW_PANNEL;
	}

	@Override
	public void execute(CGVipShowPannel message) {
		if(message.getPlayer() ==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		human.getHumanVipManager().sendUpdateVipPanelMessage();
	}

}
