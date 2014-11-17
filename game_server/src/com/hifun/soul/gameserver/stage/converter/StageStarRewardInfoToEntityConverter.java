package com.hifun.soul.gameserver.stage.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStageStarRewardEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.Stages.StageStarRewardData;

public class StageStarRewardInfoToEntityConverter implements IConverter<StageStarRewardData.Builder, HumanStageStarRewardEntity>{
	private Human _human;
	
	public StageStarRewardInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStageStarRewardEntity convert(StageStarRewardData.Builder src) {
		HumanStageStarRewardEntity entity = new HumanStageStarRewardEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		StageStarRewardData.Builder builder = StageStarRewardData.newBuilder();
		builder.setStar(src.getStar());
		builder.setRewardState(src.getRewardState());
		entity.getBuilder().setStageStarRewardData(builder);
		return entity;
	}

	@Override
	public StageStarRewardData.Builder reverseConvert(HumanStageStarRewardEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
