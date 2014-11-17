package com.hifun.soul.gameserver.cd.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanCdEntity;
import com.hifun.soul.gameserver.cd.CdInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.Cds.CdData;

public class CdInfoToEntityConverter implements IConverter<CdInfo, HumanCdEntity>{
	private Human _human;
	
	public CdInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanCdEntity convert(CdInfo src) {
		HumanCdEntity entity = new HumanCdEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setCdData(CdData.newBuilder()
				.setCdType(src.getCdType())
				.setTimes(src.getTimes())
				.setEndTime(src.getEndTime()));
		return entity;
	}

	@Override
	public CdInfo reverseConvert(HumanCdEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
