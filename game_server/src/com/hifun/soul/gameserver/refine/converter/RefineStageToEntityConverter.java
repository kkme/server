package com.hifun.soul.gameserver.refine.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanRefineStageEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.refine.RefineStageInfo;
import com.hifun.soul.proto.common.Refine.RefineStageData;

public class RefineStageToEntityConverter implements IConverter<RefineStageInfo, HumanRefineStageEntity>{
	private Human _human;
	
	public RefineStageToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanRefineStageEntity convert(RefineStageInfo src) {
		HumanRefineStageEntity entity = new HumanRefineStageEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setRefineStageData(RefineStageData.newBuilder()
				.setMapId(src.getMapId())
				.setStageId(src.getStageId())
				.setStageState(src.getAttackState())
				.setGetted(src.getGetted())
				.setRefineStageKey(genKey(src.getMapId(),src.getStageId())));
		return entity;
	}

	@Override
	public RefineStageInfo reverseConvert(HumanRefineStageEntity src) {
		RefineStageInfo refineStageInfo = new RefineStageInfo();
		int mapId = src.getBuilder().getRefineStageData().getMapId();
		int stageId = src.getBuilder().getRefineStageData().getStageId();
		RefineStageInfo refineStageInfoTemplate = GameServerAssist
				.getRefineTemplateManager().getRefineStageInfo(mapId, stageId);
		refineStageInfo.setAttackState(src.getBuilder().getRefineStageData().getStageState());
		refineStageInfo.setCommonItem(refineStageInfoTemplate.getCommonItem());
		refineStageInfo.setGetted(src.getBuilder().getRefineStageData().getGetted());
		refineStageInfo.setMapId(mapId);
		refineStageInfo.setMapName(refineStageInfoTemplate.getMapName());
		refineStageInfo.setMonsterIcon(refineStageInfoTemplate.getMonsterIcon());
		refineStageInfo.setMonsterId(refineStageInfoTemplate.getMonsterId());
		refineStageInfo.setMonsterLevel(refineStageInfoTemplate.getMonsterLevel());
		refineStageInfo.setMonsterName(refineStageInfoTemplate.getMonsterName());
		refineStageInfo.setRewardNum(refineStageInfoTemplate.getRewardNum());
		refineStageInfo.setRewardAnima(refineStageInfoTemplate.getRewardAnima());
		refineStageInfo.setStageId(stageId);
		return refineStageInfo;
	}
	
	private String genKey(int mapId, int stageId) {
		return mapId + "&" + stageId;
	}
	
}
