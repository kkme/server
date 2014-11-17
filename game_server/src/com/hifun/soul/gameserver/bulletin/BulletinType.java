package com.hifun.soul.gameserver.bulletin;

import java.util.List;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 公告类型
 * @author magicstone
 *
 */
public enum BulletinType implements IndexedEnum{
	/** 一次性定时公告*/
	DELAY_BULLETIN(1),
//	/** 周期性定时公告 */
//	PERIODICAL_BULLETIN(2),
	/** 周期性相同间隔时间公告 */
	REGULAR_BULLETIN(3);
	
	private int index;
	private BulletinType(int type){
		this.index = type;
	}
	
	public int getIndex(){
		return this.index;
	}
	private static List<BulletinType> indexs = IndexedEnumUtil.toIndexes(BulletinType.values());
	
	public static BulletinType indexOf(int index){
		return EnumUtil.valueOf(indexs, index);
	}
}
