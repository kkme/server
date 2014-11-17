package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanItem.Builder;

/**
 * 角色物品实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanItemEntity extends BaseProtobufEntity<HumanItem.Builder>
		implements IHumanSubEntity {

	public HumanItemEntity(Builder builder) {
		super(builder);
	}

	public HumanItemEntity() {
		this(HumanItem.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return this.builder.getItem().getUuid();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getItemBuilder().setUuid((String) id);
	}

}
