package com.hifun.soul.gameserver.bloodtemple.converter;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleOwnerType;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legion.Legion;

public class BloodTempleRoomToInfoConverter {
	public static BloodTempleRoomInfo converter(BloodTempleRoom room) {
		BloodTempleRoomInfo roomInfo = new BloodTempleRoomInfo();
		roomInfo.setRoomId(room.getRoomId());
		roomInfo.setRoomName(room.getRoomName());
		roomInfo.setOwnerId(room.getOwnerId());
		roomInfo.setOwnerOccupationType(room.getOwnerOccupationType());
		roomInfo.setOwnerType(room.getOwnerType());
		roomInfo.setOwnerName(room.getOwnerName());
		roomInfo.setOwnerLevel(room.getOwnerLevel());
		roomInfo.setOwnerTitleId(room.getOwnerTitleId());
		roomInfo.setOwnerTitleName(room.getOwnerTitleName());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				room.getOwnerId());
		if (legion != null) {
			roomInfo.setOwnerLegionId(legion.getId());
			roomInfo.setOwnerLegionName(legion.getLegionName());
		}
		roomInfo.setRevenue(room.getRevenue());
		// 保护剩余时间(玩家占据着才有)
		if (room.getOwnerType() == BloodTempleOwnerType.PLAYER_WRESTLER
				.getIndex()) {
			roomInfo.setProtectTime((int) (room.getLootTime()
					+ room.getProtectTime() * TimeUtils.MIN - GameServerAssist
					.getSystemTimeService().now()));
		}
		return roomInfo;
	}
}
