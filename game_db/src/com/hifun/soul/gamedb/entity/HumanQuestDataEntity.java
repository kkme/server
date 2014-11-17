package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData.Builder;

/**
 * 任务数据实体,是角色的子实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanQuestDataEntity extends
		BaseProtobufEntity<HumanQuestData.Builder> implements IHumanSubEntity {

	public HumanQuestDataEntity(Builder builder) {
		super(builder);
	}

	public HumanQuestDataEntity() {
		this(HumanQuestData.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return builder.getQuestStateData().getQuestId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getQuestStateDataBuilder().setQuestId((Integer) id);
	}

}
