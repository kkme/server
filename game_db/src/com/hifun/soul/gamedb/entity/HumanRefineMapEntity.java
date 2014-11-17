package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap.Builder;

/**
 * 角色物品实体;
 * @author magicstone
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanRefineMapEntity extends BaseProtobufEntity<HumanRefineMap.Builder>
		implements IHumanSubEntity {

	public HumanRefineMapEntity(Builder builder) {
		super(builder);
	}

	public HumanRefineMapEntity() {
		this(HumanRefineMap.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getRefineMapData().getMapId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getRefineMapDataBuilder().setMapId((Integer) id);
	}

}
