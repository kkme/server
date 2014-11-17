package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBagCell;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBagCell.Builder;

/**
 * 玩家精灵背包格子实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpriteBagCellEntity extends
		BaseProtobufEntity<HumanSpriteBagCell.Builder> implements
		IHumanSubEntity {

	public HumanSpriteBagCellEntity(Builder builder) {
		super(builder);
	}

	public HumanSpriteBagCellEntity() {
		super(HumanSpriteBagCell.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getCell().getIndex();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getCellBuilder().setIndex((Integer) id);
	}

}
