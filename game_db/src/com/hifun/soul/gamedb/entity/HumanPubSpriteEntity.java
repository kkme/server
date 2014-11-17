package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanPubSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanPubSprite.Builder;

/**
 * 角色精灵酒馆精灵信息;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanPubSpriteEntity extends
		BaseProtobufEntity<HumanPubSprite.Builder> implements IHumanSubEntity {

	public HumanPubSpriteEntity() {
		super(HumanPubSprite.newBuilder());
	}

	public HumanPubSpriteEntity(Builder builder) {
		super(builder);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getSpriteId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setSpriteId((Integer) id);
	}

}
