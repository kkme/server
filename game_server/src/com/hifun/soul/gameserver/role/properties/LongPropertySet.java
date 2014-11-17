package com.hifun.soul.gameserver.role.properties;

/**
 * Long型属性集;
 * 
 * @author crazyjohn
 * 
 */
public abstract class LongPropertySet extends GenericPropertyObject<Long> {

	public LongPropertySet(Class<Long> clazz, int size, int properType) {
		super(clazz, size, properType);
	}

	@Override
	public Long getPropertyValue(int index) {
		Long result = super.getPropertyValue(index);
		if (result == null) {
			return 0l;
		}
		return result;
	}

}
