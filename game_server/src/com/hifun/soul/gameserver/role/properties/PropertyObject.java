package com.hifun.soul.gameserver.role.properties;

import com.hifun.soul.core.util.PropertyArray;

/**
 * 适用于各种类型对象的属性对象
 * 
 */
public class PropertyObject {
	protected final PropertyArray props;
	protected final int propertyType;

	public PropertyObject(int size, int properType) {
		this.props = new PropertyArray(size);
		this.propertyType = properType;
	}

	public byte getByte(int index) {
		return props.getByte(index);
	}

	public int getInt(int index) {
		return props.getInt(index);
	}

	public long getLong(int index) {
		return props.getLong(index);
	}

	public short getShort(int index) {
		return props.getShort(index);
	}

	public String getString(int index) {
		return props.getString(index);
	}

	public void setByte(int index, byte val) {
		props.setByte(index, val);
	}

	public void setInt(int index, int val) {
		props.setInt(index, val);
	}

	public void setLong(int index, long val) {
		props.setLong(index, val);
	}

	public void setShort(int index, short val) {
		props.setShort(index, val);
	}

	public void setString(int index, String val) {
		props.setString(index, val);
	}

	public void setObject(int index, Object val) {
		props.setObject(index, val);
	}

	public Object getObject(int index) {
		return props.getObject(index);
	}
	
	public boolean isChanged() {
		return props.isChanged();
	}

	public void resetChanged() {
		props.resetChanged();
	}

	/**
	 * 取得被修改过的的属性索引及其对应的值
	 * 
	 * @return 
	 */
	public Object[] getChanged() {
		return props.getChanged();
	}
	
}
