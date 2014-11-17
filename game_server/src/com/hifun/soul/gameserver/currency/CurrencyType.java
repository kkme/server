package com.hifun.soul.gameserver.currency;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

/**
 * 货币类型
 * 
 * @author magicstone
 * 
 */
@AutoCreateClientEnumType
public enum CurrencyType implements IndexedEnum {

	/** 金币 */
	@ClientEnumComment(comment = "金币")
	COIN((short) 1, "金币"),
	/** 魔晶 */
	@ClientEnumComment(comment = "魔晶")
	CRYSTAL((short) 2, "魔晶石");

	private short type;
	private String desc;

	private static final List<CurrencyType> indexes = IndexedEnumUtil
			.toIndexes(CurrencyType.values());

	CurrencyType(short type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public short getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static CurrencyType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

	@Override
	public int getIndex() {
		return type;
	}
}
