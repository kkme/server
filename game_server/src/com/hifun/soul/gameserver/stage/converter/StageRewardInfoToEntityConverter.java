package com.hifun.soul.gameserver.stage.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStageRewardEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;

public class StageRewardInfoToEntityConverter implements IConverter<ItemRateData, HumanStageRewardEntity>{
	private Human _human;
	
	public StageRewardInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStageRewardEntity convert(ItemRateData src) {
		HumanStageRewardEntity entity = new HumanStageRewardEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setItemRateData(com.hifun.soul.proto.common.Stages.ItemRateData.newBuilder()
				.setItemId(src.getItemId())
				.setRate(src.getRate())
				.setIndex(src.getIndex())
				.setIsSelected(src.isSelected()));
		return entity;
	}

	@Override
	public ItemRateData reverseConvert(HumanStageRewardEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
