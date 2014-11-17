package com.hifun.soul.gameserver.bag.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.msg.CGRemoveItem;
import com.hifun.soul.gameserver.bag.service.BagService;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGRemoveItemHandler implements
		IMessageHandlerWithType<CGRemoveItem> {

	@Autowired
	BagService bagService;

	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_ITEM;
	}

	@Override
	public void execute(CGRemoveItem message) {
		Player player = message.getSession().getPlayer();
		BagType bagType = BagType.indexOf(message.getBagType());
		int bagIndex = message.getBagIndex();
		player.getHuman().getBagManager()
				.removeItem(bagType, bagIndex,ItemLogReason.DELETE, "");

	}

}
