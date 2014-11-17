package com.hifun.soul.gameserver.rechargetx.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 充值
 * 
 * @author SevenSoul
 */
@Component
public class CGRecharge extends CGMessage{
	
	/** 充值档位 */
	private int id;
	/** 平台发给客户端的相关的信息 */
	private String jsonInfo;
	
	public CGRecharge (){
	}
	
	public CGRecharge (
			int id,
			String jsonInfo ){
			this.id = id;
			this.jsonInfo = jsonInfo;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		jsonInfo = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		writeString(jsonInfo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RECHARGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECHARGE";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	public String getJsonInfo(){
		return jsonInfo;
	}
		
	public void setJsonInfo(String jsonInfo){
		this.jsonInfo = jsonInfo;
	}

	@Override
	public void execute() {
	}
}