package com.hifun.soul.gameserver.stage.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanStageDramaEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.StageDramaStateInfo;
import com.hifun.soul.gameserver.stage.TriggerType;
import com.hifun.soul.proto.common.Stages.StageDramaData;
import com.hifun.soul.proto.common.Stages.StageDramaStateData;

public class StageDramaInfoToEntityConverter implements IConverter<StageDramaStateInfo, HumanStageDramaEntity>{
	private Human _human;
	
	public StageDramaInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanStageDramaEntity convert(StageDramaStateInfo src) {
		HumanStageDramaEntity entity = new HumanStageDramaEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		StageDramaData.Builder stageDramaData = StageDramaData.newBuilder();
		stageDramaData.setStageId(src.getStageId());
		for(TriggerType triggerType : src.getDramas().keySet()){
			StageDramaStateData.Builder stageDramaStateData = StageDramaStateData.newBuilder();
			stageDramaStateData.setTriggerType(triggerType.getIndex());
			stageDramaStateData.setTriggerState(src.getDramas().get(triggerType));
			stageDramaData.addDramaState(stageDramaStateData);
		}
		entity.getBuilder().setStageDramaData(stageDramaData);
		return entity;
	}

	@Override
	public StageDramaStateInfo reverseConvert(HumanStageDramaEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
