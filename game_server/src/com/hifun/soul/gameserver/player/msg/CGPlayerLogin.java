package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 用户登录
 * 
 * @author SevenSoul
 */
@Component
public class CGPlayerLogin extends CGMessage{
	
	/** 玩家的账户 */
	private String account;
	/** 玩家的密码  */
	private String password;
	
	public CGPlayerLogin (){
	}
	
	public CGPlayerLogin (
			String account,
			String password ){
			this.account = account;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		account = readString();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(account);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_LOGIN";
	}

	public String getAccount(){
		return account;
	}
		
	public void setAccount(String account){
		this.account = account;
	}

	public String getPassword(){
		return password;
	}
		
	public void setPassword(String password){
		this.password = password;
	}

	@Override
	public void execute() {
	}
}