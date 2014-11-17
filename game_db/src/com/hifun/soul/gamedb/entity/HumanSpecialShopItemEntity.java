package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.core.orm.annotation.AutoCreateHumanEntityHolder;
import com.hifun.soul.gamedb.IHumanSubEntity;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialShopItem;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialShopItem.Builder;

/**
 * 角色神秘商店信息;
 * 
 * @author magicstone
 * 
 */
@AutoCreateHumanEntityHolder(EntityHolderClass = "CollectionEntityHolder")
public class HumanSpecialShopItemEntity extends BaseProtobufEntity<HumanSpecialShopItem.Builder>
		implements IHumanSubEntity {

	public HumanSpecialShopItemEntity(Builder builder) {
		super(builder);
	}

	public HumanSpecialShopItemEntity() {
		this(HumanSpecialShopItem.newBuilder());
	}

	@Override
	public long getHumanGuid() {
		return this.builder.getHumanGuid();
	}

	@Override
	public Integer getId() {
		return this.builder.getSpecialShopItem().getId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.getSpecialShopItemBuilder().setId((Integer) id);
	}

}
