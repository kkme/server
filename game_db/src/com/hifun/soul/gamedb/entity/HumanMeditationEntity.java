package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanMeditation;
import com.hifun.soul.proto.data.entity.Entity.HumanMeditation.Builder;

/**
 * 冥想实体
 * @author magicstone
 *
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanMeditationEntity extends BaseProtobufEntity<HumanMeditation.Builder> implements IHumanSubEntity{
	public HumanMeditationEntity(Builder builder) {
		super(builder);
	}
	
	public HumanMeditationEntity() {
		this(HumanMeditation.newBuilder());
	}

	@Override
	public Serializable getId() {		
		return builder.getHumanGuid();
	}

	@Override
	public void setId(Serializable id) {
		builder.setHumanGuid((Long)id);
	}

	@Override
	public long getHumanGuid() {
		return builder.getHumanGuid();
	}

	

}
