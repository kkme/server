package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox.Builder;

/**
 * 玩家日常任务宝箱信息;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanDailyQuestRewardBoxEntity
		extends
		BaseProtobufEntity<com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox.Builder>
		implements IHumanSubEntity {

	public HumanDailyQuestRewardBoxEntity(Builder builder) {
		super(builder);
	}

	public HumanDailyQuestRewardBoxEntity() {
		this(HumanDailyQuestRewardBox.newBuilder());
	}

	@Override
	public Serializable getId() {
		return builder.getBox().getBoxId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getBoxBuilder().setBoxId((Integer) id);
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

}
