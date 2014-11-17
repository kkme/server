package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionTask;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionTask.Builder;

/**
 * 角色秘药实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanLegionTaskEntity extends
		BaseProtobufEntity<HumanLegionTask.Builder> implements IHumanSubEntity {

	public HumanLegionTaskEntity(Builder builder) {
		super(builder);
	}

	public HumanLegionTaskEntity() {
		this(HumanLegionTask.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getLegionTask().getTaskId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getLegionTaskBuilder().setTaskId((Integer) id);
	}

}
