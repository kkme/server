package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.manager.GlobalBloodTempleManager;
import com.hifun.soul.gameserver.bloodtemple.msg.CGQuitBloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.msg.GCQuitBloodTempleRoom;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGQuitBloodTempleRoomHandler implements
		IMessageHandlerWithType<CGQuitBloodTempleRoom> {

	@Override
	public short getMessageType() {
		return MessageType.CG_QUIT_BLOOD_TEMPLE_ROOM;
	}

	@Override
	public void execute(CGQuitBloodTempleRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		GlobalBloodTempleManager manager = GameServerAssist
				.getGlobalBloodTempleManager();
		BloodTempleRoom room = manager.getHumanBloodTempleRoom(human
				.getHumanGuid());
		if (room == null) {
			human.sendErrorMessage(LangConstants.BLOOD_TEMPLE_ROOM_NOT_EXIST);
			return;
		}
		manager.quitRoom(room);
		human.sendSuccessMessage(LangConstants.BLOOD_TEMPLE_QUIT_ROOM_SUCCESS);
		GCQuitBloodTempleRoom msg = new GCQuitBloodTempleRoom();
		human.sendMessage(msg);
	}

}
