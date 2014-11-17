package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示
 * 
 * @author SevenSoul
 */
@Component
public class CGShowSomething extends CGMessage{
	
	/** 物品归属人id */
	private long humanGuid;
	/** 链接类型(角色：1,通用物品：2,含特殊属性的物品：3) */
	private int linkType;
	/** key(key的意义跟lingType不同而不同，为1时，可以不传;为2时传物品的itemId;为3时传物品的uuid) */
	private String key;
	
	public CGShowSomething (){
	}
	
	public CGShowSomething (
			long humanGuid,
			int linkType,
			String key ){
			this.humanGuid = humanGuid;
			this.linkType = linkType;
			this.key = key;
	}
	
	@Override
	protected boolean readImpl() {
		humanGuid = readLong();
		linkType = readInteger();
		key = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanGuid);
		writeInteger(linkType);
		writeString(key);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_SOMETHING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SOMETHING";
	}

	public long getHumanGuid(){
		return humanGuid;
	}
		
	public void setHumanGuid(long humanGuid){
		this.humanGuid = humanGuid;
	}

	public int getLinkType(){
		return linkType;
	}
		
	public void setLinkType(int linkType){
		this.linkType = linkType;
	}

	public String getKey(){
		return key;
	}
		
	public void setKey(String key){
		this.key = key;
	}

	@Override
	public void execute() {
	}
}