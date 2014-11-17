package com.hifun.soul.common.constants;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;

/**
 * 系统提示消息类型
 * 
 * @author magicstone
 *
 */
@AutoCreateClientEnumType
public enum SystemMessageType {
	@ClientEnumComment(comment="普通")
	GENERIC((short)1),
	@ClientEnumComment(comment="重要")
	IMPORTANT((short)2),
	@ClientEnumComment(comment="错误")
	ERROR((short)3),
	@ClientEnumComment(comment="弹出框")
	BOX((short)4),
	@ClientEnumComment(comment="成功提示")
	SUCCESS((short)5),
	@ClientEnumComment(comment="警告")
	WARNING((short)6),
	@ClientEnumComment(comment="发送到聊天框")
	CHAT((short)7),;
	
	private short index;
	private SystemMessageType(short index){
		this.index = index;
	}
	public short getIndex(){
		return index;
	}
}
