package com.hifun.soul.gameserver.stage.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStageMapStateEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.StageMapStateInfo;
import com.hifun.soul.proto.common.Stages.StageMapStateData;

public class StageMapInfoToEntityConverter implements IConverter<StageMapStateInfo, HumanStageMapStateEntity>{
	private Human _human;
	
	public StageMapInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStageMapStateEntity convert(StageMapStateInfo src) {
		HumanStageMapStateEntity entity = new HumanStageMapStateEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setStageMapStateData(StageMapStateData.newBuilder()
				.setMapId(src.getMapId())
				.setState(src.getState())
				.setPerfectMapRewardState(src.getPerfectMapRewardState()));
		return entity;
	}

	@Override
	public StageMapStateInfo reverseConvert(HumanStageMapStateEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
