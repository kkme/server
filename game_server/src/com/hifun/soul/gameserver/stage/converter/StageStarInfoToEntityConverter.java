package com.hifun.soul.gameserver.stage.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStageStarEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stagestar.StageStarInfo;
import com.hifun.soul.proto.common.Stages.StageStarData;

public class StageStarInfoToEntityConverter implements IConverter<StageStarInfo, HumanStageStarEntity>{
	private Human _human;
	
	public StageStarInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStageStarEntity convert(StageStarInfo src) {
		HumanStageStarEntity entity = new HumanStageStarEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		StageStarData.Builder stageStarData = StageStarData.newBuilder();
		stageStarData.setStageId(src.getStageId());
		stageStarData.setStageStar(src.getStageStar());
		entity.getBuilder().setStageStarData(stageStarData);
		return entity;
	}

	@Override
	public StageStarInfo reverseConvert(HumanStageStarEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
