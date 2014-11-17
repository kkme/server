package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteSlot.Builder;

/**
 * 玩家精灵装备位实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpriteSlotEntity extends
		BaseProtobufEntity<HumanSpriteSlot.Builder> implements IHumanSubEntity {

	public HumanSpriteSlotEntity(Builder builder) {
		super(builder);
	}

	public HumanSpriteSlotEntity() {
		super(HumanSpriteSlot.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getSlot().getIndex();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSlotBuilder().setIndex((Integer) id);
	}

}
