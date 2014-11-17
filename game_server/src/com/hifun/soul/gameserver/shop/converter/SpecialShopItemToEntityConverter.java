package com.hifun.soul.gameserver.shop.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanSpecialShopItemEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.shop.SpecialShopItem;

public class SpecialShopItemToEntityConverter implements IConverter<SpecialShopItem, HumanSpecialShopItemEntity>{
	private Human _human;
	
	public SpecialShopItemToEntityConverter(Human human) {
		this._human = human;
	}
	
	@Override
	public HumanSpecialShopItemEntity convert(SpecialShopItem src) {
		HumanSpecialShopItemEntity entity = new HumanSpecialShopItemEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setSpecialShopItem(com.hifun.soul.proto.common.SpecialShopItems.SpecialShopItem.newBuilder()
				.setId(src.getId())
				.setIsBuy(src.getIsBuy()));
		return entity;
	}

	@Override
	public SpecialShopItem reverseConvert(HumanSpecialShopItemEntity src) {
		throw new UnsupportedOperationException();
	}
	
}
