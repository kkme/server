package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanTotalRechargeReward.Builder;
import com.hifun.soul.proto.data.entity.Entity.HumanTotalRechargeReward;

/**
 * 角色累计充值领奖实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanTotalRechargeRewardEntity extends
		BaseProtobufEntity<HumanTotalRechargeReward.Builder> implements
		IHumanSubEntity {

	public HumanTotalRechargeRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanTotalRechargeRewardEntity() {
		this(HumanTotalRechargeReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getTotalRechargeReward().getGradeId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getTotalRechargeRewardBuilder().setGradeId((Integer) id);
	}

}
