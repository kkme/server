package com.hifun.soul.gameserver.abattoir.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.abattoir.AbattoirRoom;
import com.hifun.soul.gameserver.abattoir.manager.GlobalAbattoirManager;
import com.hifun.soul.gameserver.abattoir.msg.CGQuitAbattoirRoom;
import com.hifun.soul.gameserver.abattoir.msg.GCQuitAbattoirRoom;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGQuitAbattoirRoomHandler implements
		IMessageHandlerWithType<CGQuitAbattoirRoom> {

	@Override
	public short getMessageType() {
		return MessageType.CG_QUIT_ABATTOIR_ROOM;
	}

	@Override
	public void execute(CGQuitAbattoirRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ABATTOIR, true)) {
			return;
		}
		GlobalAbattoirManager manager = GameServerAssist
				.getGlobalAbattoirManager();
		AbattoirRoom room = manager.getHumanAbattoirRoom(human.getHumanGuid());
		if (room == null) {
			human.sendErrorMessage(LangConstants.ABATTOIR_ROOM_NOT_EXIST);
			return;
		}
		manager.quitRoom(room);
		human.sendSuccessMessage(LangConstants.ABATTOIR_QUIT_ROOM_SUCCESS);
		GCQuitAbattoirRoom msg = new GCQuitAbattoirRoom();
		human.sendMessage(msg);
	}

}
