package com.hifun.soul.gameserver.horoscope.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanHoroscopeEntity;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.Horoscopes.Horoscope;

public class HoroscopeInfoToEntityConverter implements IConverter<HoroscopeInfo, HumanHoroscopeEntity>{
	private Human _human;
	
	public HoroscopeInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanHoroscopeEntity convert(HoroscopeInfo src) {
		HumanHoroscopeEntity entity = new HumanHoroscopeEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setHoroscope(Horoscope.newBuilder()
				.setHoroscopeKey(src.getUuid())
				.setBagIndex(src.getIndex())
				.setBagType(src.getHoroscopeBag())
				.setExperience(src.getExperience())
				.setHoroscopeId(src.getHoroscopeId()));
		return entity;
	}

	@Override
	public HoroscopeInfo reverseConvert(HumanHoroscopeEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
