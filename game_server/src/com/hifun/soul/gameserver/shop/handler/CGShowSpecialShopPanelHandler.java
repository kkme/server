package com.hifun.soul.gameserver.shop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.shop.msg.CGShowSpecialShopPanel;
import com.hifun.soul.gameserver.shop.service.SpecialShopNotifyService;

@Component
public class CGShowSpecialShopPanelHandler implements
		IMessageHandlerWithType<CGShowSpecialShopPanel> {

	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private SpecialShopNotifyService specialShopService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_SPECIAL_SHOP_PANEL;
	}

	@Override
	public void execute(CGShowSpecialShopPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(player.getHuman(), GameFuncType.SPECIAL_SHOP, true)){
			return;
		}
		
		HumanSpecialShopManager specialShopManager = human.getHumanSpecialShopManager();
		specialShopManager.updateSpecialShopPanel(specialShopService);
	}

}
