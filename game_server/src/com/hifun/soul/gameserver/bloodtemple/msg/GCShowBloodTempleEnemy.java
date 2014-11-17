package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示仇人列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowBloodTempleEnemy extends GCMessage{
	
	/** 嗜血神殿仇人列表  */
	private com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo[] enemies;

	public GCShowBloodTempleEnemy (){
	}
	
	public GCShowBloodTempleEnemy (
			com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo[] enemies ){
			this.enemies = enemies;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		enemies = new com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo objenemies = new com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo();
			enemies[i] = objenemies;
					objenemies.setEnemyId(readLong());
							objenemies.setEnemyName(readString());
							objenemies.setEnemyLevel(readInteger());
							objenemies.setLootTimeInterval(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(enemies.length);
	for(int i=0; i<enemies.length; i++){
	com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo objenemies = enemies[i];
				writeLong(objenemies.getEnemyId());
				writeString(objenemies.getEnemyName());
				writeInteger(objenemies.getEnemyLevel());
				writeInteger(objenemies.getLootTimeInterval());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_BLOOD_TEMPLE_ENEMY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_BLOOD_TEMPLE_ENEMY";
	}

	public com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo[] getEnemies(){
		return enemies;
	}

	public void setEnemies(com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo[] enemies){
		this.enemies = enemies;
	}	
}