package com.hifun.soul.gameserver.tencent.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.TencentUserInfoEntity;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;

public class TencentUserInfoConverter implements IConverter<TencentUserInfo, TencentUserInfoEntity>{

	@Override
	public TencentUserInfoEntity convert(TencentUserInfo src) {
		TencentUserInfoEntity entity = new TencentUserInfoEntity();
		entity.setId(src.getId());
		entity.setPassportId(src.getPassportId());
		entity.setOpenId(src.getOpenId());
		entity.setYellowVipLevel(src.getYellowVipLevel());
		entity.setYearYellowVip(src.getIsYearYellowVip());
		entity.setYellowHighVip(src.getIsYellowHighVip());
		return entity;
	}

	@Override
	public TencentUserInfo reverseConvert(TencentUserInfoEntity src) {
		TencentUserInfo userInfo = new TencentUserInfo();
		userInfo.setId((Long)src.getId());
		userInfo.setPassportId(src.getPassportId());
		userInfo.setOpenId(src.getOpenId());
		userInfo.setYellowVipLevel(src.getYellowVipLevel());
		userInfo.setIsYearYellowVip(src.isYearYellowVip());
		userInfo.setIsYellowHighVip(src.isYellowHighVip());
		return userInfo;
	}

}
