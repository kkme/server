package com.hifun.soul.gameserver.refine;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum AttackStateType implements IndexedEnum {
	
	/** 不能攻打 */
	@ClientEnumComment(comment="不能攻打")
	CANNOT_ATTACK(1),
	/** 可以攻打 */
	@ClientEnumComment(comment="可以攻打")
	CAN_ATTACK(2),
	/** 已经攻打过 */
	@ClientEnumComment(comment="已经攻打过")
	ATTACKED(3),
	;
	
	private AttackStateType(int index) {
		this.index = index;
	}
	
	private static final List<AttackStateType> indexes = IndexedEnumUtil.toIndexes(AttackStateType.values());
	
	private int index;
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static AttackStateType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
