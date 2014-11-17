package com.hifun.soul.gameserver.mars.convert;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanMarsLoserEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.proto.common.Mars.MarsLoser;
import com.hifun.soul.proto.common.Mars.MarsLoser.Builder;

public class MarsLoserConverter implements
		IConverter<MarsPlayerInfo, HumanMarsLoserEntity> {

	private Human human;

	public MarsLoserConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanMarsLoserEntity convert(MarsPlayerInfo src) {
		HumanMarsLoserEntity entity = new HumanMarsLoserEntity();
		entity.getBuilder().setHumanGuid(human.getHumanGuid());
		Builder builder = MarsLoser.newBuilder();
		builder.setLoserId(src.getPlayerId());
		builder.setLoserLevel(src.getPlayerLevel());
		builder.setLoserName(src.getPlayerName());
		builder.setLoserOccupation(src.getOccupationType());
		builder.setTodayKillValue(src.getTodayKillValue());
		builder.setIsLoser(src.getIsLoser());
		builder.setPlayerType(src.getPlayerType().getIndex());
		builder.setKillTime(src.getKillTime());
		entity.getBuilder().setMarsLoser(builder);
		return entity;
	}

	@Override
	public MarsPlayerInfo reverseConvert(HumanMarsLoserEntity src) {
		return null;
	}

}
