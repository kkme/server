package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties.Builder;

@AutoCreateHumanEntityHolder(EntityHolderClass = "HumanOtherPropertiesEntityHolder")
public class HumanOtherPropertiesEntity extends
		BaseProtobufEntity<HumanOtherProperties.Builder> implements IHumanSubEntity{

	public HumanOtherPropertiesEntity(Builder builder) {
		super(builder);
	}

	public HumanOtherPropertiesEntity() {
		this(HumanOtherProperties.newBuilder());
	}

	@Override
	public Long getId() {
		return this.builder.getHumanGuid();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setHumanGuid((Long) id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
