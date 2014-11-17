package com.hifun.soul.gameserver.loginreward.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanSpecialLoginRewardEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo;
import com.hifun.soul.proto.common.Rewards.SpecialLoginRewardData;

public class SpecialLoginRewardInfoToEntityConverter implements IConverter<SpecialLoginRewardInfo, HumanSpecialLoginRewardEntity>{
	private Human _human;
	
	public SpecialLoginRewardInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanSpecialLoginRewardEntity convert(SpecialLoginRewardInfo src) {
		HumanSpecialLoginRewardEntity entity = new HumanSpecialLoginRewardEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		SpecialLoginRewardData.Builder builder = SpecialLoginRewardData.newBuilder();
		builder.setDays(src.getDays());
		builder.setState(src.getState());
		entity.getBuilder().setSpecialLoginRewardData(builder);
		return entity;
	}

	@Override
	public SpecialLoginRewardInfo reverseConvert(HumanSpecialLoginRewardEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
