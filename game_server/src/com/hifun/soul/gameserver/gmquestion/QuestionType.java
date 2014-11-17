package com.hifun.soul.gameserver.gmquestion;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum QuestionType  implements IndexedEnum {
	/** bug */
	@ClientEnumComment(comment = "BUG")
	BUG(1),
	/** 投诉 */
	@ClientEnumComment(comment = "投诉")
	COMPLAIN(2),
	/** 建议 */
	@ClientEnumComment(comment = "建议")
	SUGGEST(3),
	/** 其他 */
	@ClientEnumComment(comment = "其他")
	OTHERS(4);

	private int index;
	
	private static final List<QuestionType> indexes = IndexedEnumUtil.toIndexes(QuestionType.values());
	
	private QuestionType(int index){
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
	public static QuestionType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
