package com.hifun.soul.gameserver.sprite;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 出拳手势类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum FingerType implements IndexedEnum {
	/** 剪刀 */
	@ClientEnumComment(comment = "剪刀 ")
	JIANDAO(1, 3, 2),
	/** 石头 */
	@ClientEnumComment(comment = "剪刀 ")
	STONE(2, 1, 3),
	/** 布 */
	@ClientEnumComment(comment = "布 ")
	BU(3, 2, 1), ;
	private int type;
	private int smallType;
	private int bigType;
	private static Map<Integer, FingerType> types = new HashMap<Integer, FingerType>();;

	static {
		for (FingerType each : FingerType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	FingerType(int type, int preOperation, int succeedOperation) {
		this.type = type;
		this.smallType = preOperation;
		this.bigType = succeedOperation;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static FingerType typeOf(int guessType) {
		return types.get(guessType);
	}

	/**
	 * 给出猜拳的结果;
	 * 
	 * @param fingerType
	 * @return
	 */
	public FingerGuessResultType giveResult(FingerType fingerType) {
		if (this == fingerType) {
			return FingerGuessResultType.DRAW;
		}
		if (FingerType.typeOf(this.smallType) == fingerType) {
			return FingerGuessResultType.WIN;
		}
		return FingerGuessResultType.LOSE;
	}

	/**
	 * 获取比当前类型大的类型;
	 * 
	 * @return
	 */
	public FingerType getBigType() {
		return typeOf(this.bigType);
	}
}
