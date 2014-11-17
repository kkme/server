package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回玩家角色列表
 *
 * @author SevenSoul
 */
@Component
public class GCCharList extends GCMessage{
	
	/** 角色列表  */
	private com.hifun.soul.common.auth.LoginChar[] charList;

	public GCCharList (){
	}
	
	public GCCharList (
			com.hifun.soul.common.auth.LoginChar[] charList ){
			this.charList = charList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		charList = new com.hifun.soul.common.auth.LoginChar[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.common.auth.LoginChar objcharList = new com.hifun.soul.common.auth.LoginChar();
			charList[i] = objcharList;
					objcharList.setHumanGuid(readLong());
							objcharList.setName(readString());
							objcharList.setOccupation(readInteger());
							objcharList.setLevel(readInteger());
							objcharList.setEnergy(readLong());
							objcharList.setHomeLevel(readInteger());
							objcharList.setCoins(readLong());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(charList.length);
	for(int i=0; i<charList.length; i++){
	com.hifun.soul.common.auth.LoginChar objcharList = charList[i];
				writeLong(objcharList.getHumanGuid());
				writeString(objcharList.getName());
				writeInteger(objcharList.getOccupation());
				writeInteger(objcharList.getLevel());
				writeLong(objcharList.getEnergy());
				writeInteger(objcharList.getHomeLevel());
				writeLong(objcharList.getCoins());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHAR_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHAR_LIST";
	}

	public com.hifun.soul.common.auth.LoginChar[] getCharList(){
		return charList;
	}

	public void setCharList(com.hifun.soul.common.auth.LoginChar[] charList){
		this.charList = charList;
	}	
}