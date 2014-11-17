package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanGift.Builder;
import com.hifun.soul.proto.data.entity.Entity.HumanGift;

/**
 * 天赋数据实体
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanGiftEntity extends BaseProtobufEntity<HumanGift.Builder>
		implements IHumanSubEntity {

	public HumanGiftEntity(Builder builder) {
		super(builder);
	}

	public HumanGiftEntity() {
		this(HumanGift.newBuilder());
	}

	@Override
	public Serializable getId() {
		return builder.getGift().getGiftId();
	}

	@Override
	public void setId(Serializable id) {
		builder.getGiftBuilder().setGiftId((Integer) id);

	}

	@Override
	public long getHumanGuid() {
		return builder.getHumanGuid();
	}

}
