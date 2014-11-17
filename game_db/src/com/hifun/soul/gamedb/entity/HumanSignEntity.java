package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSign;
import com.hifun.soul.proto.data.entity.Entity.HumanSign.Builder;

/**
 * 星座实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSignEntity extends BaseProtobufEntity<HumanSign.Builder>
		implements IHumanSubEntity {

	public HumanSignEntity(Builder builder) {
		super(builder);
	}

	public HumanSignEntity() {
		super(HumanSign.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getSign().getSignId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSignBuilder().setSignId((Integer) id);
	}

}
