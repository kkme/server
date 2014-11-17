package com.hifun.soul.gameserver.legion.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.LegionShopEntity;
import com.hifun.soul.gameserver.legion.LegionShop;

public class LegionShopToEntityConverter implements
		IConverter<LegionShop, LegionShopEntity> {

	@Override
	public LegionShopEntity convert(LegionShop src) {
		LegionShopEntity entity = new LegionShopEntity();
		entity.setId(src.getId());
		entity.setLegionId(src.getLegionId());
		entity.setItemId(src.getItemId());
		entity.setBuyNum(src.getBuyNum());
		return entity;
	}

	@Override
	public LegionShop reverseConvert(LegionShopEntity src) {
		LegionShop shop = new LegionShop();
		shop.setId((Long) src.getId());
		shop.setLegionId(src.getLegionId());
		shop.setItemId(src.getItemId());
		shop.setBuyNum(src.getBuyNum());
		return shop;
	}

}
