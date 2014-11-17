package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanCd;
import com.hifun.soul.proto.data.entity.Entity.HumanCd.Builder;

/**
 * 角色cd信息;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanCdEntity extends BaseProtobufEntity<HumanCd.Builder>
		implements IHumanSubEntity {

	public HumanCdEntity(Builder builder) {
		super(builder);
	}

	public HumanCdEntity() {
		this(HumanCd.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getCdData().getCdType();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getCdDataBuilder().setCdType((Integer) id);
	}

}
