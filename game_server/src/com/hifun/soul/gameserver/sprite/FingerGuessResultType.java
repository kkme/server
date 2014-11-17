package com.hifun.soul.gameserver.sprite;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 单回合猜拳结果类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum FingerGuessResultType implements IndexedEnum {
	/** 胜利 */
	@ClientEnumComment(comment = "胜利 ")
	WIN(1),
	/** 平局 */
	@ClientEnumComment(comment = "平局")
	DRAW(2),
	/** 失败 */
	@ClientEnumComment(comment = "失败 ")
	LOSE(3), ;
	private int type;
	private static Map<Integer, FingerGuessResultType> types = new HashMap<Integer, FingerGuessResultType>();;

	static {
		for (FingerGuessResultType each : FingerGuessResultType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	FingerGuessResultType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static FingerGuessResultType typeOf(int guessType) {
		return types.get(guessType);
	}
}
