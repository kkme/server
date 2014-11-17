package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanGodsoul;
import com.hifun.soul.proto.data.entity.Entity.HumanGodsoul.Builder;

/**
 * 角色神魄实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanGodsoulEntity extends
		BaseProtobufEntity<HumanGodsoul.Builder> implements IHumanSubEntity {

	public HumanGodsoulEntity(Builder builder) {
		super(builder);
	}

	public HumanGodsoulEntity() {
		this(HumanGodsoul.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getEquipBit().getEquipBitType();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getEquipBitBuilder().setEquipBitType((Integer) id);
	}

}
