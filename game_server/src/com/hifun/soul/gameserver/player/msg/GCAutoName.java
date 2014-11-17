package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回自动生成的角色名字
 *
 * @author SevenSoul
 */
@Component
public class GCAutoName extends GCMessage{
	
	/** 角色名字  */
	private String roleName;

	public GCAutoName (){
	}
	
	public GCAutoName (
			String roleName ){
			this.roleName = roleName;
	}

	@Override
	protected boolean readImpl() {
		roleName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_AUTO_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_AUTO_NAME";
	}

	public String getRoleName(){
		return roleName;
	}
		
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
}