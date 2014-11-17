package com.hifun.soul.gameserver.turntable.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.turntable.msg.CGShowTurntablePanel;

@Component
public class CGShowTurntablePanelHandler implements
		IMessageHandlerWithType<CGShowTurntablePanel> {
		
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_TURNTABLE_PANEL;
	}

	@Override
	public void execute(CGShowTurntablePanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
//		//检查是否可用
//		if(!marketActSetting.isEnable(MarketActType.TURNTABLE, human.getLevel(), human.getVipLevel())){
//			return;
//		}
		human.getHumanTurntableManager().onOpenClick();
	}

}
