package com.hifun.soul.gameserver.costnotify.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanCostNotifyEntity;
import com.hifun.soul.gameserver.costnotify.CostNotifyInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.CostNotifyDatas.CostNotifyData;

public class CostNotifyInfoToEntityConverter implements IConverter<CostNotifyInfo, HumanCostNotifyEntity>{
	private Human _human;
	
	public CostNotifyInfoToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanCostNotifyEntity convert(CostNotifyInfo src) {
		HumanCostNotifyEntity entity = new HumanCostNotifyEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setCostNotifyData(
					CostNotifyData.newBuilder()
					.setCostNotifyType(src.getCostNotifyType())
					.setOpen(src.getOpen()));
		return entity;
	}

	@Override
	public CostNotifyInfo reverseConvert(HumanCostNotifyEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
