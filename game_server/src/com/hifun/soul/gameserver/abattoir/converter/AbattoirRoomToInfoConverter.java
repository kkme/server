package com.hifun.soul.gameserver.abattoir.converter;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.abattoir.AbattoirOwnerType;
import com.hifun.soul.gameserver.abattoir.AbattoirRoom;
import com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo;
import com.hifun.soul.gameserver.common.GameServerAssist;

public class AbattoirRoomToInfoConverter {
	public static AbattoirRoomInfo converter(AbattoirRoom room) {
		AbattoirRoomInfo roomInfo = new AbattoirRoomInfo();
		roomInfo.setRoomId(room.getRoomId());
		roomInfo.setRoomName(room.getRoomName());
		roomInfo.setOwnerId(room.getOwnerId());
		roomInfo.setOwnerType(room.getOwnerType());
		roomInfo.setOwnerName(room.getOwnerName());
		roomInfo.setOwnerLevel(room.getOwnerLevel());
		roomInfo.setOwnerOccupationType(room.getOwnerOccupationType());
		roomInfo.setRevenue(room.getRevenue());
		// 保护剩余时间(玩家占据着才有)
		if (room.getOwnerType() == AbattoirOwnerType.PLAYER_WRESTLER.getIndex()) {
			roomInfo.setProtectTime((int) (room.getLootTime()
					+ room.getProtectTime() * TimeUtils.MIN - GameServerAssist
					.getSystemTimeService().now()));
		}
		return roomInfo;
	}
}
