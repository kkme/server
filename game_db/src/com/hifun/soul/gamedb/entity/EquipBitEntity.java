package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology.Builder;

/**
 * 角色装备位实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class EquipBitEntity extends BaseProtobufEntity<HumanTechnology.Builder>
		implements IHumanSubEntity {

	public EquipBitEntity(Builder builder) {
		super(builder);
	}

	public EquipBitEntity() {
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
