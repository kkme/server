package com.hifun.soul.gameserver.friend.converter;

import java.util.HashSet;
import java.util.Set;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.FriendEntity;
import com.hifun.soul.gameserver.friend.FriendInfos;

public class FriendInfosToEntityConverter implements IConverter<FriendInfos, FriendEntity>{
	public FriendInfosToEntityConverter() {
	}
	
	@Override
	public FriendEntity convert(FriendInfos friendInfos) {
		FriendEntity friendEntity = new FriendEntity();
		friendEntity.setId(friendInfos.getHumanId());
		friendEntity.setHumanName(friendInfos.getHumanName());
		friendEntity.setHumanLevel(friendInfos.getHumanLevel());
		friendEntity.setHumanOccupation(friendInfos.getHumanOccupation());
		friendEntity.setFriendIds(genStringBySet(friendInfos.getFriendIds()));
		friendEntity.setSendEnergyToSelfFriendIds(genStringBySet(friendInfos.getSendEnergyToSelfFriendIds()));
		friendEntity.setSendEnergyToOtherFriendIds(genStringBySet(friendInfos.getSendEnergyToOtherFriendIds()));
		friendEntity.setRecievedEnergyFriendIds(genStringBySet(friendInfos.getRecievedEnergyFriendIds()));
//		friendEntity.setSelfSendFriendApplyIds(genStringBySet(friendInfos.getSelfSendFriendApplyIds()));
		friendEntity.setOtherSendFriendApplyIds(genStringBySet(friendInfos.getOtherSendFriendApplyIds()));
		friendEntity.setLatestFriendIds(genStringBySet(friendInfos.getLatestFriendIds()));
//		friendEntity.setYellowVipLevel(friendInfos.getYellowVipLevel());
//		friendEntity.setIsYearYellowVip(friendInfos.getIsYearYellowVip());
//		friendEntity.setIsYellowHighVip(friendInfos.getIsYellowHighVip());
		return friendEntity;
	}

	@Override
	public FriendInfos reverseConvert(FriendEntity friendEntity) {
		FriendInfos friendInfos = new FriendInfos();
		friendInfos.setHumanId((Long)friendEntity.getId());
		friendInfos.setHumanName(friendEntity.getHumanName());
		friendInfos.setHumanLevel(friendEntity.getHumanLevel());
		friendInfos.setHumanOccupation(friendEntity.getHumanOccupation());
		friendInfos.setFriendIds(genSetByString(friendEntity.getFriendIds()));
		friendInfos.setSendEnergyToSelfFriendIds(genSetByString(friendEntity.getSendEnergyToSelfFriendIds()));
		friendInfos.setSendEnergyToOtherFriendIds(genSetByString(friendEntity.getSendEnergyToOtherFriendIds()));
		friendInfos.setRecievedEnergyFriendIds(genSetByString(friendEntity.getRecievedEnergyFriendIds()));
//		friendInfos.setSelfSendFriendApplyIds(genSetByString(friendEntity.getSelfSendFriendApplyIds()));
		friendInfos.setOtherSendFriendApplyIds(genSetByString(friendEntity.getOtherSendFriendApplyIds()));
		friendInfos.setLatestFriendIds(genSetByString(friendEntity.getLatestFriendIds()));
//		friendInfos.setYellowVipLevel(friendEntity.getYellowVipLevel());
//		friendInfos.setIsYearYellowVip(friendEntity.getIsYearYellowVip());
//		friendInfos.setIsYellowHighVip(friendEntity.getIsYellowHighVip());
		return friendInfos;
	}
	
	/**
	 * 把Set<Long>里面的数据转化成以逗号分隔的字符串
	 * @param ids
	 * @return
	 */
	private String genStringBySet(Set<Long> ids) {
		if(ids == null){
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		for(Long id : ids){
			if(id != null){
				stringBuffer.append(id).append(",");
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 把以逗号分隔的ids转化成Set<Long>
	 * @param ids
	 * @return
	 */
	private Set<Long> genSetByString(String ids) {
		Set<Long> idSet = new HashSet<Long>();
		if(ids == null
				|| ids.isEmpty()){
			return idSet;
		}
		for(String id : ids.split(",")){
			idSet.add(Long.valueOf(id));
		}
		return idSet;
	}
	
}
