package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanTarget;
import com.hifun.soul.proto.data.entity.Entity.HumanTarget.Builder;

/**
 * 角色个人目标实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanTargetEntity extends BaseProtobufEntity<HumanTarget.Builder>
		implements IHumanSubEntity {

	public HumanTargetEntity(Builder builder) {
		super(builder);
	}

	public HumanTargetEntity() {
		this(HumanTarget.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getTarget().getTargetId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getTargetBuilder().setTargetId((Integer) id);
	}

}
