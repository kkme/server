package com.hifun.soul.gameserver.mars.handler;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.msg.CGRfreshMarsRoom;
import com.hifun.soul.gameserver.mars.msg.GCRfreshMarsRoom;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;

@Component
public class CGRfreshMarsRoomHandler implements
		IMessageHandlerWithType<CGRfreshMarsRoom> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RFRESH_MARS_ROOM;
	}

	@Override
	public void execute(CGRfreshMarsRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS_REFRESH_ROOM, true)) {
			return;
		}
		// 消费魔晶
		int costNum = GameServerAssist.getGameConstants()
				.getRefreshMarsRoomCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.MARS_FEFRESH_ROOM, "")) {
			MarsRoomInfo roomInfo = human.getHumanMarsManager().getRoomInfo(
					message.getRoomType());
			if (roomInfo == null) {
				return;
			}
			// 将原房主添加到遭遇玩家列表中
			MarsPlayerInfo oldOwnerInfo = roomInfo.getOwnerInfo();
			MarsPlayerInfo loserInfo = new MarsPlayerInfo();
			try {
				PropertyUtils.copyProperties(loserInfo, oldOwnerInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			human.getHumanMarsManager().addLoser(loserInfo);
			// 为房间重新获取在线玩家或npc
			human.getHumanMarsManager().setOwnerForRoom(roomInfo);
			// 发送响应消息
			GCRfreshMarsRoom msg = new GCRfreshMarsRoom();
			msg.setRoomType(roomInfo.getRoomType());
			msg.setOwnerInfo(roomInfo.getOwnerInfo());
			human.sendMessage(msg);
		}

	}
}
