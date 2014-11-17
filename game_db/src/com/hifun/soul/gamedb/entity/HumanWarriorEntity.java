package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.common.Warrior.HumanWarriorInfo.Builder;
import com.hifun.soul.proto.common.Warrior.HumanWarriorInfo;

/**
 * 勇者之路数据实体
 * @author magicstone
 *
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanWarriorEntity extends BaseProtobufEntity<HumanWarriorInfo.Builder> implements IHumanSubEntity {

	public HumanWarriorEntity(Builder builder) {
		super(builder);		
	}
	
	public HumanWarriorEntity(){
		this(HumanWarriorInfo.newBuilder());
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
