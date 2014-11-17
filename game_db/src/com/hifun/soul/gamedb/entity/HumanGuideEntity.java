package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanGuide;
import com.hifun.soul.proto.data.entity.Entity.HumanGuide.Builder;

/**
 * 新手引导;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanGuideEntity extends BaseProtobufEntity<HumanGuide.Builder>
		implements IHumanSubEntity {

	public HumanGuideEntity(Builder builder) {
		super(builder);
	}

	public HumanGuideEntity() {
		this(HumanGuide.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return String.valueOf(this.builder.getGuideType());
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setGuideType((Integer)id);
	}

}
