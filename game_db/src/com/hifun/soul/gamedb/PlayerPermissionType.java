package com.hifun.soul.gamedb;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;


/**
 * 
 * 玩家的权限类型
 * 
 * @author magicstone
 *
 */
public enum PlayerPermissionType implements IndexedEnum{
	/** 普通玩家 */
	NORMAL_PLAYER(1),
	/** 管理员玩家 */
	GM_PLAYER(2);

	private int index;
	
	private static final List<PlayerPermissionType> indexes = IndexedEnumUtil.toIndexes(PlayerPermissionType.values());
	
	private PlayerPermissionType(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static PlayerPermissionType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
	
}
