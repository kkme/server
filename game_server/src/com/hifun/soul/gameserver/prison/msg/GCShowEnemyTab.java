package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示夺俘之敌标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowEnemyTab extends GCMessage{
	
	/** 夺俘之敌列表  */
	private com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] enemies;

	public GCShowEnemyTab (){
	}
	
	public GCShowEnemyTab (
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] enemies ){
			this.enemies = enemies;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		enemies = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo objenemies = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo();
			enemies[i] = objenemies;
					objenemies.setHumanId(readLong());
							objenemies.setHumanName(readString());
							objenemies.setHumanLevel(readInteger());
							objenemies.setIdentityType(readInteger());
							objenemies.setLegionId(readLong());
							objenemies.setLegionName(readString());
							objenemies.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(enemies.length);
	for(int i=0; i<enemies.length; i++){
	com.hifun.soul.gameserver.prison.msg.PrisonerInfo objenemies = enemies[i];
				writeLong(objenemies.getHumanId());
				writeString(objenemies.getHumanName());
				writeInteger(objenemies.getHumanLevel());
				writeInteger(objenemies.getIdentityType());
				writeLong(objenemies.getLegionId());
				writeString(objenemies.getLegionName());
				writeInteger(objenemies.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ENEMY_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ENEMY_TAB";
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] getEnemies(){
		return enemies;
	}

	public void setEnemies(com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] enemies){
		this.enemies = enemies;
	}	
}