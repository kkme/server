package com.hifun.soul.gamedb.cache.holder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.gamedb.entity.HumanOtherPropertiesEntity;
import com.hifun.soul.proto.common.Character.CharProperty;

public class HumanOtherPropertiesEntityHolder extends
		ExclusiveEntityHolder<HumanOtherPropertiesEntity> {

	private Map<Integer, CharProperty> propertyMap = new HashMap<Integer, CharProperty>();

	@Override
	public Collection<HumanOtherPropertiesEntity> getEntities() {
		if (isModified()) {
			this.entity.getBuilder().clearProps()
					.addAllProps(propertyMap.values());
		}
		return super.getEntities();
	}

	@Override
	public boolean update(HumanOtherPropertiesEntity entity) {
		for (CharProperty.Builder prop : entity.getBuilder().getPropsBuilderList()) {
			propertyMap.put(prop.getKey(), prop.build());
		}
		return super.update(entity);
	}
}
