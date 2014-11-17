package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties.Builder;

@AutoCreateHumanEntityHolder(EntityHolderClass = "ExclusiveEntityHolder")
public class HumanBasePropertiesEntity extends
		BaseProtobufEntity<HumanBaseProperties.Builder> implements IHumanSubEntity{

	public HumanBasePropertiesEntity(Builder builder) {
		super(builder);
	}

	@Override
	public Long getId() {
		return this.builder.getHumanGuid();
	}

	@Override
	public void setId(Serializable id) {
		this.getBuilder().setHumanGuid((Long) id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
