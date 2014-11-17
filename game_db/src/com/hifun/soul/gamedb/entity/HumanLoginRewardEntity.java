package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward.Builder;

/**
 * 角色连续登陆信息;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanLoginRewardEntity extends BaseProtobufEntity<HumanLoginReward.Builder>
		implements IHumanSubEntity {

	public HumanLoginRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanLoginRewardEntity() {
		this(HumanLoginReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getLoginRewardData().getIndex();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getLoginRewardDataBuilder().setIndex((Integer) id);
	}

}
