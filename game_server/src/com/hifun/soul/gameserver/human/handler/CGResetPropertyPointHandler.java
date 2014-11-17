package com.hifun.soul.gameserver.human.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.CGResetPropertyPoint;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.mall.service.MallService;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGResetPropertyPointHandler implements IMessageHandlerWithType<CGResetPropertyPoint> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RESET_PROPERTY_POINT;
	}

	@Override
	public void execute(CGResetPropertyPoint message) {
		Player player = message.getSession().getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		Item resetPropItem = human.getBagManager().searchItem(ItemConstantId.RESET_PROP_ITEM_ID, BagType.MAIN_BAG);
		if (resetPropItem != null) {
			human.getBagManager().usePropertyPointResetItem(resetPropItem.getBagType(), resetPropItem.getBagIndex());
		} else {
			MallService.sendAskMallItemInfoMessage(human, ItemConstantId.RESET_PROP_ITEM_ID);
		}
	}

}
