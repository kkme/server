package com.hifun.soul.gameserver.abattoir.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.AbattoirRoomEntity;
import com.hifun.soul.gameserver.abattoir.AbattoirRoom;

public class AbattoirRoomToEntityConverter implements
		IConverter<AbattoirRoom, AbattoirRoomEntity> {

	@Override
	public AbattoirRoomEntity convert(AbattoirRoom room) {
		AbattoirRoomEntity entity = new AbattoirRoomEntity();
		entity.setId(room.getRoomId());
		entity.setOwnerId(room.getOwnerId());
		entity.setOwnerName(room.getOwnerName());
		entity.setOwnerOccupationType(room.getOwnerOccupationType());
		entity.setOwnerLevel(room.getOwnerLevel());
		entity.setOwnerType(room.getOwnerType());
		entity.setLootTime(room.getLootTime());
		return entity;
	}

	@Override
	public AbattoirRoom reverseConvert(AbattoirRoomEntity entity) {
		AbattoirRoom room = new AbattoirRoom();
		room.setRoomId((Integer) entity.getId());
		room.setOwnerId(entity.getOwnerId());
		room.setOwnerName(entity.getOwnerName());
		room.setOwnerOccupationType(entity.getOwnerOccupationType());
		room.setOwnerLevel(entity.getOwnerLevel());
		room.setOwnerType(entity.getOwnerType());
		room.setLootTime(entity.getLootTime());
		return room;
	}

}
