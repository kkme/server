package com.hifun.soul.gameserver.godsoul;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanGodsoulEntity;
import com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo;
import com.hifun.soul.gameserver.human.Human;

public class GodsoulToEntityConverter implements
		IConverter<EquipBitInfo, HumanGodsoulEntity> {
	private Human _human;

	public GodsoulToEntityConverter(Human human) {
		this._human = human;
	}

	@Override
	public HumanGodsoulEntity convert(EquipBitInfo equipBit) {
		HumanGodsoulEntity entity = new HumanGodsoulEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setEquipBit(
				com.hifun.soul.proto.common.EquipBits.EquipBit.newBuilder()
						.setEquipBitType(equipBit.getEquipBitType())
						.setCurrentLevel(equipBit.getCurrentLevel()));
		return entity;
	}

	@Override
	public EquipBitInfo reverseConvert(HumanGodsoulEntity src) {
		throw new UnsupportedOperationException();
	}

}
