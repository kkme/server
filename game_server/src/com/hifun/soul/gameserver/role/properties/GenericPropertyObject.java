package com.hifun.soul.gameserver.role.properties;

import java.util.BitSet;

import com.hifun.soul.core.util.GenericPropertyArray;
import com.hifun.soul.core.util.KeyValuePair;

/**
 * 
 * 泛型的属性对象
 * 
 */
public abstract class GenericPropertyObject<T> {

	protected final GenericPropertyArray<T> props;
	protected final int propertyType;

	public GenericPropertyObject(Class<T> clazz, int size, int properType) {
		this.props = new GenericPropertyArray<T>(clazz, size);
		this.propertyType = properType;
	}

	public boolean isChanged() {
		return props.isChanged();
	}

	public void resetChanged() {
		props.resetChanged();
	}

	
	public int size(){
		return props.size();
	}
	/**
	 * @see {@link GenericPropertyArray#fillChangedBit(BitSet)}
	 * @param toBitSet
	 * @return
	 */
	public boolean fillChangedBit(final BitSet toBitSet) {
		return props.fillChangedBit(toBitSet);
	}

	/**
	 * 设置属性值
	 * 
	 * @param index
	 * @param value
	 */
	public void setPropertyValue(int index, T value) {
		props.set(index, value);
	}

	/**
	 * 获取属性值
	 * 
	 * @param index
	 * @return
	 */
	public T getPropertyValue(int index) {
		return props.get(index);
	}

	
	
	/**
	 * 取得被修改过的的属性索引,修正后的索引，及其对应的值
	 * 
	 * @return
	 */
	public <ValueType>KeyValuePair<Integer, ValueType>[] getChanged() {
		KeyValuePair<Integer, ValueType>[] changes= props.getChanged();
		for(int i=0;i<changes.length;i++)
		{
			changes[i].setKey(PropertyType.genPropertyKey(changes[i].getKey(), getPropertyType()));
		}
		return changes;
	}
	
	public abstract int getPropertyType();

}
