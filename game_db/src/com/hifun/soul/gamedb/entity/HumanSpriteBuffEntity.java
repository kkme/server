package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBuff;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBuff.Builder;

/**
 * 玩家精灵buff实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpriteBuffEntity extends
		BaseProtobufEntity<HumanSpriteBuff.Builder> implements IHumanSubEntity {

	public HumanSpriteBuffEntity(Builder builder) {
		super(builder);
	}

	public HumanSpriteBuffEntity() {
		super(HumanSpriteBuff.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getBuff().getBuffId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getBuffBuilder().setBuffId((Integer) id);
	}

}
