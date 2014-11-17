package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStar;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStar.Builder;

/**
 * 角色关卡评星信息;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStageStarEntity extends BaseProtobufEntity<HumanStageStar.Builder>
		implements IHumanSubEntity {

	public HumanStageStarEntity(Builder builder) {
		super(builder);
	}

	public HumanStageStarEntity() {
		this(HumanStageStar.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getStageStarData().getStageId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStageStarDataBuilder().setStageId((Integer) id);
	}

}
