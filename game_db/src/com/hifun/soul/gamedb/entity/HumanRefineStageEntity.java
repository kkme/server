package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage.Builder;

/**
 * 角色物品实体;
 * @author magicstone
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanRefineStageEntity extends BaseProtobufEntity<HumanRefineStage.Builder>
		implements IHumanSubEntity {

	public HumanRefineStageEntity(Builder builder) {
		super(builder);
	}

	public HumanRefineStageEntity() {
		this(HumanRefineStage.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return this.builder.getRefineStageData().getRefineStageKey();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getRefineStageDataBuilder().setRefineStageKey((String) id);
	}

}
