package com.hifun.soul.gameserver.loginreward.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanLoginRewardEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.loginreward.LoginRewardInfo;
import com.hifun.soul.proto.common.Rewards.LoginRewardData;

public class LoginRewardInfoToEntityConverter implements IConverter<LoginRewardInfo, HumanLoginRewardEntity>{
	private Human _human;
	
	public LoginRewardInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanLoginRewardEntity convert(LoginRewardInfo src) {
		HumanLoginRewardEntity entity = new HumanLoginRewardEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		LoginRewardData.Builder builder = LoginRewardData.newBuilder();
		builder.setIndex(src.getIndex());
		if(src.getCommonItem() != null){
			builder.setItemId(src.getCommonItem().getItemId());
		}
		else{
			builder.setItemId(-1);
		}
		entity.getBuilder().setLoginRewardData(builder);
		
		return entity;
	}

	@Override
	public LoginRewardInfo reverseConvert(HumanLoginRewardEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
