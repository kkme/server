package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanMagicPaper;
import com.hifun.soul.proto.data.entity.Entity.HumanMagicPaper.Builder;

/**
 * 角色灵图实体;
 * 
 * @author yandajun
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanMagicPaperEntity extends
		BaseProtobufEntity<HumanMagicPaper.Builder> implements IHumanSubEntity {

	public HumanMagicPaperEntity(Builder builder) {
		super(builder);
	}

	public HumanMagicPaperEntity() {
		this(HumanMagicPaper.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getMagicPaper().getPaperId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getMagicPaperBuilder().setPaperId((Integer) id);
	}

}
