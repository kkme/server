package com.hifun.soul.gameserver.bloodtemple.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.BloodTempleRoomEntity;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;

public class BloodTempleRoomToEntityConverter implements
		IConverter<BloodTempleRoom, BloodTempleRoomEntity> {

	@Override
	public BloodTempleRoomEntity convert(BloodTempleRoom room) {
		BloodTempleRoomEntity entity = new BloodTempleRoomEntity();
		entity.setId(room.getRoomId());
		entity.setOwnerId(room.getOwnerId());
		entity.setOwnerOccupationType(room.getOwnerOccupationType());
		entity.setOwnerName(room.getOwnerName());
		entity.setOwnerLevel(room.getOwnerLevel());
		entity.setOwnerTitleId(room.getOwnerTitleId());
		entity.setLootTime(room.getLootTime());
		entity.setOwnerType(room.getOwnerType());
		return entity;
	}

	@Override
	public BloodTempleRoom reverseConvert(BloodTempleRoomEntity entity) {
		BloodTempleRoom room = new BloodTempleRoom();
		room.setRoomId((Integer) entity.getId());
		room.setOwnerId(entity.getOwnerId());
		room.setOwnerOccupationType(entity.getOwnerOccupationType());
		room.setOwnerName(entity.getOwnerName());
		room.setOwnerLevel(entity.getOwnerLevel());
		room.setOwnerTitleId(room.getOwnerTitleId());
		room.setOwnerType(entity.getOwnerType());
		room.setLootTime(entity.getLootTime());
		return room;
	}

}
