package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo;
import com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo.Builder;;

/**
 * 精英副本实体
 * 
 * @author magicstone
 *
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanEliteStageEntity extends BaseProtobufEntity<HumanEliteStageInfo.Builder> implements IHumanSubEntity{
	public HumanEliteStageEntity(Builder builder) {
		super(builder);
	}
	
	public HumanEliteStageEntity() {
		this(HumanEliteStageInfo.newBuilder());
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