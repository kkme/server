package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求显示VIP详情
 * 
 * @author SevenSoul
 */
@Component
public class CGShowVipInfo extends CGMessage{
	
	/** vip等级 */
	private int level;
	
	public CGShowVipInfo (){
	}
	
	public CGShowVipInfo (
			int level ){
			this.level = level;
	}
	
	@Override
	protected boolean readImpl() {
		level = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(level);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_VIP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_VIP_INFO";
	}

	public int getLevel(){
		return level;
	}
		
	public void setLevel(int level){
		this.level = level;
	}

	@Override
	public void execute() {
	}
}