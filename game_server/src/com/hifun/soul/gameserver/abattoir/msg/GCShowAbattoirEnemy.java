package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示仇人列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowAbattoirEnemy extends GCMessage{
	
	/** 角斗场仇人列表  */
	private com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo[] enemies;

	public GCShowAbattoirEnemy (){
	}
	
	public GCShowAbattoirEnemy (
			com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo[] enemies ){
			this.enemies = enemies;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		enemies = new com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo objenemies = new com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo();
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
	com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo objenemies = enemies[i];
				writeLong(objenemies.getEnemyId());
				writeString(objenemies.getEnemyName());
				writeInteger(objenemies.getEnemyLevel());
				writeInteger(objenemies.getLootTimeInterval());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ABATTOIR_ENEMY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ABATTOIR_ENEMY";
	}

	public com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo[] getEnemies(){
		return enemies;
	}

	public void setEnemies(com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo[] enemies){
		this.enemies = enemies;
	}	
}