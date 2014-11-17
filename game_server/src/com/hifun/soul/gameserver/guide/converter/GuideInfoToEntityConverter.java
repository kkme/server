package com.hifun.soul.gameserver.guide.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanGuideEntity;
import com.hifun.soul.gameserver.human.Human;

public class GuideInfoToEntityConverter implements IConverter<Integer, HumanGuideEntity>{
	private Human _human;
	
	public GuideInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanGuideEntity convert(Integer src) {
		HumanGuideEntity entity = new HumanGuideEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setGuideType(src);
		return entity;
	}

	@Override
	public Integer reverseConvert(HumanGuideEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
