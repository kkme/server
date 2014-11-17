package com.hifun.soul.gameserver.horoscope.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStargazerEntity;
import com.hifun.soul.gameserver.horoscope.StargazerInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.Stargazers.Stargazer;

public class StargazerInfoToEntityConverter implements IConverter<StargazerInfo, HumanStargazerEntity>{
	private Human _human;
	
	public StargazerInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStargazerEntity convert(StargazerInfo src) {
		HumanStargazerEntity entity = new HumanStargazerEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setStargazer(Stargazer.newBuilder()
				.setOpen(src.getOpen())
				.setStargazerId(src.getStargazerId()));
		return entity;
	}

	@Override
	public StargazerInfo reverseConvert(HumanStargazerEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
