package com.hifun.soul.gameserver.honourshop.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.honourshop.msg.CGShowHonourShopItemList;
import com.hifun.soul.gameserver.honourshop.msg.GCShowHonourShopItemList;
import com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo;
import com.hifun.soul.gameserver.honourshop.service.HonourShopTemplateManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowHonourShopItemListHandler implements
		IMessageHandlerWithType<CGShowHonourShopItemList> {

	@Autowired
	private HonourShopTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_HONOUR_SHOP_ITEM_LIST;
	}

	@Override
	public void execute(CGShowHonourShopItemList message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.HONOUR_SHOP,
				true)) {
			return;
		}
		List<HonourShopItemInfo> allCanSeeList = templateManager
				.getHonourShopItemInfos(human.getLevel());
		if (allCanSeeList == null) {
			return;
		}
		templateManager.sortHonourShopItemList(allCanSeeList);

		GCShowHonourShopItemList gcMsg = new GCShowHonourShopItemList();
		gcMsg.setHonourShopItemList(allCanSeeList
				.toArray(new HonourShopItemInfo[0]));
		human.sendMessage(gcMsg);
	}

}
