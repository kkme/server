package com.hifun.soul.gameserver.chat;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;


/**
 * 聊天类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum ChatType  implements IndexedEnum{
	/** 世界 */
	@ClientEnumComment(comment = "世界")
	WORLD(1),
	/** 私聊 */
	@ClientEnumComment(comment = "私聊")
	PRIVATE(2),
	/** 公会 */
	GUILD(3),
	/** 附近 */
	NEAR(4),
	/** 组队 */
	TEAM(5);

	private int index;
	
	private static final List<ChatType> indexes = IndexedEnumUtil.toIndexes(ChatType.values());
	
	private ChatType(int index){
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
	public static ChatType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
