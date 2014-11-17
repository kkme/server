package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回VIP详情
 *
 * @author SevenSoul
 */
@Component
public class GCShowVipInfo extends GCMessage{
	
	/** 特权描述 */
	private String desc;

	public GCShowVipInfo (){
	}
	
	public GCShowVipInfo (
			String desc ){
			this.desc = desc;
	}

	@Override
	protected boolean readImpl() {
		desc = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(desc);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_VIP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_VIP_INFO";
	}

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}
}