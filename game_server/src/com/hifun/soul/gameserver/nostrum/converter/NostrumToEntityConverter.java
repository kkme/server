package com.hifun.soul.gameserver.nostrum.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanNostrumEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.nostrum.Nostrum;

public class NostrumToEntityConverter implements
		IConverter<Nostrum, HumanNostrumEntity> {
	private Human human;

	public NostrumToEntityConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanNostrumEntity convert(Nostrum src) {
		HumanNostrumEntity entity = new HumanNostrumEntity();
		entity.getBuilder().setHumanGuid(human.getHumanGuid());
		entity.getBuilder().setNostrum(
				com.hifun.soul.proto.common.Nostrums.Nostrum.newBuilder()
						.setPropertyId(src.getPropertyId())
						.setEndTime(src.getEndTime())
						.setPropertyAmendRate(src.getPropertyAmendRate())
						.setPropertyAmendMethod(src.getPropertyAmendMethod()));
		return entity;
	}

	@Override
	public Nostrum reverseConvert(HumanNostrumEntity src) {
		throw new UnsupportedOperationException();
	}

}
