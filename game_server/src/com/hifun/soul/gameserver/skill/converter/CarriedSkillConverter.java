package com.hifun.soul.gameserver.skill.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanCarriedSkillEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;

public class CarriedSkillConverter implements
		IConverter<ISkill, HumanCarriedSkillEntity> {
	private Human human;

	public CarriedSkillConverter(Human human) {
		this.human = human;
	}

	@Override
	public HumanCarriedSkillEntity convert(ISkill src) {
		HumanCarriedSkill.Builder builder = HumanCarriedSkill.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.getSkillBuilder().setSkillId(src.getSkillId());
		builder.getSkillBuilder().setSlotIndex(src.getSlotIndex());
		builder.getSkillBuilder().setSkillState(src.getSkillState());
		HumanCarriedSkillEntity skillEntity = new HumanCarriedSkillEntity(builder);
		return skillEntity;
	}

	@Override
	public ISkill reverseConvert(HumanCarriedSkillEntity src) {
		// TODO Auto-generated method stub
		return null;
	}

}
