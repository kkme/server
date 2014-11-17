package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsRoom;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsRoom.Builder;

/**
 * 玩家战神之巅房间实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanMarsRoomEntity extends
		BaseProtobufEntity<HumanMarsRoom.Builder> implements IHumanSubEntity {

	public HumanMarsRoomEntity(Builder builder) {
		super(builder);
	}

	public HumanMarsRoomEntity() {
		super(HumanMarsRoom.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Serializable getId() {
		return this.builder.getMarsRoom().getRoomType();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getMarsRoomBuilder().setRoomType((Integer) id);
	}

}
