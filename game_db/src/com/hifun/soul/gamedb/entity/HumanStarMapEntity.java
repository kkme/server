package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStarMap;
import com.hifun.soul.proto.data.entity.Entity.HumanStarMap.Builder;

/**
 * 玩家星图实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStarMapEntity extends
		BaseProtobufEntity<HumanStarMap.Builder> implements IHumanSubEntity {

	public HumanStarMapEntity(Builder builder) {
		super(builder);
	}

	public HumanStarMapEntity() {
		super(HumanStarMap.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getStarMap().getStarMapId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStarMapBuilder().setStarMapId((Integer) id);
	}

}
