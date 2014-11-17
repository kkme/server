package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanSprite.Builder;

/**
 * 玩家精灵实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpriteEntity extends BaseProtobufEntity<HumanSprite.Builder>
		implements IHumanSubEntity {

	public HumanSpriteEntity(Builder builder) {
		super(builder);
	}

	public HumanSpriteEntity() {
		super(HumanSprite.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getSprite().getSpriteUUID();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSpriteBuilder().setSpriteUUID((String) id);
	}

}
