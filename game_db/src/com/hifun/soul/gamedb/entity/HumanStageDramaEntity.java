package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanStageDrama;
import com.hifun.soul.proto.data.entity.Entity.HumanStageDrama.Builder;

/**
 * 角色关卡剧情实体;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanStageDramaEntity extends BaseProtobufEntity<HumanStageDrama.Builder>
		implements IHumanSubEntity {

	public HumanStageDramaEntity(Builder builder) {
		super(builder);
	}

	public HumanStageDramaEntity() {
		this(HumanStageDrama.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public String getId() {
		return String.valueOf(this.builder.getStageDramaData().getStageId());
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getStageDramaDataBuilder().setStageId((Integer)id);
	}

}
