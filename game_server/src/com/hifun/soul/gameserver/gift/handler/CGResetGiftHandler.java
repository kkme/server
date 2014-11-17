package com.hifun.soul.gameserver.gift.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.gift.manager.HumanGiftManager;
import com.hifun.soul.gameserver.gift.msg.CGResetGift;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.mall.service.MallService;

@Component
public class CGResetGiftHandler implements IMessageHandlerWithType<CGResetGift> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RESET_GIFT;
	}

	@Override
	public void execute(CGResetGift message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		Item item = human.getBagManager().searchItem(ItemConstantId.RESET_GIFT_POINT_ITEM_ID, BagType.MAIN_BAG);
		if (item != null) {
			boolean useResult = human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1,
					ItemLogReason.COMSUME_USE, "");
			if (useResult) {
				HumanGiftManager giftManager = human.getHumanGiftManager();
				giftManager.sendShowGiftPanelMessage();
			}
		} else {
			MallService.sendAskMallItemInfoMessage(human, ItemConstantId.RESET_GIFT_POINT_ITEM_ID);
		}
	}
}
