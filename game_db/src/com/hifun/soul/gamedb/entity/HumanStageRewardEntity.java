package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStageReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStageReward.Builder;

/**
 * 角色关卡奖励实体;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStageRewardEntity extends BaseProtobufEntity<HumanStageReward.Builder>
		implements IHumanSubEntity {

	public HumanStageRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanStageRewardEntity() {
		this(HumanStageReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return String.valueOf(this.builder.getItemRateData().getIndex());
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getItemRateDataBuilder().setIndex((Integer)id);
	}

}
