package com.hifun.soul.gameserver.skill.buff;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.skill.msg.GCClearBuff;

/**
 * 清除buff类型;<br>
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum ClearBuffType implements IndexedEnum {
	/** 清除所有buff + debuff */
	@ClientEnumComment(comment="清除所有buff + debuff")
	CLEAR_ALL(1),
	/** 清除所有buff */
	@ClientEnumComment(comment="清除所有buff")
	CLEAR_BUFF(2),
	/** 清除所有debuff */
	@ClientEnumComment(comment="清除所有debuff")
	CLEAR_DEBUFF(3);
	private int type;
	private static Map<Integer, ClearBuffType> types = new HashMap<Integer, ClearBuffType>();

	static {
		for (ClearBuffType type : ClearBuffType.values()) {
			types.put(type.getIndex(), type);
		}
	}

	ClearBuffType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static ClearBuffType typeOf(int clearType) {
		return types.get(clearType);
	}

	public void doClearAction(IBattleUnit target) {
		// 清除玩家身上所有buff + debuff, 并且通知client
		target.getBattleContext().getBuffManager().removeBuffByType(this);
		GCClearBuff clearMsg = new GCClearBuff();
		clearMsg.setTargetGuid(target.getUnitGuid());
		clearMsg.setClearType(type);
		target.getBattleContext().getBattle().broadcastToBattleUnits(clearMsg);
	}

}
