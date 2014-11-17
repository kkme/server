package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialLoginReward.Builder;

/**
 * 角色连续登陆特殊奖励信息;
 * @author magicstone
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpecialLoginRewardEntity extends BaseProtobufEntity<HumanSpecialLoginReward.Builder>
		implements IHumanSubEntity {

	public HumanSpecialLoginRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanSpecialLoginRewardEntity() {
		this(HumanSpecialLoginReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getSpecialLoginRewardData().getDays();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSpecialLoginRewardDataBuilder().setDays((Integer) id);
	}

}
