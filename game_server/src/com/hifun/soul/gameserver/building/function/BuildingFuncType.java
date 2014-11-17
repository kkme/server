package com.hifun.soul.gameserver.building.function;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 建筑功能的类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum BuildingFuncType implements IndexedEnum{
	;
	
	private int index;
	
	private Class<? extends AbstractBuildingFunc> clazz;
	
	private static final List<BuildingFuncType> indexes = 
			IndexedEnumUtil.toIndexes(BuildingFuncType.values());
	
	private BuildingFuncType(int index,Class<? extends AbstractBuildingFunc> clazz){
		this.index = index;
		this.clazz = clazz;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	public Class<? extends AbstractBuildingFunc> getClazz(){
		return clazz;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static BuildingFuncType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
