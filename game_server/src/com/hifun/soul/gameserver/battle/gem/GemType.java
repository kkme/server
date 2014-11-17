package com.hifun.soul.gameserver.battle.gem;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.battle.gem.function.AllTypeFunctionGem;
import com.hifun.soul.gameserver.battle.gem.function.AttackFunctionGems;
import com.hifun.soul.gameserver.battle.gem.function.IFunctionGems;

/**
 * 宝石类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum GemType implements IndexedEnum {
	/** 红宝石 */
	@ClientEnumComment(comment="红宝石")
	RED(0, "红"),
	/** 黄宝石 */
	@ClientEnumComment(comment="黄宝石")
	YELLOW(1, "黄"),
	/** 蓝宝石 */
	@ClientEnumComment(comment="蓝宝石")
	BLUE(2, "蓝"),
	/** 绿宝石 */
	@ClientEnumComment(comment="绿宝石 ")
	GREEN(3, "绿"),
	/** 紫宝石 */
	@ClientEnumComment(comment="紫宝石")
	PURPLE(4, "紫"),
	/** 全能量 */
	@ClientEnumComment(comment="全能量, 白色")
	ALL(5, "白") {
		@Override
		public IFunctionGems createFunction() {
			return new AllTypeFunctionGem();
		}
	},
	/** 强袭 */
	@ClientEnumComment(comment="强袭, 黑色")
	ATTACK(6, "黑") {
		@Override
		public IFunctionGems createFunction() {
			return new AttackFunctionGems();
		}
	},;

	private int type;
	private String desc;
	private static Map<Integer, GemType> types = new HashMap<Integer, GemType>();

	static {
		for (GemType each : GemType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	GemType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public String getDesc() {
		return this.desc;
	}

	public static GemType typeOf(int color) {
		return types.get(color);
	}

	public IFunctionGems createFunction() {
		return null;
	}
}
