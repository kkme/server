package com.hifun.soul.gameserver.common.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;

/**
 * 系统提示消息
 * 
 * @author crazyjohn
 */
@Component
public class GCSystemMessage extends GCMessage {

	/** 消息内容 */
	private String content;
	/** 消息显示类型 */
	private short showType;

	public GCSystemMessage() {
	}

	public GCSystemMessage(String content, short showType) {
		this.content = content;
		this.showType = showType;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		showType = readShort();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeString(content);
		writeShort(showType);
		return true;
	}

	@Override
	public short getType() {
		return MessageType.GC_SYSTEM_MESSAGE;
	}

	@Override
	public String getTypeName() {
		return "GC_SYSTEM_MESSAGE";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public short getShowType() {
		return showType;
	}

	public void setShowType(short showType) {
		this.showType = showType;
	}
}