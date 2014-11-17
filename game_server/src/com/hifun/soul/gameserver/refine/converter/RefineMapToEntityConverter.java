package com.hifun.soul.gameserver.refine.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanRefineMapEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.RefineMapInfo;
import com.hifun.soul.gameserver.refine.template.RefineMapTemplate;
import com.hifun.soul.proto.common.Refine.RefineMapData;

public class RefineMapToEntityConverter implements IConverter<RefineMapInfo, HumanRefineMapEntity>{
	private Human _human;
	
	public RefineMapToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanRefineMapEntity convert(RefineMapInfo src) {
		HumanRefineMapEntity entity = new HumanRefineMapEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setRefineMapData(RefineMapData.newBuilder()
				.setMapId(src.getMapId())
				.setBestStageId(src.getBestStageId()));
		return entity;
	}

	@Override
	public RefineMapInfo reverseConvert(HumanRefineMapEntity src) {
		RefineMapInfo refineMapInfo = new RefineMapInfo();
		int mapId = src.getBuilder().getRefineMapData().getMapId();
		refineMapInfo.setMapId(mapId);
		RefineMapTemplate refineMapTemplate = GameServerAssist
				.getRefineTemplateManager().getRefineMapTemplate(mapId);
		refineMapInfo.setMapName(refineMapTemplate.getName());
		refineMapInfo.setMapOpenLevel(refineMapTemplate.getOpenLevel());
		refineMapInfo.setMapStageNum(GameServerAssist
				.getRefineTemplateManager().getRefineStageSizeInMap(mapId));
		refineMapInfo.setBestStageId(src.getBuilder().getRefineMapData().getBestStageId());
		return refineMapInfo;
	}
	
}
