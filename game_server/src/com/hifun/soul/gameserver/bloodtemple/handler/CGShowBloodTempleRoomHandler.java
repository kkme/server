package com.hifun.soul.gameserver.bloodtemple.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.converter.BloodTempleRoomToInfoConverter;
import com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo;
import com.hifun.soul.gameserver.bloodtemple.msg.CGShowBloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.msg.GCShowBloodTempleRoom;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowBloodTempleRoomHandler implements
		IMessageHandlerWithType<CGShowBloodTempleRoom> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_BLOOD_TEMPLE_ROOM;
	}

	@Override
	public void execute(CGShowBloodTempleRoom message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		if (message.getPageIndex() < 1
				|| message.getPageIndex() > SharedConstants.BLOOD_TEMPLE_PAGE_COUNT) {
			return;
		}
		List<BloodTempleRoomInfo> roomInfoList = new ArrayList<BloodTempleRoomInfo>();
		List<BloodTempleRoom> roomList = GameServerAssist
				.getGlobalBloodTempleManager().getBloodTempleRoomList(
						message.getPageIndex());
		for (BloodTempleRoom room : roomList) {
			BloodTempleRoomInfo roomInfo = BloodTempleRoomToInfoConverter
					.converter(room);
			roomInfoList.add(roomInfo);
		}
		Collections.sort(roomInfoList);
		GCShowBloodTempleRoom msg = new GCShowBloodTempleRoom();
		msg.setRooms(roomInfoList.toArray(new BloodTempleRoomInfo[0]));
		human.sendMessage(msg);

	}

}
