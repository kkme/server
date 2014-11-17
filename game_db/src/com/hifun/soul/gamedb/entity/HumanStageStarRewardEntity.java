package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStarReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStarReward.Builder;

/**
 * 角色关卡评星奖励信息;
 * @author magicstone
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStageStarRewardEntity extends BaseProtobufEntity<HumanStageStarReward.Builder>
		implements IHumanSubEntity {

	public HumanStageStarRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanStageStarRewardEntity() {
		this(HumanStageStarReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getStageStarRewardData().getStar();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStageStarRewardDataBuilder().setStar((Integer) id);
	}

}
