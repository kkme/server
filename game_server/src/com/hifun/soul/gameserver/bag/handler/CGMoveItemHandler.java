package com.hifun.soul.gameserver.bag.handler;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.bag.msg.CGMoveItem;
import com.hifun.soul.gameserver.bag.service.BagService;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGMoveItemHandler implements IMessageHandlerWithType<CGMoveItem> {

	@Autowired
	BagService bagService;

	@Override
	public short getMessageType() {
		return MessageType.CG_MOVE_ITEM;
	}

	@Override
	public void execute(CGMoveItem message) {
		Player player = message.getSession().getPlayer();
		HumanBagManager bagManager = player.getHuman().getBagManager();
		BagType fromBagType = BagType.indexOf(message.getFromBagType());
		BagType toBagType = BagType.indexOf(message.getToBagType());
		int fromBagIndex = message.getFromBagIndex();
		int toBagIndex = message.getToBagIndex();
		String reasonText = ItemLogReason.MOVE.getReasonText();
		String param = MessageFormat.format(reasonText, fromBagType.getIndex(),fromBagIndex,toBagType.getIndex(),toBagIndex);
		if (fromBagType == BagType.MAIN_BAG && toBagType == BagType.MAIN_BAG) {
			// 背包中移动
			bagManager.moveItem(fromBagIndex, toBagIndex, ItemLogReason.MOVE, param);
		}
	}
}
