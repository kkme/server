package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState.Builder;

/**
 * 角色关卡地图状态实体;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStageMapStateEntity extends BaseProtobufEntity<HumanStageMapState.Builder>
		implements IHumanSubEntity {

	public HumanStageMapStateEntity(Builder builder) {
		super(builder);
	}

	public HumanStageMapStateEntity() {
		this(HumanStageMapState.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return String.valueOf(this.builder.getStageMapStateData().getMapId());
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStageMapStateDataBuilder().setMapId((Integer)id);
	}

}
