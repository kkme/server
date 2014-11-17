package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionMineBattleReward;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionMineBattleReward.Builder;

/**
 * 角色军团矿战战斗奖励实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanLegionMineBattleRewardEntity extends
		BaseProtobufEntity<HumanLegionMineBattleReward.Builder> implements
		IHumanSubEntity {

	public HumanLegionMineBattleRewardEntity(Builder builder) {
		super(builder);
	}

	public HumanLegionMineBattleRewardEntity() {
		this(HumanLegionMineBattleReward.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getBattleReward().getItemId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getBattleRewardBuilder().setItemId((Integer) id);
	}

}
