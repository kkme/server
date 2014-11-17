package com.hifun.soul.gameserver.role;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;

/**
 * 性别
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum Gender{
	@ClientEnumComment(comment="男性")
	MALE((short)1),
	@ClientEnumComment(comment="女性")
	FEMALE((short)2),
	@ClientEnumComment(comment="未知性别")
	UNKNOWN((short)3);

	private short index;
	private Gender(short index){
		this.index = index;
	}
	public short getIndex() {
		return index;
	}

}
