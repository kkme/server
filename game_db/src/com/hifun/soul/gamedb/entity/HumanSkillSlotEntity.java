package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSkillSlot;

/**
 * 角色栏位实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSkillSlotEntity extends
		BaseProtobufEntity<HumanSkillSlot.Builder> implements IHumanSubEntity {

	public HumanSkillSlotEntity(HumanSkillSlot.Builder builder) {
		super(builder);
	}

	public HumanSkillSlotEntity() {
		this(HumanSkillSlot.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getSlot().getSlotIndex();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSlotBuilder().setSlotIndex((Integer) id);
	}

}
