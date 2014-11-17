package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanNostrum;
import com.hifun.soul.proto.data.entity.Entity.HumanNostrum.Builder;

/**
 * 角色秘药实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanNostrumEntity extends
		BaseProtobufEntity<HumanNostrum.Builder> implements IHumanSubEntity {

	public HumanNostrumEntity(Builder builder) {
		super(builder);
	}

	public HumanNostrumEntity() {
		this(HumanNostrum.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getNostrum().getPropertyId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getNostrumBuilder().setPropertyId((Integer) id);
	}

}
