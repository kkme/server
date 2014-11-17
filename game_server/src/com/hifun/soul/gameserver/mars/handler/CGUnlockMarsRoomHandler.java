package com.hifun.soul.gameserver.mars.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.MarsRoomLockState;
import com.hifun.soul.gameserver.mars.msg.CGUnlockMarsRoom;
import com.hifun.soul.gameserver.mars.msg.GCUnlockMarsRoom;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;

@Component
public class CGUnlockMarsRoomHandler implements
		IMessageHandlerWithType<CGUnlockMarsRoom> {

	@Override
	public short getMessageType() {
		return MessageType.CG_UNLOCK_MARS_ROOM;
	}

	@Override
	public void execute(CGUnlockMarsRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS_UNLOCK_ROOM, true)) {
			return;
		}
		// 花费魔晶
		int costNum = GameServerAssist.getGameConstants()
				.getUnlockMarsRoomCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.MARS_UNLOCK_ROOM, "")) {
			// 解锁房间
			MarsRoomInfo roomInfo = human.getHumanMarsManager().getRoomInfo(
					message.getRoomType());
			if (roomInfo == null) {
				return;
			}
			roomInfo.setIsLocked(MarsRoomLockState.UNLOCKED.getIndex());
			// 发送响应消息
			GCUnlockMarsRoom msg = new GCUnlockMarsRoom();
			msg.setRoomType(message.getRoomType());
			msg.setIsLocked(MarsRoomLockState.UNLOCKED.getIndex());
			human.sendMessage(msg);
		}
	}
}
