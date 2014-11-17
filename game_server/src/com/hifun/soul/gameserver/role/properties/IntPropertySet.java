package com.hifun.soul.gameserver.role.properties;


/**
 * Int属性集;
 * 
 * @author crazyjohn
 * 
 */
public abstract class IntPropertySet extends GenericPropertyObject<Integer> {

	public IntPropertySet(Class<Integer> clazz, int size, int properType) {
		super(clazz, size, properType);
	}
	
	@Override
	public Integer getPropertyValue(int index) {
		Integer result = super.getPropertyValue(index);
		if (result == null) {
			return 0;
		}
		return result;
	}
	
	public void clear() {
		this.props.clear();
	}

}
