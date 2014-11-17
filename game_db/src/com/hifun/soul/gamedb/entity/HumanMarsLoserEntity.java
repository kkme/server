package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsLoser;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsLoser.Builder;

/**
 * 玩家战神之巅手下败将实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanMarsLoserEntity extends
		BaseProtobufEntity<HumanMarsLoser.Builder> implements IHumanSubEntity {

	public HumanMarsLoserEntity(Builder builder) {
		super(builder);
	}

	public HumanMarsLoserEntity() {
		super(HumanMarsLoser.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getMarsLoser().getLoserId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getMarsLoserBuilder().setLoserId((Long) id);
	}

}
