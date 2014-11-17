package com.hifun.soul.gameserver.mall.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.CGShowMallItem;
import com.hifun.soul.gameserver.mall.msg.GCShowMallItem;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;
import com.hifun.soul.gameserver.mall.service.MallTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowMallItemHandler implements
		IMessageHandlerWithType<CGShowMallItem> {

	@Autowired
	private MallTemplateManager mallService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MALL_ITEM;
	}

	@Override
	public void execute(CGShowMallItem message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MALL, true)) {
			return;
		}
		
		MallItemInfo mallItemInfo = mallService.getMallItemInfo(message.getItemId());
		if(mallItemInfo == null
				|| !mallService.canSee(mallItemInfo.getCommonItem(), human.getLevel(), human.getOccupation().getIndex())){
			return;
		}
		GCShowMallItem gcMsg = new GCShowMallItem();
		gcMsg.setMallItem(mallItemInfo);
		human.sendMessage(gcMsg);
	}

}
