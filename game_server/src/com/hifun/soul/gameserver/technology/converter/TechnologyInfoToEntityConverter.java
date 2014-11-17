package com.hifun.soul.gameserver.technology.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanTechnologyEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.technology.msg.TechnologyInfo;
import com.hifun.soul.proto.common.Technologys.Technology;

public class TechnologyInfoToEntityConverter implements IConverter<TechnologyInfo, HumanTechnologyEntity>{
	private Human _human;
	
	public TechnologyInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanTechnologyEntity convert(TechnologyInfo src) {
		HumanTechnologyEntity entity = new HumanTechnologyEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setTechnology(Technology.newBuilder()
				.setLevel(src.getLevel())
				.setTechnologyId(src.getTechnologyId()));
		return entity;
	}

	@Override
	public TechnologyInfo reverseConvert(HumanTechnologyEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
