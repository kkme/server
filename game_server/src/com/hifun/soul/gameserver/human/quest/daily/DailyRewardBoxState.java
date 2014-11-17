package com.hifun.soul.gameserver.human.quest.daily;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 日常奖励宝箱状态;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum DailyRewardBoxState implements IndexedEnum {
	/** 宝箱打开 */
	@ClientEnumComment(comment="宝箱打开")
	OPEN(0),
	/** 宝箱关闭 */
	@ClientEnumComment(comment="宝箱关闭")
	CLOSED(1);
	private int type;
	private static Map<Integer, DailyRewardBoxState> states = new HashMap<Integer, DailyRewardBoxState>();
	
	static {
		for (DailyRewardBoxState state : DailyRewardBoxState.values()) {
			states.put(state.getIndex(), state);
		}
	}

	DailyRewardBoxState(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static DailyRewardBoxState typeOf(int state) {
		return states.get(state);
	}

}
