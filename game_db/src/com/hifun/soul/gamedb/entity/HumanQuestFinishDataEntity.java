package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData.Builder;

/**
 * 任务完成数据实体;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanQuestFinishDataEntity extends
		BaseProtobufEntity<HumanQuestFinishData.Builder> implements
		IHumanSubEntity {

	public HumanQuestFinishDataEntity(Builder builder) {
		super(builder);
	}

	public HumanQuestFinishDataEntity() {
		this(HumanQuestFinishData.newBuilder());
	}

	@Override
	public Integer getId() {
		return this.builder.getQuestFinishData().getQuestId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getQuestFinishDataBuilder().setQuestId((Integer) id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
