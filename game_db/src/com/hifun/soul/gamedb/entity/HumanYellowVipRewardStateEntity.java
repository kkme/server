package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.common.YellowVip.HumanYellowVipRewardStateInfo.Builder;
import com.hifun.soul.proto.common.YellowVip.HumanYellowVipRewardStateInfo;

/**
 * 黄钻礼包领取状态数据实体
 * @author magicstone
 *
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanYellowVipRewardStateEntity extends BaseProtobufEntity<HumanYellowVipRewardStateInfo.Builder> implements IHumanSubEntity {

	public HumanYellowVipRewardStateEntity(Builder builder) {
		super(builder);		
	}
	
	public HumanYellowVipRewardStateEntity(){
		this(HumanYellowVipRewardStateInfo.newBuilder());
	}

	@Override
	public Serializable getId() {
		return builder.getHumanGuid();
	}

	@Override
	public void setId(Serializable id) {
		builder.setHumanGuid((Long)id);
		
	}

	@Override
	public long getHumanGuid() {
		return builder.getHumanGuid();
	}

}
