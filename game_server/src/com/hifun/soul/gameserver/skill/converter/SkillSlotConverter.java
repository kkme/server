package com.hifun.soul.gameserver.skill.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanSkillSlotEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.slot.SkillSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanSkillSlot;

public class SkillSlotConverter implements
		IConverter<SkillSlot, HumanSkillSlotEntity> {
	private Human human;

	public SkillSlotConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanSkillSlotEntity convert(SkillSlot src) {
		HumanSkillSlot.Builder builder = HumanSkillSlot.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.getSlotBuilder().setSlotIndex(src.getSlotIndex())
								.setState(src.getState());
		HumanSkillSlotEntity entity = new HumanSkillSlotEntity(builder);
		return entity;
	}

	@Override
	public SkillSlot reverseConvert(HumanSkillSlotEntity src) {
		// TODO Auto-generated method stub
		return null;
	}

}
