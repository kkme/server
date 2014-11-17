package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify.Builder;

/**
 * 角色消费提醒信息;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanCostNotifyEntity extends BaseProtobufEntity<HumanCostNotify.Builder>
		implements IHumanSubEntity {

	public HumanCostNotifyEntity(Builder builder) {
		super(builder);
	}

	public HumanCostNotifyEntity() {
		this(HumanCostNotify.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getCostNotifyData().getCostNotifyType();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getCostNotifyDataBuilder().setCostNotifyType((Integer) id);
	}

}
