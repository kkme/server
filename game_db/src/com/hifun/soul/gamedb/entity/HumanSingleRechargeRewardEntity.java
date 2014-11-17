package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSingleRechargeReward.Builder;
import com.hifun.soul.proto.data.entity.Entity.HumanSingleRechargeReward;

/**
 * 角色单笔充值领奖实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSingleRechargeRewardEntity extends
		BaseProtobufEntity<HumanSingleRechargeReward.Builder> implements
		IHumanSubEntity {

	public HumanSingleRechargeRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanSingleRechargeRewardEntity() {
		this(HumanSingleRechargeReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getSingleRechargeReward().getGradeId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSingleRechargeRewardBuilder().setGradeId((Integer) id);
	}

}
