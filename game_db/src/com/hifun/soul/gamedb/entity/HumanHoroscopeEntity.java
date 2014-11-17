package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope.Builder;

@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanHoroscopeEntity extends
		BaseProtobufEntity<HumanHoroscope.Builder> implements IHumanSubEntity{

	public HumanHoroscopeEntity(Builder builder) {
		super(builder);
	}
	
	public HumanHoroscopeEntity() {
		this(HumanHoroscope.newBuilder());
	}

	@Override
	public String getId() {
		return this.builder.getHoroscopeBuilder().getHoroscopeKey();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getHoroscopeBuilder().setHoroscopeKey((String)id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
