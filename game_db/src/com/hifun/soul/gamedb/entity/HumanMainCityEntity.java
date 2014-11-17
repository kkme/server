package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanMainCityInfo;
import com.hifun.soul.proto.data.entity.Entity.HumanMainCityInfo.Builder;

/**
 * 主城相关数据实体
 * @author magicstone
 *
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanMainCityEntity extends BaseProtobufEntity<HumanMainCityInfo.Builder> implements IHumanSubEntity {

	public HumanMainCityEntity(Builder builder) {
		super(builder);		
	}
	
	public HumanMainCityEntity(){
		this(HumanMainCityInfo.newBuilder());
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
