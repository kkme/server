package com.hifun.soul.gameserver.mars.convert;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanMarsRoomEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;
import com.hifun.soul.proto.common.Mars.MarsRoom;
import com.hifun.soul.proto.common.Mars.MarsRoom.Builder;

public class MarsRoomConverter implements
		IConverter<MarsRoomInfo, HumanMarsRoomEntity> {

	private Human human;

	public MarsRoomConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanMarsRoomEntity convert(MarsRoomInfo src) {
		HumanMarsRoomEntity entity = new HumanMarsRoomEntity();
		entity.getBuilder().setHumanGuid(human.getHumanGuid());
		Builder builder = MarsRoom.newBuilder();
		builder.setRoomType(src.getRoomType());
		builder.setIsLocked(src.getIsLocked());
		MarsPlayerInfo ownerInfo = src.getOwnerInfo();
		builder.setOwnerType(ownerInfo.getPlayerType().getIndex());
		builder.setOwnerId(ownerInfo.getPlayerId());
		builder.setOwnerLevel(ownerInfo.getPlayerLevel());
		builder.setOwnerName(ownerInfo.getPlayerName());
		builder.setOwnerOccupation(ownerInfo.getOccupationType());
		builder.setTodayKillValue(ownerInfo.getTodayKillValue());
		entity.getBuilder().setMarsRoom(builder);
		return entity;
	}

	@Override
	public MarsRoomInfo reverseConvert(HumanMarsRoomEntity src) {

		return null;
	}

}
