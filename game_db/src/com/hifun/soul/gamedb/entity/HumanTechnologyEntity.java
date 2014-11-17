package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology.Builder;

/**
 * 角色科技实体;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanTechnologyEntity extends BaseProtobufEntity<HumanTechnology.Builder>
		implements IHumanSubEntity {

	public HumanTechnologyEntity(Builder builder) {
		super(builder);
	}

	public HumanTechnologyEntity() {
		this(HumanTechnology.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getTechnology().getTechnologyId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getTechnologyBuilder().setTechnologyId((Integer) id);
	}

}
