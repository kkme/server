package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer.Builder;

@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStargazerEntity extends
		BaseProtobufEntity<HumanStargazer.Builder> implements IHumanSubEntity{

	public HumanStargazerEntity(Builder builder) {
		super(builder);
	}
	
	public HumanStargazerEntity() {
		this(HumanStargazer.newBuilder());
	}

	@Override
	public Integer getId() {
		return this.builder.getStargazerBuilder().getStargazerId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStargazerBuilder().setStargazerId((Integer)id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
